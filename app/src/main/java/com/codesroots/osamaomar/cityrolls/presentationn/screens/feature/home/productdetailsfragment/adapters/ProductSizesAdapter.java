package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.Productsize;
import com.codesroots.osamaomar.cityrolls.entities.StoreSetting;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;

import java.util.List;

public class  ProductSizesAdapter extends RecyclerView.Adapter<ProductSizesAdapter.ViewHolder> {

    private Context context;
    ProductDetailsFragment fragment;
    List<Productsize> productsizes;
    public int mSelectedItem = 0;
    public float priceafteroffer = 0;


    private int offer;
    private StoreSetting setting;

    public ProductSizesAdapter(Context mcontext, List<Productsize> sizes,
                               ProductDetailsFragment detailsFragment, int offer1) {
        context = mcontext;
        productsizes = sizes;
        fragment = detailsFragment;
        offer = offer1;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.size_item_adapter, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.text.setText(productsizes.get(position).getSize());
        //fragment.amount.setText(context.getText(R.string.remendier) + " " + String.valueOf(productsizes.get(mSelectedItem).getAmount()) + " " + context.getText(R.string.num));

        if (position == mSelectedItem)
            holder.text.setBackgroundResource(R.drawable.linear_background_for_selected_size);
        else
            holder.text.setBackgroundResource(R.drawable.linear_background_for_size);
    }

    @Override
    public int getItemCount() {
        return productsizes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private TextView text;

        ViewHolder(View view) {
            super(view);
            mView = view;
            text = mView.findViewById(R.id.size);

            @SuppressLint("SetTextI18n") View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                notifyDataSetChanged();
                if (offer > 0) {
                    //hasOffer = true;
                    float offerPercentage = Float.valueOf(productsizes.get(mSelectedItem).getCurrent_price()) * offer / 100;
                    priceafteroffer = Float.valueOf(productsizes.get(mSelectedItem).getCurrent_price()) - offerPercentage;
                    if (PreferenceHelper.getCurrency()!=null)
                        fragment.price.setText(String.valueOf(priceafteroffer*PreferenceHelper.getCurrencyValue()) + context.getText(R.string.coin));
                    else
                    fragment.price.setText(String.valueOf(priceafteroffer) + context.getText(R.string.coin));

                    if (offerPercentage < fragment.setting.getData().get(0).getShippingPrice()) {
                        fragment.charege.setText(R.string.charge_rules);
                        fragment.freecharg = false;  //// to set not free charge
                    }
                    else
                        fragment.charege.setText(R.string.free_charge);
                } else {
                    fragment.price.setText(productsizes.get(mSelectedItem).getCurrent_price() + " " + context.getText(R.string.coin));
                    if (Float.valueOf(productsizes.get(mSelectedItem).getCurrent_price()) < fragment.setting.getData().get(0).getShippingPrice()) {
                        fragment.charege.setText(R.string.charge_rules);
                        fragment.freecharg = false;
                    }
                    else
                        fragment.charege.setText(R.string.free_charge);
                }

                fragment.amount.setText(context.getText(R.string.remendier) + " " + String.valueOf(productsizes.get(mSelectedItem).getAmount()) +
                        " " + context.getText(R.string.num));
            };
            itemView.setOnClickListener(clickListener);
        }
    }
}
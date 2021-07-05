package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.offerfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.offers;
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.offerfragment.OffersFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsViewModel;

import java.util.List;

import static com.codesroots.osamaomar.cityrolls.entities.names.PRODUCT_ID;

public class AllOffersAdapter extends RecyclerView.Adapter<AllOffersAdapter.ViewHolder>  {

    private Context context;
    private List<offers.DataBean> offersData;
    private float priceafteroffer = 0;
    public ProductDetailsViewModel mViewModel;
    boolean productfav;
public  OffersFragment offersFragments;
    public AllOffersAdapter(Context mcontext, List<offers.DataBean> offers, ProductDetailsViewModel detailaViewModel, OffersFragment offersFragment) {
        context = mcontext;
        offersData = offers;
        mViewModel = detailaViewModel;
        offersFragments = offersFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizental_product_item_adapter, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

            Glide.with(context.getApplicationContext())
                    .load(offersData.get(position).getProduct().getPhoto()).placeholder(R.drawable.product).dontAnimate()
                    .into(holder.Image);

            holder.name.setText(offersData.get(position).getProduct().getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.desc.setText(Html.fromHtml(offersData.get(position).getProduct().getDescription(),Html.FROM_HTML_MODE_COMPACT));
        }


        priceafteroffer =Float.valueOf(offersData.get(position).getProduct().getPrice())- Float.valueOf(offersData.get(position).getProduct().getPrice())*
               Integer.valueOf(offersData.get(position).getPercentage())/100;

//        holder.discount.setText(context.getText(R.string.disscount)+" "+offersData.get(position).getPercentage()+" "+"%");


            if (PreferenceHelper.getCurrencyValue()>0)
                holder.price.setText(String.valueOf(String.valueOf(priceafteroffer *PreferenceHelper.getCurrencyValue()+" "+
                        PreferenceHelper.getCurrency())));

            else
                holder.price.setText(String.valueOf(offersData.get(position).getProduct().getPrice())+" "+
                        PreferenceHelper.getCurrency());


//            if (PreferenceHelper.getCurrencyValue()>0)
//                holder.price.setText(String.valueOf(Float.valueOf(offersData.get(position).getProduct().getProductsizes().get(0).getCurrent_price())
//                        *PreferenceHelper.getCurrencyValue()+" "+PreferenceHelper.getCurrency()));
//            else
//                holder.oldprice.setText(offersData.get(position).getProduct().getProductsizes().get(0).getCurrent_price()+" "+
//                        PreferenceHelper.getCurrency());
      //  }

//        holder.oldprice.setText(offersData.get(position).getProduct().getProductsizes().get(0).getCurrent_price()+" "+
             //   PreferenceHelper.getCurrency());
        Fragment fragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle() ;
        bundle.putInt(PRODUCT_ID,offersData.get(position).getProduct_id());
        fragment.setArguments(bundle);
        holder.mView.setOnClickListener(v -> ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().
                replace(R.id.mainfram,fragment)
                .addToBackStack(null).commit());


        holder.favortie.setOnClickListener(v ->
        {
            holder.favortie.setEnabled(false);
            if (!productfav) {
                mViewModel.AddToFav( offersData.get(position).getProduct().getId());
                productfav = true;
            } else {
                mViewModel.DeleteFav(PreferenceHelper.getUserId(), offersData.get(position).getProduct().getId());
                productfav = false;
            }
        });

        mViewModel.addToFavMutableLiveData.observe(offersFragments, aBoolean ->
        {
            holder.favortie.setEnabled(true);
            holder.favortie.setImageResource(R.drawable.favoried);
            productfav = true;

        });

        mViewModel.deleteToFavMutableLiveData.observe(offersFragments, aBoolean ->
        {
            holder.favortie.setEnabled(true);
            holder.favortie.setImageResource(R.drawable.like);
        });

        mViewModel.throwablefav.observe(offersFragments, throwable ->
        {
            holder.favortie.setEnabled(false);
            Toast.makeText(context,context.getText(R.string.error_tryagani), Toast.LENGTH_SHORT).show();
        });

        holder.addcart.setOnClickListener(v -> {
            if (PreferenceHelper.getUserId() > 0) {
                if (PreferenceHelper.retriveCartItemsValue() != null) {
                    if (!PreferenceHelper.retriveCartItemsValue().contains(offersData.get(position).getProduct().getId())) {
                        PreferenceHelper.addItemtoCart(offersData.get(position).getProduct().getId());

                        ((AddorRemoveCallbacks) context).onAddProduct();
                        Toast.makeText(context, context.getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, context.getText(R.string.aleady_exists), Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceHelper.addItemtoCart(offersData.get(position).getProduct().getId());

                    ((AddorRemoveCallbacks) context).onAddProduct();
                    Toast.makeText(context,context.getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(context, context.getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {

        return offersData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image, favortie;
        private TextView name,rateCount,amount,addcart,price,desc,discount;
        private RatingBar ratingBar;

        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            amount = mView.findViewById(R.id.quentity);
            rateCount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            desc = mView.findViewById(R.id.item_desc);

            discount = mView.findViewById(R.id.discount);
          //  oldprice = mView.findViewById(R.id.old_price);
            favortie = mView.findViewById(R.id.favorite);
            addcart = mView.findViewById(R.id.add_to_cart);

//            oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
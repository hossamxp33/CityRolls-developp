package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.cartfragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.CartItems;
import com.codesroots.osamaomar.cityrolls.entities.OrderModel;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.cartfragment.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {

    private Context context;
    private    List<CartItems.DataBean> dataBeans;
    private CartFragment cartFragment;
    public List<OrderModel.productSize> products =new ArrayList<>();
    ArrayList<String> colordata =    PreferenceHelper.GETColorstoCart();

    public CartAdapter(Context mcontext, List<CartItems.DataBean> dataBeans1,CartFragment cartFragment1) {
        context = mcontext;
        dataBeans = dataBeans1;
        cartFragment = cartFragment1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        try {

           // Log.i("COLORS", "onBindViewHolder: "+ colordata.get(position));
            holder.name.setText(dataBeans.get(position).getName());
            products.add(new OrderModel.productSize(dataBeans.get(position).getId()));

           // holder.amount.setText(context.getText(R.string.remendier) + " " +String.valueOf(dataBeans.get(position).getAmount()) + " " + context.getText(R.string.num));
//            if (dataBeans.get(position).getProduct().getOffers() != null)
//            {
//                if (PreferenceHelper.getCurrencyValue()>0)
//                    holder.price.setText(getPriceAfterDiscount(dataBeans.get(position).getProduct().getOffers().get(0).getPercentage(),
//                            (float) dataBeans.get(position).getCurrent_price())*PreferenceHelper.getCurrencyValue()+" "+PreferenceHelper.getCurrency());
//                else
//                    holder.price.setText(getPriceAfterDiscount(dataBeans.get(position).getProduct().getOffers().get(0).getPercentage(),
//                            (float) dataBeans.get(position).getCurrent_price())  + " " + context.getText(R.string.realcoin));
//
//                //  holder.price.setText(dataBeans.get(position).getStart_price() + " " + context.getText(R.string.realcoin));
//               products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString())*
//                        getPriceAfterDiscount(dataBeans.get(position).getProduct().getOffers().get(0).getPercentage(),
//                                (float) dataBeans.get(position).getCurrent_price())));
//
//
              //  products.get(position).setNotice("?????? ???????? ?????????? ??????"+dataBeans.get(position).getProduct().getOffers().get(0).getId());
//            }
//            else {
                products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) *
                        Float.valueOf(dataBeans.get(position).getCurrent_price())));

                if (PreferenceHelper.getCurrencyValue()>0)
                    holder.price.setText(Float.valueOf(dataBeans.get(position).getCurrent_price())+" "+context.getText(R.string.coin));

                else
                    holder.price.setText(dataBeans.get(position).getCurrent_price() + " " + context.getText(R.string.coin));
        //    }

            if (dataBeans.get(position).getTotal_rating() != null)
                if (dataBeans.get(position).getTotal_rating().size() > 0) {
                    holder.ratingBar.setRating(dataBeans.get(position).getTotal_rating().get(0).getStars() /
                            dataBeans.get(position).getTotal_rating().get(0).getCount());
                    holder.rateCount.setText("(" + dataBeans.get(position).getTotal_rating().get(0).getCount() + ")");
                }
                    Glide.with(context.getApplicationContext())
                            .load(dataBeans.get(position).getPhoto()).placeholder(R.drawable.product).dontAnimate()
                            .into(holder.Image);
          //  }
        }

        catch (Exception e)
        {
            Log.d("exception",e.getMessage());
        }

        holder.delete_item.setOnClickListener(v -> {

            PreferenceHelper.removeItemFromCart(dataBeans.get(position).getId());

           // dataBeans.remove(position);

            cartFragment.onRemoveProduct(position, Integer.parseInt(holder.products_count.getText().toString()),1f);

           //  notifyItemRemoved(position);
        });

        holder.quintityPlus.setOnClickListener(v ->
        {
          //  if (Integer.valueOf(holder.products_count.getText().toString())+1 <= dataBeans.get(position).getAmount()) {
                holder.products_count.setText(String.valueOf(Integer.valueOf(holder.products_count.getText().toString()) + 1));
                products.get(position).setAmount( Integer.valueOf(holder.products_count.getText().toString()));
//                if (dataBeans.get(position).getProduct().getOffers().size()>0)
//                {
//                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString())*
//                            getPriceAfterDiscount(dataBeans.get(position).getProduct().getOffers().get(0).getPercentage(), (float) dataBeans.get(position).getCurrent_price())));
//                    products.get(position).setNotice("?????? ???????? ?????????? ?????? "+dataBeans.get(position).getProduct().getOffers().get(0).getId());
//                }
             //   else
                    products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString())*
                            Float.valueOf(dataBeans.get(position).getCurrent_price())));
                cartFragment.onPlusProduct(position);

          //  }
         //   else
             //   Toast.makeText(context,context.getText(R.string.requestnotallow),Toast.LENGTH_SHORT).show();
        });

        holder.quintityMinus.setOnClickListener(v -> {
            int newValue = Integer.valueOf(holder.products_count.getText().toString())-1;
            if (newValue >0) {
                holder.products_count.setText(String.valueOf(newValue));
                products.get(position).setAmount( Integer.valueOf(holder.products_count.getText().toString()));
                try {
//                    if (dataBeans.get(position).getProduct().getOffers().size()>0)
//                    {
//                        products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString())*
//                                getPriceAfterDiscount(dataBeans.get(position).getProduct().getOffers().get(0).getPercentage(), (float) dataBeans.get(position).getCurrent_price())));
//                        products.get(position).setNotice("?????? ???????? ?????????? ?????? "+dataBeans.get(position).getProduct().getOffers().get(0).getId());
//                    }
//                    else
                        products.get(position).setTotal(String.valueOf(Integer.valueOf(holder.products_count.getText().toString())*
                                Float.valueOf(dataBeans.get(position).getCurrent_price())));
                    cartFragment.onMinusProduct(position);
                }catch (Exception e){
//
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    private float getPriceAfterDiscount(String Percentage ,Float oldPrice)
    {
        float offer = oldPrice *Integer.valueOf(Percentage)/100;
        return oldPrice -offer;
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private ImageView Image,favorite,delete_item,quintityMinus,quintityPlus;
        private TextView name,rateCount,amount,price;
        private RatingBar ratingBar;
        private EditText products_count;
        ViewHolder(View view) {
            super(view);
            mView = view;
            Image = mView.findViewById(R.id.item_img);
            name = mView.findViewById(R.id.item_name);
            price = mView.findViewById(R.id.item_price);
            amount = mView.findViewById(R.id.quentity);
            rateCount = mView.findViewById(R.id.rate_count);
            ratingBar = mView.findViewById(R.id.rates);
            favorite = mView.findViewById(R.id.favorite);
            delete_item = mView.findViewById(R.id.delete_item);
            quintityMinus = mView.findViewById(R.id.quintityMinus);
            quintityPlus = mView.findViewById(R.id.quintityPlus);
            products_count = mView.findViewById(R.id.quintity_value);
        }
    }
}
package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.AddToFavModel;
import com.codesroots.osamaomar.cityrolls.entities.Items;
import com.codesroots.osamaomar.cityrolls.entities.ProductDetails;
import com.codesroots.osamaomar.cityrolls.entities.StoreSetting;
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.helper.ResourceUtil;
import com.codesroots.osamaomar.cityrolls.helper.kotlinusercase;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.adapters.ProductSizesAdapter;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.adapters.RelatedProductsAdapter;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.adapters.SliderProductDetailsAdapter;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.login.LoginFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.rate.RateActivity;
import com.viewpagerindicator.CirclePageIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.codesroots.osamaomar.cityrolls.entities.names.PRODUCT_ID;


public class ProductDetailsFragment extends Fragment {
    CirclePageIndicator indicator;
    ConstraintLayout product_view;
    ViewPager slider;
    Spinner spinner ,color_spinner;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ProductDetailsViewModel mViewModel;
    RecyclerView images_rec, sizes_rec,recommended_products;
    private int productid;
    private int sizeid,colorid;
    RelativeLayout textscroll;
    FrameLayout loading;
    public TextView product_name, description, price, ratecount, amount, addtocart, charege, oldprice;
    RatingBar ratingBar;
    public ImageView item_img,item_photo;
    int userid = PreferenceHelper.getUserId(), favid = 0;
    ProductSizesAdapter productSizesAdapter;
//    ProductImagesAdapter productImagesAdapter;
    ArrayList<String> images = new ArrayList<>();
    ImageView addToFav;
    Button share , hide_desc , show_desc;
    boolean productfav;
    public float priceafteroffer = 0;
    AddToFavModel products ;

    Items productdetails;
    public StoreSetting setting;
    public boolean freecharg = false;
    private String imagurl = "";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        //((MainActivity) getActivity()).head_title.setText(getText(R.string.product_details));
        ((MainActivity) getActivity()).logo.setVisibility(View.VISIBLE);
        productid = getArguments().getInt(PRODUCT_ID, 0);

        findViewsFromXml(view);

        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ProductDetailsViewModel.class);
        mViewModel.productDetailsMutableLiveData.observe(this,this::setDatainViews);
        mViewModel.productDetailsizeMutableLiveData.observe(this,this::setDataToViews);

        if (ResourceUtil.getCurrentLanguage(getActivity()).matches("en"))
            description.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
        description.setMovementMethod(new ScrollingMovementMethod());
        mViewModel.throwableMutableLiveData.observe(this, throwable -> Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show());
        share.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        });
userid = PreferenceHelper.getUserId();

        mViewModel.getData();

//



        mViewModel.productDetailsMutableLiveData.observe(this, productDetails ->
        {
            loading.setVisibility(View.GONE);
            if (productDetails.getProductdetails().getId() != 0) {
                productdetails = productDetails.getProductdetails();
                if (productDetails.getProductdetails() != null)
                    setDataToViews(productDetails.getProductdetails());
            } else
                Toast.makeText(getActivity(), getActivity().getText(R.string.error_in_data), Toast.LENGTH_SHORT).show();
        });

        addtocart.setOnClickListener(v -> {
            if (userid > 0) {
                if (PreferenceHelper.retriveCartItemsValue() != null) {
                    if (!PreferenceHelper.retriveCartItemsValue().contains(String.valueOf(productid))) {
                        PreferenceHelper.addItemtoCart(productid);
                        PreferenceHelper.addColorstoCart(colorid);

                        ((AddorRemoveCallbacks) getActivity()).onAddProduct();
                        Toast.makeText(getActivity(), getActivity().getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), getActivity().getText(R.string.aleady_exists), Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceHelper.addItemtoCart(productid);
                    PreferenceHelper.addColorstoCart(colorid);

                    ((AddorRemoveCallbacks) getActivity()).onAddProduct();
                    Toast.makeText(getActivity(), getActivity().getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), getActivity().getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainfram, new LoginFragment()).addToBackStack(null).commit();
            }
        });




        addToFav.setOnClickListener(v ->
        {
            addToFav.setEnabled(false);
            if (!productfav) {
                mViewModel.AddToFav(productid);
                productfav = true;
            } else {
                mViewModel.DeleteFav(userid, productid);
                productfav = false;
            }
        });

        mViewModel.addToFavMutableLiveData.observe(this, aBoolean ->
        {
            addToFav.setEnabled(true);
            addToFav.setImageResource(R.drawable.favoried);
            productfav = true;

        });

        mViewModel.deleteToFavMutableLiveData.observe(this, aBoolean ->
        {
            addToFav.setEnabled(true);
            addToFav.setImageResource(R.drawable.like);
        });

        mViewModel.throwablefav.observe(this, throwable ->
        {
            addToFav.setEnabled(false);
            Toast.makeText(getActivity(), getText(R.string.error_tryagani), Toast.LENGTH_SHORT).show();
        });

        ratingBar.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(getActivity(), RateActivity.class);
                intent.putExtra(PRODUCT_ID, productid);
                getActivity().startActivity(intent);
            }
            return true;
        });


        return view;
    }

    private void findViewsFromXml(View view) {
        //     images_rec = view.findViewById(R.id.images_rec);
        //  sizes_rec = view.findViewById(R.id.sizes);
        loading = view.findViewById(R.id.progress);
        product_name = view.findViewById(R.id.product_name);
//        textscroll  = view.findViewById(R.id.horizontalScrollView1);
        description = view.findViewById(R.id.description);
        price = view.findViewById(R.id.price);
        item_photo = view.findViewById(R.id.itemphoto);

        ratecount = view.findViewById(R.id.rate_count);
        ratingBar = view.findViewById(R.id.rates);
        item_img = view.findViewById(R.id.item_img);
        addToFav = view.findViewById(R.id.fav);
        //amount = view.findViewById(R.id.amount);
        addtocart = view.findViewById(R.id.addtocart);
        show_desc = view.findViewById(R.id.show);
        hide_desc = view.findViewById(R.id.hide);
        //   charege = view.findViewById(R.id.charge);
        share = view.findViewById(R.id.share_button);
        product_view = view.findViewById(R.id.product_view);
        //   oldprice = view.findViewById(R.id.oldprice);
//        oldprice.setPaintFlags(oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        slider = view.findViewById(R.id.sliderr);
        indicator = view.findViewById(R.id.indicatorProductDetails);
        recommended_products = view.findViewById(R.id.recommended_products);
//        spinner = view.findViewById(R.id.planets_spinner);
       //   color_spinner = view.findViewById(R.id.color_spinner);
    }

    private void setDatainViews(ProductDetails productDetails) {

        try {
            recommended_products.setAdapter(new RelatedProductsAdapter(getActivity(),productDetails.
                    getRelated()));
            if (productDetails.getProductdetails().getItem_photo()  != null) {
                slider.setAdapter(new SliderProductDetailsAdapter(getActivity(), productDetails.getProductdetails().getItem_photo()));
                indicator.setViewPager(slider);
                init(productDetails.getProductdetails().getItem_photo().size());
                product_name.setText(productDetails.getProductdetails().getName());


            } else {
                slider.setVisibility(View.GONE);
                item_photo.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(productdetails.getPhoto())
                        .into(item_photo);

            }
        }catch (Exception e){}

    }

    private void setDataToViews(@NotNull Items productdetailsBean) {
        loading.setVisibility(View.GONE);
        show_desc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Show button");
                show_desc.setVisibility(View.INVISIBLE);
                hide_desc.setVisibility(View.VISIBLE);
                description.setMaxLines(Integer.MAX_VALUE);

            }
        });

        hide_desc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Hide button");
                hide_desc.setVisibility(View.INVISIBLE);
                show_desc.setVisibility(View.VISIBLE);
                description.setMaxLines(5);

            }
        });
        ////////////////// Price /////////////////
        String the_price = String.format("%.2f",Float.valueOf(productdetailsBean.getPrice()
                *
                PreferenceHelper.getCurrencyValue()) );

                price.setText(the_price  + this.getText(R.string.coin));

             sizeid = productdetailsBean.getId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setMovementMethod(new ScrollingMovementMethod());

            description.setText(Html.fromHtml(productdetailsBean.getDescription(),Html.FROM_HTML_MODE_COMPACT));
        }


        try {
            for (int i = 0; i < productdetailsBean.getItem_photo().size(); i++)
                images.add(productdetailsBean.getItem_photo().get(i).getPhoto());
            Glide.with(getActivity()).load(productdetailsBean.getPhoto())
                    .useAnimationPool(true).placeholder(R.drawable.product).into(item_img);
            imagurl = productdetailsBean.getPhoto();

//            productImagesAdapter = new ProductImagesAdapter(getActivity(), productdetailsBean.getProductphotos(), this);
//            images_rec.setAdapter(productImagesAdapter);
//


            if (productdetailsBean.getTotal_rating() != null) {
                if (productdetailsBean.getTotal_rating().size() > 0) {
                    ratecount.setText("(" + productdetailsBean.getTotal_rating().get(0).getCount() + ")");
                    ratingBar.setRating(productdetailsBean.getTotal_rating().get(0).getStars() /
                            productdetailsBean.getTotal_rating().get(0).getCount());
                }
            }

            if (productdetailsBean.getFavourites().size() > 0) {
                addToFav.setImageResource(R.drawable.favoried);
                productfav = true;
                favid = productdetailsBean.getFavourites().get(0).getId();
            } else {
                addToFav.setImageResource(R.drawable.like);
                productfav = false;
            }
        }
        catch (Exception e)
        {}

    }

    public void setImageToImageView(String url) {
        Glide.with(getActivity()).load(url)
                .useAnimationPool(true).placeholder(R.drawable.product).into(item_img);
        imagurl = url;
    }


    private ProductDetailsModelFactory getViewModelFactory() {
        return new ProductDetailsModelFactory(this.getActivity().getApplication(), userid,productid);
    }

    private void init(int size) {
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(4 * density);
        NUM_PAGES = size;
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            slider.setCurrentItem(currentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 5000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int pos) {}
        });
    }

}

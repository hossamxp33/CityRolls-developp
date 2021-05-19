package com.codesroots.osamaomar.cityrolls.presentationn.getuserlocation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.helper.ResourceUtil;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.chating.MessagesChatingActivity;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.CustomView> {

    private Context context;
    //  private List<ChhatList.MyChat> allchats;
    PreferenceHelper preferenceHelper;

    public PlacesAdapter(FragmentActivity activity) {
        this.context = activity;
        preferenceHelper = new PreferenceHelper(context);
    }


    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);

        return new CustomView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, final int position) {

        holder.mView.setOnClickListener(v -> context.startActivity(new Intent(context, MessagesChatingActivity.class)));
        holder.ivCall.setOnClickListener(v ->
                ResourceUtil.callNumber("122545",context));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class CustomView extends RecyclerView.ViewHolder {

        private final View mView;
        private ImageView itemImage, unSeen, ivCall, ivLocation;
        private TextView message, time, tvNameC;
        LinearLayout cardwithimage;
        CardView cardwithmessage;

        private CustomView(View view) {
            super(view);
            mView = view;


            itemImage = mView.findViewById(R.id.image);

        }
    }



}

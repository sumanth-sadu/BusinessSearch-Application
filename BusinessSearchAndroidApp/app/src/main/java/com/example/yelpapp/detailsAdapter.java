package com.example.yelpapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class detailsAdapter extends RecyclerView.Adapter<detailsAdapter.detailsViewHolder>{

    ArrayList<detailsData> arrayList_details;
    private ViewPager2 viewpager2;
    Context context;

    public detailsAdapter(Context context, ArrayList<detailsData> arrayList_details, ViewPager2 viewpager2) {
        this.arrayList_details = arrayList_details;
        this.context = context;
        this.viewpager2 = viewpager2;
    }

//    public detailsAdapter(Context context, ArrayList<detailsData> arrayList_details) {
//        this.arrayList_details = arrayList_details;
//        this.context = context;
//    }

    @NonNull
    @Override
    public detailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false);

        return new detailsAdapter.detailsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull detailsViewHolder holder, int position) {
        holder.setImage(arrayList_details.get(position));

//        holder.address.setText(data.getaddressview());
//        holder.price.setText(data.getpriceview());
//        holder.phone.setText(data.getphoneview());
//        holder.status.setText(data.getstatusview());
//        holder.category.setText(data.getcategoryview());
//        holder.yelplink.setText(data.getyelplinkview());

//        String img_url;
//        img_url = data.getimageview();
//
//        Glide.with(holder.imageslide.getContext()).load(img_url).into(holder.imageslide);
    }

    @Override
    public int getItemCount() {
        return arrayList_details.size();
    }

    public class detailsViewHolder extends RecyclerView.ViewHolder{

//        TextView address;
//        TextView price;
//        TextView phone;
//        TextView status;
//        TextView category;
//        TextView yelplink;
        RoundedImageView imageslide;

        public detailsViewHolder(@NonNull View itemView) {
            super(itemView);

//            address = itemView.findViewById(R.id.address_text);
//            price = itemView.findViewById(R.id.pricerange_text);
//            phone = itemView.findViewById(R.id.phone_text);
//            status = itemView.findViewById(R.id.status_text);
//            category = itemView.findViewById(R.id.category_text);
//            yelplink = itemView.findViewById(R.id.yelplink_text);
            imageslide = itemView.findViewById(R.id.slider);
        }

        void setImage(detailsData data){
            Glide.with(imageslide.getContext()).load(data.getimageview()).into(imageslide);
        }
    }
}

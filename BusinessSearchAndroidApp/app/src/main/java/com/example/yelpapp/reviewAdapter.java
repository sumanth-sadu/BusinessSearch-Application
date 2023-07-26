package com.example.yelpapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.reviewViewHolder>{

    Context context;
    ArrayList<reviewData> arrayList_reviews;

    public reviewAdapter(Context context, ArrayList<reviewData> arrayList_reviews) {
        this.arrayList_reviews = arrayList_reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_design,parent, false);

        return new reviewViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewViewHolder holder, int position) {

        reviewData data = arrayList_reviews.get(position);
        holder.user_name.setTag(position);

        holder.user_name.setText(data.getnameview());
        holder.user_rating.setText("Rating :"+ data.getratingview() +"/5");
        holder.user_review.setText(data.gettextview());
        holder.user_date.setText(data.getdateview());

    }

    @Override
    public int getItemCount() {
        if(arrayList_reviews.size() < 3)
            return arrayList_reviews.size();
        else
            return 3;
    }

    public class reviewViewHolder extends RecyclerView.ViewHolder{

        TextView user_name;
        TextView user_rating;
        TextView user_date;
        TextView user_review;

        public reviewViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.user_name);
            user_rating = itemView.findViewById(R.id.user_rating);
            user_review = itemView.findViewById(R.id.user_review);
            user_date = itemView.findViewById(R.id.user_date);
        }
    }
}

package com.example.yelpapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Data> arrayList;
    Context ctx;
//    private RecyclerViewClickListener listener;

    public RecyclerAdapter(ArrayList<Data> arrayList){
        this.arrayList = arrayList;
//        this.ctx = ctx;
//        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent, false);
        ctx= parent.getContext();

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = arrayList.get(position);
        holder.businessName.setTag(position);
        String img_url;
        img_url = data.getImageview();

        Glide.with(holder.imageUrl.getContext()).load(img_url).into(holder.imageUrl);

        holder.index.setText(data.getTextview0());
        holder.businessName.setText(data.getTextview1());
        holder.distRange.setText(data.getTextview2());
        holder.businessRate.setText(data.getTextview3());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                listener.onClick(arrayList.get(position));
                Log.d("getpos", String.valueOf(holder.getAdapterPosition()));
                Log.d("id", arrayList.get(holder.getAdapterPosition()).getTextview0().toString());
                Log.d("arrlist", String.valueOf(arrayList.get(holder.getAdapterPosition()).getTextview1().toString()));

                Intent i = new Intent(ctx, BusinessInfo.class);
                i.putExtra("business_id", data.getIdview());
                Log.d("starttest","starttest");
                i.putExtra("blat",data.getlat());
                i.putExtra("blng",data.getlng());
                Log.d("starttest2","starttest2");
                ctx.startActivity(i);
                Log.d("starttest3","starttest3");

            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUrl;
        TextView index;
        TextView businessName;
        TextView distRange;
        TextView businessRate;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.textView0);
            imageUrl = itemView.findViewById(R.id.imageView0);
            businessName = itemView.findViewById(R.id.textView1);
            distRange = itemView.findViewById(R.id.textView2);
            businessRate = itemView.findViewById(R.id.textView3);
            relativeLayout = itemView.findViewById(R.id.itemDesign);
        }
    }
//
//    public interface RecyclerViewClickListener{
//        void onClick(Data data);
//    }
}

package com.example.yelpapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<bookingData> bookingDataArrayList;
    private Context context;

    // creating a constructor for our variables.
    public bookingAdapter(ArrayList<bookingData> bookingDataArrayList, Context context) {
        this.bookingDataArrayList = bookingDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public bookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookingAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        bookingData modal = bookingDataArrayList.get(position);
        holder.book_index.setText(modal.getBookingIndex());
        holder.book_name.setText(modal.getBusinessName());
        holder.book_date.setText(modal.getBookingDate());
        holder.book_time.setText(modal.getBookingTime());
        holder.book_mail.setText(modal.getBookingMail());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        Log.d("size", String.valueOf(bookingDataArrayList.size()));
        if(bookingDataArrayList.size()==0){
            showNoRecords();
        }
        return bookingDataArrayList.size();
    }

    public void removeItem(int position){
        bookingDataArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        public TextView book_name, book_date, book_time, book_mail,book_index;
        public RelativeLayout view_foreground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            book_index = itemView.findViewById(R.id.book_index);
            book_name = itemView.findViewById(R.id.book_name);
            book_date = itemView.findViewById(R.id.book_date);
            book_time = itemView.findViewById(R.id.book_time);
            book_mail = itemView.findViewById(R.id.book_mail);
            view_foreground = itemView.findViewById(R.id.view_foreground);
        }
    }

    public void showNoRecords(){

    }
}

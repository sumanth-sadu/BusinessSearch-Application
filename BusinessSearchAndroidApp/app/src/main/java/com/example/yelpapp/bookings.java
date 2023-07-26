package com.example.yelpapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.yelpapp.databinding.ActivityMainBinding;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class bookings extends AppCompatActivity implements RecyclerItemTouchHelperListener {

//    private ActivityMainBinding binding;
    String data;

    private RecyclerView bookingRV;

    // variable for our adapter class and array list
    private bookingAdapter adapter;
    private ArrayList<bookingData> bookingDataArrayList = new ArrayList<>();
    JSONArray booking_list;
    private ConstraintLayout booking_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        Toolbar btoolbar = (Toolbar) findViewById(R.id.bookings_toolbar);
        setSupportActionBar(btoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bookingRV = findViewById(R.id.booking_recyclerview);

        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);

//        sharedPreferences.edit().remove("reservation").commit();

        String json = sharedPreferences.getString("reservation", null);

        Log.d("checking for null", String.valueOf(json==""));
        Log.d("checking for null", String.valueOf(json));
        Log.d("checking for null", String.valueOf(json==null));
        if(json==null){
            TextView txtView = (TextView)findViewById(R.id.noRecords);
            txtView.setVisibility(View.VISIBLE);
        }
        else {


            try {
                JSONArray noRecordsCheck_arr = new JSONArray(json);
                Log.d("records0", String.valueOf(noRecordsCheck_arr));
                Log.d("records", String.valueOf(noRecordsCheck_arr.length()));
                Log.d("records2", String.valueOf(noRecordsCheck_arr.length() == 0));
                TextView txtView = (TextView) findViewById(R.id.noRecords);

                if (noRecordsCheck_arr.length() == 0) {
                    Log.d("in", "in");
                    if (txtView.getVisibility() == View.INVISIBLE)
                        txtView.setVisibility(View.VISIBLE);
                } else {
                    if (txtView.getVisibility() == View.VISIBLE)
                        txtView.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                booking_list = new JSONArray(json);
                Log.d("bookinglist", String.valueOf(booking_list));
                for (int i = 0; i < booking_list.length(); i++) {
                    JSONObject business = booking_list.getJSONObject(i);
                    Log.d("bn", business.getString("businessName"));
                    Log.d("bm", business.getString("bookingMail"));
                    Log.d("bd", business.getString("bookingDate"));

                    String bname = business.getString("businessName");
                    String date = business.getString("bookingDate");
                    String time = business.getString("bookingTime");
                    String email = business.getString("bookingMail");

                    bookingDataArrayList.add(new bookingData(Integer.toString(i + 1), bname, date, time, email));
                }
                Log.d("businessList", String.valueOf(bookingDataArrayList));
                adapter = new bookingAdapter(bookingDataArrayList, this);

                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                bookingRV.setLayoutManager(layoutManager);
                bookingRV.setItemAnimator(new DefaultItemAnimator());
                bookingRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                bookingRV.setAdapter(adapter);

                ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, (RecyclerItemTouchHelperListener) this);
                new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(bookingRV);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

//        loadData();
//
//        buildRecyclerView();

//        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
//        Log.d("sp", String.valueOf(sp));
//        Log.d("sp_mail", String.valueOf(sp.getString("reserve_mail","")));
//        Log.d("sp_date", String.valueOf(sp.getString("reserve_date","")));
//        Log.d("sp_time", String.valueOf(sp.getString("reserve_time","")));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof bookingAdapter.ViewHolder){
//            Item deletedItem = bookingDataArrayList.get(viewHolder.getAdapterPosition());
            booking_layout = findViewById(R.id.booking_layout);
            int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("reservation", null);

            try{
                JSONArray json_array = new JSONArray(json);
                json_array.remove(deleteIndex);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("reservation", String.valueOf(json_array));
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d("delete it", String.valueOf(bookingDataArrayList.size()));
//            Log.d("delete it2", String.valueOf(bookingDataArrayList));
//            bookingDataArrayList.remove(deleteIndex);
//            Log.d("delete it new", String.valueOf(bookingDataArrayList.size()));
//            Log.d("delete it new2", String.valueOf(bookingDataArrayList));
            Snackbar snackbar = Snackbar.make(booking_layout, "Removing Existing Reservation",Snackbar.LENGTH_SHORT);
            snackbar.show();

            String noRecordsCheck = sharedPreferences.getString("reservation", null);
            try{
                JSONArray noRecordsCheck_arr = new JSONArray(noRecordsCheck);
                Log.d("records0", String.valueOf(noRecordsCheck_arr));
                Log.d("records", String.valueOf(noRecordsCheck_arr.length()));
                Log.d("records2", String.valueOf(noRecordsCheck_arr.length()==0));
                TextView txtView = (TextView)findViewById(R.id.noRecords);

                if(noRecordsCheck_arr.length()==0){
                    Log.d("in","in");
                    if (txtView.getVisibility() == View.INVISIBLE)
                        txtView.setVisibility(View.VISIBLE);
                }
                else{
                    if (txtView.getVisibility() == View.VISIBLE)
                        txtView.setVisibility(View.INVISIBLE);
                }
            }catch(JSONException e) {
                e.printStackTrace();
            }



        }
    }
}


















//    private ActivityMainBinding binding;
//    ArrayList<String> arrayList;
//    Adapter adapter;
//    String data;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bookings);
//
//        Toolbar btoolbar = (Toolbar) findViewById(R.id.bookings_toolbar);
//        setSupportActionBar(btoolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        binding=ActivityMainBinding.inflate(getLayoutInflater());
//        View view=binding.getRoot();
//        setContentView(view);
//
//        arrayList=new ArrayList<>();
//        binding.booking_recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        adapter=new Adapter(arrayList);
//        binding.booking_recyclerview.setAdapter(adapter);
//
//        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.booking_recyclerview);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void save(View view) {
//        data=binding.editText.getText().toString();
//        arrayList.add(data);
//        adapter.notifyDataSetChanged();
//        binding.editText.setText("");
//    }
//    String deleteData;
//    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            int position=viewHolder.getBindingAdapterPosition();
//
//            deleteData=arrayList.get(position);
//            arrayList.remove(position);
//            adapter.notifyDataSetChanged();
//
//            Snackbar.make(binding.recyclerview,deleteData,Snackbar.LENGTH_LONG).setAction("Geri Al", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    arrayList.add(position,deleteData);
//                    adapter.notifyDataSetChanged();
//                }
//            }).show();
//        }
//
//        @Override
//        public void onChildDraw(@NonNull Canvas c, @NonNull  RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.deleteColor))
//                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
//                    .addSwipeLeftLabel("Delete")
//                    .setSwipeLeftLabelColor(ContextCompat.getColor(MainActivity.this,R.color.white))
//                    .create()
//                    .decorate();
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//    };
//
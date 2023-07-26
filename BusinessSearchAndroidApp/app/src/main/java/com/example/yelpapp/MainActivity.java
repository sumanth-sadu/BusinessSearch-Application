package com.example.yelpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//implements AdapterView.OnItemSelectedListener
//implements RecyclerAdapter.RecyclerViewClickListener
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<Data> arrayList;
    private Toolbar toolbar;

   // String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar=findViewById(R.id.myToolBar);
//        setSupportActionBar(toolbar);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

//        Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();

//        View toast_view = LayoutInflater.from(MainActivity.this)
//                .inflate(R.layout.toast_icon, null);

//        Toast toast = new Toast(getApplicationContext());

//        TextView reserve_toast = toast_view.findViewById(R.id.reserve_toast);
//        reserve_toast.setText("Time should be between 10PM AND 5PM");
//        ImageView view = new ImageView(getApplicationContext());

        // set image resource to be shown
//        view.setImageResource(R.drawable.yelp_splash);


        // setting view to toast
//        toast.setView(toast_view);
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
//
//        toast.show();



        AutoCompleteTextView keyw = (AutoCompleteTextView) findViewById(R.id.keyword);

        keyw.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String location_url = "https://myfinalsecondwt-assignv3.wl.r.appspot.com/api/auto?text=" + keyw.getText().toString();
                RequestQueue queue_loc = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest_loc = new StringRequest(Request.Method.GET, location_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("autoresponse call", "Onresponse : " + response);

                                ArrayList<String> key_str_list = new ArrayList<String>();

                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray keyword_arr1 = obj.getJSONArray("categories");
                                    JSONArray keyword_arr2 = obj.getJSONArray("terms");

                                    for (int i = 0; i < keyword_arr1.length(); i++) {
                                        key_str_list.add(keyword_arr1.getJSONObject(i).getString("title"));
                                    }
                                    for (int i = 0; i < keyword_arr2.length(); i++) {
                                        key_str_list.add(keyword_arr2.getJSONObject(i).getString("text"));
                                    }

                                    Log.d("key_str_list", String.valueOf(key_str_list));

                                    updateAutocomplete(key_str_list, keyw);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("api call", "Onerror : " + error.getLocalizedMessage());
                    }
                });
                queue_loc.add(stringRequest_loc);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CheckBox loc_check_box = findViewById(R.id.checkBox);
//        EditText keyw = findViewById(R.id.keyword);
        EditText dist = findViewById(R.id.distance);
        EditText loc = findViewById(R.id.location);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        loc_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked() == true) {
                    loc.setEnabled(false);
                    if (loc.getVisibility() == View.VISIBLE)
                        loc.setVisibility(View.INVISIBLE);
                } else {
                    if (loc.getVisibility() == View.INVISIBLE)
                        loc.setVisibility(View.VISIBLE);
                    loc.setEnabled(true);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(keyw.getText().toString()))
                    keyw.setError("This field is required");
                else if(TextUtils.isEmpty(dist.getText().toString()))
                    dist.setError("This field is required");
                else if(TextUtils.isEmpty(loc.getText().toString()) && ((CheckBox) findViewById(R.id.checkBox)).isChecked() == false)
                    loc.setError("This field is required");
                else{
                    getBusinessData();
                }

                RecyclerView rcycleview = findViewById(R.id.recyclerView);
                if (rcycleview.getVisibility() == View.INVISIBLE){
                    rcycleview.setVisibility(View.VISIBLE);
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView keyw = findViewById(R.id.keyword);
                EditText dist = findViewById(R.id.distance);
                Spinner category = findViewById(R.id.spinner1);
                EditText loc = findViewById(R.id.location);
                CheckBox loc_check_box = findViewById(R.id.checkBox);
                RecyclerView rcycleview = findViewById(R.id.recyclerView);

                category.setSelection(0);
//                Spinner spinner = findViewById(R.id.spinner1);
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
//                        R.array.numbers, android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter);
//                spinner.setOnItemSelectedListener(MainActivity.this);

                if (keyw != null) keyw.setText("");
                if (dist != null) dist.setText("");
                if (loc != null){
                    loc.setText("");
                    loc.setVisibility(View.VISIBLE);
                }
                if (loc_check_box.isChecked()) loc_check_box.setChecked(false);

                if (rcycleview.getVisibility() == View.VISIBLE){
                    rcycleview.setVisibility(View.INVISIBLE);
                }
                loc.setEnabled(true);
                loc.setError(null);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            Intent book = new Intent(MainActivity.this, bookings.class);
            book.putExtra("test", "hello bookings");
            startActivity(book);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getBusinessData() {
        AutoCompleteTextView keyw = findViewById(R.id.keyword);
        String keyw2 = keyw.getText().toString();
        EditText dist = findViewById(R.id.distance);
        double dist2 = Integer.parseInt(dist.getText().toString()) * 1609.34;
        Spinner category = findViewById(R.id.spinner1);
        String category2 = category.getSelectedItem().toString();
        EditText loc = findViewById(R.id.location);
        String loc2 = loc.getText().toString();
        CheckBox loc_check_box = findViewById(R.id.checkBox);


        if (loc_check_box.isChecked() == true) {
            loc.setEnabled(false);
            getTableChecked(keyw2, dist2, category2, loc2);
        } else {
            loc.setEnabled(true);
            getTableLoc(keyw2, dist2, category2, loc2);
        }

// ------------------------------------

// ------------------------------------

    }

    public void updateAutocomplete(ArrayList<String> key_str_list, AutoCompleteTextView actv){
        //Creating the instance of ArrayAdapter containing list of fruit names
        Log.d("outside_list", String.valueOf(key_str_list));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, key_str_list);

        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

//    public void getBusinessInfo(View v) {
//        Intent i = new Intent(this, BusinessInfo.class);
//
//        i.putExtra("business_data", "sumanth");
//        startActivity(i);
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getTableChecked(String keyw2, double dist2, String category2, String loc2) {
        String checked_url = "https://ipinfo.io/json?token=7b827d7a0f6858";
        RequestQueue queue_check = Volley.newRequestQueue(this);
        StringRequest stringRequest_check = new StringRequest(Request.Method.GET, checked_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("checked call", "Onresponse : " + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String lat_checked = obj.getString("loc").split(",")[0];
                            String lng_checked = obj.getString("loc").split(",")[1];
                            getTableData(keyw2, dist2, category2, lat_checked, lng_checked);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api call", "Onerror : " + error.getLocalizedMessage());
            }
        });
        queue_check.add(stringRequest_check);
    }

    public void getTableLoc(String keyw2, double dist2, String category2, String loc2) {
        String location_url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + loc2 + "&key=" + "AIzaSyBLEkKp9FHS7iECybzv9AesVs-fuoFXlIE";
        RequestQueue queue_loc = Volley.newRequestQueue(this);
        StringRequest stringRequest_loc = new StringRequest(Request.Method.GET, location_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("loc call", "Onresponse : " + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject loc_arr = obj.getJSONArray("results").getJSONObject(0);
                            String lat_checked = loc_arr.getJSONObject("geometry").getJSONObject("location").getString("lat");
                            String lng_checked = loc_arr.getJSONObject("geometry").getJSONObject("location").getString("lng");
                            getTableData(keyw2, dist2, category2, lat_checked, lng_checked);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("api call", "Onerror : " + error.getLocalizedMessage());
            }
        });
        queue_loc.add(stringRequest_loc);
    }

    public void getTableData(String keyw2, double dist2, String category2, String lat_checked, String lng_checked) {

        String url = "https://myfinalsecondwt-assignv3.wl.r.appspot.com/api/search?keyword=" + keyw2 + "&category=" + category2 + "&distance=" + dist2 + "&latitude=" + lat_checked + "&longitude=" + lng_checked;

        RequestQueue queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray businesses_arr = obj.getJSONArray("businesses");
                            Log.d("businesses", String.valueOf(businesses_arr));

                            for (int i = 0; i < businesses_arr.length(); i++) {
                                JSONObject business = businesses_arr.getJSONObject(i);
                                Log.d("id", business.getString("id"));
                                arrayList.add(new Data(business.getString("id"), Integer.toString(i + 1), business.getString("image_url"), business.getString("name"), business.getString("distance"), business.getString("rating"), lat_checked, lng_checked));
                            }
                            Log.d("businessList", String.valueOf(arrayList));
                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
                            recyclerView.setAdapter(recyclerAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);

//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, RecyclerView.VERTICAL);
//                            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_drawable));
//                            recyclerView.addItemDecoration(dividerItemDecoration);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api call", "Onerror : " + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }

}

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_favorite:
//                Toast.makeText((Context) item.getContentDescription(),"Hey there ", Toast.LENGTH_LONG).show();
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//
//    }



//    @Override
//    public void onClick(Data data) {
//        Toast.makeText(getApplicationContext(), "hey there", Toast.LENGTH_LONG).show();
//    }







//        RelativeLayout relativeLayout = findViewById(R.id.itemDesign);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "hey there", Toast.LENGTH_LONG).show();
//            }
//        });


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
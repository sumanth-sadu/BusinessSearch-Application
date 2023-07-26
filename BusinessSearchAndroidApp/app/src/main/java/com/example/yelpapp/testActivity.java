package com.example.yelpapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class testActivity extends AppCompatActivity {

//    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.testactivity_layout);
//
//        //Getting the instance of AutoCompleteTextView
//        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.keyword);
//
//        actv.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//                String location_url = "https://myfinalsecondwt-assignv3.wl.r.appspot.com/api/auto?text=" + actv.getText().toString();
//                RequestQueue queue_loc = Volley.newRequestQueue(testActivity.this);
//                StringRequest stringRequest_loc = new StringRequest(Request.Method.GET, location_url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Log.d("autoresponse call", "Onresponse : " + response);
//
//                                ArrayList<String> key_str_list = new ArrayList<String>();
//
//                                try {
//                                    JSONObject obj = new JSONObject(response);
//                                    JSONArray keyword_arr1 = obj.getJSONArray("categories");
//                                    JSONArray keyword_arr2 = obj.getJSONArray("terms");
//
//                                    for (int i = 0; i < keyword_arr1.length(); i++) {
//                                        key_str_list.add(keyword_arr1.getJSONObject(i).getString("title"));
//                                    }
//                                    for (int i = 0; i < keyword_arr2.length(); i++) {
//                                        key_str_list.add(keyword_arr2.getJSONObject(i).getString("text"));
//                                    }
//
//                                    Log.d("key_str_list", String.valueOf(key_str_list));
//
//                                    updateAutocomplete(key_str_list, actv);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("api call", "Onerror : " + error.getLocalizedMessage());
//                    }
//                });
//                queue_loc.add(stringRequest_loc);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int
//                    after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//}

//    public void updateAutocomplete(ArrayList<String> key_str_list, AutoCompleteTextView actv){
//        //Creating the instance of ArrayAdapter containing list of fruit names
//        Log.d("outside_list", String.valueOf(key_str_list));
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this, android.R.layout.select_dialog_item, key_str_list);
//
//        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//    }
}


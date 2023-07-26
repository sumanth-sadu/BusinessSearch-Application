package com.example.yelpapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessInfo extends AppCompatActivity {

    String bname = "sumanth";
    private String baddress = "";
    private String bprice = "";
    private String bphone = "";
    private String bstatus = "";
    private String bcategory = "";
    private String burl = "";
    private String bphotos = "";
    private String breviews = "";
    private String res = "";
    private businessinfoAdapter BIadapter;
    String lat;
    String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_info);
        Intent i = getIntent();
        String id = i.getStringExtra("business_id");

        lat = i.getStringExtra("blat");
        lng = i.getStringExtra("blng");
        Log.d("starttest4","starttest4");

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar_info);
        setSupportActionBar(mTopToolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        RequestQueue queue = Volley.newRequestQueue(this);

        String businessinfo_url = "https://myfinalsecondwt-assignv3.wl.r.appspot.com/api/moreinfo?id=" + id;

        StringRequest stringRequest_info = new StringRequest(Request.Method.GET, businessinfo_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("info call","Onresponse : " + response);
                        res = response;
                        try {
                            Log.d("starttest5","starttest5");
                            sendData(response, id, lat, lng);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("api call","Onerror : " + error.getLocalizedMessage());
            }
        });

        queue.add(stringRequest_info);
    }

    public void sendData(String response, String id, String lat, String lng) throws JSONException {
        Log.d("res string", response);

        JSONObject obj = new JSONObject(response);
        bname = obj.getString("name");
        getSupportActionBar().setTitle(bname);
        JSONObject baddress_obj = obj.getJSONObject("location");
        JSONArray baddress_arr = baddress_obj.getJSONArray("display_address");

        Log.d("starttest6","starttest6");

        for (int i=0; i<baddress_arr.length();i++){
            baddress = baddress + baddress_arr.getString(i);
        }

        bphotos = obj.getJSONArray("photos").toString();
        Log.d("bphotos check",bphotos);
        bprice = obj.getString("price");
        bphone = obj.getString("display_phone");

        bstatus = obj.getString("name");

        if(obj.getJSONArray("hours") != null){
            if(obj.getJSONArray("hours").getJSONObject(0).getBoolean("is_open_now") == true){
                bstatus = "Open Now";
            }else{
                bstatus = "Closed";
            }
        }else{
            bstatus = "";
        }

        JSONArray bcategory_arr = obj.getJSONArray("categories");
        bcategory = "";
        for (int i=0; i<bcategory_arr.length();i++){
            if(i == bcategory_arr.length() - 1){
                bcategory = bcategory + bcategory_arr.getJSONObject(i).getString("title");
            }
            else{
                bcategory = bcategory + bcategory_arr.getJSONObject(i).getString("title") + "|";
            }
        }

        burl = obj.getString("url");

        Log.d("starttest7","starttest7");

        BIadapter = new businessinfoAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Log.d("starttest8","starttest8");

        fragment1 f1 = new fragment1();
        fragment2 f2 = new fragment2();

        Log.d("response string",res);

        Bundle dataf1 = new Bundle();
        Bundle dataf2 = new Bundle();

        dataf1.putString("bname", bname);
        dataf1.putString("baddress", baddress);
        dataf1.putString("bprice", bprice);
        dataf1.putString("bphone", bphone);
        dataf1.putString("bcategory", bcategory);
        dataf1.putString("bstatus", bstatus);
        dataf1.putString("burl", burl);
        dataf1.putString("bphotos", bphotos);

        dataf2.putString("blat",lat);
        dataf2.putString("blng",lng);

        f1.setArguments(dataf1);
        f2.setArguments(dataf2);

        RequestQueue queue_review = Volley.newRequestQueue(this);

        String businessreview_url = "https://myfinalsecondwt-assignv3.wl.r.appspot.com/api/reviews?id=" + id;

        StringRequest stringRequest_review = new StringRequest(Request.Method.GET, businessreview_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("review call","Onresponse : " + response);
                        try {
                            sendReviews(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("api call","Onerror : " + error.getLocalizedMessage());
            }
        });

        queue_review.add(stringRequest_review);

        BIadapter.addFragment(f1, "business details");
        BIadapter.addFragment(f2, "map location");

    }

    public void sendReviews(String response) throws JSONException {
        TabLayout tableLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        tableLayout.setupWithViewPager(viewPager);

        JSONObject obj = new JSONObject(response);

        Log.d("review", String.valueOf(obj.getJSONArray("reviews")));
        breviews =  obj.getJSONArray("reviews").toString();

        Log.d("breviews", String.valueOf(breviews));

        fragment3 f3 = new fragment3();
        Bundle dataf3 = new Bundle();
        Log.d("revclass", String.valueOf(breviews.getClass()));

        dataf3.putString("breviews", breviews);

        f3.setArguments(dataf3);
        BIadapter.addFragment(f3, "reviews");
        viewPager.setAdapter(BIadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_info_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.facebook_icon) {
            String link = String.format("https://www.facebook.com/sharer/sharer.php?u=%s", burl);
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link));
            startActivity(intent);
            return true;
        }

        if (id == R.id.twitter_icon) {
            String link = String.format("https://twitter.com/intent/tweet?text=check %s on Yelp.&url=%s", bname, burl);
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

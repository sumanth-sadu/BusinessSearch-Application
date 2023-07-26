package com.example.yelpapp;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment3 extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<reviewData> arrayList_reviews = new ArrayList<>();
    JSONArray array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragment3, container, false);

        Bundle data = getArguments();
        String s = data.getString("breviews");
        Log.d("think",s);

        try {
            array = new JSONArray(s);

            for(int i=1; i<=3; i++){
                if(array.length() < 3 && array.length() == i){

                    arrayList_reviews.add(new reviewData(array.getJSONObject(i-1).getJSONObject("user").getString("name"),
                            array.getJSONObject(i-1).getString("rating"),
                            array.getJSONObject(i-1).getString("text"),
                            array.getJSONObject(i-1).getString("time_created").split(" ")[0]));
                    break;
                }else{
                    Log.d("user_name", array.getJSONObject(i-1).getJSONObject("user").getString("name"));
                    Log.d("user_rating", array.getJSONObject(i-1).getString("rating"));
                    Log.d("user_text", array.getJSONObject(i-1).getString("text"));
                    Log.d("user_text", array.getJSONObject(i-1).getString("time_created").split(" ")[0]);
                    arrayList_reviews.add(new reviewData(array.getJSONObject(i-1).getJSONObject("user").getString("name"),
                            array.getJSONObject(i-1).getString("rating"),
                            array.getJSONObject(i-1).getString("text"),
                            array.getJSONObject(i-1).getString("time_created").split(" ")[0]));
                }
            }
            recyclerView = v.findViewById(R.id.recyclerView_reviews);
            reviewAdapter rAdapter = new reviewAdapter(getContext(), arrayList_reviews);
            recyclerView.setAdapter(rAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_drawable));
//            recyclerView.addItemDecoration(dividerItemDecoration);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL) {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getChildAdapterPosition(view);
                    // hide the divider for the last child
                    if (position == state.getItemCount() - 1) {
                        outRect.setEmpty();
                    } else {
                        super.getItemOffsets(outRect, view, parent, state);
                    }
                }
            };
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_drawable));
            recyclerView.addItemDecoration(dividerItemDecoration);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;

    }
}
package com.example.yelpapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class fragment1 extends Fragment implements reserveDialog.reserveDialogListener{

    private ViewPager2 viewPager2;
    private ArrayList<detailsData> arrayList = new ArrayList<>();
    JSONArray image_array;

    private TextView formemail;
    private TextView formdate;
    private TextView formtime;
    private Button button;

    private String bname;

    private ArrayList<bookingData> bookingDataArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private bookingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
//        TextView t = view.findViewById(R.id.textView7);

        Bundle dataf1 = getArguments();
        Log.d("starttest9","starttest9");

        bname = dataf1.getString("bname");
//        dataf1.getString("bname", bname);
        String add = dataf1.getString("baddress");
        String pri = dataf1.getString("bprice");
        String pho = dataf1.getString("bphone");
        String cat = dataf1.getString("bcategory");
        String status = dataf1.getString("bstatus");
        String url = dataf1.getString("burl");
        String img_slide = dataf1.getString("bphotos");
//        Log.d("starttest10","starttest10");
//        Log.d("img1_slide", img_slide);
//        Log.d("pho",dataf1.getString("bphotos"));
//        Log.d("starttest11","starttest11");


        if(img_slide!=null) {
            try {
                image_array = new JSONArray(img_slide);

                Log.d("image_array", image_array.getString(0));
                for (int i = 1; i <= 3; i++) {
                    if (image_array.length() < 3 && image_array.length() == i) {
                        arrayList.add(new detailsData(image_array.getString(i - 1)));
                    } else {
                        arrayList.add(new detailsData(image_array.getString(i - 1)));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        String images = dataf1.getString("bphotos");

//        Log.d("mystr",mystr);

        TextView add_view = view.findViewById(R.id.address_text);
        add_view.setText(add);
        TextView phone_view = view.findViewById(R.id.phone_text);
        phone_view.setText(pho);
        TextView price_view = view.findViewById(R.id.pricerange_text);
        price_view.setText(pri);
        TextView cat_view = view.findViewById(R.id.category_text);
        cat_view.setText(cat);
        TextView status_view = view.findViewById(R.id.status_text);

        status_view.setText(status);
        if(status == "Open Now")
            status_view.setTextColor(Color.parseColor("#92F791"));
        else
            status_view.setTextColor(Color.parseColor("#FF0102"));

        TextView link_view = view.findViewById(R.id.yelplink_text);
        link_view.setPaintFlags(link_view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        link_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                startActivity(intent);
            }
        });



//        link_view.setClickable(true);
//        link_view.setMovementMethod(LinkMovementMethod.getInstance());
//        String text = "<a href=" + url + "> Business Link </a>";
//        String text = "<a href='http://www.google.com'> Google </a>";
//
//        link_view.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

//        link_view.setText(url);

//        t.setText(mystr);

       // arrayList.add(new detailsData(add,pri, pho, cat, status, url, "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"));

//        Log.d("details", String.valueOf(arrayList));
//        detailsAdapter recyclerAdapter = new detailsAdapter(getContext(), arrayList, viewPager2);
//        viewPager2.setAdapter(recyclerAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);

        viewPager2 = view.findViewById(R.id.image_slide);

        Log.d("details", String.valueOf(arrayList));
        detailsAdapter recyclerAdapter = new detailsAdapter(getContext(), arrayList, viewPager2);
        viewPager2.setAdapter(recyclerAdapter);

//        formemail = (TextView) findViewById(R.id.reserve_email_text);
//        formdate = (TextView) findViewById(R.id.reserve_date_text);
//        formtime = (TextView) findViewById(R.id.reserve_time_text);

        button = view.findViewById(R.id.reserve_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("opend","opendialog");
                openDialog();
            }
        });

        return view;
    }

    public void openDialog(){
        reserveDialog reserveDialogObj = new reserveDialog();
        Bundle args = new Bundle();
        args.putString("bname", bname);
        reserveDialogObj.setArguments(args);
        Log.d("in here","hello");
        reserveDialogObj.show(getChildFragmentManager(), "example");
//        reserveDialogObj.show(requireActivity().getSupportFragmentManager(), "example dialog");
        Log.d("nothing","showing");
    }

    @Override
    public void applyTexts(String email, String date, String time) {

        loadData();

        Bundle dataf1 = getArguments();

        String bname = dataf1.getString("bname");

        bookingDataArrayList.add(new bookingData(Integer.toString(bookingDataArrayList.size()+1),bname, date, time, email));
        // notifying adapter when new data added.
//        adapter.notifyItemInserted(bookingDataArrayList.size());

        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("reserve_mail", email);
//        editor.putString("reserve_date", date);
//        editor.putString("reserve_time", time);

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(bookingDataArrayList);

        Log.d("print json", json);
        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("reservation", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }


    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        sharedPreferences = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("reservation", null);

//        Log.d("able to retreive data", json);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<bookingData>>() {}.getType();
//
//        Log.d("check gson", gson.fromJson(json, type).toString());
        // in below line we are getting data from gson
        // and saving it to our array list
        bookingDataArrayList = gson.fromJson(json, type);
//
//        Log.d("gson to ", String.valueOf(bookingDataArrayList));

        // checking below if the array list is empty or not
        if (bookingDataArrayList == null) {
            Log.d("just check","msg");
            // if the array list is empty
            // creating a new array list.
            bookingDataArrayList = new ArrayList<>();
        }
    }


}
















//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public fragment1() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment fragment1.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static fragment1 newInstance(String param1, String param2) {
//        fragment1 fragment = new fragment1();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
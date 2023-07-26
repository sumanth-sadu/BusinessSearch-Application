package com.example.yelpapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//
public class reserveDialog extends AppCompatDialogFragment {

    private TextView emailText;
    private TextView dateText;
    private TextView timeText;
    private TextView rbName;
    private reserveDialogListener listener;
    int thr,tmin;
    String emailValidator;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        Log.d("text1","newtext");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Log.d("text2","newtext2");
        View view = inflater.inflate(R.layout.reservation_layout, null);
        Log.d("text3","newtext3");
        TextView businessName = view.findViewById(R.id.reserveBusinessName);

        Bundle args = getArguments();
        String bname = args.getString("bname");

        Log.d("text4","text4");
        emailText = view.findViewById(R.id.reserve_email_text);
        dateText = view.findViewById(R.id.reserve_date_text);
        timeText = view.findViewById(R.id.reserve_time_text);
        rbName = view.findViewById(R.id.reserveBusinessName);
        rbName.setText(bname);


        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        dateText.setText(date);

                    }
                },year, month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
                dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.buttons));
                dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.buttons));
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                        thr = hourOfDay;
                        tmin = minute;
                        calendar.set(0,0,0,thr,tmin);
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                        timeText.setText(format.format(calendar.getTime()));
                    }
                },12,0,false);

                timePickerDialog.updateTime(thr,tmin);
                timePickerDialog.show();
                timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.buttons));
                timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.buttons));
            }});

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = emailText.getText().toString().trim();
                        String date = dateText.getText().toString();
                        String time = timeText.getText().toString();
                        Log.d("mail entered", email);
                        Log.d("mail patter", emailPattern);
                        Log.d("matches or not", String.valueOf(email.matches(emailPattern)));

                        if(thr<10 || thr>17)
                            Toast.makeText(getParentFragment().getContext(), "Time should be between 10PM AND 5PM", Toast.LENGTH_SHORT).show();
                        else if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                            Toast.makeText(getParentFragment().getContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
                        else{
                            listener.applyTexts(email, date, time);
                            Toast.makeText(getParentFragment().getContext(), "Reservation Booked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//
//        AlertDialog dialog = builder.create();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.buttons));
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.buttons));
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (reserveDialogListener) getParentFragment();
    }

    public interface reserveDialogListener {
        void applyTexts(String email, String date, String time);
    }

}


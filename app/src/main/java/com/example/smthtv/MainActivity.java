package com.example.smthtv;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.Calendar;

public class MainActivity extends FragmentActivity {

    EditText edtSeconds;
    Button btnStartTimer;
    Calendar dateTime = Calendar.getInstance();
    TimePickerDialog.OnTimeSetListener picker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSeconds = findViewById(R.id.edtSeconds);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        picker = (timePicker, hourOfDay, minute) -> {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            dateTime.set(Calendar.SECOND, 0);
            edtSeconds.setText(DateUtils.formatDateTime(getApplicationContext(), dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
        };
        edtSeconds.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                new TimePickerDialog(MainActivity.this, picker,
                        dateTime.get(Calendar.HOUR_OF_DAY),
                        dateTime.get(Calendar.MINUTE), true).show();
            }
            return true;
        });
        btnStartTimer.setOnClickListener(view -> {
            long seconds = dateTime.getTimeInMillis();
            if (dateTime.getTimeInMillis() < System.currentTimeMillis()) seconds += 86400000;
            Toast.makeText(getApplicationContext(), "Будильник установлен на " + DateUtils.formatDateTime(getApplicationContext(), seconds, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME), Toast.LENGTH_SHORT).show();
        });
    }
}
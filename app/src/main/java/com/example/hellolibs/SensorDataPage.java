package com.example.hellolibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

public class SensorDataPage extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data_page);

        TextView randomNumberTextView = findViewById(R.id.randomNumberView);
        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String message = intent.getStringExtra("Random Bytes");
        sharedpreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String message = sharedpreferences.getString("MyPrefs", "Nothing saved ");

        randomNumberTextView.setText(message);
        randomNumberTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onBackPressed(){
        sharedpreferences.edit().putString("MyPrefs","").commit();
        super.onBackPressed();
    }
}


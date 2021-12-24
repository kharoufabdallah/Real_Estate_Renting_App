package com.example.renting_app;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

    Button button;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgress(false);
        button = (Button) findViewById(R.id.connectB_intro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask(IntroActivity.this);
                connectionAsyncTask.execute("http://www.mocky.io/v2/5b4e6b4e3200002c009c2a44");
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.layout);
    }
    public void setButtonText(String text) {
        button.setText(text);
    }
    public void fillStudents(List<Property> properties) {
        LinearLayout linearLayout = (LinearLayout)
                findViewById(R.id.layout);

        linearLayout.removeAllViews();
        for (int i = 0; i < properties.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(properties.get(i).toString());
            linearLayout.addView(textView);
        }
    }
    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar_intro);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
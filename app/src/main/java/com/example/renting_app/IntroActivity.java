package com.example.renting_app;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    Button connection_button;
    LinearLayout linearLayout;

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setProgress(false);    // progressBar functionalities

        connection_button = findViewById(R.id.connectB_intro);
        connection_button.setOnClickListener(v -> {
//            @Override
//            public void onClick(View v) {
            if(this.count == 0) {
                Log.d("one", "Entered else");
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask(IntroActivity.this);
                connectionAsyncTask.execute("https://run.mocky.io/v3/4d0f58c0-eb99-4154-b1b8-0d018c752003");
                this.count = 1;

                if (connectionAsyncTask.isSuccess()) {  // attribute of the connection
                    Intent intent = new Intent(IntroActivity.this, SigningActivity.class);
                    startActivity(intent);
                    finish();

                    /// TODO: not working in here -> in main works fine
                } else {
                //    this.count = 0;
                    Log.d("ELSE", "Entered else");

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context,
                            "Unsuccessful Connection - Retry", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                this.count = 0;
                Intent intent = new Intent(IntroActivity.this, SigningActivity.class);
                startActivity(intent);
                finish();
            }
        }
        );

            linearLayout = (LinearLayout) findViewById(R.id.layout);
    }
    public void setButtonText(String text) { // used in AsyncTask
        connection_button.setText(text);
    }
    public void fillProperties(List<Property> properties) { // used in AsyncTask
        LinearLayout linearLayout = (LinearLayout)
                findViewById(R.id.layout);

        linearLayout.removeAllViews();

        for (int i = 0; i < properties.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(properties.get(i).toString()); // writes the read data
            linearLayout.addView(textView);
        }
        call_dataB_to_add_properties(properties); // this method adds all online-REST-properties into database table Property

    }

    public void call_dataB_to_add_properties(List<Property>props)
    {
        DataBaseHelper db = new DataBaseHelper(IntroActivity.this);
        db.insert_property_from_json_file(props);
        Toast.makeText(this, "Inserted in property DB", Toast.LENGTH_SHORT).show();
    }
    public void setProgress(boolean progress) { // used here and in AsyncTask methods
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
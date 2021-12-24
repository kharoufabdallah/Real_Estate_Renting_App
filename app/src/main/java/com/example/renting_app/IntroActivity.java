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

    Intent intent=new Intent(Intent.ACTION_MAIN, Uri.parse("tel:+1555"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setProgress(false);

        connection_button = findViewById(R.id.connectB_intro);
        connection_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("one","Entered else");
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask(IntroActivity.this);
                connectionAsyncTask.execute("https://run.mocky.io/v3/4d0f58c0-eb99-4154-b1b8-0d018c752003");

                // in here, we have to test weather the connection is successfull or not
                // if success -> go into sign/in/up page
                // else stay here until success !

                    if (connectionAsyncTask.isSuccess()) ; // write code here to move into sign/up intent

                    /// TODO: not working in here -> in main works fine
                    else { // write code of TOAST message that is not successfull

                        Log.d("ELSE","Entered else");

                        Context context = getApplicationContext();
                     Toast toast =Toast.makeText(context,
                            "Unsuccessful Connection - Retry",Toast.LENGTH_SHORT);
                     toast.show();
                   }
                }
            });
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
    }
    public void setProgress(boolean progress) { // used here and in AsyncTask methods
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar_intro);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
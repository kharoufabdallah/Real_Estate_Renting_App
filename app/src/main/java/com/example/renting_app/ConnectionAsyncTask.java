package com.example.renting_app;
import android.app.Activity;
import android.os.AsyncTask;
import java.util.List;


//You will be making the API call in a separate thread, which is always a good practice since
//the user interface will not be blocked while the call is being made. This is especially important in
//mobile devices that may drop network connections or experience high network latency. To
//accomplish this, you will implement an AsyncTask class

public class ConnectionAsyncTask extends AsyncTask<String,String,String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ((IntroActivity) activity).setButtonText("connecting");
        super.onPreExecute();
        ((IntroActivity) activity).setProgress(true);
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ((IntroActivity) activity).setProgress(false);
        ((IntroActivity) activity).setButtonText("connected");
        List<Property> properties =
                PropertyJsonParser.getObjectFromJason(s);
      //  assert properties != null;
        ((IntroActivity) activity).fillProperties(properties);
    }
}
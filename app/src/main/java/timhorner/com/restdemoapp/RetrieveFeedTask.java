package timhorner.com.restdemoapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gibl3t on 3/14/17.
 */

class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private ProgressBar progressBar;
    private TextView responseView;
    private EditText idText;
    private String id;

    public RetrieveFeedTask(ProgressBar progressBar, TextView responseView, EditText idText){
        this.progressBar = progressBar;
        this.responseView = responseView;
        this.idText = idText;
    }

    protected void onPreExecute(){
        progressBar.setVisibility(View.VISIBLE);
        responseView.setText("");
        id = idText.getText().toString();
    }

    protected String doInBackground(Void... urls){
        //do validation here

        try{
            URL url = new URL("http://10.35.18.240:4567/users/" + id);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response){
        if(response == null){
            response = "THERE WAS AN ERROR";
        }
        progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        responseView.setText(response);
    }
}

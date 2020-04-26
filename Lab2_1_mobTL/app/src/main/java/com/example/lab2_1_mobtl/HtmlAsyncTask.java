package com.example.lab2_1_mobtl;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlAsyncTask extends AsyncTask<String, Integer, String> {

    String htmlText;

    public  interface ReporterInterface{
        void networkFetchDone(String data);
    }



    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            htmlText = fromStream(in);
        }
        catch (Exception e) {e.printStackTrace();}
        return htmlText;
    }

    @Override
    protected void onPostExecute(String data) {
        if (callbackInterface != null)
        {
            callbackInterface.networkFetchDone(data);
        }
    }

    public static String fromStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}

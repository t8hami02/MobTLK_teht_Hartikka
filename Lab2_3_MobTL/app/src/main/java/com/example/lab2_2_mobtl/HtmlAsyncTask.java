package com.example.lab2_2_mobtl;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlAsyncTask extends AsyncTask<String, Integer, String> {

    public  interface ReporterInterface{
        void networkFetchDone(String data);
    }


    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }


    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        //for (int i = 0; i++, i<  )

        try {
            URL url = new URL("https://financialmodelingprep.com/api/company/price/"+strings[0]+"?datatype=json");
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());

            reader = new BufferedReader(new InputStreamReader(in));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                //Log.d("testiaaa: ", "> " + line);   //here u ll get whole response...... :-)

            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        if (callbackInterface != null)
        {
            //Log.d("testiaaa: ", "> " + data);
            callbackInterface.networkFetchDone(data);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

    }
}

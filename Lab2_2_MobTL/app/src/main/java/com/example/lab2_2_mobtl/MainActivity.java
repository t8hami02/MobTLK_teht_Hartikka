package com.example.lab2_2_mobtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements HtmlAsyncTask.ReporterInterface {

    ArrayList<String> stocksList = new ArrayList<String>();
    String jsonResult;
    String text1 = "GOOGL";
    String text2 = "AAPL", text3="FB", text4 = "NOK";
    String priceStr, name, givenName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrintJson(text1);
        PrintJson(text2);
        PrintJson(text3);
        PrintJson(text4);



    }

    public void updateListView()
    {
        ListView myListView;
        myListView = (ListView) findViewById(R.id.ListViewStock);
        ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>( this,android.R.layout.simple_list_item_1,
                stocksList);
        myListView.setAdapter(aa);
    }

    @Override
    public void networkFetchDone(String data) {

        //jsonResult = data;
        //Log.d("testia", "networkFetchDone: " + jsonResult);

        try {
            JSONObject jsonObj = new JSONObject(data);
            Iterator<String> keys = jsonObj.keys();
            name = keys.next();
            JSONObject price = jsonObj.getJSONObject(name);
            priceStr = price.getString("price");
            Log.d("testia", "networkFetchDone: " + priceStr);

            switch (name)
            {
                case "AAPL":
                    stocksList.add("Apple: "+ priceStr + " USD");
                    break;
                case "GOOGL":
                    stocksList.add("Alphabet (Google): "+ priceStr + " USD");
                    break;
                case "FB":
                    stocksList.add("Facebook: "+ priceStr + " USD");
                    break;
                case "NOK":
                    stocksList.add("Nokia: "+ priceStr + " USD");
                    break;
                default:
                    stocksList.add(givenName+" "+ priceStr + " USD");
                    break;
            }
            //stocksList.add(priceStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateListView();
    }

    public void PrintJson(String code)
    {
        HtmlAsyncTask task1 = new HtmlAsyncTask();
        task1.setCallbackInterface(this);
        task1.execute(code);
    }
}

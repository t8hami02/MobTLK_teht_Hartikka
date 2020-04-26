package com.example.lab2_2_mobtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements HtmlAsyncTask.ReporterInterface, View.OnClickListener {

    ArrayList<String> stocksList = new ArrayList<String>();
    String jsonResult;
    String text1 = "GOOGL";
    String text2 = "AAPL", text3="FB", text4 = "NOK";
    String priceStr, name, givenName = "";
    EditText editStockName, editStockID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ButtonAdd).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        editStockName = findViewById(R.id.EditTxtStockName);
        editStockID = findViewById(R.id.EditTxtStockId);
        if (v.getId() == R.id.ButtonAdd)
        {
            givenName = editStockName.getText().toString();
            Log.d("testia", "onClick: " + givenName);
            Log.d("testia", "onClick: " + editStockID.getText().toString().toUpperCase());
            PrintJson(editStockID.getText().toString().toUpperCase());
        }
    }
}

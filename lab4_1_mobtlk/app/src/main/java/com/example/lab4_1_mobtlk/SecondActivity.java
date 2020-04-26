package com.example.lab4_1_mobtlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    AlueetClass saatuAlue;
    ListView myListView2;
    ArrayList<String> liigat = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        saatuAlue = (AlueetClass) getIntent().getSerializableExtra("AlueetClass");

        String url ="https://api.football-data.org/v2/competitions?areas=" + saatuAlue.getId();
        Log.d("testia", "url: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray competitionsArray = response.getJSONArray("competitions");
                            //String num = "0";
                            if(competitionsArray.length() == 0)
                            {
                                String name = "No data";

                                Log.d("testia", "onResponse: " + name);
                                liigat.add(name);
                            }
                            else
                            {
                                for( int i = 0; i < competitionsArray.length(); i++)
                                {
                                    JSONObject competitionDetail = competitionsArray.getJSONObject(i);
                                    String name = competitionDetail.getString("name");

                                    Log.d("testia", "onResponse: " + name);
                                    liigat.add(name);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("testia", "onResponse: eip");
                        }


                        updateListViewSecond();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("testia", "onErrorResponse: ei toimi secondActivity");

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void updateListViewSecond()
    {
        myListView2 = (ListView) findViewById(R.id.myListView2);
        ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>( this,android.R.layout.simple_list_item_1,
                liigat);
        myListView2.setAdapter(aa);
    }
}

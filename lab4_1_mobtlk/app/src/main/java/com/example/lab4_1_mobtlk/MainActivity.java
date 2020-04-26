package com.example.lab4_1_mobtlk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<AlueetClass> alueet = new ArrayList<AlueetClass>();
    ListView myListview;
    RequestQueue queue;

    CustomArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById(R.id.myButton).setOnClickListener(this);


        //String url ="http://api.football-data.org/v2/competitions?areas=2072";
        String url ="https://api.football-data.org/v2/areas";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray areaArray = response.getJSONArray("areas");

                            for( int i = 0; i < areaArray.length(); i++)
                            {
                                JSONObject areaDetail = areaArray.getJSONObject(i);
                                String name = areaDetail.getString("name");
                                String countryCode = areaDetail.getString("countryCode");
                                String parentArea = areaDetail.getString("parentArea");
                                int id = areaDetail.getInt("id");
                                AlueetClass alue = new AlueetClass(name, countryCode, parentArea, id);
                                alueet.add(alue);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("testia", "onResponse: eip");
                        }


                        updateListView();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("testia", "onErrorResponse: ei toimi");

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }



    public void updateListView()
    {
        myListview = (ListView) findViewById(R.id.myListView);
        adapter = new CustomArrayAdapter(this,alueet);
        myListview.setAdapter(adapter);
        myListview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("testia", "onItemClick: " + alueet.get(position).getNimi());

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("AlueetClass",alueet.get(position));
        startActivity(intent);

    }

    /*
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myButton)
        {
            //Log.d("Testi", "onClick: " + alueet.get(0).getNimi());

            myListview = (ListView) findViewById(R.id.myListView);
            adapter = new CustomArrayAdapter(this,alueet);
            myListview.setAdapter(adapter);
        }
    }*/
}
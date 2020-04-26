package com.example.lab3_2_mobtlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> liigat = new ArrayList<>();
    ListView myListview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url ="https://api.football-data.org/v2/competitions?areas=2072";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray competitionsArray = response.getJSONArray("competitions");

                            for( int i = 0; i < competitionsArray.length(); i++)
                            {
                                JSONObject competitionDetail = competitionsArray.getJSONObject(i);
                                String name = competitionDetail.getString("name");
                                Log.d("testia", "onResponse: " + name);
                                liigat.add(name);
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
        ListView myListView;
        myListView = (ListView) findViewById(R.id.myListView);
        ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>( this,android.R.layout.simple_list_item_1,
                liigat);
        myListView.setAdapter(aa);
    }
}

package com.example.lab2_1_mobtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HtmlAsyncTask.ReporterInterface {

    RequestQueue queue;
    //= Volley.newRequestQueue(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.myBtn).setOnClickListener(this);
        findViewById(R.id.myBtn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myBtn)
        {
            EditText myEditText = findViewById(R.id.myEditText);
            String urlia = myEditText.getText().toString();
            HtmlAsyncTask task = new HtmlAsyncTask();
            task.setCallbackInterface(this);
            task.execute(urlia);
        }
        if (v.getId() == R.id.myBtn2)
        {
            final TextView textView = findViewById(R.id.myTextView);
            EditText myEditText = findViewById(R.id.myEditText);
            queue = Volley.newRequestQueue(this);
            String url = myEditText.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            textView.setText("Response is: "+ response.substring(0,500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    textView.setText("That didn't work!");
                }
            });

            queue.add(stringRequest);

        }
    }

    @Override
    public void networkFetchDone(String data) {
        TextView textView = findViewById(R.id.myTextView);
        textView.setText(data);
    }
}

package com.example.lab2_1_mobtl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HtmlAsyncTask.ReporterInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.myBtn).setOnClickListener(this);
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
    }

    @Override
    public void networkFetchDone(String data) {
        TextView textView = findViewById(R.id.myTextView);
        textView.setText(data);
    }
}

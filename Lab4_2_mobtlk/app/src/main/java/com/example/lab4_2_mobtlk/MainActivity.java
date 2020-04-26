package com.example.lab4_2_mobtlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EchoClientInterface {

    EchoClient echoClient = null;
    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.myButton).setOnClickListener(this);
        myTextView = findViewById(R.id.myTextView);

        openConnection();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myButton)
        {
            if (echoClient != null && echoClient.isOpen())
            {
                EditText editor = findViewById(R.id.myEditText);
                String text = editor.getText().toString();
                echoClient.send(text);
            }
        }
    }

    private void openConnection(){
        try {
            echoClient = new EchoClient(new URI("wss://obscure-waters-98157.herokuapp.com"), this);
            echoClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView myTextView = findViewById(R.id.myTextView);
                myTextView.append(message + "\n");
            }
        });
    }

    @Override
    public void onStatusChange(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView myTextView = findViewById(R.id.myTextViewStatus);
                myTextView.setText(newStatus);
            }
        });
    }
}

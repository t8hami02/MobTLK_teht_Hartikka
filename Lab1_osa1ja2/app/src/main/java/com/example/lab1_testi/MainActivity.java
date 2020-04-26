package com.example.lab1_testi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;

    String myLatitude;
    String myLongitude;

    String cityName = "";
    String stateName = "";
    String countryName = "";
    Geocoder geocoder;

    private static DecimalFormat df = new DecimalFormat("0.00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.myBtn).setOnClickListener(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);






    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myBtn)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);

            }
            geocoder = new Geocoder(this, Locale.getDefault());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                myLatitude = df.format(location.getLatitude());
                                myLongitude = df.format(location.getLongitude());


                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    cityName = addresses.get(0).getAddressLine(0);
                                    stateName = addresses.get(0).getAddressLine(1);
                                    countryName = addresses.get(0).getAddressLine(2);
                                }
                                catch(IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    });



            TextView TxtView1 = findViewById(R.id.myTextView1);
            TxtView1.setText(myLatitude);
            TextView TxtView2 = findViewById(R.id.myTextView2);
            TxtView2.setText(myLongitude);
            TextView TxtView3 = findViewById(R.id.myTextView3);
            TxtView3.setText(cityName);
            TextView TxtView4 = findViewById(R.id.myTextView4);
            TxtView4.setText(countryName);
        }
    }
}

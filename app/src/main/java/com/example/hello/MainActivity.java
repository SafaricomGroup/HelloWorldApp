package com.example.hello;

import android.view.Window;
import androidx.core.content.ContextCompat;
import android.view.WindowManager;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.net.Uri;


import android.content.Intent;
import android.provider.Settings;
    
     
   import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
  
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
    
import android.util.TypedValue;

import android.view.Gravity;
import android.widget.ImageView;

import android.graphics.Color;

import java.lang.reflect.Field;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
 
   import androidx.appcompat.app.AppCompatActivity;

  public class MainActivity extends AppCompatActivity {
    
private TextView ue;
 private TextView ve;
    private TextView te;
    private LocationManager locationManager;
   
double fixedla=0;

double fixedlo=0;
double la = 0;
double lo = 0;
boolean isInitialized = false; 
int a=0;
double NOISE_THRESHOLD_KM = 1; 
   

StringBuilder st = new StringBuilder();


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        ConstraintLayout layout = new ConstraintLayout(this);

        ConstraintLayout.LayoutParams jparams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        jparams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        jparams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        jparams.topMargin = 50;
        jparams.leftMargin = 100;

        te = new TextView(this);
        te.setId(View.generateViewId());
        te.setText("waiting");
        te.setTextSize(14);
        te.setTextColor(Color.rgb(70, 70, 70));
        te.setPadding(0, 10, 0, 10);
        te.setLayoutParams(jparams);

        layout.addView(te);

    ConstraintLayout.LayoutParams hparams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        hparams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        hparams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        hparams.topMargin = 135;
        hparams.leftMargin = 100;

     hparams.bottomMargin = 50;
        ue = new TextView(this);
        ue.setId(View.generateViewId());
        
        ue.setTextSize(14);
        ue.setTextColor(Color.rgb(70, 70, 70));
        ue.setPadding(0, 10, 0, 10);
        ue.setLayoutParams(hparams);

        layout.addView(ue);

    ConstraintLayout.LayoutParams kparams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        kparams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        kparams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        kparams.topMargin = 95;
        kparams.leftMargin = 100;

     kparams.bottomMargin = 50;
        ve = new TextView(this);
        ve.setId(View.generateViewId());
        
        ve.setTextSize(14);
        ve.setTextColor(Color.rgb(70, 70, 70));
        ve.setPadding(0, 10, 0, 10);
        ve.setLayoutParams(kparams);

        layout.addView(ve);

        setContentView(layout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    != PackageManager.PERMISSION_GRANTED &&
    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    != PackageManager.PERMISSION_GRANTED) {

    ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
 Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
} else {
    startLocationUpdates();
}
}
    private void startLocationUpdates() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                

   String result = "Lat: " + lat + "\nLon: " + lon;

            // Check which provider sent the update
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                // Update the GPS TextView
                te.setText("GPS Data:\n" + result);
                 } 
            else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                // Update the Network TextView
                te.setText("Network Data:\n" + result);
                }   
     
          if (!isInitialized) {
  fixedla=lat;
  fixedlo=lon;
    la = lat;
    lo = lon;
    isInitialized = true;
}      


double distancea = haversineDistance(la, lo, lat, lon); // Latitude change
double distancec = haversineDistance(fixedla, fixedlo, lat, lon);   // To

String distanceaa = String.format("%.3f", distancea);
String distancecc= String.format("%.3f", distancec);
  

      if (distancec >= NOISE_THRESHOLD_KM) {
 st.setLength(0);
   if ((lat -fixedla) >=0){
        st.append("NORTH");
    } 

   else if ((lat -fixedla) <=-0){
        st.append("SOUTH");  
    }
    
    if ((lon - fixedlo)>=0){
        st.append("EAST");
    } 

     else if( (lon -fixedlo)<= -0){
        st.append("WEST");
    }
    
    ue.setText("\ndevice moved " + st + "\n" + distancecc + " km " +" totally ");
    a=1;
     }


  if (distancea >= NOISE_THRESHOLD_KM ) {
 st.setLength(0);
     if ((lat -la)>=0) {
        st.append("NORTH");
    } 
    else if( (lat-la) <= -0){
        st.append("SOUTH");  
    }
    
    if ((lon - lo) >=0){
        st.append("EAST");
    } 
    else if ( (lon -lo) <= -0) {   
        st.append("WEST");
    }
    
    ve.setText("\ndevice moving " + st + "\n" + distanceaa + " km " +" at this point");
    la=lat;
    lo=lon;
}
  
         
  }
        };

  
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            
                      if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, locationListener);
            }

                      
            // Register Network
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, locationListener);
            }
        }
    } 


private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
    double R = 6371; 
    double dLat = (lat2 - lat1) * Math.PI / 180;
    double dLon = (lon2 - lon1) * Math.PI / 180;
    
    // Added Math. to all the trig and square root functions below
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
               Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
               Math.sin(dLon / 2) * Math.sin(dLon / 2);
               
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
    return R * c; // Changed method header from void to double to allow this
}


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
 int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   if (requestCode == 1 && grantResults.length > 0 &&
 grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            te.setText("Permission denied");
        }
    }
}
    

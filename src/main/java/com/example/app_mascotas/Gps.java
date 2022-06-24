package com.example.app_mascotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
//import android.location.LocationRequest;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.Switch;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class Gps extends AppCompatActivity  implements  View.OnClickListener{

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private FusedLocationProviderClient mFusedLocationClient;
    DatabaseReference mDatabase; //referencia de la base de datos
    FirebaseDatabase mibase;
    private Button mbtnMaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mibase = FirebaseDatabase.getInstance();
        mDatabase = mibase.getReference("IUbicaciones");

        //obtener la ultima localización
        Subir_lat_log_Firebase();
        mbtnMaps = findViewById(R.id.btnMaps);
        mbtnMaps.setOnClickListener(this);
    }

    //esto es un metodo
    private void Subir_lat_log_Firebase() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //permiso para hacer gps
            ActivityCompat.requestPermissions(Gps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.e("Latitud: ", + location.getLatitude() + "Longitud: " +location.getLongitude());
                            Map<String,Object> latlang = new HashMap<>();
                            latlang.put("latitud",location.getLatitude());
                            latlang.put("longitud",location.getLongitude());
                            mDatabase.child("1234").setValue(latlang);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMaps: Intent intent = new Intent(Gps.this,MapsActivity.class);
                                startActivity(intent);
                                break;
        }
    }
}


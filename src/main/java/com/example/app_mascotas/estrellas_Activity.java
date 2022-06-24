package com.example.app_mascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class estrellas_Activity extends AppCompatActivity {

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estrellas);

        ratingBar = (RatingBar)findViewById(R.id.calificacion_star);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(estrellas_Activity.this, "Usted ha votado con: "+rating, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
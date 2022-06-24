package com.example.app_mascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio_Activity extends AppCompatActivity {
    FirebaseAuth autenticacion;
    FirebaseUser usuario;
    /*videoView videoView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        /*videoView =(videoView)  findViewById(R.id.v_video);
        videoView.setVideoURI(Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.coco ));
        videoView.start();*/

        autenticacion = FirebaseAuth.getInstance();
        usuario = autenticacion.getCurrentUser();
    }

    @Override
    protected void onStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validar_s();

            }
        },(2000));
        super.onStart();
    }

    public void validar_s(){
        if(autenticacion.getCurrentUser()!=null){
            Intent intent = new Intent(Inicio_Activity.this,Home_Activity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(Inicio_Activity.this,Login_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
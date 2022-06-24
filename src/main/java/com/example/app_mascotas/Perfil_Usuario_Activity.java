package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Perfil_Usuario_Activity extends AppCompatActivity {
    TextView txt_correo_usuario, txt_nombre_usuario;
    Button btn_registrar_mascota;
    ImageView img_usuario;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mi_autenticacion;
    FirebaseUser mi_usuario;
    DatabaseReference USUARIOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        Iniciar_variables();
        Onclik();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Consultar_usuario();
    }

    private void Iniciar_variables(){
        txt_correo_usuario =(TextView)findViewById(R.id.txt_correo_usurio);
        txt_nombre_usuario=(TextView)findViewById(R.id.txt_nombre_usuario);
        img_usuario=(ImageView)findViewById(R.id.img_usuario);
        btn_registrar_mascota=(Button)findViewById(R.id.btn_registrar_mascota);
        firebaseDatabase = FirebaseDatabase.getInstance();
        USUARIOS = firebaseDatabase.getReference("USUARIOS");
        mi_autenticacion = FirebaseAuth.getInstance();
        mi_usuario = mi_autenticacion.getCurrentUser();
    }

    private void Consultar_usuario(){
        USUARIOS.orderByChild("email").equalTo(mi_usuario.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String nombre_usuario =""+ds.child("nombre").getValue();
                    String correo_electronico = ""+ds.child("email").getValue();
                    String imagen_usuario =""+ds.child("imagen").getValue();
                    asignar_datos_usuarios(nombre_usuario,correo_electronico,imagen_usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void asignar_datos_usuarios(String nombre,String correo, String imagen){
        txt_nombre_usuario.setText(nombre);
        txt_correo_usuario.setText(correo);

    }

    private void Onclik(){
        btn_registrar_mascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_mas = new Intent(getApplicationContext(), registrar_masc_Activity.class);
                startActivity(reg_mas);
            }
        });


    }



}
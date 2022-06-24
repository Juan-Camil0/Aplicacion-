package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_Activity extends AppCompatActivity implements adaptador_proveedores.item_seleccionado {

    RecyclerView Rvproveedores;
    ArrayList<Proveedor> lista_proveedores = new ArrayList<>();
    adaptador_proveedores adaptador_proveedores;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mi_autenticacion;
    FirebaseUser mi_usuario;
    DatabaseReference PROVEEDORES;
    ImageView Btn_cerrar, img_perfil;
    Button Btn_Gps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Rvproveedores = findViewById(R.id.rv_proveedores);
        firebaseDatabase = FirebaseDatabase.getInstance();
        PROVEEDORES = firebaseDatabase.getReference("Proveedores");
        mi_autenticacion = FirebaseAuth.getInstance();
        mi_usuario = mi_autenticacion.getCurrentUser();
        Btn_Gps = (Button)findViewById(R.id.Btn_Gps);
        Inicar_variables();
        Onclick();
        consulta_proveedor();
        construir_recycleView();

    }

    private  void consulta_proveedor(){
        PROVEEDORES.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String nombre = ""+ds.child("nombre").getValue().toString();
                        String url = ""+ds.child("imagen").getValue().toString();

                        Proveedor p = new Proveedor(nombre,url,"","");
                        lista_proveedores.add(p);
                    }adaptador_proveedores.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void construir_recycleView(){
        Rvproveedores.setLayoutManager(new GridLayoutManager(this,2));
        adaptador_proveedores = new adaptador_proveedores(lista_proveedores,this, this);
        Rvproveedores.setAdapter(adaptador_proveedores);

    }

    public void Onclick(){
        Btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mi_autenticacion.getInstance().signOut();
                Intent login = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(login);
                finish();

            }
        });
        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil_usuario =new Intent(getApplicationContext(),Perfil_Usuario_Activity.class);
                startActivity(perfil_usuario);

            }
        });
        Btn_Gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gps= new Intent(getApplicationContext(),Gps.class);
                startActivity(Gps);
            }
        });

    }



    private void Inicar_variables(){
        Btn_cerrar =(ImageView) findViewById(R.id.btn_cerrar);
        img_perfil =(ImageView) findViewById(R.id.img_perfil_usuario);
    }

    @Override
    public void item_seleccionado(Proveedor proveedor) {
        Intent perfil_proveedor = new Intent(getApplicationContext(),Servicio_Activity.class);
        startActivity(perfil_proveedor);
        Toast.makeText(this, proveedor.nombre, Toast.LENGTH_SHORT).show();

    }
}

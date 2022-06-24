package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Servicio_Activity extends AppCompatActivity implements adaptador_mascotas.mascota_seleccionada{

    RadioButton r1,r2,r3,r4;
    TextView estado;
    Spinner paseo,horas;
    RecyclerView Rvmascotas;
    ArrayList<Mascotas> lista_mascotas = new ArrayList<>();
    adaptador_mascotas adaptador_mascotas;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser mi_usuario;
    FirebaseAuth mi_autenticacion;
    DatabaseReference Mascota, Solicitudes;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        firebaseDatabase = firebaseDatabase.getInstance();
        Mascota = firebaseDatabase.getReference("Mascota"); //se hace esto para hacer la referencia de la tabla que se va crear
        Solicitudes = firebaseDatabase.getReference("Solicitudes");
        paseo = (Spinner)findViewById(R.id.id_spinnerpaseo); // en esta linea de codigo se distance las funciones
        horas = (Spinner)findViewById(R.id.id_spinnerhora);
        r1 = (RadioButton)findViewById(R.id.br_radio1);
        r2 = (RadioButton)findViewById(R.id.br_radio2);
        r3 = (RadioButton)findViewById(R.id.br_radio3);
        r4 = (RadioButton)findViewById(R.id.br_radio4);
        btn_registrar = (Button)findViewById(R.id.btn_registrar);
        mi_autenticacion = FirebaseAuth.getInstance();
        mi_usuario = mi_autenticacion.getCurrentUser();
        Rvmascotas =(RecyclerView)findViewById(R.id.rv_mascotas);
        iniciar_spinner();
        consulta_mascotas();

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro_bd();
                Toast.makeText(Servicio_Activity.this, "FUNCIONA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        construir_recicleView();
    }

    //recicleView
    private void consulta_mascotas(){
        String id_us = mi_usuario.getUid(); // nos trae el idea que esta identificado el usuario
        Mascota.addValueEventListener(new ValueEventListener(){
        @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {


                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ID = ds.child("Id_usuario").getValue().toString();

                    if (id_us.equals(ID)) {
                        String nombre =  ds.child("nombre").getValue().toString(); //se debe modificar
                        String url = ds.child("imagen").getValue().toString();

                        Mascotas m = new Mascotas(nombre, url);
                        lista_mascotas.add(m);
                        Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_LONG).show();
                    }

                }
                adaptador_mascotas.notifyDataSetChanged();
            }
        }

        @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void construir_recicleView(){ //dar las propiedad es para que sea horizontal
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        Rvmascotas.setLayoutManager(linearLayoutManager);
        adaptador_mascotas = new adaptador_mascotas(lista_mascotas,this,this);
        Rvmascotas.setAdapter(adaptador_mascotas);
    }

    public void registro_bd(){
        String tipo_servicio = paseo.getSelectedItem().toString().trim(); //el trim quita los espacio del inicio o el final se hace porque es un spinner
        String hora = horas.getSelectedItem().toString().trim();
        String paseos = capturar_opcionpaseo();
        String pago = tipo_de_pago();
        FirebaseUser user =mi_autenticacion.getCurrentUser();
        assert user != null;
        String id_usuario = user.getUid();
        HashMap<Object, Object> datos_servicio = new HashMap<>();
        datos_servicio.put("cantidad","1");
        datos_servicio.put("servicio",tipo_servicio);
        datos_servicio.put("hora",hora);
        datos_servicio.put("paseo",paseos);
        datos_servicio.put("pago",pago);
        datos_servicio.put("observacion","informacion");
        FirebaseDatabase bd = FirebaseDatabase.getInstance();
        Solicitudes.child(id_usuario).setValue(datos_servicio);
    }

    public String capturar_opcionpaseo(){
        String opcion = "";
        if (r1.isChecked() == true){
            r1.setError(null);
            return opcion =r1.getText().toString().trim();
        }
        else
        {
            if (r2.isChecked() == true){
             r2.setError(null);
             return opcion = r2.getText().toString().trim();
        }
        }
        return opcion;
    }

    public  String tipo_de_pago(){
        String opcion = "";
        if (r3.isChecked() == true){
            r3.setError(null);
            return  opcion = r3.getText().toString().trim();
        }
        else
        {
            if (r4.isChecked() == true){
                r4.setError(null);
                return  opcion = r4.getText().toString().trim();
            }
        }
        return  opcion;
    }

        //Spinner
    public void iniciar_spinner(){

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.tipo_paseo, android.R.layout.simple_spinner_item);
        paseo.setAdapter(adapter);

        paseo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public  void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(parent.getContext(), "Seleccionado"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                //estado.setText("seleccion: "+parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //Spinner2
        horas=(Spinner)findViewById(R.id.id_spinnerhora);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.horas, android.R.layout.simple_spinner_item);
        horas.setAdapter(adapter2);

        horas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public  void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(parent.getContext(), "Seleccionado"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                //estado.setText("seleccion: "+parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Radiobutton
        r1=(RadioButton) findViewById(R.id.br_radio1);
        r2=(RadioButton) findViewById(R.id.br_radio2);
        r3=(RadioButton) findViewById(R.id.br_radio3);
        r4=(RadioButton) findViewById(R.id.br_radio4);

    }

    /*public void onclick(View view){
        if(view.getId()==R.id.br_radio1){
            validar();
        }

    }

    private void validar(){
        String cad="Seleccionado: \n";

        if(r1.isChecked()==true){
            cad+="Opcion1\n";
        }

        if(r2.isChecked()){
            cad+="Opcion2\n";
        }
    }*/

    @Override
    public void mascota_seleccionada(Mascotas mascotas) {

    }
}
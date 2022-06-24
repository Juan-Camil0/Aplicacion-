package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class registrar_masc_Activity extends AppCompatActivity {

    ImageView Img_mascota;
    EditText edt_nombre_masc, edt_raza, edt_edad, edt_genero;
    Button btn_registrar;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mi_autenticacion;
    DatabaseReference MASCOTAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //ciclo de vida de la aplicacion -->crea lo visual  y llama el layaout creo el desarrollador
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_masc); //llena el contendor del layaout de la lista que se creo y lo trae
        instanciar_variables(); //instanciamos y llamamos los metodos
        onClick(); //activa el metodo del boton
    }


    private void instanciar_variables(){ //conectar el layout con la clase para usar sus metodos
        Img_mascota =(ImageView)findViewById(R.id.foto_mascota);
        edt_nombre_masc =(EditText)findViewById(R.id.edt_nombre_masc); //FindviewById es un conector de las variables que se creo en el layout
        edt_raza =(EditText)findViewById(R.id.edt_raza);
        edt_edad =(EditText)findViewById(R.id.edt_edad);
        edt_genero =(EditText)findViewById(R.id.edt_genero);
        btn_registrar =(Button)findViewById(R.id.btn_registrar_masc);
        firebaseDatabase = FirebaseDatabase.getInstance(); //getinstance --> hacer una conexion ha ese objeto para usar la db
        MASCOTAS = firebaseDatabase.getReference("MASCOTAS");// getreference --> llamando una tabla que vamos a crear
        mi_autenticacion = FirebaseAuth.getInstance();// conectando al usuario que ya se conecto
    }


    //en este metodo es el trae la informacion
    private void registro_dbmascotas(){
        String mascota = edt_nombre_masc.getText().toString();//creo un string para obtenga un dato que esta en el editex y lo guardo en el string mascotas
        String raza = edt_raza.getText().toString();
        String edad = edt_edad.getText().toString();
        String genero = edt_genero.getText().toString();
        Random id_aleatorio = new Random();

        int numero = id_aleatorio.nextInt(100);

        FirebaseUser user = mi_autenticacion.getCurrentUser();// para obtener los datos del usuario autenticado, los datos del usario que inicio seccion
        assert user != null;
        String id_usuario = user.getUid();// trear el id del usario que se registro

        HashMap<Object, Object> datos_mascota = new HashMap<>(); //hashmap es una caja o un contenedor para encapsular datos que se quiere enviar una base de datos
        datos_mascota.put("nombre",mascota); //tendra una etiqueta y un elemento y se envia a la base de datos
        datos_mascota.put("raza",raza);
        datos_mascota.put("edad",edad);
        datos_mascota.put("genero",genero);
        datos_mascota.put("imagen","");
        datos_mascota.put("Id_usuario",id_usuario);

        //Creo la base de datos para las mascotas
        FirebaseDatabase bd = FirebaseDatabase.getInstance(); // se instancia la informacion para conectar la base datos para enviar la informacion
        DatabaseReference reference = bd.getReference("Mascota");// se crea la referencia a la tabla donde se va enviar los datos
        reference.child(id_usuario+String.valueOf(numero)).setValue(datos_mascota).addOnCompleteListener(new OnCompleteListener<Void>() { //setvalue--> se envia los datos nuestra referencia
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
        //reference es la tabla de la mascota
    }

    //llamo el registro de mascota
    public  void onClick(){ // se crea un metodo y se usa los botones
        btn_registrar.setOnClickListener(new View.OnClickListener() { //ya se instancio el boton ya se puede usar los metodos -->setonclier escucha el boton y ejecuta su funcion
            @Override
            public void onClick(View v) {
                registro_dbmascotas();
                Toast.makeText(registrar_masc_Activity.this, "Mascota registrada", Toast.LENGTH_SHORT).show(); //para verificar si hubo conexecion
            }
        });

        }


}
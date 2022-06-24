package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Registro_Activity extends AppCompatActivity {

    Button btnregistrar;
    EditText edt_usuario, edt_email, edt_pass , edt_comfirmar_pass;
    FirebaseAuth mi_autenticacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciar_elementos();
    }

    private void iniciar_elementos(){
        btnregistrar = (Button) findViewById(R.id.btn_registrar);
        edt_usuario = (EditText) findViewById(R.id.edt_usu);
        edt_email = (EditText) findViewById(R.id.edt_correo);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        edt_comfirmar_pass = (EditText) findViewById(R.id.edt_confimar_pass);
    }

    private  void registrar_usuario(){
        mi_autenticacion = FirebaseAuth.getInstance();
        String correo = edt_email.getText().toString().trim();
        String contraseña = edt_pass.getText().toString().trim();
        Toast.makeText(this, correo+contraseña, Toast.LENGTH_LONG).show();
        mi_autenticacion.createUserWithEmailAndPassword(correo,contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    registro_bd();
                }

                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent act_Login = new Intent(Registro_Activity.this, Login_Activity.class);
                startActivity(act_Login);

            }
        });
    }

    public void registro_bd(){
        String usuario = edt_usuario.getText().toString();
        String email = edt_email.getText().toString();
        String pass = edt_pass.getText().toString();


        FirebaseUser user =mi_autenticacion.getCurrentUser();
        assert user != null;
        String id_usuario = user.getUid();

        HashMap<Object, Object> datos_usuario = new HashMap<>();
        datos_usuario.put("nombre",usuario);
        datos_usuario.put("email",email);
        datos_usuario.put("contraseña",pass);
        datos_usuario.put("uid",id_usuario);
        datos_usuario.put("imagen","");
        datos_usuario.put("telefono","");
        datos_usuario.put("direccion","");

        FirebaseDatabase bd = FirebaseDatabase.getInstance();
        DatabaseReference referencia = bd.getReference("USUARIOS");
        referencia.child(id_usuario).setValue(datos_usuario);
    }

    public  void onClick(View view){
        switch (view.getId()){
            case R.id.btn_registrar:
            {registrar_usuario();
            break;
            }
        }
    }


}
package com.example.app_mascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    Button btniniciar;
    Button btnregistro;
    FirebaseAuth autenticacion;
    EditText email, contraseña;


    // Controla la pulsacion del boton atrás

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la App?")
                    .setPositiveButton(" Si ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(" Cancelar ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    // Control Finalizado.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btniniciar = findViewById(R.id.btn_iniciar);
        email = findViewById(R.id.edt_correo);
        contraseña = findViewById(R.id.edt_pass);
        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              iniciar_s();
            }
        });
        btnregistro = findViewById(R.id.btn_registrar);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Registro_Activity.class);
                startActivity(intent);
            }
        });

    }

    public  void iniciar_s(){
        autenticacion = FirebaseAuth.getInstance();
        String correo =  email.getText().toString().trim();
        String pass =  contraseña.getText().toString().trim();

        autenticacion.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
package com.example.examen_prom_ev2_marcosvazquez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private ImageView img;
    private EditText etNombre, etPassword;
    private TextView txtConsultar;
    private Button btnLogin, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar las vistas
        btnLogin = findViewById(R.id.btnLogin);
        btnVolver = findViewById(R.id.btnVolver);
        txtConsultar = findViewById(R.id.txtConsultar);
        etNombre = findViewById(R.id.etNombre);
        etPassword = findViewById(R.id.etPassword);
        img = findViewById(R.id.imgError);

        img.setImageResource(R.drawable.imgerror);
        img.setVisibility(View.INVISIBLE);

        // Manejar las acciones de los botones
        btnLogin.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String password = etPassword.getText().toString();
            login(nombre,password);
        });

        btnVolver.setOnClickListener(v -> {
            volver(v);
        });
    }

    public void login(String nombre, String password) {
        if (nombre.equals("admin") && password.equals("admin")){
            //Creamos el Intent
            Intent intent = new Intent( LoginActivity.this,
                    AdministrationActivity.class);

            //Iniciamos / lanzamos la nueva actividad
            startActivity(intent);
        }else {
            txtConsultar.setText("Error, Nombre o Password incorrectos.");
            img.setVisibility(View.VISIBLE);
        }
    }

    public void volver(View v){
        //Creamos el Intent
        Intent intent = new Intent( LoginActivity.this,
                MainActivity.class);

        //Iniciamos / lanzamos la nueva actividad
        startActivity(intent);
    }
}
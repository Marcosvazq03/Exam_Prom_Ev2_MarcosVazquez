package com.example.examen_prom_ev2_marcosvazquez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtConsultar;
    private Button btnConsultar, btnQuimico, btnSalir;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las vistas
        btnConsultar = findViewById(R.id.btnConsultar);
        btnQuimico = findViewById(R.id.btnQuimico);
        btnSalir = findViewById(R.id.btnSalir);
        txtConsultar = findViewById(R.id.txtConsultar);


        // Manejar las acciones de los botones
        btnConsultar.setOnClickListener(v -> {
            consultar();
        });

        btnQuimico.setOnClickListener(v -> {
            quimico();
        });

        btnSalir.setOnClickListener(v -> {
            salir();
        });

        dbHelper = new DatabaseHelper(this);

        // Añadir elementos a la bd
        agregarElemento(1, "HELIO", "He", 2, "GAS");
        agregarElemento(2, "HIERRO", "Fe", 26, "SOLIDO");
        agregarElemento(3, "MERCURIO", "Hg", 80, "LIQUIDO");

        //Recuperamos la información pasada en el intento
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        if (bundle != null) {
            txtConsultar.setText("Numero de consultas: " + bundle.getString("CONTADOR"));
        }
    }

    public void agregarElemento(int id, String nombre, String simbolo, int numAtomico, String estado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nombre", nombre);
        values.put("simbolo", simbolo);
        values.put("numAtomico", numAtomico);
        values.put("estado", estado);
        db.insert("elemento", null, values);
    }

    public void consultar() {
        //Creamos el Intent
        Intent intent = new Intent( MainActivity.this,
                SearchActivity.class);

        //Iniciamos / lanzamos la nueva actividad
        startActivity(intent);
    }

    public void quimico() {
        //Creamos el Intent
        Intent intent = new Intent( MainActivity.this,
                LoginActivity.class);

        //Iniciamos / lanzamos la nueva actividad
        startActivity(intent);
    }

    public void salir() {
        Toast.makeText(getApplicationContext(), "Adios", Toast.LENGTH_SHORT).show();
        
        finishAffinity();
    }

}
package com.example.examen_prom_ev2_marcosvazquez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdministrationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etNombre, etId, etSimbolo, etNumAtomico, etEstado;
    private Button btnAgregar, btnEliminar, btnActualizar, btnVolver;
    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        // Inicializar las vistas
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etSimbolo = findViewById(R.id.etSimbolo);
        etNumAtomico= findViewById(R.id.etNumAtomico);
        etEstado = findViewById(R.id.etEstado);

        // Manejar las acciones de los botones
        btnAgregar.setOnClickListener(v -> {
            int id = Integer.parseInt(etId.getText().toString());
            String nombre = etNombre.getText().toString();
            String simbolo = etSimbolo.getText().toString();
            int numAtomico = Integer.parseInt(etNumAtomico.getText().toString());
            String estado = etEstado.getText().toString();
            agregarElemento(id, nombre, simbolo, numAtomico, estado);
        });

        btnActualizar.setOnClickListener(v -> {
            int id = Integer.parseInt(etId.getText().toString());
            String nombre = etNombre.getText().toString();
            String simbolo = etSimbolo.getText().toString();
            int numAtomico = Integer.parseInt(etNumAtomico.getText().toString());
            String estado = etEstado.getText().toString();
            actualizarElemento(id, nombre, simbolo, numAtomico, estado);
        });

        btnEliminar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            eliminarElemento(nombre);
        });

        btnVolver.setOnClickListener(v -> {
            volver(v);
        });

        dbHelper = new DatabaseHelper(this);

    }

    public void agregarElemento(int id, String nombre, String simbolo, int numAtomico, String estado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM elemento WHERE nombre='" + nombre + "'", null);
        if (cursor.moveToFirst()) {
            do {
                dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Error");
                dialogBuilder.setMessage("El elemento con ese nombre ya existe.");

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            } while (cursor.moveToNext());
        }else {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("nombre", nombre);
            values.put("simbolo", simbolo);
            values.put("numAtomico", numAtomico);
            values.put("estado", estado);
            db.insert("elemento", null, values);
            Toast.makeText(getApplicationContext(), "Elemento agregado: " + nombre, Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarElemento(int id, String nombre, String simbolo, int numAtomico, String estado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM elemento WHERE nombre='" + nombre + "'", null);
        if (cursor.moveToFirst()) {
            do {
                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("nombre", nombre);
                values.put("simbolo", simbolo);
                values.put("numAtomico", numAtomico);
                values.put("estado", estado);
                db.update("elemento", values, "nombre = ?", new String[]{String.valueOf(nombre)});
                Toast.makeText(getApplicationContext(), "Elemento actualizado: " + nombre, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }else {
            dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Error");
            dialogBuilder.setMessage("El elemento con ese nombre no existe.");

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    public void eliminarElemento(String nombre) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM elemento WHERE nombre='" + nombre + "'", null);
        if (cursor.moveToFirst()) {
            do {
                db.delete("elemento", "nombre = ?", new String[]{String.valueOf(nombre)});
                Toast.makeText(getApplicationContext(), "Elemento eliminado con nombre: " + nombre, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }else {
            dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Error");
            dialogBuilder.setMessage("El elemento con ese nombre no existe.");

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    public void volver(View v){
        //Creamos el Intent
        Intent intent = new Intent( AdministrationActivity.this,
                MainActivity.class);

        //Iniciamos / lanzamos la nueva actividad
        startActivity(intent);
    }
}
package com.example.examen_prom_ev2_marcosvazquez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private EditText etConsultar;
    private TextView txtConsultar;
    private Button btnBuscar, btnVolver;
    private DatabaseHelper dbHelper;
    private AlertDialog.Builder dialogBuilder;
    int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Inicializar las vistas
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver);
        txtConsultar = findViewById(R.id.txtConsultar);
        etConsultar = findViewById(R.id.etConsultar);

        // Manejar las acciones de los botones
        btnBuscar.setOnClickListener(v -> {
            String nombre = etConsultar.getText().toString();
            buscar(nombre);
        });

        btnVolver.setOnClickListener(v -> {
            volver(v);
        });

        dbHelper = new DatabaseHelper(this);

    }

    public void buscar(String nomElemento) {
        if (btnBuscar.getText().toString().equals("Limpiar")){
            etConsultar.setText("");
            txtConsultar.setText("");
            btnBuscar.setText("Buscar");
        }else {
            cont = cont+1;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM elemento WHERE nombre='" + nomElemento + "'", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String simbolo = cursor.getString(cursor.getColumnIndexOrThrow("simbolo"));
                    int numAtomico = cursor.getInt(cursor.getColumnIndexOrThrow("numAtomico"));
                    String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                    txtConsultar.setText("Elemento: " + id + ", " + nombre + ", " + simbolo + ", "
                            + numAtomico + ", " + estado);
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (!txtConsultar.getText().toString().equals("")) {
                btnBuscar.setText("Limpiar");
            }else {
                dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Mensaje");
                dialogBuilder.setMessage("No existe elemento");

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        }
    }

    public void volver(View v){
        //Creamos el Intent
        Intent intent = new Intent( SearchActivity.this,
                MainActivity.class);

        //Creamos la información a pasar entre actividades
        Bundle b = new Bundle();
        b.putString("CONTADOR", cont+"");

        //Añadimos la información al intento
        intent.putExtras(b);

        //Iniciamos / lanzamos la nueva actividad
        startActivity(intent);
    }
}
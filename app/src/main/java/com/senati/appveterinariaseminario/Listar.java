package com.senati.appveterinariaseminario;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {
    ListView lstMascotas;
    /**
     * dirección del endPoint donde recuperaremos todos los registros de las mascotas
     */
    private final String URL = "http://192.168.0.107:3000/mascotas"; // constante

    RequestQueue requestQueue; //Cola de pedido

    /**
     * Este metodo carga los componentes graficos
     */
    private void loadIU(){
        lstMascotas = findViewById(R.id.lstMascotas);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadIU();
        getData();
    }

    /**
     * Este método obtiene los datos de las tabla y los guarda en un ArrayJSON
     */
    private void getData() {
        // 1.- Habilitar el canal de comuniciacion
        requestQueue = Volley.newRequestQueue(this);// Usamos this y no getAplicationcontext() porque es una orden de alto nivel

        // 2.- Obtenemos los datos de las tablas en un Array JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Datos recibidos:",jsonArray.toString());
                        renderData(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error en WS: ", volleyError.toString());
                    }
                }
        );
        // 3.- Enviar la solicitud
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Cargamos los datos obtenidos al componente ListView
     * @param mascotas
     */
    private void renderData(JSONArray mascotas) {
        try {
            ArrayAdapter arrayAdapter;
            ArrayList<String> listaMascotas = new ArrayList<>();

            for (int i = 0; i<mascotas.length(); i++){
                JSONObject jsonObject = mascotas.getJSONObject(i);
                listaMascotas.add(jsonObject.getString("nombre") +" \t- \t"+jsonObject.getString("raza") +"\t - \t"+jsonObject.getString("tipo"));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaMascotas);
            lstMascotas.setAdapter(arrayAdapter);

        } catch (Exception e) {
            Log.e("Error Json recibido:", e.toString());
        }
    }
}
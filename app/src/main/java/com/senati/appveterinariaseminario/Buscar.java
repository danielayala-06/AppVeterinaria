package com.senati.appveterinariaseminario;

import android.content.DialogInterface;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscar extends AppCompatActivity {
    EditText edtId, edtNombre, edtTipo, edtRaza, edtColor, edtPeso, edtGenero;
    Button btnBuscar, btnActualizar, btnEliminar;

    private final String URL = "http://192.168.0.107:3000/mascotas/";
    RequestQueue requestQueue; //Cola de pedido

    private void loadIU(){
        edtId = findViewById(R.id.edtIdEditar);
        edtNombre = findViewById(R.id.edtNombreEditar);
        edtTipo = findViewById(R.id.edtTipoEditar);
        edtRaza = findViewById(R.id.edtRazaEditar);
        edtColor = findViewById(R.id.edtColorEditar);
        edtPeso = findViewById(R.id.edtPesoEditar);
        edtGenero = findViewById(R.id.edtGeneroEditar);

        btnBuscar = findViewById(R.id.btnBuscarId);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadIU();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchById();
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdate();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
    }

    /**
     * Abre un cuadro de dialogo para confirmar la actualización
     */
    private void confirmUpdate() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Mantenimiento de Mascotas");
        dialog.setMessage("¿Desea proceder con la actualización?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancelar", null);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateMascota();
                formClear();
            }
        });
        dialog.show();
    }

    /**
     * Envia un objeto JSON con los datos actualizados
     */
    private void updateMascota() {
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("nombre", getEdtTextText(edtNombre));
            jsonObject.put("tipo", getEdtTextText(edtTipo));
            jsonObject.put("raza", getEdtTextText(edtRaza));
            jsonObject.put("color", getEdtTextText(edtColor));
            jsonObject.put("peso", getEdtTextText(edtPeso));
            jsonObject.put("genero", getEdtTextText(edtGenero));
        } catch (JSONException e) {
            Log.e("Erro en el JSON", e.toString());
        }

        String endPoint = URL + getEdtTextText(edtId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Se actualizo el Registro", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Busca una mascta por su ID. En caso de encontrar la mascota llena los campos con los datos actuales de la mascota.
     */
    private void searchById() {
        String idMascota = edtId.getText().toString().trim();

        if(idMascota.isEmpty()){
            edtId.setError("Escriba el ID");
            edtId.requestFocus();
        }else{
            //1 .- Canal de comunicación
            requestQueue = Volley.newRequestQueue(this);
            String endPoint = URL+idMascota;

            //2 .- Solicitud
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    endPoint,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                edtNombre.setText(response.getString("nombre"));
                                edtTipo.setText(response.getString("tipo"));
                                edtRaza.setText(response.getString("raza"));
                                edtColor.setText(response.getString("color"));
                                edtPeso.setText(response.getString("peso"));
                                edtGenero.setText(response.getString("genero"));
                            } catch (Exception e) {
                                Log.e("Error JSON", e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error en WS: ", error.toString());
                            formClear();
                            edtId.requestFocus();
                            Toast.makeText(getApplicationContext(), "Mascota no encontrada", Toast.LENGTH_LONG).show();
                        }
                    }
            );


            //3 .- Envio de la solicitud
            requestQueue.add(jsonObjectRequest);
        }
    }

    /**
     * Limpia los campos del formulario
     */
    private void formClear(){
        edtId.setText(null);
        edtNombre.setText(null);
        edtTipo.setText(null);
        edtRaza.setText(null);
        edtColor.setText(null);
        edtPeso.setText(null);
        edtGenero.setText(null);
    }

    /**
     * Devuelve el texto de un componente EditText
     * @param edt
     * @return String
     */
    private String getEdtTextText(EditText edt){
        return edt.getText().toString().trim();
    }

    /**
     * Eliminamos una mascota por su id
     */
    private void deleteMascota(){
        requestQueue = Volley.newRequestQueue(this);

        String endPoint = URL + getEdtTextText(edtId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        formClear();
                        Toast.makeText(getApplicationContext(), "Mascota eliminada", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No se pudo eliminar la mascota", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Abre un dialogo para confirmar la eliminación de un registro.
     */
    private void confirmDelete(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Mantenimiento de Mascotas");
        dialog.setMessage("¿Realmente desea eliminar el Registro?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancelar", null);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMascota();
                formClear();
            }
        });
        dialog.show();
    }
}
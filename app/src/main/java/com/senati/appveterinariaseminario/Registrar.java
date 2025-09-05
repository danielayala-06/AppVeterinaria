package com.senati.appveterinariaseminario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import org.json.JSONObject;

public class Registrar extends AppCompatActivity {

    EditText edtNombre, edtColor, edtTipo, edtRaza, edtGenero, edtPeso;
    Button btnGuardar;

    private final String URL = "http://192.168.0.107:3000/mascotas"; // constante
    RequestQueue requestQueue; //Cola de pedido
    private void loadIU(){
        edtNombre = findViewById(R.id.edtNombre);
        edtColor = findViewById(R.id.edtColor);
        edtTipo = findViewById(R.id.edtTipo);
        edtRaza = findViewById(R.id.edtRaza);
        edtGenero = findViewById(R.id.edtGenero);
        edtPeso = findViewById(R.id.edtPeso);

        btnGuardar = findViewById(R.id.btnGuardar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadIU();
        textWatcher();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataWS();
            }
        });

    }

    private void sendDataWS() {
        //1.- Habilitar el servicio
        requestQueue = Volley.newRequestQueue(this);

        //1.5.- Para que este ENDPOINT /POST funcione debemos preparar un JSON con los datos
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("tipo", edtTipo.getText().toString());
            jsonObject.put("raza", edtRaza.getText().toString());
            jsonObject.put("color", edtColor.getText().toString());
            jsonObject.put("peso", Double.parseDouble(edtPeso.getText().toString()));
            jsonObject.put("genero", edtGenero.getText().toString());

        } catch (Exception e) {
            Log.e("Error JSON envio", e.toString());
        }

        //2.- Tipp de dato obtenido y párametros
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, //Verbo
                URL, //ENDPOINT
                jsonObject, //JSON
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(getApplicationContext(), "Mascota Registrada con Exito", Toast.LENGTH_SHORT).show();
                        Log.d("Id Obtenido", jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
                        Log.e("Error al enviar el json", volleyError.toString());
                    }
                }
        );

        //3.- Envio
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Este metodo comprueba si hay datos vacios, en caso haya un campo sin llenar devuelve true.
     * @return
     */
    private boolean hayCamposVacios(){
        if(!edtNombre.getText().toString().isEmpty()
        && !edtTipo.getText().toString().isEmpty()
        && !edtRaza.getText().toString().isEmpty()
        && !edtColor.getText().toString().isEmpty()
        && !edtPeso.getText().toString().isEmpty()
        && !edtGenero.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * Este metodo permite activar o desactivar el boton guardar
     * @param bool
     */
    private void toogleButtonGuardar(boolean bool){
        btnGuardar.setEnabled(bool);
    }

    /**
     * Este metodo llama a la funcion para activar o desctivar el boton de guardar.
     */
    private void validarDatos(){
        toogleButtonGuardar(!hayCamposVacios());
    }

    /**
     * Este metodo observa los cambios en los EditText y activa o desctiva el boton para guardar un registro.
     */
    private void textWatcher(){
        edtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        edtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        edtColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        edtRaza.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
               validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        edtTipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        edtGenero.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                validarDatos();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
    }
}
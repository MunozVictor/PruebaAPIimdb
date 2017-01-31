package com.example.victor.pruebaactores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InfoActorActivity extends AppCompatActivity {
    // key : 869d9232-de37-4b73-9c8e-312ed5edfe44
    String key ="869d9232-de37-4b73-9c8e-312ed5edfe44";
    String forecastURL ;
    String TAG = "INFOACTOR:";
    Forecast forecast;
    TextView tvNombreActor , tvBiografia;
    Button btnFilmografia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_actor);
        Intent i = getIntent();

        String codigo = i.getStringExtra(MainActivity.CODIGO_ELEGIDO);

        forecastURL ="http://imdb.wemakesites.net/api/"+codigo+"?api_key="+key;
        tvBiografia = (TextView) findViewById(R.id.tvBiografia);
        tvNombreActor = (TextView) findViewById(R.id.tvNombreActor);
        btnFilmografia = (Button) findViewById(R.id.btnFilmografia);

        obtenerForecast();




    }

    private void obtenerForecast() {
       if(isNetworkAvailable()){
           OkHttpClient client = new OkHttpClient();//se importa la libreria que hemos añadido en el gradle moduleapp
           Request request = new Request.Builder().url(forecastURL).build();
           //se crea la consulta a la que pasamos la url
           Call call = client.newCall(request);
           call.enqueue(new Callback() {
               @Override
               public void onFailure(Request request, IOException e) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                          // imageRefresh.setVisibility(View.VISIBLE);
                           //progresBar.setVisibility(View.INVISIBLE);
                       }
                   });

               }

               @Override
               public void onResponse(Response response) throws IOException {
                   try {

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               //imageRefresh.setVisibility(View.VISIBLE);
                              // progresBar.setVisibility(View.INVISIBLE);
                           }
                       });

                       //los lopgs a la hora de querer subir la apk no permite se sustituyen , no esta permitido
                       //Response response = call.execute();//este proceso debe esperar a que el layout se carga por lo que hay que ponerlo en cola
                       String jsonData=response.body().string();//solo se puede hacer un response
                       Log.v(TAG, jsonData);//devuelve los daros del archivo json con los datos del api que estamos usando
                       if (response.isSuccessful()) {
                           forecast = crearForecast(jsonData);
                           cargarDatos();
                       } else {
                           //SALTARA CUANDO HAYA UN NOT FOUND
                           alertUserAboutError();
                       }
                   } catch (IOException e) {
                       Log.e(TAG, "Excepcion: Entrada / Salida", e);
                   }catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           });

       }else{
           Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show();
       }


    }

    private void cargarDatos() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tvNombreActor.setText(forecast.getNombreActor());
                tvBiografia.setText(forecast.getBiografia());
            }
        });
    }

    private Forecast crearForecast(String jsonData) throws JSONException {
        forecast = new Forecast();
        forecast.setNombreActor(getNombreActor(jsonData));
        forecast.setBiografia(getBiografia(jsonData));
        forecast.setPeliculas(getPeliculas(jsonData));
        return forecast;
    }

    private Pelicula[] getPeliculas(String jsonData) {
        //// TODO: 31/01/2017
        return null;
    }

    private String getBiografia(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject data = forecast.getJSONObject("data");
        String bio = data.getString("description");
        return bio;
    }

    private String getNombreActor(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject data = forecast.getJSONObject("data");
        String bio = data.getString("title");
        return bio;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();//hay que habilitar permisos en el manifest
        boolean isAvailable=false;
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "Error dialog:");
    }


}

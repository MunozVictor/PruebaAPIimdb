package com.example.victor.pruebaactores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class InfoActorActivity extends AppCompatActivity {
    public static final String LISTA_PELICULAS = "LISTA_PELICULAS";
    // key : 869d9232-de37-4b73-9c8e-312ed5edfe44

    //http://imdb.wemakesites.net/api/nm0000314?api_key=869d9232-de37-4b73-9c8e-312ed5edfe44
    String key ="869d9232-de37-4b73-9c8e-312ed5edfe44";
    String filmografiaURL ;
    String TAG = "INFOACTOR:";
    Filmografia filmografia;
    TextView tvNombreActor , tvBiografia;
    Button btnFilmografia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_actor);
        Intent i = getIntent();

        String codigo = i.getStringExtra(MainActivity.CODIGO_ELEGIDO);

        filmografiaURL ="http://imdb.wemakesites.net/api/"+codigo+"?api_key="+key;
        tvBiografia = (TextView) findViewById(R.id.tvBiografia);
        tvNombreActor = (TextView) findViewById(R.id.tvNombreActor);
        btnFilmografia = (Button) findViewById(R.id.btnFilmografia);
        btnFilmografia.setVisibility(View.INVISIBLE);

        obtenerfilmografia();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();//para probar este metodo , termina apliacion en la pantalla del actor
    }

    private void obtenerfilmografia() {
       if(isNetworkAvailable()){
           OkHttpClient client = new OkHttpClient();//se importa la libreria que hemos añadido en el gradle moduleapp
           Request request = new Request.Builder().url(filmografiaURL).build();
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

                       if(statusOk(jsonData)){

                           if (response.isSuccessful()) {
                               filmografia = crearfilmografia(jsonData);
                               cargarDatos();

                           } else {
                               //SALTARA CUANDO HAYA UN NOT FOUND
                               alertUserAboutError();
                           }

                       }else{
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   tvBiografia.setText("Codigo no correcto");

                               }
                           });

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

    private boolean statusOk(String jsonData) throws JSONException {
        boolean statusOk = false;
        JSONObject filmografia = new JSONObject(jsonData);
        String stat = filmografia.getString("status");
        if(stat.equals("success")){
            statusOk = true;
        }
        return statusOk;
    }

    private void cargarDatos() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                btnFilmografia.setVisibility(View.VISIBLE);
                tvNombreActor.setText(filmografia.getNombreActor());
                tvBiografia.setText(filmografia.getBiografia());
            }
        });
    }

    private Filmografia crearfilmografia(String jsonData) throws JSONException {
        filmografia = new Filmografia();
        filmografia.setNombreActor(getNombreActor(jsonData));
        filmografia.setBiografia(getBiografia(jsonData));
        filmografia.setPeliculas(getPeliculas(jsonData));
        return filmografia;
    }

    private ArrayList<Pelicula> getPeliculas(String jsonData) throws JSONException {
        JSONObject filmografia = new JSONObject(jsonData);
        Log.i(TAG,"filmografia : "+filmografia);
        JSONObject data = filmografia.getJSONObject("data");
        Log.i(TAG,"data : "+data);

        JSONArray filmography = data.getJSONArray("filmography");
        Log.i(TAG,"filmography : "+filmography);
        //Pelicula [] peliculas = new Pelicula[filmografia.length()];
        JSONObject peliJSON;
        ArrayList<Pelicula>peliculas= new ArrayList<>();
        Pelicula peli;
        for(int i = 0; i<filmography.length();i++){
            peliJSON = filmography.getJSONObject(i);
            peli = new Pelicula();
            peli.setTitulo(peliJSON.getString("title"));
            peli.setAnio(peliJSON.getString("year"));
            peliculas.add(peli);
        }
        return peliculas;
    }

    private String getBiografia(String jsonData) throws JSONException {
        JSONObject filmografia = new JSONObject(jsonData);
        JSONObject data = filmografia.getJSONObject("data");
        String bio = data.getString("description");
        return bio;
    }

    private String getNombreActor(String jsonData) throws JSONException {
        JSONObject filmografia = new JSONObject(jsonData);
        JSONObject data = filmografia.getJSONObject("data");
        String nombre = data.getString("title");
        return nombre;
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
    public void verFilmografia(View v){
        Intent i = new Intent(this,FilmografiaActivity.class);
        for (int t =0;t<filmografia.getPeliculas().size();t++){
            Log.i(TAG,"PELICULA : "+filmografia.getPeliculas());

        }
        i.putParcelableArrayListExtra(LISTA_PELICULAS,filmografia.getPeliculas());
        startActivity(i);
    }

    //Prueba de un menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.nuevaBusqueda){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        return true;
    }



}

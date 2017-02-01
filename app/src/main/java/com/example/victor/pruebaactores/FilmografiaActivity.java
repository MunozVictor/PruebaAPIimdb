package com.example.victor.pruebaactores;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FilmografiaActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Pelicula>peliculas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmografia);

        listView = (ListView) findViewById(R.id.listView);

        Intent i = getIntent();
        peliculas = i.getParcelableArrayListExtra(InfoActorActivity.LISTA_PELICULAS);
        PeliculasAdapter adapter = new PeliculasAdapter(this,peliculas);
        listView.setAdapter(adapter);



    }
}

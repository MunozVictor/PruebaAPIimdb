package com.example.victor.pruebaactores;

import java.util.ArrayList;

/**
 * Created by Victor on 31/01/2017.
 */

public class Filmografia {
    String nombreActor;
    String biografia;
    ArrayList <Pelicula> peliculas;

    public String getNombreActor() {
        return nombreActor;
    }

    public void setNombreActor(String nombreActor) {
        this.nombreActor = nombreActor;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}

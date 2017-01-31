package com.example.victor.pruebaactores;

/**
 * Created by Victor on 31/01/2017.
 */

public class Forecast {
    String nombreActor;
    String biografia;
    Pelicula [] peliculas;

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

    public Pelicula[] getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Pelicula[] peliculas) {
        this.peliculas = peliculas;
    }
}

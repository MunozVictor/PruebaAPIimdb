package com.example.victor.pruebaactores;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor on 31/01/2017.
 */

public class Pelicula implements Parcelable{
    String titulo;
    String anio;


    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(anio);

    }

    public Pelicula (Parcel in){
        titulo = in.readString();
        anio = in.readString();
    }
    public Pelicula(){}
}

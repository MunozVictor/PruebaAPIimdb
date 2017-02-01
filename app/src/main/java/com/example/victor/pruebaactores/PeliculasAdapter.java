package com.example.victor.pruebaactores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 21542295 on 01/02/2017.
 */
public class PeliculasAdapter extends BaseAdapter{

    private ArrayList<Pelicula> peliculas = new ArrayList<>();
    private Context contexto;

    public PeliculasAdapter(Context contexto , ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return peliculas.size();
    }

    @Override
    public Object getItem(int i) {
        return peliculas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertview ==null){
            convertview = LayoutInflater.from(contexto).inflate(R.layout.item_peli,null);
            holder = new ViewHolder();
            holder.tvAnio = (TextView) convertview.findViewById(R.id.tvAnioPelicula);
            holder.tvTitulo = (TextView) convertview.findViewById(R.id.tvTituloPelicula);
            convertview.setTag(holder);

        }else{
            holder = (ViewHolder) convertview.getTag();
        }

        holder.tvAnio.setText(peliculas.get(i).getAnio());
        holder.tvTitulo.setText(peliculas.get(i).getTitulo());

        return convertview;
    }
    public class ViewHolder{
        TextView tvAnio , tvTitulo;
    }
}

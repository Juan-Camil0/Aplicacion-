package com.example.app_mascotas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class adaptador_mascotas extends RecyclerView.Adapter<adaptador_mascotas.ViewHolder>  {
    ArrayList<Mascotas> listamascotas;
    Activity activity;
    private mascota_seleccionada mascota_seleccionada;

    public adaptador_mascotas(ArrayList<Mascotas> lista_mascotas, Activity activity, mascota_seleccionada item_seleccionado){
        this.listamascotas = lista_mascotas;
        this.activity = activity;
        this.mascota_seleccionada = item_seleccionado; //this -->cuando cree el adaptador traerlo
    }

    @NonNull
    @Override
    public adaptador_mascotas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascotas, parent,false);
        return new ViewHolder(vista);
    }
    //onBindViewHolder -->se puede utilizar todos los elementos que esta en viewHolder
    @Override
    public void onBindViewHolder(@NonNull adaptador_mascotas.ViewHolder holder, int position) {
        holder.txt_nombre_mascota.setText(listamascotas.get(position).getNombre()); //entre al holder y traer el elemento txt y setText--> envie el texto
        if(listamascotas.get(position).getImagen().toString()!=""){ //Glide-->es una libreria que permite imagenes circulares
            Glide.with(activity).load(listamascotas.get(position).getImagen()).apply(RequestOptions.circleCropTransform()).into(holder.img_mascotas);
        }
    }
    //getItemCount-->se crea un adaptador y quiero saber el tamaño que tiene el numero de items que tiene el recyclerView
    @Override
    public int getItemCount() {
        return listamascotas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_mascotas;
        TextView txt_nombre_mascota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_mascotas = itemView.findViewById(R.id.img_mascotas);
            txt_nombre_mascota = itemView.findViewById(R.id.txt_nombre_mascota);
            itemView.setOnClickListener(new View.OnClickListener() { //si toca ese objeto quiero que realice algo o active el metodo
                @Override
                public void onClick(View v) {
                    mascota_seleccionada.mascota_seleccionada(listamascotas.get(getAdapterPosition()));//cuando se haga click en un item nos indique cual es la posicion del item que se acaba de seleccionar para urar su informacion

                }
            });
        }
    }

    public interface mascota_seleccionada
            {
    void mascota_seleccionada(Mascotas mascotas);
            }
}



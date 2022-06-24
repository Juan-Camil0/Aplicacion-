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

public class adaptador_proveedores extends RecyclerView.Adapter<adaptador_proveedores.ViewHolder> {
    ArrayList<Proveedor> listaproveedores;
    Activity activity; //se usa para usar metodos en que actividad quiero en donde funcione
    private item_seleccionado item_seleccionado;

    public adaptador_proveedores(ArrayList<Proveedor> proveedores_caninos, Activity activity, item_seleccionado item_seleccionado){
        this.listaproveedores = proveedores_caninos;
        this.activity = activity;
        this.item_seleccionado = item_seleccionado; //this -->cuando cree el adaptador traerlo

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedores, parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_nombre.setText(listaproveedores.get(position).getNombre());
        if(listaproveedores.get(position).getImagen().toString()!=""){
            Glide.with(activity).load(listaproveedores.get(position).getImagen()).apply(RequestOptions.circleCropTransform()).into(holder.img_perfil);
        }

    }

    @Override
    public int getItemCount() {
        return listaproveedores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_perfil;
        TextView txt_nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_perfil = itemView.findViewById(R.id.img_perfil);
            txt_nombre = itemView.findViewById(R.id.txt_nombre);
            itemView.setOnClickListener(new View.OnClickListener() { //si toca ese objeto quiero que realice algo o active el metodo
                @Override
                public void onClick(View v) {
                    item_seleccionado.item_seleccionado(listaproveedores.get(getAdapterPosition()));//cuando se haga click en un item nos indique cual es la posicion del item que se acaba de seleccionar para urar su informacion

                }
            });
        }
    }

    public interface item_seleccionado
    {
        void item_seleccionado(Proveedor proveedor);
    }



}

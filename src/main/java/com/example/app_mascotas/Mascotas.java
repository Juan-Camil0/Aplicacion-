package com.example.app_mascotas;

public class Mascotas {
    String nombre, imagen;

    public Mascotas(String nombre,String imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre(){ return  nombre;}
    public String getImagen(){ return  imagen;}

    //los metodos get permite traer ese metodo
    // los metodos set permite enviar al metodo información
}

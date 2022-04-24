package com.example.exam3panny.configuracion;

import java.io.Serializable;

public class medica implements Serializable {

    private int id;
    private String descripcion;
    private String cantidad;
    private String periocidad;
    private String tiempo;
    private String imagen;

    public medica(int id, String descripcion, String cantidad, String periocidad, String tiempo, String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.periocidad = periocidad;
        this.tiempo = tiempo;
        this.imagen = imagen;
    }

    public medica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPeriocidad() {
        return periocidad;
    }

    public void setPeriocidad(String periocidad) {
        this.periocidad = periocidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTerminacion(){
        String terminacion=""+
                tiempo.charAt(tiempo.length()-3)+
                tiempo.charAt(tiempo.length()-2)
                ;

        return terminacion;
    }

    @Override
    public String toString() {
        return  descripcion + " - " + periocidad + " - " + tiempo;
    }


}

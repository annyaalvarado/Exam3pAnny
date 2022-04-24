package com.example.exam3panny.configuracion;

public class bdTransaccione {
    public static final String NAME_DATABASE = "Exam3parcialA";

    //Creacion de la tabla persona en la base de datos
    public static final String TABLA_Medicamentos = "medicamentos";

    //Creacion de los atributos de la tabla
    public static final String ID = "id";
    public static final String Descripcion = "descripcion";
    public static final String Cantidad = "cantidad";
    public static final String Tiempo = "tiempo";
    public static final String Periocidad = "periocidad";
    public static final String IMAGEN = "imagen";


    //Creacion y eliminacion de la tabla

    public static final String CREATE_TABLE_Medicamentos = "CREATE TABLE " + TABLA_Medicamentos +
            "("+
            ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Descripcion +" TEXT, "+
            Cantidad +" TEXT, "+
            Tiempo +" TEXT, "+
            Periocidad +" TEXT, "+
            IMAGEN +" TEXT"+
            ")";
    public static final String DROP_TABLE_Medicamentos = "DROP TABLE IF EXIST " + TABLA_Medicamentos;

    //Seleccionar todas las personas
    public static final String SELECT_TABLE_Medicamentos = "SELECT * FROM " + TABLA_Medicamentos;
}

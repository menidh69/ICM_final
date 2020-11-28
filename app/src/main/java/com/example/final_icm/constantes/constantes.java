package com.example.final_icm.constantes;

public class constantes {
    public static final String crear_tabla_cliente = "CREATE TABLE cliente (id INTEGER PRIMARY KEY, nombre VARCHAR(20), telefono VARCHAR(10))";
    public static final String crear_tabla_servicios = "CREATE TABLE servicios (id INTEGER PRIMARY KEY, nombre varchar(20) NOT NULL, descripcion varchar(50), precio FLOAT(15,2))";
}

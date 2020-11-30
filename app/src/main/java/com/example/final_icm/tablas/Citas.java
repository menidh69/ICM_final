package com.example.final_icm.tablas;

public class Citas {
    private Integer id;
    private String nombre;
    private String servicio;
    private float precio;
    private String fecha;
    private String hora;
    private Integer finalizado;
    private String metodo;

    public Citas(Integer id, String nombre, String servicio, float precio, String fecha, String hora) {
        this.id = id;
        this.nombre = nombre;
        this.servicio = servicio;
        this.precio = precio;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Citas() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Integer finalizado) {
        this.finalizado = finalizado;
    }


    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}

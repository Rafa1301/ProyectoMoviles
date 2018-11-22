package com.example.rafao.proyectomoviles.Models;

public class Productos {
    public String Codigo;
    public int Dependencia;
    public String Descripcion;
    public int Estado;

    public Productos(String codigo, int dependencia, String descripcion, int state) {
        Codigo = codigo;
        Dependencia = dependencia;
        Descripcion = descripcion;
        Estado = state;
    }

    public Productos(){}

}

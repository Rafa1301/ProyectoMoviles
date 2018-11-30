package com.example.rafao.proyectomoviles.Models;

import java.io.Serializable;

public class Productos implements Serializable {
    public String id;
    public String Codigo;
    public int Dependencia;
    public String Descripcion;
    public int Estado;
    public int  Inventario;

    public Productos(String codigo, int dependencia, String descripcion, int state, int inventario) {
        Codigo = codigo;
        Dependencia = dependencia;
        Descripcion = descripcion;
        Estado = state;
        Inventario = inventario;
    }

    public Productos(){}

}

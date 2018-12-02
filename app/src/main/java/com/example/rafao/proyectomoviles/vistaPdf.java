package com.example.rafao.proyectomoviles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class vistaPdf extends AppCompatActivity {
    private String []head = {"Id","Nombre","Apellido"};
    private String shortext = "Hola";
    private String Longtex ="akjdshakjsljfaksjdjlaskjdlaskjdaslkjlaskdjlasd";
    private TemplatePDF templatePDF;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistapdf);
        templatePDF =new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMeta("Clientes","ventas", "autor");
        templatePDF.addTitles("tienda","clientes", "1-12-2018");
        templatePDF.addParra(shortext);
        templatePDF.addParra(Longtex);
        templatePDF.createTable(head,getClientes());
        templatePDF.closeDocument();


    }

    public void pdfview(View view){
        templatePDF.viewPdf();
    }

    private ArrayList<String[]>getClientes(){
        ArrayList<String []>rows = new ArrayList<>();

        rows.add(new String[]{"1","Pedro","Lopez"});
        rows.add(new String[]{"2","Rafa","Ortiz"});
        rows.add(new String[]{"3","Ricardo","Sol"});
        return rows;
    }
}

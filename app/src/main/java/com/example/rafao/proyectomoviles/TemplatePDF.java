package com.example.rafao.proyectomoviles;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font ftitle = new Font(Font.FontFamily.HELVETICA,20,Font.BOLD);
    private Font fsubtitle = new Font(Font.FontFamily.HELVETICA,16,Font.BOLD);
    private Font ftext = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD);
    private Font fhigh = new Font(Font.FontFamily.HELVETICA,15,Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context  context) { //el constructor recibe el contexto no se de que :'v no lo explica
        this.context =context;  //para compartir el contexto con la clase
    }

    public void openDocument(){//escribir en el documento
        createFile();
        try{
            document = new Document(PageSize.A4); //creado el documento
            pdfWriter = new PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    private void createFile(){
        //creamos carpeta dentro del dispositivo
        File folder = new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists())
            folder.mkdir(); //esto si no existe el folder lo crea
        pdfFile = new File(folder, "TemplatePDF.pdf"); //crea el archivo

    }

    public void closeDocument(){ //esta madre sirve para cerrar
        document.close();
    }

    public void addMeta(String title, String subjet, String autor){//esto agrega los metadatos.
        document.addTitle(title);
        document.addSubject(subjet);
        document.addAuthor(autor);
    }

    public void addTitles(String title, String sub, String fecha){//esto agrega los parrafos
        try{
         paragraph = new Paragraph();
         addChildP(new Paragraph(title,ftitle));
         addChildP(new Paragraph(sub,fsubtitle));
         addChildP(new Paragraph("Generado: " +fecha,fhigh));
         paragraph.setSpacingAfter(30);
         document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles",e.toString());
        }

    }
    private void addChildP(Paragraph child){ //parrafos hijos de los del padre
        child.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(child);
    }

    public void addParra (String text){ //hacemos un parrafo mistico
        try{
        paragraph = new Paragraph(text,ftext);
        paragraph.setSpacingAfter(5);
        paragraph.setSpacingBefore(5);
        document.add(paragraph);
        }catch (Exception e){
            Log.e("addParra",e.toString());
        }
    }

    public void createTable(String []Head, ArrayList<String[]>cliente){//crear tablas misticas
        try{
        paragraph = new Paragraph();
        paragraph.setFont(ftext);
        PdfPTable pdfPTable = new PdfPTable(Head.length);
        pdfPTable.setWidthPercentage(100);
        PdfPCell pdfPCell;
        int index =0; //numero de columnas
        while (index < Head.length){ //aqui llenas encabezados
            pdfPCell = new PdfPCell(new Phrase(Head[index++],fsubtitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GREEN);
            pdfPTable.addCell(pdfPCell);
        }

        for(int i =0; i<cliente.size();i++){//esto recorre las filas
            String []row = cliente.get(i);
            for(index =0; i<Head.length;index++) { //ahora columnas con crema
                pdfPCell = new PdfPCell(new Phrase(row[index]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);
                pdfPTable.addCell(pdfPCell);

            }
        }
        //al parrafo le añadimos la tabla y al documento le añadimos el parrafo
        paragraph.add(pdfPTable);
        document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }

    public void viewPdf(){ //para que se vea el documento
        Intent intent =new Intent(context,ViewPDFActivity.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

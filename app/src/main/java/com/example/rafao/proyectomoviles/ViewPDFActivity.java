package com.example.rafao.proyectomoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewPDFActivity extends AppCompatActivity {
    private PDFView pdfView;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView =(PDFView)findViewById(R.id.pdfview);


    Bundle bundle = getIntent().getExtras();
    if(bundle != null){
        file = new File(bundle.getString("path",""));
    }
    pdfView.fromFile(file).enableSwipe(true).swipeHorizontal(true).enableDoubletap(true).load(); // cambair hojas del pdf vertical
    }
}

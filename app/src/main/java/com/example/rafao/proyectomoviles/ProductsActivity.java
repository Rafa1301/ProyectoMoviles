package com.example.rafao.proyectomoviles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rafao.proyectomoviles.Models.Productos;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private EditText code,desc;
    private int dep;
    private Productos pr;
    private FirebaseDatabase database;
    private DatabaseReference root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_product);

        database = FirebaseDatabase.getInstance();
        root = database.getReference("/Productos");

        pr = (Productos) getIntent().getSerializableExtra("Producto");

        code = findViewById(R.id.code);
        desc = findViewById(R.id.desc);
        btn1 = findViewById(R.id.addmas);

        if(pr == null) {
            dep = getIntent().getIntExtra("code", 0);
        }else{
            code.setText(pr.Codigo);
            desc.setText(pr.Descripcion);
            btn1.setText("Actualizar");
        }

        btn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String code = this.code.getText().toString();
        String desc = this.desc.getText().toString();

        if(v.getId() == R.id.addmas){

            HashMap<String, Object> hashMap = new HashMap<>();

            if(pr == null) {
                Productos newProduct = new Productos(code, dep, desc, 1, 0);
                String id = root.push().getKey();
                newProduct.id = id;
                hashMap.put(id, newProduct);
            }else{
                pr.Codigo = code;
                pr.Descripcion = desc;
                hashMap.put(pr.id, pr);
            }

            root.updateChildren(hashMap)
                    .addOnSuccessListener(aVoid -> {
                        this.code.setText("");
                        this.desc.setText("");
                    });

        }
    }
}

package com.example.rafao.proyectomoviles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rafao.proyectomoviles.Models.Productos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsList extends AppCompatActivity implements TextWatcher {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private Button btn;
    private EditText edit;
    private RecyclerView rc;
    private int code;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference root = database.getReference("/Productos");

    private ArrayList<Productos> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_product);

        code = getIntent().getIntExtra("code",0);
        //btn = findViewById(R.id.voz);
        //btn.setOnClickListener(v -> startVoiceRecognitionActivity());

        edit = findViewById(R.id.buscar);
        edit.addTextChangedListener(this);

        rc = findViewById(R.id.recyclerP);
        rc.setLayoutManager (new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false));
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Productos product = dt.getValue(Productos.class);
                    if(product.Dependencia == code){
                        list.add(product);
                    }
                }
                rc.setAdapter (new ProductListAdapter(getApplicationContext(), list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Definimos el mensaje que aparecerá
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"¿Que desea realizar?");
        // Lanzamos la actividad esperando resultados
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);
            String [] palabras = matches.get(0).split(" ");
            Log.i("CRUD",palabras[0]);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String search = s.toString();
        if(!search.equals("")){
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        Productos product = dt.getValue(Productos.class);
                        if(product.Dependencia == code && product.Descripcion.contains(search)){
                            list.add(product);
                        }
                    }
                    rc.setAdapter (new ProductListAdapter(getApplicationContext(), list));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        Productos product = dt.getValue(Productos.class);
                        if(product.Dependencia == code){
                            list.add(product);
                        }
                    }
                    rc.setAdapter (new ProductListAdapter(getApplicationContext(), list));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder>{

    private Context _context;
    private ArrayList<Productos> pr;

    ProductListAdapter(Context c, ArrayList<Productos> pr){
        _context = c;
        this.pr = pr;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (_context).inflate (R.layout.product_list_item, viewGroup, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder productListViewHolder, int i) {
        productListViewHolder.bind(pr.get(i));
    }

    @Override
    public int getItemCount() {
        return pr.size();
    }
}

class ProductListViewHolder extends RecyclerView.ViewHolder {

    private Switch sw;
    private TextView tv;

    public ProductListViewHolder(@NonNull View itemView) {
        super(itemView);
        sw = itemView.findViewById(R.id.switch2);
        tv = itemView.findViewById(R.id.textView2);
    }

    @SuppressLint("ResourceAsColor")
    public void bind(Productos productos) {
        String txt = productos.Descripcion;
        tv.setText(txt);
        tv.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ProductsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("Producto", productos);
            v.getContext().startActivity(i);
        });
        tv.setTextColor(R.color.black);
        if (productos.Estado == 1) {
            sw.setChecked(true);
        } else {
            sw.setChecked(false);
        }
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (sw.isChecked() == true) {
                productos.Estado = 1;
            } else {
                productos.Estado = 0;
            }
            DatabaseReference root = FirebaseDatabase.getInstance().getReference("/Productos").child(productos.id);
            root.setValue(productos);
        });
    }
}

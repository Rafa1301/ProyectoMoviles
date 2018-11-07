package com.example.rafao.proyectomoviles;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginuv);
    }

    //Se activa al dar click sobre el textView para logear
    public void perform_action(View v)
    {
        TextView tv= (TextView) findViewById(R.id.textView2);
    }

}

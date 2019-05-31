package com.example.mi_album;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class acercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
    }

    public void salir(View v){
        finish();

    }
}

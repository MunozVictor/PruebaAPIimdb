package com.example.victor.pruebaactores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String CODIGO_ELEGIDO ="CODIGO_ELEGIDO" ;
    Button btnInfoActor;
    EditText etCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInfoActor = (Button) findViewById(R.id.btnBuscar);
        etCodigo = (EditText) findViewById(R.id.etCodigo);

    }

    public void verInfoActor(View v){
        Intent i = new Intent(this,InfoActorActivity.class);
        i.putExtra(CODIGO_ELEGIDO,etCodigo.getText().toString());
        startActivity(i);

    }
}

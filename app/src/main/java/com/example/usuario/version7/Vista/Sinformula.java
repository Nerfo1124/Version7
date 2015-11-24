
package com.example.usuario.version7.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.usuario.version7.R;

public class Sinformula extends AppCompatActivity {

    private EditText texto;
    private  String [] dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinformula);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        texto = (EditText)findViewById(R.id.txttexto);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getStringArray("1 2 3");
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed(); }
        return super.onOptionsItemSelected(item); }

    public void siguiente (View v){
        dato[9]=""+texto.getTextSize();
        Intent intento = new Intent(getApplicationContext(),Frecuencia.class);
        intento.putExtra("1 2 3",dato);
        startActivity(intento);
    }
    public void mas(View v){
        float t= texto.getTextSize();
        texto.setTextSize(t + 2);
    }

    public void menos(View v){
        float t= texto.getTextSize();
        texto.setTextSize(t-2);
    }
}

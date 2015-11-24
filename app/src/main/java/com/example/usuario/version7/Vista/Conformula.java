package com.example.usuario.version7.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.usuario.version7.R;

public class Conformula extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar barra, barra2;
    private TextView iz,de;
    private String[]dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conformula);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        barra = (SeekBar)findViewById(R.id.barra);
        barra.setOnSeekBarChangeListener(this);
        barra2 = (SeekBar)findViewById(R.id.barra2);
        barra2.setOnSeekBarChangeListener (this);
        iz = (TextView)findViewById(R.id.lblizq);
        de = (TextView)findViewById(R.id.lblder);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getStringArray("1 2 3");
        //Toast.makeText(this, dato[7], Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed(); }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(barra))
            iz.setText(""+progress);
        if(seekBar.equals(barra2))
            de.setText(""+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    public void siguiente(View v){
        dato[10]=de.getText().toString();
        dato[11]=iz.getText().toString();
        Intent intento = new Intent(getApplicationContext(),Frecuencia.class);
        intento.putExtra("1 2 3", dato);
        startActivity(intento);
    }

    public void mas(View v){
        barra.setProgress(barra.getProgress() + 1);
    }
    public void menos(View v){
        barra.setProgress(barra.getProgress() - 1);
    }
    public  void mas2(View v){
        barra2.setProgress(barra2.getProgress() + 1);
    }
    public  void menos2(View v){
        barra2.setProgress(barra2.getProgress() - 1);
    }
}

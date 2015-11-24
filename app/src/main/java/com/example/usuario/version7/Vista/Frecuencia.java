package com.example.usuario.version7.Vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.usuario.version7.BD.DBManager;
import com.example.usuario.version7.R;

public class Frecuencia extends AppCompatActivity {

    private NumberPicker fre;
    private String[] dato;
    private DBManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frecuencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manager = new DBManager(this);
        fre= (NumberPicker)findViewById(R.id.numero);
        fre.setMaxValue(24);
        fre.setMinValue(1);
        fre.setWrapSelectorWheel(false);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getStringArray("1 2 3");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed(); }
        return super.onOptionsItemSelected(item);
    }


    public void terminar(View v){
        try {
            dato[12] = "" + fre.getValue();
            int a = manager.insertar(dato[0], dato[1], dato[2], dato[3], dato[4], dato[5], dato[6], dato[7], dato[8],dato[9],
                    dato[10],dato[11],dato[12]);
            if (a >= 1)
                Dialogo("Mensaje", "Se han guardado los datos con exito, BIENVENIDO: " + dato[0] + "\n " + dato[1] + "\n" + dato[2] +
                        "\n" + dato[3] + "\n" + dato[4] + "\n" + dato[5] + "\n" + dato[6] + "\n" + dato[7] + "\n" +
                        dato[8] + "\n" + dato[9] + "\n" + dato[10]+ "\n" + dato[11]+ "\n"+dato[12],1);
            else
                Dialogo("Mensaje","Se ha generado un error durante el registo,\npor favor empieze de nuevo",2);

        }catch (Exception e){
            Toast.makeText(this, "Error Frecuencia - Terminar:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Dialogo(String tit, final String men, final int opc){
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (opc == 1) {
                                Intent intento = new Intent(getApplicationContext(), Inicio.class);
                                intento.putExtra("1 2 3", dato[0]);
                                startActivity(intento);
                            }
                            if (opc == 2) {
                                Frecuencia.this.finish();
                            }
                        }
                    }).show();
        }catch (Exception e){
            Toast.makeText(this,"Error Frecuencia - Dialogo:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.usuario.version7.Vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.version7.BD.DBManager;
import com.example.usuario.version7.BD.DBManager2;
import com.example.usuario.version7.R;
import com.example.usuario.version7.Vista.InicioSesion;

public class Inicio extends AppCompatActivity {

    private String dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getString("1 2 3");
        getSupportActionBar().setTitle("BIENVENIDO: " + dato);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.boton) {
            Toast.makeText(this, "Se presionó el ícono de la *", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.ejercicio) {
            Toast.makeText(this, "Se presionó la opción ejercicio", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.usuario) {
            try{
                final Dialog personal = new Dialog(Inicio.this);
                personal.setContentView(R.layout.editaruno);
                personal.setTitle("Cambiar nombre de usuario");
                personal.show();
                personal.setCancelable(false);
                final EditText txtdato= (EditText)personal.findViewById(R.id.txtdato);
                Button btnguardar = (Button)personal.findViewById(R.id.btnguardar);
                Button btncancelar = (Button)personal.findViewById(R.id.btncancelar);
                btnguardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), " Dato:" + txtdato.getText().toString()+":", Toast.LENGTH_SHORT).show();
                        if(!"".equals(txtdato.getText().toString())) {
                            if (verificarUsuario(txtdato.getText().toString()) == 1) {
                                modificar(txtdato.getText().toString());
                                personal.cancel();
                            }else{
                                txtdato.setText("");
                                txtdato.setHint("Ya hay un usuario con este nombre");
                                txtdato.setHintTextColor(Color.parseColor("#51FF1218"));
                            }
                        }else{
                            txtdato.setText("");
                            txtdato.setHint("Debe digitar un nombre de usuario");
                            txtdato.setHintTextColor(Color.parseColor("#51FF1218"));
                        }

                    }
                });
                btncancelar.setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        personal.cancel();
                    }
                });
            }catch (Exception e){
                Toast.makeText(this,"Error inicio - usuario:"+e.toString(),Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == R.id.cuenta) {
            Intent intento  = new Intent(getApplicationContext(),Micuenta.class);
            intento.putExtra("1 2 3", dato);
            startActivity(intento);
            return true;
        }
        if (id == R.id.cerrar) {
            Dialogo("¿Cerrar Sesion?","\tDesea Cerrar Sesion?",0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ejercicio (View v){
        Toast.makeText(this, " HOLA  ", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir?")
                    .setMessage("No hay marcha atras")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            //Inicio.this.finish();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Dialogo(String tit, final String men, final int opc){
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(opc==0) {
                                Intent i = new Intent(getApplicationContext(),InicioSesion.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        }
                    }).show();
        }catch (Exception e){
            Toast.makeText(this,"Error Inicio - Dialogo:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void  modificar(String dato){
        DBManager obj = new DBManager(this);
        DBManager2 obj2 = new DBManager2(this);
        obj.modificarusuario(this.dato, dato);
        obj2.modificarusuario(this.dato, dato);
        this.dato= "dato";
        Dialogo("Informacion","\tCambio Realizado Con Exito",1);
        getSupportActionBar().setTitle("BIENVENIDO: "+dato);
    }

    public int verificarUsuario(String usu) {
        DBManager obj = new DBManager(this);
        int r = obj.consultanombreu(usu);
        if (r == 0)
            return 1;
        else
            return 0;
    }

}

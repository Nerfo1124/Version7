package com.example.usuario.version7.Vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.version7.BD.DBManager;
import com.example.usuario.version7.R;

public class InicioSesion extends AppCompatActivity {

    private EditText txtusuario,txtpassword;
    private DBManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportActionBar().setTitle("Inicio Sesion");
        //getSupportActionBar().setIcon(R.drawable.icono_home);
        txtusuario = (EditText)findViewById(R.id.txtusuario);
        txtpassword = (EditText)findViewById(R.id.txtcontraseña);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio_sesion, menu);
        return true;
    }

    public void registrar(View v){
        Intent intento= new Intent(getApplicationContext(),Registro.class);
        startActivity(intento);
    }

    public void olvido(View v){
        final Dialog personal = new Dialog(this);
        personal.setContentView(R.layout.recuperarcuenta);
        personal.setTitle("\tRecuperar Cuenta");
        final EditText txtusuario= (EditText)personal.findViewById(R.id.txtnusuario);
        final EditText txtrespuesta= (EditText)personal.findViewById(R.id.txtrespuesta);
        final TextView txtpregunta = (TextView)personal.findViewById(R.id.txtpregunta);
        Button btnbuscar = (Button)personal.findViewById(R.id.btnbuscar);
        final Button btnenviar = (Button)personal.findViewById(R.id.btnenviar);
        btnenviar.setEnabled(false);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r= Buscar(txtusuario.getText().toString());
                if(r==" -1 "){
                    txtusuario.setText("");
                    txtusuario.setHint("No se encontro ese usuario");

                }else {
                    if(r==" -2 ")
                        txtusuario.setHint("Debe digitar un usuario");
                    else {
                        txtpregunta.setText(r);
                        btnenviar.setEnabled(true);
                        txtusuario.setEnabled(false);
                    }
                }
            }
        });

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtrespuesta.getText().toString().equals("")){
                    txtrespuesta.setHint("Debe digitar una respuesta");
                }else {
                    if(txtrespuesta.getText().toString().equals(Respuesta(txtusuario.getText().toString()))){
                        Toast.makeText(getApplicationContext(), "Cambiar Contraseña", Toast.LENGTH_SHORT).show();
                    }else{
                        txtrespuesta.setText("");
                        txtrespuesta.setHint("Respuesta Incorrecta");
                    }

                }
            }
        });

        personal.show();

    }
    public void entrar (View v){
        manager = new DBManager(this);
        int e=espaciosblancos();
        int u=0;
        if( e==1) {
            u = verificarUsuario(txtusuario.getText().toString(),1);
        }
        if(e==1 && u==1){
            if(manager.consultacontrasenia(txtusuario.getText().toString()).equals(txtpassword.getText().toString())){
                Intent intento = new Intent(this,Inicio.class);
                intento.putExtra("1 2 3", txtusuario.getText().toString());
                startActivity(intento);
                finish();
            }else{
                Dialogo("Mensaje","La Contraseña ingresada es incorrecta",3);
                txtpassword.setText("");
                txtpassword.setHint(" La Contraseña ingresada es incorrecta");
                txtpassword.setHintTextColor(Color.parseColor("#51FF1218"));
            }

        }
    }

    public int verificarUsuario(String usu, int opc){
        DBManager manager2 = new DBManager(this);
        int r=manager2.consultanombreu(usu);
        if(r==0 && opc==1 ){
            txtusuario.setText("");
            txtusuario.setHint("No hay un usuario con este nombre");
            txtusuario.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
        if(r==0 && opc==2 )
            return 0;
        return 1;
    }

    public int espaciosblancos() {
        int r = 1;
        if ("".equals(txtusuario.getText().toString())) {
            r = 0;
            txtusuario.setHint("Debe ingresar su nombre de usuario");
            txtusuario.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtpassword.getText().toString())) {
            r = 0;
            txtpassword.setHint("Debe ingresar su contraseña");
            txtpassword.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
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
                        }
                    }).show();
        }catch (Exception e){
            Toast.makeText(this,"Error Frecuencia - Dialogo:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public String Buscar(String txt){
        if(!txt.equals("")){
            if (this.verificarUsuario(txt,2)==1){
                DBManager manager2 = new DBManager(this);
                String r=manager2.consultapregunta(txt);
                return r;
            }else
                return " -1 ";
        }
        else
            return " -2 ";
    }

    public String Respuesta(String txt){
        DBManager manager2 = new DBManager(this);
        String r=manager2.consultarespuesta(txt);
        return r;
    }


}

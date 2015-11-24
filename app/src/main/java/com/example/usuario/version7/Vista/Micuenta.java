package com.example.usuario.version7.Vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.usuario.version7.BD.DBManager;
import com.example.usuario.version7.BD.DBManager2;
import com.example.usuario.version7.Import.DateDialog;
import com.example.usuario.version7.Import.ExpandableListAdapter;
import com.example.usuario.version7.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Micuenta extends AppCompatActivity {

    private String dato;
    private DBManager manager;
    private DBManager2 manager2;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getString("1 2 3");
        manager= new DBManager(this);
        manager2=new DBManager2(this);
        expListView = (ExpandableListView) findViewById(R.id.ListaExpandible);
        prepareListData();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed(); }
        return super.onOptionsItemSelected(item);
    }

    private void prepareListData() {
        String[]datos=manager.consultadatos(dato);
        String[]datos2=manager2.consultadatos(dato);
        getSupportActionBar().setTitle("Datos De: " + dato);
        if(datos!=null || datos2!=null) {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            listDataHeader.add("\t  Datos Personales");
            listDataHeader.add("\t  Configuraciones");

            List<String> nowShowing = new ArrayList<String>();
            nowShowing.add("\t\t\tNombres: " + datos[1]);
            nowShowing.add("\t\t\tApellidos: " + datos[2]);
            nowShowing.add("\t\t\tF. nacimiento: " + datos[3]);
            nowShowing.add("\t\t\tSexo: " + datos[4]);

            List<String> comingSoon = new ArrayList<String>();
            comingSoon.add("\t\t\tOjo derecho: "+datos2[3]);
            comingSoon.add("\t\t\tOjo izquierdo: "+datos2[4]);
            comingSoon.add("\t\t\tTamaño letra: " + datos2[2]);
            comingSoon.add("\t\t\tFrecuencia: "+datos2[5]);

            listDataChild.put(listDataHeader.get(0), nowShowing);
            listDataChild.put(listDataHeader.get(1), comingSoon);
            listAdapter= new ExpandableListAdapter(this,this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
        }else
            Toast.makeText(this, "No se encontraron sus datos", Toast.LENGTH_SHORT).show();
    }

    public void editar(int grupo, final int subgrupo){
        try{
            final Dialog personal = new Dialog(Micuenta.this);
            personal.setContentView(R.layout.editaruno);
            personal.setTitle("Editar Dato");
            final EditText txtdato= (EditText)personal.findViewById(R.id.txtdato);
            if(grupo==0){
                if(subgrupo==0){
                    txtdato.setHint("Ingresar nombre nuevo");
                    personal.show();
                }
                if(subgrupo==1){
                    txtdato.setHint("Ingresar apellidos nuevos");
                    personal.show();
                }
                if (subgrupo==2) {
                    txtdato.setHint("Ingresar fecha nacimiento nuevo");
                    personal.show();
                    txtdato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                DateDialog Dialog = new DateDialog(v);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                Dialog.show(ft, "DatePiker");
                            }
                        }
                    });
                }
                if(subgrupo==3){
                    Dialogosexo();
                }

                if(subgrupo==4){
                    //txtdato.setHint("Ingresar nombre nuevo");
                }
            }
            Button btnguardar = (Button)personal.findViewById(R.id.btnguardar);
            Button btncancelar = (Button)personal.findViewById(R.id.btncancelar);
            btnguardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), " Dato: " + txtdato.getText().toString(), Toast.LENGTH_SHORT).show();
                    if(subgrupo==0)
                        manager.modificarnombre(dato,txtdato.getText().toString());
                    if(subgrupo==1)
                        manager.modificarapellidos(dato,txtdato.getText().toString());
                    if(subgrupo==2){
                        Calendar f = Calendar.getInstance();
                        int año = f.get(Calendar.YEAR);
                        String[] fecha= txtdato.getText().toString().split("-");
                        año= año-Integer.parseInt(fecha[2]);
                        if(año>34)
                            manager.modificarfecha(dato,txtdato.getText().toString());
                        else {
                            txtdato.setText("");
                            txtdato.setHint("Su Fecha Debe Ser Menor Al Año 1981");
                            txtdato.setHintTextColor(Color.parseColor("#51FF1218"));
                        }
                    }
                    prepareListData();
                    personal.cancel();
                }
            });
            btncancelar.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    personal.cancel();
                }
            });
        }catch (Exception e){
            Toast.makeText(this,"Error Cuenta - abrir:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void Dialogosexo(){
        final CharSequence[] items = new CharSequence[2];
        items[0] = "Femenino";
        items[1] = "Masculino";
        final String[] sexo = new String[1];
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Seleccionar Sexo")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "seleciono: " + sexo[0], Toast.LENGTH_SHORT).show();
                            manager.modificarsexo(dato,sexo[0]);
                            prepareListData();
                        }
                    })
                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sexo[0] = (String) items[which];
                        }
                    }).show();
        }catch (Exception e){
            Toast.makeText(this,"Error cuenta - Dialogo:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}

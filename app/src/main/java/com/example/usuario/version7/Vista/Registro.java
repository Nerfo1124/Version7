package com.example.usuario.version7.Vista;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.usuario.version7.BD.DBManager;
import com.example.usuario.version7.Import.DateDialog;
import com.example.usuario.version7.R;

import java.util.Calendar;

public class Registro extends AppCompatActivity {

    private RadioGroup grupos,grupo;
    private DBManager manager;
    private Spinner selector;
    private EditText txtfecha,txtnombreu,txtcontra, txtcontrados, txtnombre,txtapellido,txtrespuesta;
    String []opciones={"¿Nombre de tu mascota preferida?","¿Lugar de nacimiento de tu padre?","¿Cancion favorita?","¿Mejor amigo?"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras
        manager = new DBManager(this);
        this.grupos = (RadioGroup)findViewById(R.id.radiogrupo);
        this.grupo = (RadioGroup)findViewById(R.id.radiogrupo2);
        this.txtfecha = (EditText)findViewById(R.id.txtfecha);
        this.txtnombreu = (EditText)findViewById(R.id.txtusuario);
        this.txtcontra = (EditText)findViewById(R.id.txtcontraseñar);
        this.txtcontrados = (EditText)findViewById(R.id.txtcontraseña2);
        this.txtnombre = (EditText)findViewById(R.id.txtnombre);
        this.txtapellido = (EditText)findViewById(R.id.txtapellido);
        this.selector = (Spinner) findViewById(R.id.selector);
        this.txtrespuesta = (EditText)findViewById(R.id.txtpregunta);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, opciones);
        selector.setAdapter(adapter);
        abrir();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed(); }
        return super.onOptionsItemSelected(item); }

    public  void abrir (){
        txtfecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    public void siguiente(View v){
        int p = espaciosblancos();
        int r=0,r2=0,r3=0;
        if(p==1) {
            r = compararfecha();
            if(r==1) {
                r2 = compararcontraseñas();
            }
            if(r2==1){
                r3=verificarUsuario();
            }
        }
        if( r==1 && r2==1 && r3==1&& p==1 ) {
            if (grupo.getCheckedRadioButtonId() == R.id.radiosi)
                Opcsiquiente("Con");
            else
                Opcsiquiente("Sin");
        }
    }

    public void Opcsiquiente(String clase){
        String[]valores={txtnombreu.getText().toString(),txtnombre.getText().toString(),txtapellido.getText().toString(),
                txtfecha.getText().toString(),sexo(),selector.getSelectedItem().toString(),txtrespuesta.getText().toString(),
                txtcontra.getText().toString(), "8","tamaño","der","izq","frec"};
        if(clase=="Con") {
            valores[8]="true";
            Intent intento = new Intent(getApplicationContext(), Conformula.class);
            intento.putExtra("1 2 3", valores);
            this.startActivity(intento);
        }
        else {
            valores[8]="false";
            Intent intento = new Intent(getApplicationContext(), Sinformula.class);
            intento.putExtra("1 2 3", valores);
            this.startActivity(intento);
        }
    }

    public int compararfecha(){
        Calendar f = Calendar.getInstance();
        int año = f.get(Calendar.YEAR);
        String[] fecha= txtfecha.getText().toString().split("-");
        año= año-Integer.parseInt(fecha[2]);
        if(año>34)
            return 1;
        else {
            txtfecha.setText("");
            txtfecha.setHint("Su Fecha Debe Ser Menor Al Año 1981");
            txtfecha.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    public int compararcontraseñas() {
        if (txtcontra.getText().toString().equals(txtcontrados.getText().toString()))
            return 1;
        else{
            txtcontrados.setText("");
            txtcontrados.setHint("Las contraseñas no coinciden, repitala");
            txtcontrados.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }

    }

    public int verificarUsuario(){
        int r=manager.consultanombreu(txtnombreu.getText().toString());
        if(r==0)
            return 1;
        else{
            txtnombreu.setText("");
            txtnombreu.setHint("Ya hay un usuario con este nombre");
            txtnombreu.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }

    }
    public int espaciosblancos(){
        int r=1;
        if("".equals(txtnombreu.getText().toString())){
            r=0;
            txtnombreu.setHint("Debe ingresar un nombre de usuario");
            txtnombreu.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtnombre.getText().toString())){
            r=0;
            txtnombre.setHint("Debe ngresar su nombre");
            txtnombre.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtapellido.getText().toString())){
            r=0;
            txtapellido.setHint("Debe ingresar su apellido");
            txtapellido.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtfecha.getText().toString())){
            r=0;
            txtfecha.setHint("Debe ingresar su fecha de nacimiento");
            txtfecha.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtcontra.getText().toString())){
            r=0;
            txtcontra.setHint("Debe ingresar una contraseña");
            txtcontra.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtcontrados.getText().toString())){
            r=0;
            txtcontrados.setHint("Debe ingresar la contraseña");
            txtcontrados.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtrespuesta.getText().toString())){
            r=0;
            txtrespuesta.setHint("Debe ingresar una respuesta");
            txtrespuesta.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    public String sexo(){
        if (grupos.getCheckedRadioButtonId() == R.id.rbmujer)
            return "Femenino";
        else
            return "Masculino";
    }

}

package com.example.usuario.version7.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Usuario on 03/10/2015.
 */
public class DBManager2 {

    public static final String TABLE_NAME="DATO";
    public static final String dat_id="dat_id";
    public static final String dat_nombreusuario="dat_nombreusuario";
    public static final String dat_tamanio="dat_tamaño";
    public static final String dat_ojoder="dat_ojoder";
    public static final String dat_ojoizq="dat_ojoizq";
    public static final String dat_frecuencia="dat_frecuencia";

    public static final  String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + dat_id + " integer primary key autoincrement, "
            + dat_nombreusuario + " text not null, "
            + dat_tamanio + " text, "
            + dat_ojoder + " text, "
            + dat_ojoizq + " text, "
            + dat_frecuencia + " text not null, "
            + "FOREIGN KEY("+dat_nombreusuario+") REFERENCES USUARIO(usu_nombreusuario))";

    public DBhelper helper;
    public SQLiteDatabase db;
    public Context contexto;

    public DBManager2(Context context){
        try {
            contexto=context;
            helper = new DBhelper(context);
            db = helper.getWritableDatabase();
        }catch (Exception e){
            Toast.makeText(context, "Error En DBManager2" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  int  insertar(String nombreu, String tamanio, String ojoder, String ojoizq,String frecuencia){
        try {
            long i = db.insert(TABLE_NAME, null, generarContentValuesString(nombreu, tamanio, ojoder, ojoizq, frecuencia));
            return Integer.parseInt(""+i);
        }catch (Exception e){
            Toast.makeText(contexto,"Error Insertar: XXXX"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public ContentValues generarContentValuesString (String nombreU, String tamanio, String ojoder, String ojoizq,String frecuencia){
        ContentValues valores = new ContentValues();
        valores.put(dat_nombreusuario,nombreU);
        valores.put(dat_tamanio, tamanio);
        valores.put(dat_ojoder,ojoder);
        valores.put(dat_ojoizq,ojoizq);
        valores.put(dat_frecuencia,frecuencia);
        return  valores;
    }

    public String[] consultadatos( String nombreu) {
        try {
            String[] r = new String[6];
            Cursor fila = db.rawQuery("select *from " + TABLE_NAME + " where " + dat_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            Toast.makeText(contexto,fila.getString(4),Toast.LENGTH_SHORT).show();
            for(int i=0;i<=5;i++){
                r[i]=fila.getString(i);
            }
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error:"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    public void modificarusuario (String nombre, String nombrenew){
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + dat_nombreusuario + "='" + nombrenew + "' where " + dat_nombreusuario + "='"
                    + nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager2 - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}


package com.example.usuario.version7.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Usuario on 19/09/2015.
 */
public class DBManager {

    public static final String TABLE_NAME="USUARIO";
    public static final String usu_nombreusuario="usu_nombreusuario";
    public static final String usu_nombre="usu_nombre";
    public static final String usu_apellidos="usu_apellidos";
    public static final String usu_fecha="usu_fecha";
    public static final String usu_sexo="usu_sexo";
    public static final String usu_pregunta="usu_pregunta";
    public static final String usu_respuesta="usu_respuesta";
    public static final String usu_contrasenia="usu_contraseña";
    public static final String usu_formula="usu_formula";

    public static final  String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + usu_nombreusuario + " text primary key, "
            + usu_nombre + " text not null, "
            + usu_apellidos + " text not null, "
            + usu_fecha + " text not null, "
            + usu_sexo + " text not null, "
            + usu_pregunta + " text not null, "
            + usu_respuesta + " text not null, "
            + usu_contrasenia + " text not null, "
            + usu_formula +  " text not null )";

    public DBhelper helper;
    public SQLiteDatabase db;
    public Context contexto;

    public DBManager(Context context){
        try {
            contexto=context;
            helper = new DBhelper(context);
            db = helper.getWritableDatabase();
        }catch (Exception e){
            Toast.makeText(context, "Error En DBManager"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  int  insertar(String nombreU,String nombre, String apellido, String fecha, String sexo,String pregunta,
                          String respuesta ,String contrasenia, String formula,String tamanio, String der, String izq, String fre){
        try {
            DBManager2 objD = new DBManager2(contexto);
            long i = db.insert(TABLE_NAME, null, generarContentValues(nombreU, nombre, apellido, fecha, sexo, pregunta,
                    respuesta, contrasenia, formula));
            Toast.makeText(contexto,"Resultado DATO: "+objD.insertar(nombreU, tamanio, der, izq, fre), Toast.LENGTH_SHORT).show();
            return Integer.parseInt("" + i);
        }catch (Exception e){
            Toast.makeText(contexto,"Error Insertar:"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public ContentValues generarContentValues(String nombreU,String nombre, String apellido, String fecha, String sexo,
                                              String pregunta, String respuesta, String contrasenia, String  formula){
        ContentValues valores = new ContentValues();
        valores.put(usu_nombreusuario,nombreU);
        valores.put(usu_nombre, nombre);
        valores.put(usu_apellidos,apellido);
        valores.put(usu_fecha,fecha);
        valores.put(usu_sexo,sexo);
        valores.put(usu_pregunta,pregunta);
        valores.put(usu_respuesta,respuesta);
        valores.put(usu_contrasenia,contrasenia);
        valores.put(usu_formula, formula);
        return  valores;
    }

    public int consultanombreu( String nombreu) {
        try {
            int r;
            Cursor fila = db.rawQuery("select  count(*) from " + TABLE_NAME + " where " + usu_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            r=Integer.parseInt(fila.getString(0));
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error:"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public String consultacontrasenia( String nombreu) {
        try {
            String  r="";
            Cursor fila = db.rawQuery("select "+usu_contrasenia+" from " + TABLE_NAME + " where " + usu_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            r=fila.getString(0);
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error:"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public String[] consultadatos( String nombreu) {
        try {
            String[] r = new String[9];
            Cursor fila = db.rawQuery("select *from " + TABLE_NAME + " where " + usu_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            for(int i=0;i<=8;i++){
                r[i]=fila.getString(i);
            }
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error:"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /*
    public void eliminar (String nombre){
        db.delete(TABLE_NAME, con_nombre + "=?", new String[]{nombre});
    }
    */
    public void modificarusuario (String nombre, String nombrenew){
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + usu_nombreusuario + "='" + nombrenew + "' where " + usu_nombreusuario + "='"+ nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarnombre (String nombre, String nombrenew){
        try {
            //Toast.makeText(contexto,"errrorororo",Toast.LENGTH_SHORT).show();

            db.execSQL("UPDATE " + TABLE_NAME + " SET " + usu_nombre + "='" + nombrenew + "' where " + usu_nombreusuario + "='"+ nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarapellidos (String nombre, String apellido){
        try {
            //Toast.makeText(contexto,"errrorororo",Toast.LENGTH_SHORT).show();

            db.execSQL("UPDATE " + TABLE_NAME + " SET " + usu_apellidos + "='" + apellido + "' where " + usu_nombreusuario + "='"+ nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarfecha (String nombre, String fecha){
        try {
            //Toast.makeText(contexto,"errrorororo",Toast.LENGTH_SHORT).show();

            db.execSQL("UPDATE " + TABLE_NAME + " SET " + usu_fecha + "='" + fecha + "' where " + usu_nombreusuario + "='"+ nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarsexo (String nombre, String sexo){
        try {
            //Toast.makeText(contexto,"errrorororo",Toast.LENGTH_SHORT).show();

            db.execSQL("UPDATE " + TABLE_NAME + " SET " + usu_sexo + "='" + sexo + "' where " + usu_nombreusuario + "='"+ nombre + "'");
        }catch (Exception e ){
            Toast.makeText(contexto,"Error manager - modificar "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public String consultapregunta(String nombreu) {
        try {
            String  r="";
            Cursor fila = db.rawQuery("select "+usu_pregunta+" from " + TABLE_NAME + " where " + usu_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            r=fila.getString(0);
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error: BD1 - pregunta :D :D :( :/ "+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public String consultarespuesta(String nombreu) {
        try {
            String  r="";
            Cursor fila = db.rawQuery("select "+usu_respuesta+" from " + TABLE_NAME + " where " + usu_nombreusuario + "='" + nombreu + "'", null);
            fila.moveToFirst();
            r=fila.getString(0);
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error: BD1 - respuesta :D :D :( :/ "+ e.toString(),Toast.LENGTH_SHORT).show();
        }
        return "";
    }
}

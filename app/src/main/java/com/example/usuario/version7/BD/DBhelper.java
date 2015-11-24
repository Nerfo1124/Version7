package com.example.usuario.version7.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 19/09/2015.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final String BD_NAME="aplicacion.sqlite";
    private static final int DB_SCHEME_VERSION=1;
    Context contexto;

    public DBhelper(Context context) {
        super(context, BD_NAME, null, DB_SCHEME_VERSION);
        contexto=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBManager.CREATE_TABLE);
        db.execSQL(DBManager2.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

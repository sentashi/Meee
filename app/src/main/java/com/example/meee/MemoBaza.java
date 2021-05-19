package com.example.meee;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MemoBaza extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Memo.db";
    private static final int DATABASE_VERSION = 4;

    public MemoBaza(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_KORISNIK = "create table korisnik ("
                + "_id" + " integer primary key autoincrement, "
                + "imePrez" + " text not null, "
                + "korIme" + " text not null, "
                + "lozinka" + " text not null);";
        db.execSQL(CREATE_TABLE_KORISNIK);
        String CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS korisnik_ui ON korisnik(korIme)";
        db.execSQL(CREATE_UNIQUE_INDEX);

        String CREATE_TABLE_PROJEKTI = "create table projekti ("
                + "_id" + " integer primary key autoincrement, "
                + "naziv" + " text not null, "
                + "opis" + " text, "
                + "klijent" + " text not null, "
                + "budzet" + " float not null, "
                + "id_kor" + " integer not null);";
        db.execSQL(CREATE_TABLE_PROJEKTI);

        String CREATE_TABLE_TROSKOVNIK = "create table troskovnik ("
                + "_id" + " integer primary key autoincrement, "
                + "stavka" + " text not null, "
                + "kolicina" + " integer not null, "
                + "cijena" + " float not null, "
                + "id_projekta" + " integer not null);";
        db.execSQL(CREATE_TABLE_TROSKOVNIK);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("drop table KORISNIK");
        db.execSQL("drop table PROJEKTI");
        db.execSQL("drop table TROSKOVNIK");
        Log.i("izmjena_verzije" , "dropa tablicu");
        onCreate(db);
    }

    Cursor readAllUsers(){
        String query = "SELECT * FROM korisnik;";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readUserInfo(int id){
        String query = "SELECT * FROM korisnik WHERE _id= " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllProjects(int id){
        String query = "SELECT * FROM projekti WHERE id_kor= " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
    Cursor projectDetails(int id){
        String query = "SELECT * FROM projekti WHERE _id= " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }

    Cursor troskovnikDetails(int id){
        String query = "SELECT * FROM troskovnik WHERE id_projekta= " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }


}

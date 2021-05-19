package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class NoviProjektActivity extends AppCompatActivity {
    SharedPrefs session;
    private int id;
    private int idKor;
    MemoBaza mb;

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noviprojekt);

        mb = new MemoBaza(this);
        session = new SharedPrefs(NoviProjektActivity.this);

        HashMap<String, String> user = session.getUserDetails(NoviProjektActivity.this);
        String strId = user.get(SharedPrefs.USER_ID);
        setIdKor(Integer.parseInt(strId));
    }

    public void addNewProject(View v){
        EditText naziv = (EditText) findViewById(R.id.naziv);
        EditText klijent = (EditText) findViewById(R.id.klijent);
        EditText budzet = (EditText) findViewById(R.id.budzet);
        EditText opis = (EditText) findViewById(R.id.opis);

        Cursor cursor = mb.readAllProjects(idKor);
        while(cursor.moveToNext()){
            if(cursor.getString(1).equals(naziv.getText().toString())){
                setId(cursor.getInt(0));
            }
        }

        if (naziv.getText().toString().length() < 1 || klijent.getText().toString().length() < 1 || budzet.getText().toString().length() < 1){
            Toast.makeText(this,"Morate unijeti naziv, klijenta i budzet",Toast.LENGTH_SHORT).show();
        }
        if(getId()!=0){
            Toast.makeText(getApplicationContext(), "Naziv projekta se već koristi!", Toast.LENGTH_LONG).show();
        }else {
            SQLiteDatabase db = mb.getWritableDatabase();
            ContentValues projektValues = new ContentValues();
            projektValues.put("naziv", naziv.getText().toString());
            projektValues.put("opis", opis.getText().toString());
            projektValues.put("klijent", klijent.getText().toString());
            projektValues.put("budzet", budzet.getText().toString());
            projektValues.put("id_kor", getIdKor());
            long rowid = db.insert("PROJEKTI", null, projektValues);
            if (rowid == -1) {
                Toast.makeText(NoviProjektActivity.this, "Neuspješan unos projekta!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NoviProjektActivity.this, "Uspješan unos projekta!", Toast.LENGTH_LONG).show();
                Intent iMojiProjekti= new Intent(this, MojiProjektiActivity.class);
                session.projectSession(String.valueOf(rowid));
                startActivity(iMojiProjekti);
            }
        }
    }

}
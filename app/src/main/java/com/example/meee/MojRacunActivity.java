package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class MojRacunActivity extends AppCompatActivity{
    SharedPrefs session;
    private EditText putKorIme, putPunoIme, putLozinka;
    private String punoIme, lozinka, trenutnoKorIme, korIme;
    MemoBaza mb;
    private int id, pomId;

    public String getPunoIme() {
        return punoIme;
    }

    public void setPunoIme(String punoIme) {
        this.punoIme = punoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorIme() {
        return korIme;
    }

    public void setKorIme(String korIme) {
        this.korIme = korIme;
    }

    public String getTrenutnoKorIme() {
        return trenutnoKorIme;
    }

    public void setTrenutnoKorIme(String trenutnoKorIme) {
        this.trenutnoKorIme = trenutnoKorIme;
    }

    public int getPomId() {
        return pomId;
    }

    public void setPomId(int pomId) {
        this.pomId = pomId;
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
        setContentView(R.layout.activity_mojracun);

        mb = new MemoBaza(this);

        session = new SharedPrefs(MojRacunActivity.this);

        HashMap<String, String> user = session.getUserDetails(MojRacunActivity.this);
        setKorIme(user.get(SharedPrefs.USERNAME));
        String strId = user.get(SharedPrefs.USER_ID);
        setId(Integer.parseInt(strId));

        EditText putPunoIme = (EditText) findViewById(R.id.putPunoIme);
        EditText putKorIme = (EditText) findViewById(R.id.putKorIme);
        EditText putLozinka = (EditText) findViewById(R.id.putLozinka);

        Cursor cursor = mb.readUserInfo(getId());
        if(cursor.moveToFirst()){
            do{
                setPunoIme(cursor.getString(1));
                setLozinka(cursor.getString(3));
            }while(cursor.moveToNext());
        }cursor.close();

        putPunoIme.setText(punoIme);
        putKorIme.setText(korIme);
        putLozinka.setText(lozinka);

        setTrenutnoKorIme(korIme);

    }

     public void updateUser(View v){
         EditText putPunoIme = (EditText) findViewById(R.id.putPunoIme);
         EditText putKorIme = (EditText) findViewById(R.id.putKorIme);
         EditText putLozinka = (EditText) findViewById(R.id.putLozinka);

         String punoIme = putPunoIme.getText().toString();
         String korIme = putKorIme.getText().toString();
         String pwd = putLozinka.getText().toString();

         if(!getTrenutnoKorIme().equals(korIme)) {
             Cursor cursor = mb.readAllUsers();
             while (cursor.moveToNext()) {
                 if (cursor.getString(2).equals(korIme)) {
                     setPomId(cursor.getInt(0));
                 }
             }
             if(getPomId()!=0){
                 Toast.makeText(getApplicationContext(), "Korisničko ime već postoji!", Toast.LENGTH_LONG).show();
             }
         }
         else if (punoIme.length() < 1 || korIme.length() < 1 || pwd.length() < 1){
             Toast.makeText(this,"Morate unijeti puno ime, korisničko ime i lozinku",Toast.LENGTH_SHORT).show();
         }else {
             SQLiteDatabase db = mb.getWritableDatabase();
             ContentValues korisnikValues = new ContentValues();
             korisnikValues.put("imePrez", putPunoIme.getText().toString());
             korisnikValues.put("korIme", putKorIme.getText().toString());
             korisnikValues.put("lozinka", putLozinka.getText().toString());
             int update = db.update("KORISNIK", korisnikValues, "_id = ? ", new String[]{String.valueOf(getId())});
             if (update > 0) {
                 Toast.makeText(MojRacunActivity.this, "Uspješno ažuriranje!", Toast.LENGTH_LONG).show();
             } else {
                 Toast.makeText(MojRacunActivity.this, "Neuspješno ažuriranje!", Toast.LENGTH_LONG).show();
             }
         }
    }

}
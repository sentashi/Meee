package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistracijaActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.meee.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.meee.EXTRA_TEXT2";
    SharedPrefs session;
    private String punoIme;
    private String korisnickoIme;
    private String lozinka;
    private int id;
    private Context con;
    MemoBaza mdb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPunoIme() {
        return punoIme;
    }

    public void setPunoIme(String punoIme) {
        this.punoIme = punoIme;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        Intent intent = getIntent();
        String korIme = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        String lozinka = intent.getStringExtra(MainActivity.EXTRA_TEXT2);

        EditText korIme1 = (EditText) findViewById(R.id.korImeReg);
        EditText lozinka1 = (EditText) findViewById(R.id.lozinkaReg);

        korIme1.setText(korIme);
        lozinka1.setText(lozinka);

        con=this;

        session = new SharedPrefs(con);

        mdb = new MemoBaza(this);
    }

    public void prebaciHome(View v) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        EditText punoIme = findViewById(R.id.punoImeReg);
        EditText korIme = findViewById(R.id.korImeReg);
        EditText pwd = findViewById(R.id.lozinkaReg);

        setKorisnickoIme(korIme.getText().toString());
        setPunoIme(punoIme.getText().toString());
        setLozinka(pwd.getText().toString());

        Cursor cursor = mdb.readAllUsers();
        while(cursor.moveToNext()){
            if(cursor.getString(2).equals(getKorisnickoIme())){
                setId(cursor.getInt(0));
            }
        }

        if((punoIme.getText().toString().length() <= 0) || (korIme.getText().toString().length() <= 0) || (pwd.getText().toString().length() <= 0)) {
            Toast.makeText(getApplicationContext(), "Popunite sva polja!", Toast.LENGTH_LONG).show();
            return;
        }
        if(getId()!=0){
            Toast.makeText(getApplicationContext(), "Korisničko ime već postoji!", Toast.LENGTH_LONG).show();
        }else{
            ContentValues korisnikValues = new ContentValues();
            korisnikValues.put("imePrez",getPunoIme());
            korisnikValues.put("korIme", getKorisnickoIme());
            korisnikValues.put("lozinka", getLozinka());
            long rowid = db.insert("KORISNIK", null, korisnikValues);
            if (rowid == -1){
                Toast.makeText(RegistracijaActivity.this, "Neupsješna registracija!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Uspješno ste registrirani!", Toast.LENGTH_LONG).show();
                Intent iHome = new Intent(this, HomeScreenActivity.class);
                session.logInSession(korisnickoIme, String.valueOf(rowid));
                startActivity(iHome);
            }
        }
    }
}
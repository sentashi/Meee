package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPrefs session;
    public static final String EXTRA_TEXT = "com.example.meee.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.meee.EXTRA_TEXT2";

    private Context con;

    MemoBaza mdb;
    ArrayList<Integer> id;
    ArrayList<String> punoIme, username, lozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con=this;

        session = new SharedPrefs(con);

        mdb = new MemoBaza(this);
        id = new ArrayList<>();
        punoIme = new ArrayList<>();
        username = new ArrayList<>();
        lozinka = new ArrayList<>();

        getAllUsers();
    }

    public void prebaciRegistracija(View v) {
        EditText korIme = (EditText) findViewById(R.id.korisnickoIme);
        String text1 = korIme.getText().toString();

        EditText pwd = (EditText) findViewById(R.id.lozinka);
        String text2 = pwd.getText().toString();

        Intent iRegistracija = new Intent(this, RegistracijaActivity.class);
        iRegistracija.putExtra(EXTRA_TEXT, text1);
        iRegistracija.putExtra(EXTRA_TEXT2, text2);
        startActivity(iRegistracija);
    }

    public void prebaciHome(View v) {
        MemoBaza mb = new MemoBaza(con);
        SQLiteDatabase db = mb.getWritableDatabase();
        Intent iHome = new Intent(this, HomeScreenActivity.class);

        EditText korIme = (EditText) findViewById(R.id.korisnickoIme);
        String text1 = korIme.getText().toString();

        EditText pwd = (EditText) findViewById(R.id.lozinka);
        String text2 = pwd.getText().toString();

        if(text1.equals("") && text2.equals("")){
            Toast.makeText(this, "Unesite korisničko ime i lozinku!", Toast.LENGTH_SHORT).show();
        }

        else if (username.contains(text1) && (!lozinka.contains(text2))){
            pwd.getText().clear();
            Toast.makeText(this, "Neispravna lozinka!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!username.contains(text1)){
            korIme.getText().clear();
            pwd.getText().clear();
            Toast.makeText(this, "Korisnik ne postoji! Registrirajte se!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(username.contains(text1) && lozinka.contains(text2)){
            int indexId = username.indexOf(text1) + 1;
            session.logInSession(text1, String.valueOf(indexId));
            startActivity(iHome);
        }
    }
    void getAllUsers(){
        Cursor cursor = mdb.readAllUsers();

        while(cursor.moveToNext()){
            id.add(cursor.getInt(0));
            punoIme.add(cursor.getString(1));
            username.add(cursor.getString(2));
            lozinka.add(cursor.getString(3));
        }
    }

}
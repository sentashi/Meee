package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CrijepActivity extends AppCompatActivity {
    SharedPrefs session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crijep);
        session = new SharedPrefs(CrijepActivity.this);
    }
    public void idiIzracunKont(View v){
        Intent iKont = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("kont");
        startActivity(iKont);
    }
    public void idiIzracunMedit(View v){
        Intent iMedit = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("medit");
        startActivity(iMedit);
    }
    public void idiIzracunKanalice(View v){
        Intent iKanalice = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("kanalice");
        startActivity(iKanalice);
    }
}
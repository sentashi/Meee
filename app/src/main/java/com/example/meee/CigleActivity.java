package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CigleActivity extends AppCompatActivity {
    SharedPrefs session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SharedPrefs(CigleActivity.this);
        setContentView(R.layout.activity_cigle);
    }

    public void idiIzracunYtong(View v){
        Intent iYtong = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("ytong");
        startActivity(iYtong);
    }
    public void idiIzracunBB(View v){
        Intent iBB = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("bb");
        startActivity(iBB);
    }
    public void idiIzracunVanjska(View v){
        Intent iVanjska = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("cigleniBlok");
        startActivity(iVanjska);
    }
    public void idiIzracunPreg(View v){
        Intent iPreg = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("preg");
        startActivity(iPreg);
    }

}
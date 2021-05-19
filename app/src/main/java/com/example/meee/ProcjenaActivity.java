package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ProcjenaActivity extends AppCompatActivity {
    SharedPrefs session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procjena);
        session = new SharedPrefs(ProcjenaActivity.this);

    }
    public void idiCigle(View v){
        Intent iCigle = new Intent(this, CigleActivity.class);
        startActivity(iCigle);
    }
    public void idiCrijep(View v){
        Intent iCrijep = new Intent(this, CrijepActivity.class);
        startActivity(iCrijep);
    }
    public void idiIzracun(View v){
        Intent iIzracun = new Intent(this, IzracunSvihActivity.class);
        session.stavkaSession("plocice");
        startActivity(iIzracun);
    }

}
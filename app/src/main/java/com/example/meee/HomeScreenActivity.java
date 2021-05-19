package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class HomeScreenActivity extends AppCompatActivity {
    private Context con;
    SharedPrefs session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        session = new SharedPrefs(HomeScreenActivity.this);
    }

    public void prebaciMojiProjekti(View v) {
        Intent iMojiProjekti = new Intent(this, MojiProjektiActivity.class);
        startActivity(iMojiProjekti);
    }
    public void prebaciMojRacun(View v) {
        Intent iMojRacun = new Intent(this, MojRacunActivity.class);
        startActivity(iMojRacun);
    }
    public void prebaciNoviProjekt(View v) {
        Intent iNoviProjekt = new Intent(this, NoviProjektActivity.class);
        startActivity(iNoviProjekt);
    }
    public void prebaciProcjena(View v) {
        Intent iProcjena = new Intent(this, ProcjenaActivity.class);
        session.projectSession("0");
        startActivity(iProcjena);
    }

    public void odjava(View v){
        session.logOutUser();
    }
}
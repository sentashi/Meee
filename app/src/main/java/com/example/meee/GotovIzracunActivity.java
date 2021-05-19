package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GotovIzracunActivity extends AppCompatActivity {
    TextView ispis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotovizracun);

        Intent intent = getIntent();
        double ukCijena = intent.getDoubleExtra("ukupnaCijena", 0.00);
        int ukKolicina = intent.getIntExtra("ukupnaKolicina", 0);
        double povrsina = intent.getDoubleExtra("povrsina", 0.00);
        String stavka = intent.getStringExtra("stavka");

        ispis = (TextView) findViewById(R.id.ispis);
        String cijenaIspis = String.valueOf(Math.round(ukCijena));

        ispis.setText("Za površinu " + povrsina + " m^2 potrebno je " + ukKolicina + " " + "(" + stavka + ")" + ". Ukupna cijena iznosi " + cijenaIspis);
    }

    public void prebaciHome(View v){
        Intent iHome = new Intent(GotovIzracunActivity.this, HomeScreenActivity.class);
        startActivity(iHome);
    }

    public void prebaciMojiProjekti(View v){
        Intent iMojiProjekti = new Intent(GotovIzracunActivity.this, MojiProjektiActivity.class);
        startActivity(iMojiProjekti);

    }

}
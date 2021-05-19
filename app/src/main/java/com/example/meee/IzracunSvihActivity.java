package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IzracunSvihActivity extends AppCompatActivity {
    EditText duljina, sirina, povrsina, cijena;
    double sir; //visina za blokove
    double pov;
    double dulj;
    double cij;
    double ukupnaCijena;
    String stavka;
    int id, kolicina;
    SharedPrefs session;
    MemoBaza mb;

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    public void setUkupnaCijena(double ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    public String getStavka() {
        return stavka;
    }

    public void setStavka(String stavka) {
        this.stavka = stavka;
    }

    public double getCij() {
        return cij;
    }

    public void setCij(double cij) {
        this.cij = cij;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getDulj() {
        return dulj;
    }

    public void setDulj(double dulj) {
        this.dulj= dulj;
    }

    public double getSir() {
        return sir;
    }

    public void setSir(double sir) {
        this.sir = sir;
    }

    public double getPov() {
        return pov;
    }

    public void setPov(double pov) {
        this.pov = pov;
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
        setContentView(R.layout.activity_izracunsvih);

        session = new SharedPrefs(IzracunSvihActivity.this);
        String idStr = session.getProjectDetails(IzracunSvihActivity.this);
        setId(Integer.parseInt(idStr));

        String tip = session.getStavka(IzracunSvihActivity.this);

        mb = new MemoBaza(IzracunSvihActivity.this);

        duljina = (EditText) findViewById(R.id.duljinaEditText);
        sirina = (EditText) findViewById(R.id.sirinaEditText);

        if(tip.equals("kont")){
            duljina.setText("0.4");
            sirina.setText("0.25");
            setDulj(0.4);
            setSir(0.25);
            setStavka("Kontinentalni crijep");
        }
        else if(tip.equals("medit")){
            duljina.setText("0.36");
            sirina.setText("0.21");
            setDulj(0.36);
            setSir(0.21);
            setStavka("Mediteranski crijep");
        }
        else if(tip.equals("kanalice")){
            duljina.setText("0.36");
            sirina.setText("0.21");
            setDulj(0.36);
            setSir(0.21);
            setStavka("Kanalice");
        }

        else if(tip.equals("ytong")){
            duljina.setText("0.625");
            sirina.setText("0.20");
            setDulj(0.625);
            setSir(0.20);
            setStavka("Ytong blok");
        }

        else if(tip.equals("bb")){
            duljina.setText("0.39");
            sirina.setText("0.29");
            setDulj(0.39);
            setSir(0.29);
            setStavka("Beton blok");
        }

        else if(tip.equals("cigleniBlok")){
            duljina.setText("0.29");
            sirina.setText("0.19");
            setDulj(0.29);
            setSir(0.19);
            setStavka("Cigleni blok");
        }

        else if(tip.equals("preg")){
            duljina.setText("0.5");
            sirina.setText("0.19");
            setDulj(0.5);
            setSir(0.19);
            setStavka("Pregradna cigla");
        }
        else if(tip.equals("plocice")){
            duljina.setText("");
            sirina.setText("");
            setStavka("Plocice");
        }
    }

    public void idiGotoviIzracun(View v) {

        duljina = (EditText) findViewById(R.id.duljinaEditText);
        sirina = (EditText) findViewById(R.id.sirinaEditText);
        cijena = (EditText) findViewById(R.id.cijenaEditText);
        povrsina = (EditText) findViewById(R.id.povrsinaEditText);

        if((duljina.getText().toString().length() <= 0) || (sirina.getText().toString().length() <= 0) || (cijena.getText().toString().length() <= 0) || (povrsina.getText().toString().length() <= 0)){
            Toast.makeText(IzracunSvihActivity.this, "Popunite sva polja!", Toast.LENGTH_LONG).show();
        }
        else if(getId()!=0) {
        setDulj(Double.parseDouble(duljina.getText().toString()));
        setSir(Double.parseDouble(sirina.getText().toString()));
        setCij(Double.parseDouble(cijena.getText().toString()));
        setPov(Double.parseDouble(povrsina.getText().toString()));

        setKolicina((int) (Math.ceil(getPov()/(getDulj() * getSir()))));
        setUkupnaCijena((double) getKolicina() * getCij());

            SQLiteDatabase db = mb.getWritableDatabase();
            ContentValues izracunValues = new ContentValues();
            izracunValues.put("stavka", getStavka());
            izracunValues.put("kolicina", getKolicina());
            izracunValues.put("cijena", getUkupnaCijena());
            izracunValues.put("id_projekta", getId());

            long rowid = db.insert("TROSKOVNIK", null, izracunValues);
            if (rowid == -1) {
                Toast.makeText(IzracunSvihActivity.this, "Neuspješan unos izračuna!", Toast.LENGTH_LONG).show();
            }else {
                Intent iGotIzracun = new Intent(IzracunSvihActivity.this, GotovIzracunActivity.class);
                Toast.makeText(this, "Uspješan unos izračuna!", Toast.LENGTH_LONG).show();
                iGotIzracun.putExtra("stavka", getStavka());
                iGotIzracun.putExtra("ukupnaKolicina", getKolicina());
                iGotIzracun.putExtra("ukupnaCijena", getUkupnaCijena());
                iGotIzracun.putExtra("povrsina", getPov());
                startActivity(iGotIzracun);
            }
        }
        else if(getId()==0){
            Intent iGotIzracun = new Intent(IzracunSvihActivity.this, GotovIzracunActivity.class);
            iGotIzracun.putExtra("stavka", getStavka());
            iGotIzracun.putExtra("ukupnaKolicina", getKolicina());
            iGotIzracun.putExtra("ukupnaCijena", getUkupnaCijena());
            iGotIzracun.putExtra("povrsina", getPov());
            startActivity(iGotIzracun);
        }
    }

}
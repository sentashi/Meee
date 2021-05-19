package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ProjektActivity extends AppCompatActivity {
    SharedPrefs session;
    MemoBaza mb;
    private TextView putNaziv, putKlijent, putBudzet, putOpis, putTrosak;
    private int id;
    double cijenaStavke[];
    private double ukupniTrosak;
    private int brojRedaka;
    private String naziv, klijent, budzet, opis;

    public double getUkupniTrosak() {
        return ukupniTrosak;
    }

    public void setUkupniTrosak(double ukupniTrosak) {
        this.ukupniTrosak = ukupniTrosak;
    }

    public int getBrojRedaka() {
        return brojRedaka;
    }

    public void setBrojRedaka(int brojRedaka) {
        this.brojRedaka = brojRedaka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKlijent() {
        return klijent;
    }

    public void setKlijent(String klijent) {
        this.klijent = klijent;
    }

    public String getBudzet() {
        return budzet;
    }

    public void setBudzet(String budzet) {
        this.budzet = budzet;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projekt);

        putNaziv = (TextView) findViewById(R.id.putNaziv);
        putKlijent = (TextView) findViewById(R.id.putKlijent);
        putBudzet = (TextView) findViewById(R.id.putBudzet);
        putOpis = (TextView) findViewById(R.id.putOpis);
        putTrosak = (TextView) findViewById(R.id.putTrosak);

        session = new SharedPrefs(ProjektActivity.this);
        String pom = session.getProjectDetails(ProjektActivity.this);
        int pomId = Integer.parseInt(pom);
        setId(pomId);

        mb = new MemoBaza(ProjektActivity.this);
        Cursor cursor = mb.projectDetails(getId());
        if(cursor.moveToFirst()){
            do{
                setNaziv(cursor.getString(1));
                setOpis(cursor.getString(2));
                setKlijent(cursor.getString(3));
                setBudzet(cursor.getString(4));
            }while(cursor.moveToNext());
        }cursor.close();

        putNaziv.setText(getNaziv());
        putKlijent.setText(getKlijent());
        putBudzet.setText(String.valueOf(Math.round(Double.parseDouble(getBudzet()))));
        putOpis.setText(getOpis());


        Cursor brojac = mb.troskovnikDetails(getId());

        int j = 0;
        while(brojac.moveToNext()){
            j++;
            setBrojRedaka(j);
        }
        brojac.close();

        cijenaStavke = new double[getBrojRedaka()];
        setUkupniTrosak(0);
        Cursor cursorStavka = mb.troskovnikDetails(getId());
        int i = 0;
        while(cursorStavka.moveToNext()){
            double pomm = getUkupniTrosak();
            cijenaStavke[i] = cursorStavka.getDouble(3);
            setUkupniTrosak(pomm+cijenaStavke[i]);
            i++;
        }
        cursorStavka.close();
        putTrosak.setText(String.valueOf(getUkupniTrosak()));



    }

    public void dodajProcjenu(View v){
        Intent iProcjena = new Intent(this, ProcjenaActivity.class);
        startActivity(iProcjena);
    }

    public void prebaciTroskovnik(View v){
        Intent iTroskovnik = new Intent(ProjektActivity.this, Troskovnik.class);
        startActivity(iTroskovnik);
    }

}
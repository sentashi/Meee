package com.example.meee;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Troskovnik extends AppCompatActivity {
    SharedPrefs session;
    MemoBaza mb;
    int redniBroj[], id_projekta[];
    int id, kolStavke[];
    double cijenaStavke[];
    String nazivStavke[];
    private int brojRedaka;
    RecyclerView recyclerViewTroskovnik;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojRedaka() {
        return brojRedaka;
    }

    public void setBrojRedaka(int brojRedaka) {
        this.brojRedaka = brojRedaka;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.troskovnik);

        session = new SharedPrefs(Troskovnik.this);
        String pom = session.getProjectDetails(Troskovnik.this);
        int pomId = Integer.parseInt(pom);
        setId(pomId);

        mb = new MemoBaza(Troskovnik.this);


        Cursor brojac = mb.troskovnikDetails(getId());

        int j = 0;
        while(brojac.moveToNext()){
            j++;
            setBrojRedaka(j);
        }
        brojac.close();

        redniBroj = new int[getBrojRedaka()];
        id_projekta = new int[getBrojRedaka()];
        nazivStavke= new String[getBrojRedaka()];
        kolStavke = new int[getBrojRedaka()];
        cijenaStavke = new double[getBrojRedaka()];

        Cursor cursorStavka = mb.troskovnikDetails(getId());
        int i = 0;
        while(cursorStavka.moveToNext()){
            redniBroj[i] = i+1;
            id_projekta[i] = cursorStavka.getInt(0);
            nazivStavke[i] = cursorStavka.getString(1);
            kolStavke[i]= cursorStavka.getInt(2);
            cijenaStavke[i] = cursorStavka.getDouble(3);
            i++;
        }
        cursorStavka.close();
        MyAdapterTroskovnik myAdapter = new MyAdapterTroskovnik(this, redniBroj, id_projekta, nazivStavke, kolStavke, cijenaStavke);
        recyclerViewTroskovnik = (RecyclerView) findViewById(R.id.recycler_view_troskovnik);
        recyclerViewTroskovnik.setAdapter(myAdapter);
        recyclerViewTroskovnik.setLayoutManager(new LinearLayoutManager(this));

    }


}

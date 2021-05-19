package com.example.meee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MojiProjektiActivity extends AppCompatActivity {
    SharedPrefs session;
    MemoBaza mb;
    int[] redniBroj;
    int[] id_projekta;
    String[] nazivProjekt;
    String[] nazivKlijent;
    private int idKor;
    RecyclerView recyclerView;
    int brojRedaka;

    public int getBrojRedaka() {
        return brojRedaka;
    }

    public void setBrojRedaka(int brojRedaka) {
        this.brojRedaka = brojRedaka;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mojiprojekti);

        mb = new MemoBaza(this);
        session = new SharedPrefs(MojiProjektiActivity.this);

        HashMap<String, String> user = session.getUserDetails(MojiProjektiActivity.this);
        String strId = user.get(SharedPrefs.USER_ID);
        setIdKor(Integer.parseInt(strId));

        Cursor brojac = mb.readAllProjects(getIdKor());

        int j = 0;
        while(brojac.moveToNext()){
            j++;
            setBrojRedaka(j);
        }
        brojac.close();

        redniBroj = new int[brojRedaka];
        id_projekta = new int[brojRedaka];
        nazivProjekt  = new String[brojRedaka];
        nazivKlijent = new String[brojRedaka];

        Cursor cursor = mb.readAllProjects(getIdKor());
        int i = 0;
        while(cursor.moveToNext()){
            redniBroj[i] = i+1;
            id_projekta[i] = cursor.getInt(0);
            nazivProjekt[i]= cursor.getString(1);
            nazivKlijent[i] = cursor.getString(3);
            i++;
        }
        cursor.close();
        MyAdapter myAdapter = new MyAdapter(this, redniBroj, id_projekta, nazivProjekt, nazivKlijent);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}
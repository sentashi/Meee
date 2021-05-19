package com.example.meee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    SharedPrefs session;
    String nazivPro[];
    String klijent[];
    int redniBr[], idProj[];
    Context context;


    public MyAdapter(Context con, int arr1[], int arr2[], String arr3[], String arr4[]) {
        context = con;
        redniBr = arr1;
        idProj = arr2;
        nazivPro = arr3;
        klijent = arr4;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.redniBroj.setText(String.valueOf(redniBr[position]));
        holder.myProjektNaslov.setText(nazivPro[position]);
        holder.myKlijentNaziv.setText(klijent[position]);
        int pom = idProj[position];

        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                session = new SharedPrefs(context);
                Intent intent = new Intent(context, ProjektActivity.class);

                session.projectSession(String.valueOf(pom));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nazivPro.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView redniBroj, myProjektNaslov, myKlijentNaziv;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            redniBroj = itemView.findViewById(R.id.redniBrTextView);
            myProjektNaslov = itemView.findViewById(R.id.projektTextView);
            myKlijentNaziv = itemView.findViewById(R.id.klijentTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

package com.example.meee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterTroskovnik extends RecyclerView.Adapter<MyAdapterTroskovnik.MyViewHolder> {
    SharedPrefs session;
    String stavka[];
    int redniBrojcic[], kolicina[], id_projekta[];
    double cijena[];
    Context context;

    public MyAdapterTroskovnik(Context con, int arr1[], int arr2[], String arr3[], int arr4[], double arr5[]){
        context = con;
        redniBrojcic = arr1;
        id_projekta = arr2;
        stavka = arr3;
        kolicina = arr4;
        cijena = arr5;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stavka, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.redniBr.setText(String.valueOf(redniBrojcic[position]));
        holder.myNazivStavke.setText(stavka[position]);
        holder.myKolicinaStavke.setText(String.valueOf(kolicina[position]));
        holder.myCijenaStavke.setText(String.valueOf(cijena[position]));

    }

    @Override
    public int getItemCount() {
        return stavka.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView redniBr, myNazivStavke, myKolicinaStavke, myCijenaStavke;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            redniBr = itemView.findViewById(R.id.redniBrTextView);
            myNazivStavke = itemView.findViewById(R.id.stavkaTextView);
            myKolicinaStavke = itemView.findViewById(R.id.kolicinaTextView);
            myCijenaStavke = itemView.findViewById(R.id.cijenaTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

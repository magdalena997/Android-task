package com.example.zadatak1;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PodaciLista extends ArrayAdapter{

    private Activity context;

    private ArrayList<Podatak> podatak;

    public PodaciLista(Activity context, ArrayList<Podatak> podatak) {
        super(context, R.layout.row_item, podatak);
        this.context = context;
        this.podatak = podatak;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textNaslov = (TextView) row.findViewById(R.id.textNaslov);
        TextView textBody = (TextView) row.findViewById(R.id.textBody);
        Button button = (Button) row.findViewById(R.id.dugme);


        textNaslov.setText(podatak.get(position).getNaslov());
        textBody.setText(podatak.get(position).getBody());

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button



                Intent intent = new Intent(context, Detalji.class);
                intent.putExtra("naslov", podatak.get(position).getNaslov());
                intent.putExtra("telo", podatak.get(position).getBody());
                intent.putExtra("id", podatak.get(position).getId());
                intent.putExtra("userId", podatak.get(position).getUserId());
                intent.putExtra("pos", position);
                context.startActivity(intent);



            }
        });

        return  row;
    }
}

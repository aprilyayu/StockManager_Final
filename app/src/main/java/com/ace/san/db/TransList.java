package com.ace.san.db;

//import libraries sesuai kebutuhan
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ace.san.R;
import java.util.List;

public class TransList extends ArrayAdapter<Trans> {
    private Activity context;

    //diambil dari Trans.java
    List<Trans> transList;


    public TransList(Activity context, List<Trans> transList) {
        //super(context, R.layout.activity_translist, transList); bersumber dari activity_translist
        super(context, R.layout.activity_translist, transList);
        this.context = context;
        this.transList = transList;
    }

    //awal dari sebuah metode view di java
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewTran = inflater.inflate(R.layout.activity_translist, null, true);

        //Pendeklarasian objek (name dan quantity) text view untuk dimasukkan ke dalam list view transaksi
        TextView textViewName = (TextView) listViewTran.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewTran.findViewById(R.id.textViewQty);

        Trans tran = transList.get(position);
        textViewName.setText(tran.getName());
        textViewGenre.setText(tran.getQuantity());

        return listViewTran;
    }
}
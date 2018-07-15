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

//ArrayAdapter<Item> membuat fungsi dan data yang akan ditampilkan di list view
public class ItemList extends ArrayAdapter<Item> {
    private Activity context;
    //List<Item> diambil dari db Item
    private List<Item> itemList;

    //public ItemList(Activity context, List<Item> itemList) diambil dari list.java pada folder Login
    public ItemList(Activity context, List<Item> itemList){
        //super(context, R.layout.activity_itemlist, itemList); bersumber dari activity_itemList
        super(context, R.layout.activity_itemlist, itemList);
        this.context = context;
        this.itemList = itemList;
    }


    //awal dari sebuah metode View di java
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_itemlist, null, true);

        //Pendeklarasian objek (name dan category) text view untuk dimasukkan ke dalam listView
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewCat = (TextView) listViewItem.findViewById(R.id.textViewCat);

        Item item = itemList.get(position);
        textViewName.setText(item.getName());
        textViewCat.setText(item.getCategory());

        return listViewItem;
    }
}
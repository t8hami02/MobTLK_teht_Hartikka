package com.example.lab4_1_mobtlk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<AlueetClass> {

    private Context mContext;
    private List<AlueetClass> alueetList = new ArrayList<>();
    //private static LayoutInflater inflater = null;

    public CustomArrayAdapter(@NonNull Context context, ArrayList<AlueetClass> list) {
        super(context, 0 , list);
        this.mContext = context;
        this.alueetList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        AlueetClass currentAlue = alueetList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentAlue.getNimi());

        return listItem;
    }
}

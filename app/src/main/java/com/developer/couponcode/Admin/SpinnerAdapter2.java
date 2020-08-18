package com.developer.couponcode.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.couponcode.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter2 extends ArrayAdapter<SpinnerClass> {

    public SpinnerAdapter2(@NonNull Context context, ArrayList<SpinnerClass> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    public View initView(int position, View convertview, ViewGroup parent)
    {
        if(convertview==null)
        {
            convertview= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
        }
        TextView item_name=(TextView)convertview.findViewById(R.id.spinner_item_name);
        SpinnerClass obj=getItem(position);
        if(obj.getName()!=null)
        {
            item_name.setText(obj.getName());
        }
        return convertview;
    }
}

package com.developer.couponcode.User;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;


import com.developer.couponcode.R;
import com.developer.couponcode.User.userEntry;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder1>{
    ArrayList<userEntry> list;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    ArrayList<String> keys;
    public UserAdapter(ArrayList<userEntry> list, Context context,ArrayList<String> keys)
    {
        this.list=list;
        this.keys=keys;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.user_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final userEntry adapter=list.get(position);
        String date2[]=keys.get(position).split("_");
        holder.date.setText(date2[0]);
       //vpjnlk
        // holder.date.setText("08/08/2020");
        //  holder.description.setText("Description :-"+adapter.getDecription());
        holder.refferal.setText(adapter.getRefferal());
        holder.phone.setText(adapter.getPhone());
        holder.name.setText(adapter.getName());
        holder.price.setText(adapter.getPrice());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView phone,refferal;
        TextView name,price;
        LinearLayout prescribtion;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            date=(TextView) itemView.findViewById(R.id.date);
            name=(TextView) itemView.findViewById(R.id.name);
            phone=(TextView) itemView.findViewById(R.id.phone);
            refferal=(TextView) itemView.findViewById(R.id.r2);
            price=(TextView) itemView.findViewById(R.id.price);
        }
    }
    public void setProgress()
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    ProgressDialog progressDialog;
}


package com.developer.couponcode.Admin;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.developer.couponcode.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class dashboard2 extends RecyclerView.Adapter<dashboard2.viewholder1>{
    ArrayList<adminEntry> list;
    ArrayList<String> keys;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public dashboard2(ArrayList<adminEntry> list, Context context,ArrayList<String> keys)
    {
        this.list=list;
        this.context=context;
        this.keys=keys;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.record_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final adminEntry adapter=list.get(position);
        holder.date.setText("08/08/2020");
      //  holder.description.setText("Description :-"+adapter.getDecription());
        holder.refferal.setText(adapter.getRefferal());
        holder.phone.setText(adapter.getPhone());
        holder.name.setText(adapter.getName());
        holder.price.setText(adapter.getPrice());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(context,EditTransaction.class);
            intent.putExtra("name",adapter.getName());
            intent.putExtra("phone",adapter.getPhone());
            intent.putExtra("price",adapter.getPrice());
            intent.putExtra("rate","5");
            intent.putExtra("refferal",adapter.getRefferal());
            intent.putExtra("description",adapter.getDecription());
            intent.putExtra("key",keys.get(position));
            context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Information")
                        .setMessage("Are you sure you want to delete the transaction record")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reference.child("records").child(keys.get(position)).removeValue();
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,list.size());
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context,"Action cancelled",Toast.LENGTH_SHORT).show();
                                //builder.
                            }
                        });
                builder.create();
                builder.show();
            }
        });
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
        Button edit,delete;
        LinearLayout prescribtion;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            date=(TextView) itemView.findViewById(R.id.date);
            name=(TextView) itemView.findViewById(R.id.name);
            phone=(TextView) itemView.findViewById(R.id.phone);
            refferal=(TextView) itemView.findViewById(R.id.r2);
            price=(TextView) itemView.findViewById(R.id.price);
            edit=(Button)itemView.findViewById(R.id.edit);
            delete=(Button)itemView.findViewById(R.id.delete);
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


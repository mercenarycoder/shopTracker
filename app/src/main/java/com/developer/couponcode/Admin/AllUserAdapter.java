package com.developer.couponcode.Admin;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView ;
import android.widget.TextView;
import android.widget.Toast;


import com.developer.couponcode.Admin.Users;
import com.developer.couponcode.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.viewholder1>{
    ArrayList<Users> list;
    ArrayList<String> users;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public AllUserAdapter(ArrayList<Users> list, Context context,ArrayList<String> users)
    {
        this.list=list;
        this.context=context;
        this.users=users;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.count_user, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final Users adapter=list.get(position);
        holder.phone.setText(adapter.getNumber());
        holder.points.setText(adapter.getPoints());
        holder.pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context, 0);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.creditalert);
                final EditText point=(EditText)dialog.findViewById(R.id.point);
                Button confirm=(Button)dialog.findViewById(R.id.confirm);
                Button cancel=(Button)dialog.findViewById(R.id.cancel);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str=point.getText().toString();
                        String key=users.get(position);
                        modifyCredit(adapter.getPoints(),adapter.getMinus(),str,key,adapter.getNumber());
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context, 0);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.creditalert);
                final EditText point=(EditText)dialog.findViewById(R.id.point);
                Button confirm=(Button)dialog.findViewById(R.id.confirm);
                Button cancel=(Button)dialog.findViewById(R.id.cancel);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str=point.getText().toString();
                        String key=users.get(position);
                        modifyCredit(adapter.getPoints(),adapter.getMinus(),str,key,adapter.getNumber());
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    public void modifyCredit(String point,String minus,String str,String key,String phn)
    {
    int p=Integer.parseInt(point);
    int cut=Integer.parseInt(str);
    if(cut>p)
    {
        Toast.makeText(context,"Not Enough credit points",Toast.LENGTH_SHORT).show();
        return;
    }
    else
    {
        int add=Integer.parseInt(minus);
        cut+=add;
    }
    reference.child("users").child(key).child("minus").setValue(cut);
    addTransaction(phn,String.valueOf(str));
    Toast.makeText(context,String.valueOf(str),Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView phone,points;
        LinearLayout pres;
        Button edit;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            phone=(TextView) itemView.findViewById(R.id.phone);
            points=(TextView)itemView.findViewById(R.id.points);
            pres=(LinearLayout)itemView.findViewById(R.id.pres);
            edit=(Button)itemView.findViewById(R.id.edit);
        }
    }
    public void addTransaction(String phn,String minus)
    {
        Date currentTime = Calendar.getInstance().getTime();
        String key=String.valueOf(currentTime);
        transaction see=new transaction(phn,minus);
        reference.child("transactions").child(key).setValue(see).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           Toast.makeText(context,"Transaction added to histry",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Transaction failed",Toast.LENGTH_SHORT).show();
            }
        });
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


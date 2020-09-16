package com.developer.couponcode.User;

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


import com.developer.couponcode.Admin.transaction;
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

public class FinalAdapter extends RecyclerView.Adapter<FinalAdapter.viewholder1>{
    ArrayList<transaction> list;
    ArrayList<String> time;
    Context context;
    int cats[];
    int anchor[];
    int i=4;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public FinalAdapter(ArrayList<transaction> list, Context context,ArrayList<String> time)
    {
        this.list=list;
        this.context=context;
        this.time=time;
    }
    @NonNull
    @Override
    public FinalAdapter.viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.count_user2, parent,
                false);
        FinalAdapter.viewholder1 viewhold=new FinalAdapter.viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final FinalAdapter.viewholder1 holder, final int position) {
        final transaction adapter=list.get(position);
        holder.phone.setText(adapter.getPhn());
        holder.points.setText(adapter.getPoint());
        holder.alter.setText("Date & Time");
//        holder.edit.setVisibility(View.INVISIBLE);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView phone,points,alter;
        LinearLayout pres;
       // Button edit;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            phone=(TextView) itemView.findViewById(R.id.phone);
            points=(TextView)itemView.findViewById(R.id.points);
            alter=(TextView)itemView.findViewById(R.id.alter);
            pres=(LinearLayout)itemView.findViewById(R.id.pres);
         //   edit=(Button)itemView.findViewById(R.id.edit);
        }
    }
}



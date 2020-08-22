package com.developer.couponcode.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.couponcode.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditTransaction extends AppCompatActivity {
EditText name,phone,price,refferal,description;
TextView date;
Spinner rates;
SpinnerAdapter2 adapter2;
ArrayList<SpinnerClass> list;
ImageButton close;
Button update;
Context context;
DatabaseReference reference;
String name2,phone2,price2,refferal2,description2,rate2,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=EditTransaction.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        formOptions();
        initialize();
        adapter2=new SpinnerAdapter2(context,list);
        rates.setAdapter(adapter2);
        Intent intent=getIntent();
        name2=intent.getStringExtra("name");
        phone2=intent.getStringExtra("phone");
        price2=intent.getStringExtra("price");
        refferal2=intent.getStringExtra("refferal");
        description2=intent.getStringExtra("description");
        rate2=intent.getStringExtra("rate");
        key=intent.getStringExtra("key");
        if(rate2.equals("5"))
        {
            rates.setSelection(0);
        }
        else if(rate2.equals("10"))
        {
            rates.setSelection(1);
        }
        else {
            rates.setSelection(2);
        }
        key=intent.getStringExtra("key");
        name.setText(name2);
        phone.setText(phone2);
        price.setText(price2);
        refferal.setText(refferal2);
        description.setText(description2);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            name2=name.getText().toString();
            phone2=phone.getText().toString();
            price2=price.getText().toString();
            refferal2=refferal.getText().toString();
            description2=description.getText().toString();
            reference.child("records").child(key).setValue(new adminEntry(name2,phone2,refferal2,price2,description2,rate2));
            finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerClass item=adapter2.getItem(i);
                rate2=item.getId();
              //  Toast.makeText(context,rate2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void initialize()
    {
        reference= FirebaseDatabase.getInstance().getReference();
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        price=(EditText)findViewById(R.id.price);
        refferal=(EditText)findViewById(R.id.referral);
        description=(EditText)findViewById(R.id.description);
        date=(TextView)findViewById(R.id.date);
        rates=(Spinner)findViewById(R.id.rates);
        update=(Button)findViewById(R.id.update);
        close=(ImageButton)findViewById(R.id.close);
    }
    public void formOptions()
    {
        list=new ArrayList<>();
        list.add(new SpinnerClass("1","1 %"));
        list.add(new SpinnerClass("2","2 %"));
        list.add(new SpinnerClass("3","3 %"));
        list.add(new SpinnerClass("4","4 %"));
        list.add(new SpinnerClass("5","5 %"));
        list.add(new SpinnerClass("6","6 %"));
        list.add(new SpinnerClass("7","7 %"));
        list.add(new SpinnerClass("8","8 %"));
        list.add(new SpinnerClass("9","9 %"));
        list.add(new SpinnerClass("10","10 %"));
        list.add(new SpinnerClass("11","11 %"));
        list.add(new SpinnerClass("12","12 %"));
        list.add(new SpinnerClass("13","13 %"));
        list.add(new SpinnerClass("14","14 %"));
        list.add(new SpinnerClass("15","15 %"));
        list.add(new SpinnerClass("16","16 %"));
        list.add(new SpinnerClass("17","17 %"));
        list.add(new SpinnerClass("18","18 %"));
        list.add(new SpinnerClass("19","19 %"));
        list.add(new SpinnerClass("20","20 %"));
    }
}
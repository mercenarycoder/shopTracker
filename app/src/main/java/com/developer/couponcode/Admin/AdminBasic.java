package com.developer.couponcode.Admin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.couponcode.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminBasic extends Fragment {
    EditText name,phone,purchase,refferal,description;
    TextView date;
    DatabaseReference reference;
    Context context;
    Button save_service2;
    Spinner rates;
    SpinnerAdapter2 adapter2;
    ArrayList<SpinnerClass> list;
    Calendar myCalendar;
    String nameS,phoneS,priceS,refferalS,descriptionS,rateS="5";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.user_infoadder, container, false);
     reference= FirebaseDatabase.getInstance().getReference();
     name=(EditText)view.findViewById(R.id.name);
     context=getContext();
     formList();
     myCalendar=Calendar.getInstance();
     phone=(EditText)view.findViewById(R.id.phone);
     purchase=(EditText)view.findViewById(R.id.price);
     refferal=(EditText)view.findViewById(R.id.referral);
     adapter2=new SpinnerAdapter2(context,list);
     rates=(Spinner)view.findViewById(R.id.rates);
     rates.setAdapter(adapter2);
     rates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             SpinnerClass item=list.get(i);
             rateS=item.getId();
            // Toast.makeText(context,rateS,Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });
     date=(TextView)view.findViewById(R.id.date);
     date.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                 @Override
                 public void onDateSet(DatePicker view, int year, int monthOfYear,
                                       int dayOfMonth) {
                     // TODO Auto-generated method stub
                     myCalendar.set(Calendar.YEAR, year);
                     myCalendar.set(Calendar.MONTH, monthOfYear);
                     myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                     String myFormat = "MM/dd/yy"; //In which you need put here
                     SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    date.setText(sdf.format(myCalendar.getTime()));
                 }
             };
             new DatePickerDialog(context,date2,myCalendar.get(Calendar.YEAR),
                     myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
         }
     });
     description=(EditText)view.findViewById(R.id.description);
     save_service2=(Button)view.findViewById(R.id.save_service2);
     save_service2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             nameS=name.getText().toString();
             phoneS=phone.getText().toString();
             priceS=purchase.getText().toString();
             refferalS=refferal.getText().toString();
             descriptionS=description.getText().toString();
             if(phoneS.length()==10&&!nameS.isEmpty()&&refferalS.length()==10&&!priceS.isEmpty())
             {
                 Toast.makeText(context,"Record being inserted in the Database",Toast.LENGTH_SHORT).show();
                 enter();
             }
             else
             {
                 Toast.makeText(context,"Please Fill the details properly",Toast.LENGTH_SHORT).show();
             }
         }
     });
        return view;
    }
    public void formList()
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
    public void enter()
    {
        adminEntry entry=new adminEntry(nameS,phoneS,refferalS,priceS,descriptionS,rateS);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        reference.child("records").child(currentDate+"_"+currentTime).setValue(entry);
        name.setText("");
        phone.setText("");
        purchase.setText("");
        refferal.setText("");
        description.setText("");
    }
}

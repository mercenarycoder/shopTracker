package com.developer.couponcode.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.developer.couponcode.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RecordSeeAdmin extends Fragment {
    RecyclerView records;
    dashboard2 adapter;
    ArrayList<adminEntry> list,search_list;
    ArrayList<String> keys,search;
    Context context;
    SwipeRefreshLayout refresh;
    DatabaseReference reference;
    EditText search_key;
    LinearLayout mainlayout;
    ImageButton now_search;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        reference= FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.records_see, container, false);
        records=(RecyclerView)view.findViewById(R.id.records);
        search_key=(EditText)view.findViewById(R.id.search_key);
        mainlayout=(LinearLayout)view.findViewById(R.id.mainlayout);
        now_search=(ImageButton)view.findViewById(R.id.now_search);
        refresh=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                search_key.setText("");
                new getData().execute();
            }
        });
        now_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String str=search_key.getText().toString();
             if(str.length()<=0)
             {
                 new getData().execute();
             }
             else {
                 // Then just use the following:
                 InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                 imm.hideSoftInputFromWindow(mainlayout.getWindowToken(), 0);
                 searchKey(str);
             }
            }
        });
        new getData().execute();
        return view;
    }
    public void searchKey(String str)
    {
    search_list=new ArrayList<>();
    search=new ArrayList<>();
    for(int i=0;i<list.size();i++)
    {
        adminEntry item=list.get(i);
        if(item.name.contains(str)||item.rate.contains(str)||item.decription.contains(str)||item.price.contains(str)
        ||item.refferal.contains(str))
        {
            search_list.add(item);
            search.add(keys.get(i));
        }
    }
        adapter=new dashboard2(search_list,context,search);
        records.setLayoutManager(new LinearLayoutManager(context));
        records.setHasFixedSize(true);
        records.setAdapter(adapter);
    }
    public class getData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            list=new ArrayList<>();
            keys=new ArrayList<>();
            ValueEventListener listner=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        String name=postSnapshot.getKey().toString();
                        keys.add(name);
                        System.out.println("------------------------------"+name);
                        adminEntry entry=postSnapshot.getValue(adminEntry.class);
                        list.add(entry);
                        System.out.println("-------------------------------"+entry.getPhone());
                    }
                    progressDialog.dismiss();
                    adapter=new dashboard2(list,context,keys);
                    records.setLayoutManager(new LinearLayoutManager(context));
                    records.setHasFixedSize(true);
                    records.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.child("records").addValueEventListener(listner);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}

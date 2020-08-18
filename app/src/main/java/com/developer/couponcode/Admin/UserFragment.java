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
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class UserFragment extends Fragment{
Context context;
ArrayList<Users> list,search;
RecyclerView users;
SwipeRefreshLayout refresh;
TextView not_found;
EditText search_key;
ImageButton now_search;
DatabaseReference reference;
ProgressDialog progressDialog;
AllUserAdapter adapter;
RelativeLayout mainlayout;
ArrayList<String> list_users,searchUsers;
boolean check=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        reference=FirebaseDatabase.getInstance().getReference();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        users=(RecyclerView)view.findViewById(R.id.users);
        refresh=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        search_key=(EditText)view.findViewById(R.id.search_key);
        mainlayout=(RelativeLayout)view.findViewById(R.id.mainlayout);
        now_search=(ImageButton)view.findViewById(R.id.now_search);
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
                    searchMatch(str);
                }
            }
        });
        not_found=(TextView)view.findViewById(R.id.not_found);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                search_key.setText("");
                new getData().execute();
            }
        });
        new getData().execute();
        return view;
    }
    public void searchMatch(String str)
    {
        search=new ArrayList<>();
        searchUsers=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            Users user=list.get(i);
            if(user.number.contains(str))
            {
                search.add(user);
                searchUsers.add(list_users.get(i));
            }
        }
        adapter=new AllUserAdapter(search,context,searchUsers);
        users.setLayoutManager(new LinearLayoutManager(context));
        users.setHasFixedSize(true);
        users.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(check)
       new getData().execute();
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
            ValueEventListener listner=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list=new ArrayList<>();
                    list_users=new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        String name=postSnapshot.getKey().toString();
                        System.out.println("------------------------------"+name);
                        //adminEntry entry=postSnapshot.getValue(adminEntry.class);
                        String credit = postSnapshot.child("credit").getValue().toString();
                        String minus=postSnapshot.child("minus").getValue().toString();
                        int val=Integer.parseInt(minus);
                        int main=Integer.parseInt(credit);
                        if(main>val)
                        {
                            main=main-val;
                        }
                        else
                        {
                            main=0;
                        }
                        credit=String.valueOf(main);
                        list.add(new Users(name,credit,minus));
                        list_users.add(name);
                       // System.out.println("-------------------------------"+entry.getPhone());
                    }
                    progressDialog.dismiss();
                    adapter=new AllUserAdapter(list,context,list_users);
                    users.setLayoutManager(new LinearLayoutManager(context));
                    users.setHasFixedSize(true);
                    users.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.child("users").addValueEventListener(listner);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            check=true;
        }
    }
}

package com.developer.couponcode.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.couponcode.Admin.AllUserAdapter;
import com.developer.couponcode.Admin.Users;
import com.developer.couponcode.R;
import com.google.firebase.auth.FirebaseAuth;
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

public class AllTransactionFragment extends Fragment{
    Context context;
    RecyclerView users;
    SwipeRefreshLayout refresh;
    ArrayList<userEntry> list;
    UserAdapter adapter;
    TextView not_found;
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    ArrayList<String> keys;
    DatabaseReference reference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        mauth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        users=(RecyclerView)view.findViewById(R.id.users);
        refresh=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        not_found=(TextView)view.findViewById(R.id.not_found);
        new getData().execute();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                new getData().execute();
            }
        });
        return view;
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
                        System.out.println("------------------------------"+name);
                        userEntry entry=postSnapshot.getValue(userEntry.class);
                       if(mauth.getCurrentUser().getPhoneNumber().contains(entry.getRefferal()))
                        list.add(entry);
                       keys.add(name);
                        // System.out.println("-------------------------------"+entry.getPhone());
                    }
                    progressDialog.dismiss();
                    adapter=new UserAdapter(list,context,keys);
                    users.setLayoutManager(new LinearLayoutManager(context));
                    users.setHasFixedSize(true);
                    users.setAdapter(adapter);
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

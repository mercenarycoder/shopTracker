package com.developer.couponcode.User;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.couponcode.User.FinalAdapter;
import com.developer.couponcode.Admin.Users;
import com.developer.couponcode.Admin.transaction;
import com.developer.couponcode.R;
import com.developer.couponcode.basicClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CreditFragment extends Fragment {
    Context context;
    DatabaseReference reference;
    TextView points;
    ImageButton refresh;
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String cPoints="";
    RecyclerView history;
    SwipeRefreshLayout refresh_history;
    FinalAdapter adapter;
    ArrayList<String> list_users;
    ArrayList<transaction> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        preferences=context.getSharedPreferences("virgin", Context.MODE_PRIVATE);
        editor=preferences.edit();
        reference= FirebaseDatabase.getInstance().getReference();
        mauth=FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reedem_points, container, false);
        points=(TextView)view.findViewById(R.id.points);
        refresh=(ImageButton) view.findViewById(R.id.refresh);
        history=(RecyclerView)view.findViewById(R.id.history);
        refresh_history=(SwipeRefreshLayout)view.findViewById(R.id.refresh_history);
        refresh_history.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_history.setRefreshing(false);
                new getData2().execute();
            }
        });
        new getData().execute();
        new getData2().execute();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            refresh.setEnabled(false);
                new getData().execute();
                new getData2().execute();
            }
        });

        return view;
    }
    public class getData2 extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog=new ProgressDialog(context);
//            progressDialog.setMessage("Data Loading");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ValueEventListener listner=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list=new ArrayList<>();
                  //  list_users=new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        String name=postSnapshot.getKey().toString();
                        System.out.println("------------------------------"+name);
                        //adminEntry entry=postSnapshot.getValue(adminEntry.class);
                        String credit = postSnapshot.child("phn").getValue().toString();
                        if(mauth.getCurrentUser().getPhoneNumber().contains(credit)) {
                            String minus = postSnapshot.child("point").getValue().toString();
                            String date=name.substring(0,10);
                            String time=name.substring(11,19);
                            String arr[]=time.split(":");
                            int hr=Integer.parseInt(arr[0]);
                            if(hr>12)
                            {
                                hr=hr-12;
                                arr[1]+=" pm";
                            }
                            else
                            {
                                arr[1]+=" am";
                            }
                            arr[0]=String.valueOf(hr);
                            name=date+" "+arr[0]+":"+arr[1];
                            list.add(new transaction(name, minus));
                        }
                      //  list_users.add(name);
                        // System.out.println("-------------------------------"+entry.getPhone());
                    }
                   // progressDialog.dismiss();
                    adapter=new FinalAdapter(list,context,list_users);

                    history.setLayoutManager(new LinearLayoutManager(context));
                    history.setHasFixedSize(true);
                    history.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.child("transactions").addValueEventListener(listner);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // check=true;
        }
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

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            //list=new ArrayList<>();
            final int[] value = {0};
            ValueEventListener listner=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        String name=postSnapshot.getKey().toString();
                        System.out.println("------------------------------"+name);
                        userEntry entry=postSnapshot.getValue(userEntry.class);
                        if(mauth.getCurrentUser().getPhoneNumber().contains(entry.getRefferal())) {
                        value[0] +=(Integer.parseInt(entry.getPrice())/(100))*Integer.parseInt(entry.getRate());
                        }
                        // System.out.println("-------------------------------"+entry.getPhone());
                    }
                    cPoints=String.valueOf(value[0]);
                    //    reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).child("credit").setValue(value[0]);
                    refresh.setEnabled(true);
                  ///  progressDialog.dismiss();
                 //   points.setText(cPoints);
                    new confirmData().execute();
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
    public class confirmData extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            ValueEventListener listener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        if (postSnapshot.getKey().equals(mauth.getCurrentUser().getPhoneNumber())) {
                            if (preferences.getString("first", "yes").equals("no")) {
                                String value = postSnapshot.child("minus").getValue().toString();
                                String cre = postSnapshot.child("credit").getValue().toString();
                                basicClass item = postSnapshot.getValue(basicClass.class);
                                int val = Integer.parseInt(cPoints);
                                int mi = item.getMinus();
                                if (mi > val) {
                                    val = 0;
                                } else {
                                    val = val - mi;
                                }

                                points.setText(String.valueOf(val));
                                reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).setValue(new basicClass(cPoints, mi));
                              //  Toast.makeText(context, item.getCredit() + " " + item.getMinus(), Toast.LENGTH_SHORT).show();
                            } else {
                                reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).setValue(new basicClass(cPoints, 0));
                                points.setText(cPoints);
                                editor.putString("first", "no");
                                editor.apply();
                                editor.commit();
                                //Toast.makeText(context,"first he is virgin",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.child("users").addListenerForSingleValueEvent(listener);
            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

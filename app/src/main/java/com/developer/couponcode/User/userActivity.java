package com.developer.couponcode.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.couponcode.Admin.UserFragment;
import com.developer.couponcode.Admin.adminActivity;
import com.developer.couponcode.MainActivity;
import com.developer.couponcode.R;
import com.developer.couponcode.basicClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userActivity extends AppCompatActivity {
Button logout;
FirebaseAuth mauth;
Button credit,refferal_use,history;
DatabaseReference reference;
SharedPreferences preferences;
TextView user_num;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        reference= FirebaseDatabase.getInstance().getReference();
        mauth=FirebaseAuth.getInstance();
        preferences=userActivity.this.getSharedPreferences("virgin", Context.MODE_PRIVATE);
        user_num=(TextView)findViewById(R.id.user_num);
        user_num.setText(mauth.getCurrentUser().getPhoneNumber());
        // reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).child("minus").setValue(0);
        if(preferences.getString("first","no").equals("yes"))
        {
            reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).setValue(new basicClass("0",0));
        }
        logout=(Button)findViewById(R.id.logout);
        credit=(Button)findViewById(R.id.credit);
        refferal_use=(Button)findViewById(R.id.referral_use);
        //history=(Button)findViewById(R.id.history);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mauth.getCurrentUser()!=null)
                {
                    //reference.child("users").child(mauth.getCurrentUser().getPhoneNumber()).setValue("out");
                    mauth.signOut();
                    Intent intent=new Intent(userActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new CreditFragment()).commit();

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new CreditFragment()).commit();
            }
        });
        refferal_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new AllTransactionFragment()).commit();
            }
        });
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             Toast.makeText(userActivity.this,"Will be here soon",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
   static int i=0;
    @Override
    public void onBackPressed() {
        if(i==0)
        {
            i++;
            Toast.makeText(userActivity.this,"press again to exit",Toast.LENGTH_SHORT).show();
        }
        else {
            System.exit(0);
            super.onBackPressed();

        }
    }
}
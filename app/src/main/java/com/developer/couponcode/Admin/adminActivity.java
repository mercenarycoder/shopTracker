package com.developer.couponcode.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.developer.couponcode.MainActivity;
import com.developer.couponcode.R;

public class adminActivity extends AppCompatActivity {
FrameLayout frame_base;
Button insert,see_all,logout,all;
SharedPreferences preferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    preferences=getSharedPreferences("admin",MODE_PRIVATE);
    editor=preferences.edit();
    frame_base=(FrameLayout)findViewById(R.id.frame_base);
    insert=(Button)findViewById(R.id.insert);
    see_all=(Button)findViewById(R.id.see_all);
    logout=(Button)findViewById(R.id.logout);
    all=(Button)findViewById(R.id.all);
    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(adminActivity.this, MainActivity.class);
            editor.putString("login","false");
            editor.apply();
            editor.commit();
            startActivity(intent);
            finish();
        }
    });
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new AdminBasic()).commit();
    insert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            i=0;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new AdminBasic()).commit();
        }
    });
    see_all.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            i=0;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new RecordSeeAdmin()).commit();
        }
    });
    all.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            i=0;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new UserFragment()).commit();
        }
    });
    }
    int i=0;
    @Override
    public void onBackPressed() {
       if(i==0)
       {
           i++;
           Toast.makeText(adminActivity.this,"press again to exit",Toast.LENGTH_SHORT).show();
       }
       else {
       System.exit(0);
           super.onBackPressed();

       }
       }
}
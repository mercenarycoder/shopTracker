package com.developer.couponcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.developer.couponcode.Admin.Admin_Login;
import com.developer.couponcode.Admin.adminActivity;
import com.developer.couponcode.User.LoginUser;
import com.developer.couponcode.User.userActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
Button login,login_admin;
FirebaseAuth mauth;
SharedPreferences preferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        Context context=MainActivity.this;
        preferences=getSharedPreferences("admin",MODE_PRIVATE);
        editor=preferences.edit();
        if(mauth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(MainActivity.this, userActivity.class);
            startActivity(intent);
            finish();
        }
        if(preferences.getString("login","false").equals("true"))
        {
            Intent intent=new Intent(MainActivity.this, adminActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.out_from_bottom);
        login=(Button)findViewById(R.id.login);
        login_admin=(Button)findViewById(R.id.login_admin);
        //login.setAnimation(animation);
        //login_admin.setAnimation(animation);
        login.startAnimation(animation);
        login_admin.startAnimation(animation);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
              //  finish();
            }
        });
        login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Admin_Login.class);
                startActivity(intent);
               // finish();
            }
        });
    }
}
package com.developer.couponcode.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.couponcode.R;

public class Admin_Login extends AppCompatActivity {
Button login;
EditText email,password;
String em,ps;
SharedPreferences preferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);
        preferences=getSharedPreferences("admin",MODE_PRIVATE);
        editor=preferences.edit();
        login=(Button)findViewById(R.id.login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             em=email.getText().toString();
             ps=password.getText().toString();
             if(em.equals("sunil.sharma20101981@gmail.com")&&ps.equals("sunil@2020")) {
                 Intent intent = new Intent(Admin_Login.this, adminActivity.class);
                 editor.putString("login","true");
                 editor.apply();
                 editor.commit();
                 startActivity(intent);
                 finish();
             }
             else
             {
                 Toast.makeText(Admin_Login.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
}
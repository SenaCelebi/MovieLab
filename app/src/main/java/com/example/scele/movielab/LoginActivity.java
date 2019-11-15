package com.example.scele.movielab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_password;
    Button btn_login, btn_regiter;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_login.setOnClickListener(this);
        btn_regiter.setOnClickListener(this);
    }

    public void init(){
        et_email = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);
        btn_login = findViewById(R.id.btn_login);
        btn_regiter = findViewById(R.id.btn_register_inlogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                 intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register_inlogin:
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}

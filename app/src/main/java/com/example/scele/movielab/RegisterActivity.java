package com.example.scele.movielab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name_surname, et_email, et_password;
    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        et_email = findViewById(R.id.et_email_login);
        et_name_surname = findViewById(R.id.et_namesurname_register);
        btn_register = findViewById(R.id.btn_register_with_email_activity_register);
    }
}

package com.example.scele.movielab;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scele.movielab.Database.Contract;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name_surname, et_email, et_password;
    Button btn_register;
    private ContentResolver mResolver;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();


            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();
                    if(email == null || email.matches("") || password.matches("")|| password == null){
                        Toast.makeText(context, "Please Fill the Blanks", Toast.LENGTH_SHORT).show();
                    }else{
                        addUser();
                        Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }


    public void init(){
        et_email = findViewById(R.id.et_email_register);
        et_name_surname = findViewById(R.id.et_namesurname_register);
        et_password = findViewById(R.id.et_password_register);
        btn_register = findViewById(R.id.btn_register_with_email_activity_register);
    }

    public void addUser(){
        String name = et_name_surname.getText().toString();
        String password = et_password.getText().toString();
        String email = et_email.getText().toString();

        mResolver = this.getContentResolver();

        ContentValues values = new ContentValues();

        values.put(Contract.UsersEntry.COLUMN_NAME, name);
        values.put(Contract.UsersEntry.COLUMN_EMAIL, email);
        values.put(Contract.UsersEntry.COLUMN_PASSWORD, password);

        mResolver.insert(Contract.UsersEntry.U_CONTENT_URI, values);

    }
}

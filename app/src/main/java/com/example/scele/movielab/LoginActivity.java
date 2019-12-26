package com.example.scele.movielab;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scele.movielab.Data.FavoriteContract;
import com.example.scele.movielab.Database.Contract;
import com.example.scele.movielab.Models.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_password;
    Button btn_login, btn_regiter;
    Intent intent = null;
    private ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_login.setOnClickListener(this);
        btn_regiter.setOnClickListener(this);
        mResolver = this.getContentResolver();
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
                Log.v("testsena","basıldı");
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email == null || email.matches("") || password.matches("")|| password == null){
                    Toast.makeText(this, "Please Fill the Blanks", Toast.LENGTH_SHORT).show();
                }else{
                    if (isEmailExist(email) && isPasswordExist(password)){
                        intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "Wrong E-mail or Password", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.btn_register_inlogin:
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public boolean isEmailExist(String searchItem){
        String projection [] = {
                Contract.UsersEntry.COLUMN_NAME,
                Contract.UsersEntry.COLUMN_EMAIL,
                Contract.UsersEntry.COLUMN_PASSWORD,
        };

        Uri uri = Contract.UsersEntry.U_CONTENT_URI;
        String selection = Contract.UsersEntry.COLUMN_EMAIL + " =?";
        String [] selectionArgs = { searchItem };
        Log.v("senan",searchItem.toString());
        String Limit = "1";

        Cursor cursor = mResolver.query(uri, projection,selection,selectionArgs,Limit);
        boolean exists = (cursor.getCount()>0);
        cursor.close();
        Log.v("testsena",""+exists);
        return exists;
    }

    public boolean isPasswordExist(String searchItem){
        String projection [] = {
                Contract.UsersEntry.COLUMN_NAME,
                Contract.UsersEntry.COLUMN_EMAIL,
                Contract.UsersEntry.COLUMN_PASSWORD,
        };

        Uri uri = Contract.UsersEntry.U_CONTENT_URI;
        String selection = Contract.UsersEntry.COLUMN_PASSWORD + " =?";
        String [] selectionArgs = { searchItem };
        Log.v("senan",searchItem.toString());
        String Limit = "1";

        Cursor cursor = mResolver.query(uri, projection,selection,selectionArgs,Limit);
        boolean exists = (cursor.getCount()>0);
        cursor.close();
        Log.v("testsena",""+exists);
        return exists;
    }

}

package com.example.sqlitetute5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlitetute5.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.inputName);
        etPassword = findViewById(R.id.inputPw);
    }

    public void addData(View view){
        
        DBHelper dbHelper = new DBHelper(this);
        long val = dbHelper.addInfo(etUsername.getText().toString(), etPassword.getText().toString());
        
        if(val>0){
            Toast.makeText(this, "Data inserted successfully.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data not inserted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewAll(View view){

        DBHelper dbHelper = new DBHelper(this);

        List unames = dbHelper.readAllInfo("user");

        Toast.makeText(this, unames.toString(), Toast.LENGTH_SHORT).show();
    }

    public void deletedata(View view){

        DBHelper dbHelper = new DBHelper(this);

        //call deleteInfo method
        dbHelper.deleteInfo(etUsername.getText().toString());

        Toast.makeText(this, etUsername.getText().toString() + " Deleted successflly", Toast.LENGTH_SHORT).show();
    }

    public void updateData(View view){

        DBHelper dbHelper = new DBHelper(this);
        int val = dbHelper.updateInfo(etUsername.getText().toString(), etPassword.getText().toString());
        
        if(val>0){
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view){

        DBHelper dbHelper = new DBHelper(this);
        List usernames = dbHelper.readAllInfo("user"); //pass user to get username
        List passwords = dbHelper.readAllInfo("password");

        String user = etUsername.getText().toString(); //converting to string
        String password = etPassword.getText().toString();

        if(usernames.indexOf(user)>=0){
            if(passwords.get(usernames.indexOf(user)).equals(password)){
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
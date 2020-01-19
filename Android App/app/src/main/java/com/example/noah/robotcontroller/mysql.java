package com.example.noah.robotcontroller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class mysql extends AppCompatActivity {
String url = "";
String uid = "phone";
String pwd = "almostdoneguys";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);

    }
    private class mysqlconnect extends AsyncTask<String,Void,String>{
        String res ="";
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(mysql.this,"Please wait...", Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(String...params){
            try{
                Class.forName("com.mysql.cj.jdbc.Drive");
                Connection con = DriverManager.getConnection(url,uid,pwd);
                String sql = "INSERT INTO locationMatrix (xnum, ynum) VALUES(?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.executeUpdate();
            }catch (Exception e){

            }
        }
    }
}

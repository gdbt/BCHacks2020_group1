package com.example.noah.robotcontroller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class mysql extends AppCompatActivity {
String url = "jdbc://mysql:/***.***.***.***/g1RobotDB"; //replace with the IP address used
String uid = "****"; //username for the mysql database
String pwd = "*****"; //password for the mysql database
TextView conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);
        new mysqlconnect().execute(); //this is creating and calling the class mysqlconnect and running the code
    }
    private class mysqlconnect extends AsyncTask<String,Void,String>{
        String res =""; //empty value to return later.
        protected String doInBackground(String...params){
            try{
                int x = 10; //THERE ARE PROOF OF CONCEPT VARIABLES TO TEST THE DATABASE CONNECTION
                int y = 5;
                Class.forName("com.mysql.cj.jdbc.Drive"); //this is the jdbc driver name
                Connection con = DriverManager.getConnection(url,uid,pwd); //creating the connection
                String sql = "INSERT INTO locationMatrix (xnum, ynum) VALUES(?,?)"; //sql string to insert the values into the database
                PreparedStatement stmt = con.prepareStatement(sql); //prepared statement
                stmt.setInt(1,x); //setting the x variable
                stmt.setInt(2,y); //setting the y variable
                stmt.executeUpdate(); //executing the insert update
                conf.setText("Successful update."); //this will print a confirmation on the screen

            }catch (Exception e){
                conf.setText("Failure"); //should an exception occur it will print out a failure error
            }return res; //turning empty variable
        }
    }
}

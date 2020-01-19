package com.example.noah.robotcontroller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class collect_Data extends Activity {
    String address = null; //intialization of variables
    BluetoothAdapter bluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button grabbtn; //initalizing objects
    Button mysqlbutton;
    TextView output;

    private class ConnectBT extends AsyncTask<Void, Void, Void>{
        private boolean ConnectSuccess = true;

        @Override
        protected Void doInBackground(Void... devices) {//doing progress in background{
            try {
                if (btSocket == null || !isBtConnected) {
                    bluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = bluetooth.getRemoteDevice(address);//connects and checks if available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect(); //going through the process of connecting to the bluetooth
                }
            } catch (IOException e) { //in the event of an IO exception it will change to false;
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){ //checking if the connection went through
            super.onPostExecute(result);
            if(!ConnectSuccess){
                finish(); //finishing process so the program doesn't overwork itself
            }
            else{
                isBtConnected = true; //connected and working
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect__data);
        Intent newint = getIntent(); //taking the intent from the previous activity
        address = newint.getStringExtra(Main.dev_add);
        output = (TextView) findViewById(R.id.textOut); //setting up some of the objects
        grabbtn = (Button)findViewById(R.id.grabdata);
        mysqlbutton = (Button)findViewById(R.id.mys);
        new ConnectBT().execute(); //calling the ConnectBT() class and running te code
        this.grabbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                grabdata();
            }
        }); //this is the button that will jump to grabdata.
        this.mysqlbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //this button runs the intent to the mysql page to connect and update the database
                String robodata="robot data";
                String stringdata = "throwing";
                Intent mysqlintent = new Intent(collect_Data.this,mysql.class); //defining the intent.
                mysqlintent.putExtra(robodata, stringdata); //putting the data into the intent
                startActivity(mysqlintent); //launching the mysql activity
            }
        });
    }
    private void grabdata(){ //main connection point that recieves and converts the data from the arduino
        boolean bStop = false; //stop clause
        if (btSocket!=null){ //making sure its connected
            try {
                btSocket.getOutputStream().write("2".toString().getBytes()); //sending the number 2 to the arduino
                try{
                    InputStream input = collect_Data.this.btSocket.getInputStream(); //collecting what the arduino sent back
                    while(!bStop){ //using the stop clause to make sure we don't go out of bounds
                        byte[] buffer = new byte[256];
                        if(input.available() > 0){
                            input.read(buffer);
                            int i =0;
                            while(i < buffer.length && buffer[i] != 0){
                                i++;
                            }
                          final String strinput = new String(buffer,0,i); //this is the compiled output from the arduino
                            // System.out.println(strinput);  **CONSOLE print to check if data is correct**
                            bStop = true; //everything is done so change stop clause to true
                            changeText(strinput); //call the function to change the text.
                        }
                    }
                }
                catch (IOException e){ //catching exceptions
                    e.printStackTrace();
                }
            }
            catch (IOException e){ //catching exceptions
            }
        }
    }
    public void changeText(String text){
        output.setText(text);
    } //small method that changes the textview text to include the gathered values
}


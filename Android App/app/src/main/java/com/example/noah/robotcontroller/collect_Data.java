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
    String address = null;
    BluetoothAdapter bluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;
    Button grabbtn;
    Button mysqlbutton;
    TextView output;




    private class ConnectBT extends AsyncTask<Void, Void, Void>{
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(collect_Data.this,"Connecting...","Please wait!");
        }
        @Override
        protected Void doInBackground(Void... devices) {//doing progress in background{
            try {
                if (btSocket == null || !isBtConnected) {
                    bluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = bluetooth.getRemoteDevice(address);//connects and checks if available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if(!ConnectSuccess){
                System.out.println("Connection failed");
                finish();
            }
            else{
                System.out.println("Connected");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect__data);
        Intent newint = getIntent();
        address = newint.getStringExtra(Main.dev_add);
        output = (TextView) findViewById(R.id.textOut);
        grabbtn = (Button)findViewById(R.id.grabdata);
        mysqlbutton = (Button)findViewById(R.id.mys);
        new ConnectBT().execute();
        this.grabbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                grabdata();
            }
        });
        this.mysqlbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String robodata="robot data";
                String stringdata = "throwing";
                Intent mysqlintent = new Intent(collect_Data.this,mysql.class);
                mysqlintent.putExtra(robodata, stringdata);
                startActivity(mysqlintent);
            }
        });

    }
    private void grabdata(){
        boolean bStop = false;
        if (btSocket!=null){
            try {
                btSocket.getOutputStream().write("2".toString().getBytes());
                try{
                    InputStream input = collect_Data.this.btSocket.getInputStream();
                    while(!bStop){
                        byte[] buffer = new byte[256];
                        if(input.available() > 0){
                            input.read(buffer);
                            int i =0;
                            while(i < buffer.length && buffer[i] != 0){
                                i++;
                            }
                          final String strinput = new String(buffer,0,i);
                            System.out.println(strinput);
                            bStop = true;
                            changeText(strinput);

                        }
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            catch (IOException e){
                System.out.println("Error 324");
            }
        }
    }
    public void changeText(String text){
        output.setText(text);
    }

}


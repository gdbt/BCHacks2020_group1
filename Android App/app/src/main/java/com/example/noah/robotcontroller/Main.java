package com.example.noah.robotcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Set;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    private BluetoothAdapter bluetooth = null;
    private Set <BluetoothDevice>pairbt;
    Button deviceGrab;
    ListView listdevice;
    public static String dev_add = "device_address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceGrab =(Button)findViewById(R.id.viewDevice); //initalizing the button that will run the command to print devices
        listdevice = (ListView)findViewById(R.id.deviceListing); //list for all connected devices
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        deviceGrab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pairbt = bluetooth.getBondedDevices();
                ArrayList devicelist = new ArrayList();
                if(pairbt.size()>0){ //if the size is larger then 0, phone has synced to a device before
                    for(BluetoothDevice btdev : pairbt){ //create the object and allow for us to grab
                        devicelist.add(btdev.getName() + "\n" + btdev.getAddress()); //grabs names and puts in list
                    }
                }
                else{
                    //Put an error message if there is no bluetooth
                }
            final ArrayAdapter arraylister = new ArrayAdapter(Main.this,android.R.layout.simple_list_item_1, devicelist);
            listdevice.setAdapter(arraylister);
            listdevice.setOnItemClickListener(listlisten);
            }
        });
    }
    private AdapterView.OnItemClickListener listlisten = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView av, View v, int arg2, long arg3){
            String info =((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent i = new Intent(Main.this,collect_Data.class);
            i.putExtra(dev_add, address);
            startActivity(i);
        }
    };

}

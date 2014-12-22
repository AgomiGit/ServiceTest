package com.example.agomi.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "Agomi_Service";
    private Button bindService=null;
    private Button sendReply=null;
    private Binder binder=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindService = (Button)findViewById(R.id.bindService);
        sendReply=(Button)findViewById(R.id.sendReply);

        bindService.setOnClickListener(new bindServiceListener() );
        sendReply.setOnClickListener(new sendReplyListener() );

    }

    private class bindServiceListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e(TAG, "bindService Clicked");

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, FirstService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);

        }
    }

    private class sendReplyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.e(TAG, "SendReply Clicked");
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeString("from activity:data");
                try{
                    binder.transact(0,data,reply,0);
                    String str_reply = reply.readString();
                    Log.e(TAG, str_reply);
                }
                catch( Exception e){
                    e.printStackTrace();
                }


        }
    }

    ServiceConnection conn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 從遠端Service取得IBinder給main的binder
            MainActivity.this.binder = (Binder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

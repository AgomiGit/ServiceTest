package com.example.agomi.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.logging.Handler;

/**
 * Created by agomi on 14/12/21.
 */
public class FirstService extends Service {
    private String TAG = "Agomi_Service";

    class FirstBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String str = data.readString();
            Log.e(TAG, str);

            reply.writeString("來自FirstService");
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"=====>Service onCreate<======");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"=====>Service onBind<======");
        return new FirstBinder();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"=====>Service onDestroy<======");
        super.onDestroy();
    }
}

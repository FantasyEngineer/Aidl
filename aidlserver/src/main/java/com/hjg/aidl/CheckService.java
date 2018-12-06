package com.hjg.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * @author houjiguo
 * @data 2018/12/6 14:26
 * @description
 */

public class CheckService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iMyAidlInterface;
    }


    IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public boolean checkName(String name) throws RemoteException {
            startActivity(new Intent(CheckService.this, TwoActivity.class));
            /*这里写书写逻辑*/
            if (name != null && name.length() > 10) {
                return true;
            }
            return false;
        }
    };

}

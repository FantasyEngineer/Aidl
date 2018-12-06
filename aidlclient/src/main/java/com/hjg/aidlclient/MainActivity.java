package com.hjg.aidlclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hjg.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;
    private boolean isConnected = false;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
    }

    public void bind(View view) {
        Intent intent = new Intent();
        intent.setAction("com.hjg.checkservice");
        intent.setPackage("com.hjg.aidl");//隐式意图必须要添加包名，否则报错
        bindService(intent, conn, Service.BIND_AUTO_CREATE);

    }

    public void check(View view) {
        if (!isConnected) {
            Toast.makeText(MainActivity.this, "服务未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Toast.makeText(MainActivity.this, "校验是否通过：" + iMyAidlInterface.checkName(et.getText().toString()), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}

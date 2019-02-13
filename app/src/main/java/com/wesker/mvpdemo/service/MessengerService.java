package com.wesker.mvpdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.wesker.mvpdemo.constants.MyConstants;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";


    public MessengerService() {
    }

    private static class MessengerHandler extends Handler {
        private Messenger mMessenger = null;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "receive msg from Client:" + msg.getData().getString("msg"));
                    mMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain(null,MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg","嗯 ,你的消息我已经收到,稍后回复你.");
                    replyMessage.setData(bundle);
                    try {
                        mMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}

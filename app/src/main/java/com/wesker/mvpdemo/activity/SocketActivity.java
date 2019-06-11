package com.wesker.mvpdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wesker.mvpdemo.R;
import com.wesker.mvpdemo.socket.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketActivity extends AppCompatActivity {
    private List<Integer> imageUrlsl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }

    @OnClick(R.id.bt_socket)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}

package com.wesker.mvpdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.wesker.mvpdemo.R;
import com.wesker.mvpdemo.presenter.HelloWorldPresenter;
import com.wesker.mvpdemo.service.MessengerService;
import com.wesker.mvpdemo.view.HelloWorldView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class HelloWorldActivity extends MvpActivity<HelloWorldView, HelloWorldPresenter> implements HelloWorldView, LifecycleProvider<ActivityEvent> {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    @BindView(R.id.greetingTextView)
    TextView mGreetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public HelloWorldPresenter createPresenter() {
        return new HelloWorldPresenter(HelloWorldActivity.this);
    }

    @Override
    public void showHello(String greetingText) {
        mGreetingTextView.setTextColor(Color.RED);
        mGreetingTextView.setText(greetingText);
    }

    @Override
    public void showGoodbye(String greetingText) {
        mGreetingTextView.setTextColor(Color.BLUE);
        mGreetingTextView.setText(greetingText);
    }

    @Override
    public void startService() {
        Intent intent = new Intent(this, MessengerService.class);
        startService(intent);
    }

    @Override
    public void startMessengerActivity() {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
    }

    @Override
    public void startAIDLActivity() {
        Intent intent = null;
        try {
            //反射启动activity
            intent = new Intent(this, getClass().forName("com.wesker.mvpdemo.activity.BookManagerActivity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    @Override
    public void toBanner() {
        Intent intent = new Intent(this, BannerActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.helloButton)
    public void onMHelloButtonClicked() {
        presenter.greetHello();
//        presenter.toBanner();
    }

    @OnClick(R.id.goodbyeButtonClicked)
    public void onMGoodbyeButtonClickedClicked() {
        presenter.greetGoodbye();
//        presenter.toBanner();
    }

//    @OnClick(R.id.startMessengerService)
//    public void onStartServiceClicked() {
//        presenter.startService();
//    }

    @OnClick(R.id.startMssengerActivity)
    public void onStartMessengerActivityClicked() {
        presenter.startMssengerActivity();
    }

    @OnClick(R.id.startAIDLActivity)
    public void onStartAIDLActivityClicked() {
        presenter.startAIDLActivity();
    }


    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }



}

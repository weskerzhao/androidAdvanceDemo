package com.wesker.mvpdemo.presenter;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.wesker.mvpdemo.thread.GreetingGeneratorTask;
import com.wesker.mvpdemo.view.HelloWorldView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Laughing on 2018/12/17 10:41
 * 邮箱：719240226@qq.com
 */
public class HelloWorldPresenter extends MvpBasePresenter<HelloWorldView> {
    private static final String TAG = "HelloWorldPresenter";
    private GreetingGeneratorTask mGreetingGeneratorTask = GreetingGeneratorTask.getGreetingGeneratorTask();
    private LifecycleProvider<ActivityEvent> lifecycleProvider;

    public HelloWorldPresenter(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    @SuppressLint("CheckResult")
    public void greetHello() {
        mGreetingGeneratorTask.greetingGeneratorTask("hello",lifecycleProvider)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        ifViewAttached(new ViewAction<HelloWorldView>() {
                            @Override
                            public void run(@NonNull HelloWorldView view) {
                                view.showHello(s);
                            }
                        });
                        Log.d(TAG, "subscribe: " + Thread.currentThread());
                    }
                });
    }


    public void toBanner(){
        getView().toBanner();
    }

    @SuppressLint("CheckResult")
    public void greetGoodbye() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Thread.sleep(2000);
                observableEmitter.onNext("Goodbye" + (int) (Math.random() * 100));
                Log.d(TAG, "subscribe: " + Thread.currentThread());
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        ifViewAttached(new ViewAction<HelloWorldView>() {
                            @Override
                            public void run(@NonNull HelloWorldView view) {
                                view.showGoodbye(s);
                            }
                        });
                        Log.d(TAG, "subscribe: " + Thread.currentThread());
                    }
                });
    }

    public void startService() {
        ifViewAttached(new ViewAction<HelloWorldView>() {
            @Override
            public void run(@NonNull HelloWorldView view) {
                view.startService();
            }
        });
    }

    public void startMssengerActivity() {
        ifViewAttached(new ViewAction<HelloWorldView>() {
            @Override
            public void run(@NonNull HelloWorldView view) {
                view.startMessengerActivity();
            }
        });
    }

    public void startAIDLActivity() {
        ifViewAttached(new ViewAction<HelloWorldView>() {
            @Override
            public void run(@NonNull HelloWorldView view) {
                view.startAIDLActivity();
            }
        });
    }

    public void startProviderActivity() {
        ifViewAttached(new ViewAction<HelloWorldView>() {
            @Override
            public void run(@NonNull HelloWorldView view) {
                view.  startProviderActivity();
            }
        });

    }
}

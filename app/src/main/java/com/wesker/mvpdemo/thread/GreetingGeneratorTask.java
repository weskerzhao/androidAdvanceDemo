package com.wesker.mvpdemo.thread;

import android.annotation.SuppressLint;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Laughing on 2019/1/2 16:02
 * 邮箱：719240226@qq.com
 */
public class GreetingGeneratorTask {
    private static final String TAG = "GreetingGeneratorTask";
    private static GreetingGeneratorTask greetingGeneratorTask = new GreetingGeneratorTask();

    private GreetingGeneratorTask() {
    }

    public static GreetingGeneratorTask getGreetingGeneratorTask(){
        return greetingGeneratorTask;
    }

    @SuppressLint("CheckResult")
    public Observable greetingGeneratorTask(final String s, LifecycleProvider<ActivityEvent> lifecycleProvider) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Thread.sleep(2000);
                observableEmitter.onNext(s + (int) (Math.random() * 100));
                Log.d(TAG, "subscribe: " + Thread.currentThread());
            }
        })
                .compose(lifecycleProvider.bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

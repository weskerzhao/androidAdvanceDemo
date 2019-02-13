package com.wesker.mvpdemo.view;


import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * 作者：Laughing on 2018/12/17 10:38
 * 邮箱：719240226@qq.com
 */
public interface HelloWorldView extends MvpView {
    // displays "Hello" greeting text in red text color
    void showHello(String greetingText);

    // displays "Goodbye" greeting text in blue text color
    void showGoodbye(String greetingText);

    void startMessengerActivity();

    void startService();

    void toBanner();

    void startAIDLActivity();
}

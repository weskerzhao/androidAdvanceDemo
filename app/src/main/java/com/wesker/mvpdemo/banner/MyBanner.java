package com.wesker.mvpdemo.banner;

import android.content.Context;
import android.widget.Toast;

import com.youth.banner.Banner;

/**
 * 作者：Laughing on 2019/1/30 14:33
 * 邮箱：719240226@qq.com
 */
public class MyBanner extends Banner{


    public MyBanner(Context context) {
        super(context);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        Toast.makeText( getContext(),""+position,Toast.LENGTH_SHORT);

    }
}

package com.wesker.mvpdemo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.wesker.mvpdemo.Imageloader.GlideImageLoader;
import com.wesker.mvpdemo.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class BannerActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.banner_guide_content)
    BGABanner mBannerGuideContent;
    private List<Integer> imageUrlsl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        init();
        Banner banner = findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageUrlsl);
        //设置轮播时间
        banner.setDelayTime(3000);

        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.NOT_INDICATOR);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        initBanner();


    }

    private void initBanner() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        mBannerGuideContent.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.ic_home1,
                R.mipmap.ic_home2,
                R.mipmap.ic_home3);
        int currentItem = mBannerGuideContent.getCurrentItem();
        Toast.makeText(this, "" + currentItem, Toast.LENGTH_SHORT).show();
        mBannerGuideContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        Toast.makeText(BannerActivity.this, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(BannerActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(BannerActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(BannerActivity.this, "3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void init() {
        imageUrlsl.add(R.mipmap.ic_home1);
        imageUrlsl.add(R.mipmap.ic_home2);
        imageUrlsl.add(R.mipmap.ic_home3);


    }
}

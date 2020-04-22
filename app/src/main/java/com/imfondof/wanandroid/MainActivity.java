package com.imfondof.wanandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.imfondof.wanandroid.base.BaseActivity;
import com.imfondof.wanandroid.ui.find.FindFragment;
import com.imfondof.wanandroid.ui.gank.GankFragment;
import com.imfondof.wanandroid.ui.mine.MineFragment;
import com.imfondof.wanandroid.ui.wanandroid.WanAndroidFragment;
import com.imfondof.wanandroid.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 仿微信界面
 * 见 https://github.com/Imfondof/World/blob/master/wechat/src/main/java/com/imfondof/wechat/MainActivity.java
 */
public class MainActivity extends BaseActivity {
    public static final String BUNDLLE_KEY_POS = "BUNDLLE_KEY_POS";//用于恢复当前的fragment tab
    private int mCurTabPos;
    private ViewPager mVpMain;
    private TextView mTvTitle;
    private Toolbar mToolbar;
    private TabView mBtnWanAndroid, mBtnGank, mBtnFind, mBtnMine;

    private List<String> mTitles = new ArrayList<>(Arrays.asList("玩安卓", "干货", "发现", "我"));
    private List<TabView> mTabs = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        if (savedInstanceState != null) {
            mCurTabPos = savedInstanceState.getInt(BUNDLLE_KEY_POS, 0);
        }

        initView();
        initViewpagerAdapter();
        initEvent();
    }

    private void initViewpagerAdapter() {
        mVpMain.setOffscreenPageLimit(mTitles.size());//设置page被缓存（4个tab只需要设置为2就可以了，设置为size省事）
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);

                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mTvTitle.setText(mTitles.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mTvTitle = findViewById(R.id.tv_tb_title);
        mToolbar = findViewById(R.id.toolbar);
        mBtnWanAndroid = findViewById(R.id.tab_wanandroid);
        mBtnGank = findViewById(R.id.tab_gank);
        mBtnFind = findViewById(R.id.tab_find);
        mBtnMine = findViewById(R.id.tab_mine);

        mBtnWanAndroid.setIconAndText(R.drawable.wanandroid, R.drawable.wanandroid_select, "安卓");
        mBtnGank.setIconAndText(R.drawable.gank, R.drawable.gank_select, "干货");
        mBtnFind.setIconAndText(R.drawable.find, R.drawable.find_select, "发现");
        mBtnMine.setIconAndText(R.drawable.mine, R.drawable.mine_select, "我");

        mTabs.add(mBtnWanAndroid);
        mTabs.add(mBtnGank);
        mTabs.add(mBtnFind);
        mTabs.add(mBtnMine);

        mFragments.add(WanAndroidFragment.newInstance());
        mFragments.add(GankFragment.newInstance());
        mFragments.add(FindFragment.newInstance());
        mFragments.add(MineFragment.newInstance());

        setCurrentTab(mCurTabPos);
    }

    private void setCurrentTab(int pos) {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            if (pos == i) {
                tabView.setProgress(1);
                mTvTitle.setText(mTitles.get(pos));
            } else {
                tabView.setProgress(0);
            }
        }
    }

    public void initEvent() {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVpMain.setCurrentItem(finalI, false);
                    setCurrentTab(finalI);
                }
            });
        }
    }
}

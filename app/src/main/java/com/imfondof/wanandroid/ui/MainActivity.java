package com.imfondof.wanandroid.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.ui.base.BaseActivity;
import com.imfondof.wanandroid.ui.find.FindFrg;
import com.imfondof.wanandroid.ui.system.SystemFrg;
import com.imfondof.wanandroid.ui.mine.MineFrg;
import com.imfondof.wanandroid.ui.index.IndexFrg;
import com.imfondof.wanandroid.other.utils.ToastUtil;
import com.imfondof.wanandroid.other.view.TabView;

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

    private List<String> mTitles = new ArrayList<>(Arrays.asList("安卓", "体系", "发现", "我"));
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBtnWanAndroid = findViewById(R.id.tab_wanandroid);
        mBtnGank = findViewById(R.id.tab);
        mBtnFind = findViewById(R.id.tab_find);
        mBtnMine = findViewById(R.id.tab_mine);

        mBtnWanAndroid.setIconAndText(R.drawable.wanandroid, R.drawable.wanandroid_select, "安卓");
        mBtnGank.setIconAndText(R.drawable.gank, R.drawable.gank_select, "体系");
        mBtnFind.setIconAndText(R.drawable.find, R.drawable.find_select, "发现");
        mBtnMine.setIconAndText(R.drawable.mine, R.drawable.mine_select, "我");

        mTabs.add(mBtnWanAndroid);
        mTabs.add(mBtnGank);
        mTabs.add(mBtnFind);
        mTabs.add(mBtnMine);

        mFragments.add(IndexFrg.newInstance());
        mFragments.add(SystemFrg.newInstance());
        mFragments.add(FindFrg.newInstance());
        mFragments.add(MineFrg.newInstance());

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt(BUNDLLE_KEY_POS, mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_add:
                ToastUtil.showToast("添加");
                break;
            case R.id.ic_find:
                ToastUtil.showToast("查找");
                break;
            default:
                break;
        }
        return true;
    }
}

package com.imfondof.wanandroid.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.ui.find.AnimFrg;
import com.imfondof.wanandroid.ui.system.other.GankWelfareFrg;
import com.imfondof.wanandroid.ui.mine.WanCoinDetailFrg;
import com.imfondof.wanandroid.ui.mine.WanCoinRankFrg;
import com.imfondof.wanandroid.ui.mine.WanCollectArticleFrg;
import com.imfondof.wanandroid.ui.mine.WanCollectSiteFrg;
import com.imfondof.wanandroid.ui.find.WanQAFrg;
import com.imfondof.wanandroid.ui.mine.WanShareArticlesFrg;
import com.imfondof.wanandroid.ui.index.frg.WanSquareFrg;

import java.util.ArrayList;

/**
 * 空activity，用于展示fragment
 * 需要传 title、type
 * （使用了viewpager，可以展示多个fragment）
 */
public class EmptyAct extends BaseActivity {
    public static String TYPE = "TYPE";
    public static String TITLE = "TITLE";

    public static int WAN_QA_TYPE = 2001;
    public static String WAN_QA_TITLE = "问答";
    public static int GANK_WELFARE_TYPE = 2002;
    public static String GANK_WELFARE_TITLE = "福利干货";
    public static int WAN_SQUARE_TYPE = 2003;
    public static String WAN_SQUARE_TITLE = "广场";

    public static int WAN_COIN_TYPE = 1001;
    public static String WAN_COIN_TITLE = "积分系统";
    public static int WAN_COLLECT_TYPE = 1002;
    public static String WAN_COLLECT_TITLE = "收藏";
    public static int WAN_SHARE_TYPE = 1003;
    public static String WAN_SHARE_TITLE = "分享";

    public static int VIEW_ANIM = 3001;
    public static String VIEW_ANIM_TITLE = "动画";

    private TextView titleTv;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tab;
    private View line;
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        initView();
    }

    private void initFragmentList(int type) {
        mTitleList.clear();
        mFragments.clear();
        switch (type) {
            case 2001:
                mFragments.add(new WanQAFrg());
                break;
            case 2002:
                mFragments.add(GankWelfareFrg.newInstance());
                break;
            case 2003:
                mFragments.add(WanSquareFrg.newInstance());
                break;
            case 1001:
                line.setVisibility(View.VISIBLE);
                tab.setVisibility(View.VISIBLE);
                mTitleList.add("积分排行榜");
                mFragments.add(WanCoinRankFrg.newInstance());
                mTitleList.add("我的积分");
                mFragments.add(WanCoinDetailFrg.newInstance());
                break;
            case 1002:
                line.setVisibility(View.VISIBLE);
                tab.setVisibility(View.VISIBLE);
                mTitleList.add("文章");
                mFragments.add(WanCollectArticleFrg.newInstance());
                mTitleList.add("网址");
                mFragments.add(WanCollectSiteFrg.newInstance());
                break;
            case 1003:
                mFragments.add(WanShareArticlesFrg.newInstance());
                break;
            case 3001:
                mFragments.add(AnimFrg.newInstance());
                break;
            default:
                return;
        }
    }

    @Override
    public void initView() {
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.vp);
        line = findViewById(R.id.line);
        titleTv = findViewById(R.id.tv_tb_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, 0);
        titleTv.setText(intent.getStringExtra(TITLE));

        initFragmentList(type);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tab.setupWithViewPager(viewPager);
    }

    protected void initEvent() {
    }

    public static void jump(Context context, int type, String title) {
        Intent intent = new Intent(context, EmptyAct.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void jump(Context context, int type) {
        jump(context, type, "");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 返回键
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

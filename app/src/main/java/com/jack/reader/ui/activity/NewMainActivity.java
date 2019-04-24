package com.jack.reader.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hjm.bottomtabbar.BottomTabBar;
import com.jack.reader.R;
import com.jack.reader.ui.fragment.BookrackFragment;
import com.jack.reader.ui.fragment.ClassifyFragment;
import com.jack.reader.ui.fragment.MyFragment;
import com.jack.reader.ui.fragment.RankListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewMainActivity extends AppCompatActivity {

    @Bind(R.id.bottom_bar)
    BottomTabBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);

        bottomBar.init(getSupportFragmentManager())
                .setFontSize(12)
                .setTabPadding(10, 5, 5)
                .setChangeColor(getResources().getColor(R.color.font_tab_blue),getResources().getColor(R.color.font_tab_gray))
                .addTabItem("书架", R.drawable.bookrack_selected, R.drawable.bookrack_default, BookrackFragment.class)
                .addTabItem("分类", R.drawable.classify_selected, R.drawable.classify_default, ClassifyFragment.class)
                .addTabItem("榜单", R.drawable.toplist_selected,R.drawable.toplist_default, RankListFragment.class)
                .addTabItem("我的", R.drawable.find_selected,R.drawable.find_default, MyFragment.class)
                .isShowDivider(true)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.i("TGA", "位置：" + position + "      选项卡：" + name);
                    }
                })
                .setBackgroundResource(R.drawable.mq_bg_title);
    }
}

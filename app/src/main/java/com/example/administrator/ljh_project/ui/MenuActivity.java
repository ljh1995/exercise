package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.fragment.RecommendFragment;
import com.example.administrator.ljh_project.fragment.MyFragment;
import com.example.administrator.ljh_project.fragment.OrderFragment;
import com.example.administrator.ljh_project.fragment.ShouYeFragment;

/**
 * Created by Administrator on 2017\11\3 0003.
 */

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private enum TabFragment {
        practice(R.id.navigation_practice,ShouYeFragment.class),
        styles(R.id.navigation_style,RecommendFragment.class),
        order(R.id.navigation_order,OrderFragment.class),
        using(R.id.navigation_using,MyFragment.class)
        ;

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId) {
                    return fragment;
                }
            }
            return styles;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //使用BottomNavigationView菜单项多于3个时，去除动画效果，显示菜单项文字
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);
//        navigation.setSelectedItemId(R.id.navigation_style);

        ViewPager viewPager = (ViewPager) findViewById(R.id.content);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return TabFragment.values().length;
            }
            @Override
            public Fragment getItem(int position) {
                return TabFragment.values()[position].fragment();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(TabFragment.values()[position].menuId);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ((ViewPager) findViewById(R.id.content)).setCurrentItem(TabFragment.from(item.getItemId()).ordinal());
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.content,TabFragment.from(item.getItemId()).fragment())
//                .commit();
        return true;
    }
}

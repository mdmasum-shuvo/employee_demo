package com.nuveq.sojibdemo.view.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.feature.ChangePassFragment;
import com.nuveq.sojibdemo.feature.HistoryFragment;
import com.nuveq.sojibdemo.feature.HomeFragment;
import com.nuveq.sojibdemo.feature.ProfileFragment;
import com.nuveq.sojibdemo.sliding_drawer.menu.DrawerAdapter;
import com.nuveq.sojibdemo.sliding_drawer.menu.DrawerItem;
import com.nuveq.sojibdemo.sliding_drawer.menu.SimpleItem;
import com.nuveq.sojibdemo.sliding_drawer.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends BaseActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int PROFILE_FRAGMENT = 1;
    private static final int HISTORY_FRAGMENT = 2;
    private static final int CHANGE_PASS_FRAGMENT = 3;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;


    @Override
    public void onItemSelected(int position) {
        Fragment selectedScreen = null;
        if (position == HOME_FRAGMENT) {
            selectedScreen = new HomeFragment();
            setToolbarTitle("HOME");

        } else if (position == PROFILE_FRAGMENT) {
            selectedScreen = new ProfileFragment();

            setToolbarTitle("My Profile");
        } else if (position == HISTORY_FRAGMENT) {
            selectedScreen = new HistoryFragment();

            setToolbarTitle("History");
        } else if (position == CHANGE_PASS_FRAGMENT) {
            selectedScreen = new ChangePassFragment();
            setToolbarTitle("Change Password");

        } else if (position == POS_LOGOUT) {
            logout();
        }
        slidingRootNav.closeMenu();
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        initToolbar();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(getToolbar())
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(HOME_FRAGMENT).setChecked(true),
                createItemFor(PROFILE_FRAGMENT),
                createItemFor(HISTORY_FRAGMENT),
                createItemFor(CHANGE_PASS_FRAGMENT),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(HOME_FRAGMENT);
    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {

    }
}

package com.irne.instaclone;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

            toolbar = findViewById(R.id.MyToolbar);
            setSupportActionBar(toolbar);

            viewPager = findViewById(R.id.viewpager);
            tabAdapter = new TabAdapter(getSupportFragmentManager());
            viewPager.setAdapter(tabAdapter);

            tabLayout = findViewById(R.id.tablayout);
            tabLayout.setupWithViewPager(viewPager,false);

    }
}

package com.example.aleksei.myrun;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,
        Tab2.OnFragmentInteractionListener,
        Tab3.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make tabs for the layout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Time Calculator"));
        tabLayout.addTab(tabLayout.newTab().setText("Race Predictor"));
        tabLayout.addTab(tabLayout.newTab().setText("Splits Generator"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewpager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

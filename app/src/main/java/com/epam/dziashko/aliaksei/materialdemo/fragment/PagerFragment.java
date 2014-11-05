package com.epam.dziashko.aliaksei.materialdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.dziashko.aliaksei.materialdemo.R;
import com.epam.dziashko.aliaksei.materialdemo.view.SlidingTabLayout;

public class PagerFragment extends Fragment {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager, null);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override public Fragment getItem(int i) {
                return new GridFragment();
            }

            @Override public int getCount() {
                return 2;
            }

            @Override public CharSequence getPageTitle(int position) {
                return "Wow! Tab "+position;
            }
        });

        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentLight);
            }

            @Override public int getDividerColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });
    }
}

package com.youle.gamebox.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.InjectView;
import com.viewpagerindicator.TabPageIndicator;
import com.youle.gamebox.ui.R;
import com.youle.gamebox.ui.adapter.AllGiftAdapter;

/**
 * Created by Administrator on 14-6-24.
 */
public class GiftFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.giftIndicator)
    TabPageIndicator mGiftIndicator;
    @InjectView(R.id.giftViewpager)
    ViewPager mGiftViewpager;
    GiftRecommendFragment recommendFragment;
    AllGiftFragment mAllGiftFragment;
    MyGiftFragment myGiftFragment;

    @Override
    protected int getViewId() {
        return R.layout.fragment_gift;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGiftViewpager.setAdapter(new GiftViewpageAdapter());
        mGiftIndicator.setViewPager(mGiftViewpager);
        mGiftIndicator.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            if(position==2){
                myGiftFragment.onSelected();
            }else if(position==1){
                mAllGiftFragment.onSelected();
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class GiftViewpageAdapter extends FragmentPagerAdapter {

        public GiftViewpageAdapter() {
            super(getFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (recommendFragment == null) {
                    recommendFragment = new GiftRecommendFragment();
                }
                return recommendFragment;
            } else if (position == 1) {
                if (mAllGiftFragment == null) {
                    mAllGiftFragment = new AllGiftFragment();
                }
                return mAllGiftFragment;
            } else if (position == 2) {
                if (myGiftFragment == null) {
                    myGiftFragment = new MyGiftFragment();
                }
                return myGiftFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return getResources().getStringArray(R.array.gift_tab).length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.gift_tab)[position];
        }
    }
}

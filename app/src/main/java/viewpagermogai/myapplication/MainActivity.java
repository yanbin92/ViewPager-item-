package viewpagermogai.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    ViewPager mPager;
    ConstraintLayout clTop;
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = findViewById(R.id.viewpager);
        clTop = findViewById(R.id.cl_top);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mPager.setCurrentItem(3300);

        clTop.setOnTouchListener((v, event) -> {
            mPager.onTouchEvent(event);
            return true;
        });
    }

    class DepthPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position < -1) {
                // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0.8f);
                view.setScaleX(0.8f);
                view.setScaleY(0.8f);
            } else if (position < 0) {
                // [-1,0) 0->>-1 position --
                // Use the default slide transition when moving to the left page
                //1.3f->>0.8
                view.setAlpha(0.8f);
                view.setTranslationX(0);
                //-0.1->>-0.9->>-1
                float changeFloat = (float) (1.3 - (1.3 - 0.8) * (Math.abs(position)));
                view.setScaleX(changeFloat);
                view.setScaleY(changeFloat);
            } else if (position <= 1) {
                // (0,1]  1=>0  0.9
                //0.8 ->> 1.3f
                float changeFloat = (float) (0.8f + (1.3 - 0.8) * (Math.abs(1 - position)));
                view.setAlpha(0.8f);
                view.setTranslationX(0);
                view.setScaleX(changeFloat);
                view.setScaleY(changeFloat);

            } else {
                // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0.8f);
                view.setScaleX(0.8f);
                view.setScaleY(0.8f);
            }
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

    public static class ScreenSlidePageFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.item_text_center, container, false);
            TextView viewById = (TextView) rootView.findViewById(android.R.id.text1);
            viewById.setText("testxx");
            viewById.setTextColor(Color.WHITE);
            return rootView;
        }
    }

}

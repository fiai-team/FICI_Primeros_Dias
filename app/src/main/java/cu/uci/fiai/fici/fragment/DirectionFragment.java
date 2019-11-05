package cu.uci.fiai.fici.fragment;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.libs.viewpagerindicator.CirclePageIndicator;
import cu.uci.fiai.fici.libs.viewpagerindicator.PageIndicator;
import cu.uci.fiai.fici.pojo.Boss;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DirectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectionFragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    public static final String PAGE_ORIENTATION = "OrientationPageDirection";

    private ArrayList<Boss> bosses;
    private int mPage = 0;
    private SharedPreferences preferences;

    private ViewPager viewPager;
    private PageIndicator indicator;
    private IconTextView viewPrevious;
    private IconTextView viewNext;

    public DirectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DirectionFragment.
     */
    public static DirectionFragment newInstance() {
        return new DirectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.string_cons_direcc);

        bosses = ((FICIApplication) getActivity().getApplication()).getBosses();
        preferences = ((FICIApplication) getActivity().getApplication())
                .getPreferences();
        mPage = preferences.getInt(PAGE_ORIENTATION, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction, container, false);

        initViews(view);
        setupViews();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        preferences.edit().putInt(PAGE_ORIENTATION, mPage).apply();
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();

        switch (resId) {
            case R.id.viewNextBoss:
                nextBoss();
                break;
            case R.id.viewBeforeBoss:
                previousBoss();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mPage = position;

        if (position == 0) {
            viewPrevious.startAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.fade_out_slideshow));
            viewPrevious.setVisibility(View.INVISIBLE);
        } else {
            if (viewPrevious.getVisibility() == View.INVISIBLE) {
                viewPrevious.setVisibility(View.VISIBLE);
                viewPrevious.startAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.selector_left));
            }
        }

        if (position == viewPager.getAdapter().getCount() - 1) {
            viewNext.setVisibility(View.INVISIBLE);
            viewNext.startAnimation(AnimationUtils.loadAnimation(getContext(),
                    R.anim.fade_out_slideshow));
        } else {
            if (viewNext.getVisibility() == View.INVISIBLE) {
                viewNext.setVisibility(View.VISIBLE);
                viewNext.startAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.selector_rigth));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void initViews(View view) {
        viewPager = (ViewPager)view.findViewById(R.id.viewPagerDirection);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        viewNext = (IconTextView)view.findViewById(R.id.viewNextBoss);
        viewPrevious = (IconTextView)view.findViewById(R.id.viewBeforeBoss);
    }

    private void setupViews() {
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(mPage);
        viewPager.setPageTransformer(true,
                ((FICIApplication) getActivity().getApplication()).getPageTransformer());
        indicator.setViewPager(viewPager);
        viewNext.setOnClickListener(this);
        viewPrevious.setOnClickListener(this);
        viewNext.startAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.selector_rigth));
    }

    private void nextBoss(){
        int max = viewPager.getAdapter().getCount() - 1;
        int currentPage = viewPager.getCurrentItem();

        if (currentPage < max) {
            viewPager.setCurrentItem(currentPage + 1);
        }
    }

    private void previousBoss(){
        int currentPage = viewPager.getCurrentItem();

        if (currentPage > 0) {
            viewPager.setCurrentItem(currentPage - 1);
        }
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BossFragment.newInstance(bosses.get(position));
        }

        @Override
        public int getCount() {
            return bosses.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return bosses.get(position).getCargo();
        }

    }

}

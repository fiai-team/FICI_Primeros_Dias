package cu.uci.fiai.fici.fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import cu.uci.fiai.fici.libs.viewpagerindicator.PageIndicator;
import cu.uci.fiai.fici.libs.viewpagerindicator.UnderlinePageIndicator;
import cu.uci.fiai.fici.pojo.ProfesorGuia;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPGFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPGFragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    public static final String PAGE_ORIENTATION = "OrientationPagePG";

    private ArrayList<ProfesorGuia> profesoresGuias;
    private int mPage = 0;
    private SharedPreferences preferences;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageIndicator indicator;
    private IconTextView viewPrevious;
    private IconTextView viewNext;

    public AllPGFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AllPGFragment.
     */
    public static AllPGFragment newInstance() {
        return new AllPGFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.string_pgs);

        profesoresGuias = ((FICIApplication) getActivity().getApplication())
                .getProfesoresGuias();
        preferences = ((FICIApplication) getActivity().getApplication())
                .getPreferences();
        mPage = preferences.getInt(PAGE_ORIENTATION, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allpg, container, false);

        initViews(view);
        fillViews();

        return view;
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();

        switch (resId) {
            case R.id.viewNextPG:
                nextPG();
                break;
            case R.id.viewBeforePG:
                previousPG();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        preferences.edit().putInt(PAGE_ORIENTATION, mPage).apply();
    }

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabPG);
        indicator = (UnderlinePageIndicator) view.findViewById(R.id.indicator);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerPG);
        viewNext = (IconTextView) view.findViewById(R.id.viewNextPG);
        viewPrevious = (IconTextView) view.findViewById(R.id.viewBeforePG);
    }

    private void fillViews(){
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(mPage);
        viewPager.setPageTransformer(true,
                ((FICIApplication) getActivity().getApplication()).getPageTransformer());
        tabLayout.setupWithViewPager(viewPager);
        indicator.setViewPager(viewPager);
        viewNext.setOnClickListener(this);
        viewPrevious.setOnClickListener(this);
        viewNext.startAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.selector_rigth));
    }

    private void nextPG(){
        int max = viewPager.getAdapter().getCount() - 1;
        int currentPage = viewPager.getCurrentItem();

        if (currentPage < max) {
            viewPager.setCurrentItem(currentPage + 1);
        }
    }

    private void previousPG(){
        int currentPage = viewPager.getCurrentItem();

        if (currentPage > 0) {
            viewPager.setCurrentItem(currentPage - 1);
        }
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PGFragment.newInstance(profesoresGuias.get(position));
        }

        @Override
        public int getCount() {
            return profesoresGuias.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return profesoresGuias.get(position).getGroup();
        }

    }

}

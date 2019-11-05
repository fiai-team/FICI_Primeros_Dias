package cu.uci.fiai.fici.fragment;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.FICIDayEvents;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final int EVENTS_FILTER_BY = SettingsActivity.EVENTS_FILTER_BY;
    private static final int EVENTS_FILTER = SettingsActivity.EVENTS_FILTER;
    public static final String PAGE_ORIENTATION = "OrientationPageEvents";

    private SharedPreferences preferences;
    private ArrayList<FICIDayEvents> dayEvents;
    private ArrayList<String> groups;
    private ArrayAdapter<String> adapterGroups;
    private ArrayAdapter<String> adapterBlocks;
    private int flagSetup = 0;
    private int mPage = 0;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Spinner filterBySpinner;
    private Spinner filterSpinner;
    private TextView filterTextView;

    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventsFragment.
     */
    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.string_events);

        preferences = ((FICIApplication) getActivity().getApplication()).getPreferences();
        mPage = preferences.getInt(PAGE_ORIENTATION, 0);
        dayEvents = ((FICIApplication) getActivity().getApplication()).getDayEvents();
        groups = ((FICIApplication) getActivity().getApplication()).getGroups();
        adapterGroups = new ArrayAdapter<>(getContext(), R.layout.spinner_item, groups);
        adapterBlocks = new ArrayAdapter<>(getContext(), R.layout.spinner_item,
                getResources().getStringArray(R.array.array_filter_blocks));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        initViews(view);
        fillViews();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewPager.setCurrentItem(mPage);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        preferences.edit().putInt(PAGE_ORIENTATION, mPage).apply();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        mPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabEvents);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerEvents);
        filterBySpinner = (Spinner) view.findViewById(R.id.spinnerFilterBy);
        filterSpinner = (Spinner) view.findViewById(R.id.spinnerFilter);
        filterTextView = (TextView) view.findViewById(R.id.textFilter);
    }

    private void fillViews() {
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true,
                ((FICIApplication) getActivity().getApplication())
                        .getPageTransformer());
        tabLayout.setupWithViewPager(viewPager);

        filterBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                preferences.edit().putInt(getString(EVENTS_FILTER_BY), position)
                        .apply();

                final int selection = preferences.getInt(getString(EVENTS_FILTER), 0);

                if (position == SettingsActivity.EVENTS_FILTER_BY_BLOCK) {
                    filterTextView.setText(R.string.string_block);
                    filterSpinner.setAdapter(adapterBlocks);
                    filterSpinner.setPromptId(R.string.bloque_prompt);

                    if (flagSetup == 1) {
                        flagSetup++;
                        filterSpinner.setSelection(selection);
                    } else {
                        filterSpinner.setSelection(0);
                        updateEventsAdapter();
                    }
                } else if (position == SettingsActivity.EVENTS_FILTER_BY_GROUP) {
                    filterTextView.setText(R.string.string_group);
                    filterSpinner.setAdapter(adapterGroups);
                    filterSpinner.setPromptId(R.string.brigada_prompt);

                    if (flagSetup == 1) {
                        flagSetup++;
                        filterSpinner.setSelection(selection);
                    } else {
                        filterSpinner.setSelection(0);
                        updateEventsAdapter();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                preferences.edit().putInt(getString(EVENTS_FILTER), position).apply();
                updateEventsAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        setupViewFromPreferences();
        flagSetup++;
    }

    private void updateEventsAdapter() {
        dayEvents = ((FICIApplication) getActivity().getApplication()).getDayEvents();
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
    }

    private void setupViewFromPreferences() {
        final int filterBy = preferences.getInt(getString(EVENTS_FILTER_BY),
                SettingsActivity.EVENTS_FILTER_BY_BLOCK);
        final int filter = preferences.getInt(getString(EVENTS_FILTER),
                SettingsActivity.EVENTS_FILTER_ALL);

        if (filterBy == SettingsActivity.EVENTS_FILTER_BY_BLOCK) {
            filterSpinner.setSelection(filter);
        } else if (filterBy == SettingsActivity.EVENTS_FILTER_BY_GROUP) {
            filterBySpinner.setSelection(1);
            filterTextView.setText(R.string.string_group);
            filterSpinner.setAdapter(adapterGroups);
            filterSpinner.setSelection(filter);
        }
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return EventFragment.newInstance(dayEvents.get(position));
        }

        @Override
        public int getCount() {
            return dayEvents.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dayEvents.get(position).getTitle();
        }

    }

}

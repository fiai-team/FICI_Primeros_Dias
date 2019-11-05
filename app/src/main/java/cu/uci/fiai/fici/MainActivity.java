package cu.uci.fiai.fici;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import cu.uci.fiai.fici.fragment.AboutFragment;
import cu.uci.fiai.fici.fragment.AllPGFragment;
import cu.uci.fiai.fici.fragment.DirectionFragment;
import cu.uci.fiai.fici.fragment.EventsFragment;
import cu.uci.fiai.fici.fragment.FICIFragment;
import cu.uci.fiai.fici.fragment.GTCEFragment;
import cu.uci.fiai.fici.fragment.MainFragment;
import cu.uci.fiai.fici.fragment.MapFragment;
import cu.uci.fiai.fici.libs.slidingrootnav.SlidingRootNav;
import cu.uci.fiai.fici.libs.slidingrootnav.SlidingRootNavBuilder;
import cu.uci.fiai.fici.menu.DrawerAdapter;
import cu.uci.fiai.fici.menu.DrawerItem;
import cu.uci.fiai.fici.menu.SimpleItem;
import cu.uci.fiai.fici.menu.SpaceItem;
import cu.uci.fiai.fici.pojo.Boss;
import cu.uci.fiai.fici.pojo.DatabaseHelper;
import cu.uci.fiai.fici.pojo.FICIEvent;
import cu.uci.fiai.fici.pojo.GTCE;
import cu.uci.fiai.fici.pojo.POIMap;
import cu.uci.fiai.fici.pojo.ProfesorGuia;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.tutorial.TutorialActivity;
import cu.uci.fiai.fici.util.FICIApplication;

public class MainActivity extends AppCompatActivity
        implements DrawerAdapter.OnItemSelectedListener {

    //private static final String KEY_STACK = "F141";
    private static final int POS_UCI = 0;
    private static final int POS_FICI = 1;
    private static final int POS_DIRECTION = 2;
    private static final int POS_PG = 3;
    private static final int POS_EVENTS = 4;
    private static final int POS_MAP = 5;
    private static final int POS_GTCE = 6;
    private static final int POS_ABOUT = 7;
    private static final int POS_EXIT = 9;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    private ArrayList<Integer> commits;
    private int lastCommit = POS_UCI;
    private FragmentManager fragmentManager;

    private Toolbar toolbar;
    private SlidingRootNav slidingRootNav;
    private RecyclerView list;
    private DrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commits = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_main);
        setupToolbar(savedInstanceState);
        initViews();

        if (savedInstanceState == null){
            adapter.setSelected(POS_UCI);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        if (menu instanceof  MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            case R.id.action_tutorial:
                startActivity(new Intent(this, TutorialActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        setupToolbar(null);
        initViews();
        adapter.setSelected(commits.get(commits.size() - 1));
    }

    @Override
    public void onBackPressed() {
        if(slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else {
            int sizeCommits = commits.size();

            if (sizeCommits > 1) {
                showFragment(commits.get(sizeCommits - 2));
                adapter.setSelected(commits.get(sizeCommits - 2));
                commits.remove(sizeCommits - 1);
                commits.remove(sizeCommits - 1);
            } else  {
                super.onBackPressed();
                finishAffinity();
            }
        }
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_EXIT) {
            finishAffinity();
            return;
        }

        slidingRootNav.closeMenu();

        if (commits.size() > 0) {
            if (position != commits.get(commits.size() - 1)) {
                commits.add(position);
            }
        } else {
            commits.add(POS_UCI);
        }

        showFragment(position);
    }

    private void setupToolbar(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withContentClickableWhenMenuOpened(false)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_UCI).setChecked(true),
                createItemFor(POS_FICI),
                createItemFor(POS_DIRECTION),
                createItemFor(POS_PG),
                createItemFor(POS_EVENTS),
                createItemFor(POS_MAP),
                createItemFor(POS_GTCE),
                createItemFor(POS_ABOUT),
                new SpaceItem(18),
                createItemFor(POS_EXIT)));
        adapter.setListener(this);
    }

    private void initViews() {
        list = (RecyclerView) findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    @Nullable
    private Fragment chooseFragment(int position) {
        switch (position) {
            case POS_UCI:
                return MainFragment.newInstance();
            case POS_FICI:
                return FICIFragment.newInstance();
            case POS_DIRECTION:
                return DirectionFragment.newInstance();
            case POS_PG:
                return AllPGFragment.newInstance();
            case POS_EVENTS:
                return EventsFragment.newInstance();
            case POS_MAP:
                return MapFragment.newInstance();
            case POS_GTCE:
                return GTCEFragment.newInstance();
            case POS_ABOUT:
                return AboutFragment.newInstance();
            default:
                return null;
        }
    }

    private void showFragment(int fragment) {
        showFragment(chooseFragment(fragment));
    }

    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    /*.setCustomAnimations(R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit,
                            R.anim.fragment_slide_right_enter,
                            R.anim.fragment_slide_right_exit)/**/
                    .replace(R.id.container, fragment)
                    //.addToBackStack(KEY_STACK)
                    .commit();
        }
    }

    @NonNull
    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    @NonNull
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

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

}

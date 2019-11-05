package cu.uci.fiai.fici;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import cu.uci.fiai.fici.fragment.AboutFragment;
import cu.uci.fiai.fici.fragment.AllPGFragment;
import cu.uci.fiai.fici.fragment.DirectionFragment;
import cu.uci.fiai.fici.fragment.EventsFragment;
import cu.uci.fiai.fici.fragment.FICIFragment;
import cu.uci.fiai.fici.fragment.GTCEFragment;
import cu.uci.fiai.fici.fragment.MainFragment;
import cu.uci.fiai.fici.fragment.MapFragment;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.tutorial.TutorialActivity;

public class MainClassicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private static final String KEY_STACK = "F141";
    private static final int POS_UCI = 0;
    private static final int POS_FICI = 1;
    private static final int POS_DIRECTION = 2;
    private static final int POS_PG = 3;
    private static final int POS_EVENTS = 4;
    private static final int POS_MAP = 5;
    private static final int POS_GTCE = 6;
    private static final int POS_ABOUT = 7;

    private ArrayList<Integer> commits;
    private FragmentManager fragmentManager;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_classic);
        setupToolbar();

        commits = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            showFragment(R.id.navUCI);
            commits.add(R.id.navUCI);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        if (menu instanceof MenuBuilder) {
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int sizeCommits = commits.size();

            if (sizeCommits > 1) {
                showFragment(commits.get(sizeCommits - 2));
                updateMenuDrawer(commits.get(sizeCommits - 2));
                commits.remove(sizeCommits - 1);
            } else  {
                super.onBackPressed();
                finishAffinity();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        showFragment(id);
        drawer.closeDrawer(GravityCompat.START);

        if (commits.size() > 0) {
            if (id != commits.get(commits.size() - 1)) {
                commits.add(id);
            }
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main_classic);
        setupToolbar();
        updateMenuDrawer(commits.get(commits.size() - 1));
        showFragment(commits.get(commits.size() - 1));
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(@IdRes int n){
        switch (n){
            case R.id.navUCI:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                MainFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navFICI:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                FICIFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navDirection:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                DirectionFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navPGs:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                AllPGFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navEvents:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                EventsFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navMaps:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                MapFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navGTCE:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                GTCEFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navAbout:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentsLayout,
                                AboutFragment.newInstance())
                        //.addToBackStack(KEY_STACK)
                        .commit();
                fragmentManager.executePendingTransactions();
                break;
            case R.id.navExit:
                finishAffinity();
                break;
        }
    }

    private void updateMenuDrawer(@IdRes int n) {
        switch (n){
            case R.id.navUCI:
                navigationView.getMenu().getItem(POS_UCI).setChecked(true);
                break;
            case R.id.navFICI:
                navigationView.getMenu().getItem(POS_FICI).setChecked(true);
                break;
            case R.id.navDirection:
                navigationView.getMenu().getItem(POS_DIRECTION).setChecked(true);
                break;
            case R.id.navPGs:
                navigationView.getMenu().getItem(POS_PG).setChecked(true);
                break;
            case R.id.navEvents:
                navigationView.getMenu().getItem(POS_EVENTS).setChecked(true);
                break;
            case R.id.navMaps:
                navigationView.getMenu().getItem(POS_MAP).setChecked(true);
                break;
            case R.id.navGTCE:
                navigationView.getMenu().getItem(POS_GTCE).setChecked(true);
                break;
            case R.id.navAbout:
                navigationView.getMenu().getItem(POS_ABOUT).setChecked(true);
                break;
        }
    }

}

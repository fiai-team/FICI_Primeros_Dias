package cu.uci.fiai.fici.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cu.uci.fiai.fici.MainActivity;
import cu.uci.fiai.fici.MainClassicActivity;
import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.util.FICIApplication;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private static final int IS_FIRST_TIME_LAUNCH = SettingsActivity.IS_FIRST_TIME_LAUNCH;
    private static final int PAGES_AMOUNT = 9;

    private TutorialPagerAdapter pagerAdapter;

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private View viewLine;
    private Button btnSkip;
    private Button btnNext;
    private Button btnPrev;

    private TextView[] dots;

    private int[] colorsText;
    private int[] colorsActive;
    private int[] colorsInactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_tutorial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        changeStatusBarColor();
        initViews();
        initListeners();
    }

    @Override
    public void onBackPressed() {
        skipTutorial();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.bNextTutorial:
                nextTutorial();
                break;

            case R.id.bPrevTutorial:
                previousTutorial();
                break;

            case R.id.bSkipTutorial:
                skipTutorial();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);

        if (position == PAGES_AMOUNT - 1) {
            btnNext.setText(getString(R.string.string_start));
            btnSkip.setVisibility(View.GONE);
        } else {
            // still pages are left
            btnNext.setText(getString(R.string.string_next));
            btnSkip.setVisibility(View.VISIBLE);
        }

        if (position == 0) {
            btnPrev.setVisibility(View.GONE);
        } else {
            btnPrev.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initViews() {
        viewLine = findViewById(R.id.viewLine);
        viewPager = (ViewPager) findViewById(R.id.vpTutorial);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.bSkipTutorial);
        btnNext = (Button) findViewById(R.id.bNextTutorial);
        btnPrev = (Button) findViewById(R.id.bPrevTutorial);

        pagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        dots = new TextView[PAGES_AMOUNT];
        colorsText = getResources().getIntArray(R.array.array_text_color);
        colorsActive = getResources().getIntArray(R.array.array_dot_active);
        colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        addBottomDots(0);

        viewPager.setAdapter(pagerAdapter);
    }

    private void initListeners() {
        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    private void addBottomDots(int currentPage) {
        dotsLayout.removeAllViews();
        viewLine.setBackgroundColor(colorsText[currentPage]);
        btnPrev.setTextColor(colorsText[currentPage]);
        btnNext.setTextColor(colorsText[currentPage]);
        btnSkip.setTextColor(colorsText[currentPage]);

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }

    private void skipTutorial() {
        SharedPreferences preferences = ((FICIApplication) getApplication()).getPreferences();
        preferences.edit().putBoolean(getString(IS_FIRST_TIME_LAUNCH), false).apply();

        final int drawerStyle = Integer.valueOf(preferences.getString(
                getString(SettingsActivity.DRAWER_STYLE), getString(R.string.drawer_style_default)));

        switch (drawerStyle) {
            case SettingsActivity.DRAWER_CLASSIC:
                startActivity(new Intent(TutorialActivity.this, MainClassicActivity.class));
                break;

            case SettingsActivity.DRAWER_SLIDING_ROOT:
                startActivity(new Intent(TutorialActivity.this, MainActivity.class));
                break;
        }

        finish();
    }

    private void nextTutorial() {
        final int currentItem = viewPager.getCurrentItem() + 1;

        if (currentItem < PAGES_AMOUNT) {
            viewPager.setCurrentItem(currentItem);
        } else {
            skipTutorial();
        }
    }

    private void previousTutorial() {
        final int currentItem = viewPager.getCurrentItem() - 1;

        if (currentItem >= 0) {
            viewPager.setCurrentItem(currentItem);
        }
    }

    private class TutorialPagerAdapter extends FragmentStatePagerAdapter {

        TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TutorialFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGES_AMOUNT;
        }
    }

}

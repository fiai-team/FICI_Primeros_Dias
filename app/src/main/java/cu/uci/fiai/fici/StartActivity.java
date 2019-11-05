package cu.uci.fiai.fici;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.tutorial.TutorialActivity;
import cu.uci.fiai.fici.util.CopyMapTask;
import cu.uci.fiai.fici.util.FICIApplication;

public class StartActivity extends AppCompatActivity {

    private static final int IS_FIRST_TIME_LAUNCH = SettingsActivity.IS_FIRST_TIME_LAUNCH;
    private static final int DRAWER_STYLE = SettingsActivity.DRAWER_STYLE;

    private SharedPreferences preferences;
    private CoordinatorLayout containerView;
    private AnimationDrawable animationDrawable;
    private CopyMapTask cmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = ((FICIApplication) getApplication()).getPreferences();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        changeStatusBarColor();

        cmt = new CopyMapTask(this);
        containerView = (CoordinatorLayout) findViewById(R.id.containerView);
        animationDrawable = (AnimationDrawable) containerView.getBackground();

        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();

        Animation anim_fade_in = AnimationUtils.loadAnimation(getBaseContext(),
                R.anim.fade_in_start);
        anim_fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cmt.execute();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                launchNextScreen();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        findViewById(R.id.splashFenix).startAnimation(anim_fade_in);
    }

    private boolean isFirstTimeLaunch(){
        if (preferences.getBoolean(getString(IS_FIRST_TIME_LAUNCH), true)) {
            return true;
        } else {
            return false;
        }
    }

    public void launchNextScreen(){
        if (isFirstTimeLaunch()) {
            startActivity(new Intent(this, TutorialActivity.class));
        } else {
            final int drawerStyle = Integer.valueOf(preferences
                    .getString(getString(DRAWER_STYLE), getString(R.string.drawer_style_default)));

            switch (drawerStyle) {
                case SettingsActivity.DRAWER_CLASSIC:
                    startActivity(new Intent(StartActivity.this, MainClassicActivity.class));
                    break;
                case SettingsActivity.DRAWER_SLIDING_ROOT:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    break;
            }
        }

        finish();
    }

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

}

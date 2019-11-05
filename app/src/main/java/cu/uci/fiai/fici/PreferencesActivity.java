package cu.uci.fiai.fici;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cu.uci.fiai.fici.util.FICIApplication;
import cu.uci.fiai.fici.util.MyPreferenceManager;

public class PreferencesActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private MyPreferenceManager prefManager;
    private SharedPreferences preferences;

    private Toolbar toolbar;
    private TextView textViewTime;
    private Spinner spinnerTime;
    private Spinner spinnerPagerAnim;
    private RadioButton[] radioBtnsStyleDraw = new RadioButton[2];
    private Switch switchNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = ((FICIApplication) getApplication()).getPreferences();
        setContentView(R.layout.activity_preferences);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewTime = (TextView) findViewById(R.id.textTimeNotif);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTimeReminder);
        spinnerPagerAnim = (Spinner) findViewById(R.id.spinnerPagerAnim);
        radioBtnsStyleDraw[0] = (RadioButton) findViewById(R.id.radioStyleDefault);
        radioBtnsStyleDraw[1] = (RadioButton) findViewById(R.id.radioStyleSlidingRoot);
        switchNotify = (Switch) findViewById(R.id.switchNotify);

        setSupportActionBar(toolbar);
        setTitle(R.string.action_settings);
        switchNotify.setOnCheckedChangeListener(this);

        prefManager = new MyPreferenceManager(this);

        setPreferencesViews();

        ((RadioGroup) findViewById(R.id.radioGroupStyleDrawer)).setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.closeSettings:
                savePreferences();
                finish();

                return true;
            case R.id.testSettings:
                Toast.makeText(this, prefManager.toString(), Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        savePreferences();
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.radioGroupStyleDrawer:
                switch (checkedId) {
                    case R.id.radioStyleDefault:
                        prefManager.setDrawerStyle(MyPreferenceManager.DRAWER_CLASSIC);
                        prefManager.apply();
                        break;
                    case R.id.radioStyleSlidingRoot:
                        prefManager.setDrawerStyle(MyPreferenceManager.DRAWER_SLIDING_ROOT);
                        prefManager.apply();
                        break;
                }

                saveStyleDrawer();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchNotify:
                spinnerTime.setEnabled(isChecked);

                if (isChecked) {
                    textViewTime.setTextColor(getResources().getColor(R.color.textColorPrimary));
                } else {
                    textViewTime.setTextColor(getResources().getColor(R.color.textColorDisabled));
                }

                saveNotify();
                break;
        }
    }

    private void saveTimeNotify() {
        switch (spinnerTime.getSelectedItemPosition()) {
            case 0:
                prefManager.setDefaultReminderTime(0);
                break;
            case 1:
                prefManager.setDefaultReminderTime(1);
                break;
            case 2:
                prefManager.setDefaultReminderTime(5);
                break;
            case 3:
                prefManager.setDefaultReminderTime(10);
                break;
            case 4:
                prefManager.setDefaultReminderTime(15);
                break;
            case 5:
                prefManager.setDefaultReminderTime(20);
                break;
            case 6:
                prefManager.setDefaultReminderTime(25);
                break;
            case 7:
                prefManager.setDefaultReminderTime(30);
                break;
            case 8:
                prefManager.setDefaultReminderTime(45);
                break;
            case 9:
                prefManager.setDefaultReminderTime(60);
                break;
            case 10:
                prefManager.setDefaultReminderTime(120);
                break;
            case 11:
                prefManager.setDefaultReminderTime(180);
                break;
            case 12:
                prefManager.setDefaultReminderTime(720);
                break;
            case 13:
                prefManager.setDefaultReminderTime(1440);
                break;
            case 14:
                prefManager.setDefaultReminderTime(2880);
                break;
        }
    }

    private void saveStyleDrawer() {
        if (radioBtnsStyleDraw[MyPreferenceManager.DRAWER_CLASSIC].isChecked()) {
            prefManager.setDrawerStyle(MyPreferenceManager.DRAWER_CLASSIC);
        } else if (radioBtnsStyleDraw[MyPreferenceManager.DRAWER_SLIDING_ROOT].isChecked()) {
            prefManager.setDrawerStyle(MyPreferenceManager.DRAWER_SLIDING_ROOT);
        }
    }

    private void savePagerAnim() {
        prefManager.setPagerAnim(spinnerPagerAnim.getSelectedItemPosition());
    }

    private void saveNotify(){
        prefManager.setNotify(switchNotify.isChecked());
    }

    private void savePreferences() {
        Toast.makeText(this, "Opciones guardadas", Toast.LENGTH_LONG).show();

        saveTimeNotify();
        saveStyleDrawer();
        savePagerAnim();
        saveNotify();

        prefManager.apply();
    }

    private void setPreferencesViews() {
        switchNotify.setChecked(prefManager.getNotify());
        spinnerPagerAnim.setSelection(prefManager.getPagerAnim());
        radioBtnsStyleDraw[prefManager.getDrawerStyle()].setChecked(true);

        switch (prefManager.getDefaultReminderTime()) {
            case 0:
                spinnerTime.setSelection(0);
                break;
            case 1:
                spinnerTime.setSelection(1);
                break;
            case 5:
                spinnerTime.setSelection(2);
                break;
            case 10:
                spinnerTime.setSelection(3);
                break;
            case 15:
                spinnerTime.setSelection(4);
                break;
            case 20:
                spinnerTime.setSelection(5);
                break;
            case 25:
                spinnerTime.setSelection(6);
                break;
            case 30:
                spinnerTime.setSelection(7);
                break;
            case 45:
                spinnerTime.setSelection(8);
                break;
            case 60:
                spinnerTime.setSelection(9);
                break;
            case 120:
                spinnerTime.setSelection(10);
                break;
            case 180:
                spinnerTime.setSelection(11);
                break;
            case 720:
                spinnerTime.setSelection(12);
                break;
            case 1440:
                spinnerTime.setSelection(13);
                break;
            case 2880:
                spinnerTime.setSelection(14);
                break;
        }
    }

}

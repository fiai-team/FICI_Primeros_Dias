package cu.uci.fiai.fici.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.List;

import cu.uci.fiai.fici.MainActivity;
import cu.uci.fiai.fici.MainClassicActivity;
import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * Created by Tyto on 23/7/2018.
 */

public class SettingsActivity extends AppCompatPreferenceActivity {

    @StringRes
    public static final int IS_FIRST_TIME_LAUNCH = R.string.pref_is_first_time_launch;
    @StringRes
    public static final int DEFAULT_REMINDER_TIME = R.string.pref_default_reminder_time;
    @StringRes
    public static final int DRAWER_STYLE = R.string.pref_drawer_style;
    @StringRes
    public static final int PAGER_ANIM = R.string.pref_pager_anim;
    @StringRes
    public static final int NOTIFY = R.string.pref_notify;
    @StringRes
    public static final int EVENTS_FILTER_BY = R.string.pref_events_filter_by;
    @StringRes
    public static final int EVENTS_FILTER = R.string.pref_events_filter;

    public static final int DRAWER_CLASSIC = 0;
    public static final int DRAWER_SLIDING_ROOT = 1;

    public static final int EVENTS_FILTER_BY_BLOCK = 0;
    public static final int EVENTS_FILTER_BY_GROUP = 1;

    public static final int EVENTS_FILTER_ALL = 0;
    public static final int EVENTS_FILTER_BLOCK_I = 1;
    public static final int EVENTS_FILTER_BLOCK_II = 2;
    public static final int EVENTS_FILTER_BLOCK_III = 3;
    public static final int EVENTS_FILTER_BLOCK_IV = 4;
    public static final int EVENTS_FILTER_BLOCK_V = 5;
    public static final int EVENTS_FILTER_GROUP = 1;

    static OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
            new OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String stringValue = newValue.toString();

                    if (preference instanceof ListPreference) {
                        // For list preferences, look up the correct display value in
                        // the preference's 'entries' list.
                        ListPreference listPreference = (ListPreference) preference;
                        int index = listPreference.findIndexOfValue(stringValue);

                        // Set the summary to reflect the new value.
                        preference.setSummary(index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
                    } else {
                        // For all other preferences, set the summary to the value's
                        // simple string representation.
                        preference.setSummary(stringValue);
                    }

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || NotificationsFragment.class.getName().equals(fragmentName)
                || GeneralFragment.class.getName().equals(fragmentName)
                || PerfilFragment.class.getName().equals(fragmentName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            final SharedPreferences preferences = ((FICIApplication) getApplication())
                    .getPreferences();
            final int drawer = Integer.valueOf(preferences
                    .getString(getString(DRAWER_STYLE),
                            getString(R.string.drawer_style_default)));

            if (getIntent().getExtras() == null) {
                if (drawer == DRAWER_CLASSIC) {
                    startActivity(new Intent(this, MainClassicActivity.class));
                } else if (drawer == DRAWER_SLIDING_ROOT) {
                    startActivity(new Intent(this, MainActivity.class));
                }
            }

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final SharedPreferences preferences = ((FICIApplication) getApplication())
                .getPreferences();
        final int drawer = Integer.valueOf(preferences
                .getString(getString(DRAWER_STYLE),
                        getString(R.string.drawer_style_default)));

        if (getIntent().getExtras() == null) {
            if (drawer == DRAWER_CLASSIC) {
                startActivity(new Intent(this, MainClassicActivity.class));
            } else if (drawer == DRAWER_SLIDING_ROOT) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(
                        preference.getContext()).getString(preference.getKey(), ""));
    }

}

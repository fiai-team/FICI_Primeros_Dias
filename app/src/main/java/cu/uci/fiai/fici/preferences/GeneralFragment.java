package cu.uci.fiai.fici.preferences;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import cu.uci.fiai.fici.R;

/**
 * Created by Tyto on 23/7/2018.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GeneralFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(true);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        SettingsActivity.bindPreferenceSummaryToValue(
                findPreference(getString(R.string.pref_drawer_style)));
        SettingsActivity.bindPreferenceSummaryToValue(
                findPreference(getString(R.string.pref_pager_anim)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            //intent.putExtra("","");
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package cu.uci.fiai.fici.preferences;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.Perfil;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * Created by Tyto on 24/7/2018.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PerfilFragment extends PreferenceFragment {

    private Perfil perfil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.pref_perfil);

        perfil = ((FICIApplication) getActivity().getApplication()).getPerfil();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        return view;
    }/**/

}

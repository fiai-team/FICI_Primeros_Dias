package cu.uci.fiai.fici.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.fragment.AllPGFragment;
import cu.uci.fiai.fici.fragment.DirectionFragment;
import cu.uci.fiai.fici.fragment.EventsFragment;
import cu.uci.fiai.fici.pojo.Boss;
import cu.uci.fiai.fici.pojo.DatabaseHelper;
import cu.uci.fiai.fici.pojo.FICIDayEvents;
import cu.uci.fiai.fici.pojo.FICIEvent;
import cu.uci.fiai.fici.pojo.GTCE;
import cu.uci.fiai.fici.pojo.POIMap;
import cu.uci.fiai.fici.pojo.Perfil;
import cu.uci.fiai.fici.pojo.ProfesorGuia;
import cu.uci.fiai.fici.pojo.Tutor;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.transformations.CubeInDepthTransformation;
import cu.uci.fiai.fici.transformations.CubeInRotationTransformation;
import cu.uci.fiai.fici.transformations.CubeInScalingTransformation;
import cu.uci.fiai.fici.transformations.CubeOutDepthTransformation;
import cu.uci.fiai.fici.transformations.CubeOutRotationTransformation;
import cu.uci.fiai.fici.transformations.CubeOutScalingTransformation;
import cu.uci.fiai.fici.transformations.DepthTransformation;
import cu.uci.fiai.fici.transformations.FadeOutTransformation;
import cu.uci.fiai.fici.transformations.FanTransformation;
import cu.uci.fiai.fici.transformations.FidgetSpinTransformation;
import cu.uci.fiai.fici.transformations.GateTransformation;

/**
 * Created by Tyto on 20/6/2018.
 */

public class FICIApplication extends Application {

    private static final int EVENTS_FILTER_BY = SettingsActivity.EVENTS_FILTER_BY;
    private static final int EVENTS_FILTER = SettingsActivity.EVENTS_FILTER;
    private static final int EVENTS_FILTER_BY_BLOCK = SettingsActivity.EVENTS_FILTER_BY_BLOCK;
    private static final int EVENTS_FILTER_BY_GROUP = SettingsActivity.EVENTS_FILTER_BY_GROUP;
    private static final int EVENTS_FILTER_ALL = SettingsActivity.EVENTS_FILTER_ALL;

    private DatabaseHelper dbHelper;

    private ArrayList<Boss> mBosses;
    private ArrayList<ProfesorGuia> mProfesoresGuias;
    private ArrayList<POIMap> mPOIsMap;
    private ArrayList<FICIEvent> mEvents;
    private ArrayList<FICIDayEvents> mDayEvents;
    private ArrayList<GTCE> mGTCE;
    private ArrayList<Tutor> mTutors;
    private ArrayList<String> mGroups;
    private Perfil mPerfil;

    @Override
    public void onCreate() {
        dbHelper = new DatabaseHelper(this);
        dbHelper.openForWriting();

        mBosses = dbHelper.getAllBosses();
        mProfesoresGuias = dbHelper.getAllProfesoresGuias();
        mPOIsMap = dbHelper.getAllPOIsMap();
        mGTCE = dbHelper.getAllGTCESorted();
        mTutors = dbHelper.getAllTutors();

        mGroups = dbHelper.getGroups();
        mGroups.add(0,"Todas");

        mPerfil = dbHelper.getPerfil(1);

        refreshEventsObjects();

        super.onCreate();

        Iconify.with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());

        getPreferences().edit().putInt(AllPGFragment.PAGE_ORIENTATION, 0).apply();
        getPreferences().edit().putInt(DirectionFragment.PAGE_ORIENTATION, 0).apply();
        getPreferences().edit().putInt(EventsFragment.PAGE_ORIENTATION, 0).apply();
    }

    @Override
    public void onTerminate() {
        dbHelper.close();
        super.onTerminate();
    }

    private void refreshEventsObjects(){
        mEvents = dbHelper.getAllEvents();
        mDayEvents = new ArrayList<>();
        mDayEvents.add(dbHelper.getEventsByStartDay("2018/09/03"));
        mDayEvents.add(dbHelper.getEventsByStartDay("2018/09/04"));
        mDayEvents.add(dbHelper.getEventsByStartDay("2018/09/05"));
        mDayEvents.add(dbHelper.getEventsByStartDay("2018/09/06"));
        mDayEvents.add(dbHelper.getEventsByStartDay("2018/09/07"));
    }

    public SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Nullable
    public ViewPager.PageTransformer getPageTransformer() {
        final int pageTransformer = Integer.valueOf(getPreferences().getString(
                getString(SettingsActivity.PAGER_ANIM), getString(R.string.anim_pager_default)));

        switch (pageTransformer) {
            case 0:
                return new CubeInDepthTransformation();
            case 1:
                return new CubeInRotationTransformation();
            case 2:
                return new CubeInScalingTransformation();
            case 3:
                return new CubeOutDepthTransformation();
            case 4:
                return new CubeOutRotationTransformation();
            case 5:
                return new CubeOutScalingTransformation();
            case 6:
                return new DepthTransformation();
            case 7:
                return new FadeOutTransformation();
            case 8:
                return new FanTransformation();
            case 9:
                return new FidgetSpinTransformation();
            case 10:
                return new GateTransformation();
            default:
                return null;
        }
    }

    public Perfil getPerfil() {
        return mPerfil;
    }

    public ArrayList<Boss> getBosses() {
        return mBosses;
    }

    public ArrayList<ProfesorGuia> getProfesoresGuias() {
        return mProfesoresGuias;
    }

    public ArrayList<POIMap> getPOIsMap() {
        return mPOIsMap;
    }

    public ArrayList<FICIEvent> getEvents() {
        return mEvents;
    }

    public FICIEvent getEvent(long id) {
        return dbHelper.getEvent(id);
    }

    public ArrayList<FICIDayEvents> getDayEvents() {
        ArrayList<FICIDayEvents> dayEvents = new ArrayList<>();
        final int filterBy = getPreferences().getInt(getString(EVENTS_FILTER_BY),
                EVENTS_FILTER_BY_BLOCK);
        final int filter = getPreferences().getInt(getString(EVENTS_FILTER),
                EVENTS_FILTER_ALL);
        final String group;

        if (filterBy == EVENTS_FILTER_BY_GROUP && filter > EVENTS_FILTER_ALL) {
            group = mGroups.get(filter);
        } else {
            group = null;
        }

        /*if (filterBy == EVENTS_FILTER_BY_BLOCK) {
            if (filter == EVENTS_FILTER_ALL) {
                Toast.makeText(this, "Todos los bloques", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bloque " + filter, Toast.LENGTH_SHORT).show();
            }
        } else if (filterBy == EVENTS_FILTER_BY_GROUP) {
            if (filter == EVENTS_FILTER_ALL) {
                Toast.makeText(this, "Todas las brigadas", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Brigada " + group,
                        Toast.LENGTH_SHORT).show();
            }
        }/**/

        for (FICIDayEvents dayEvent : mDayEvents) {
            dayEvents.add(dayEvent.extractEvents(filterBy, filter, group));
        }

        return dayEvents;
    }

    public ArrayList<GTCE> getGTCE() {
        return mGTCE;
    }

    public ArrayList<Tutor> getTutors() {
        return mTutors;
    }

    public Tutor getTutor(String user) {
        return dbHelper.getTutor(user);
    }

    public ArrayList<String> getGroups() {
        return mGroups;
    }

    public int updateEvent(FICIEvent event) {
        int rows = dbHelper.updateEvent(event);
        refreshEventsObjects();

        return rows;
    }

    public Perfil updatePerfil(Perfil perfil) {
        int rows = dbHelper.updatePerfil(perfil);

        if (rows > 0) {
            perfil = dbHelper.getPerfil(1);
        }

        return perfil;
    }

}

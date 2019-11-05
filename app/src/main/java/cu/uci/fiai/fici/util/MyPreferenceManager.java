package cu.uci.fiai.fici.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tyto on 26/6/2018.
 */

public class MyPreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // shared preferences mode
    private static final int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "fici2018";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String DEFAULT_REMINDER_TIME = "DefaultReminderTime";
    private static final String DRAWER_STYLE = "DrawerStyle";
    private static final String PAGER_ANIM = "PagerAnim";
    private static final String NOTIFY = "Notify";
    private static final String EVENTS_FILTER_BY = "EventsFilterBy";
    private static final String EVENTS_FILTER = "EventsFilter";

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
    public static final int EVENTS_FILTER_GROUP = 2;

    public MyPreferenceManager(Context context) {
        this.context = context;
        this.pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void apply() {
        editor.apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setDefaultReminderTime(int time) {
        editor.putInt(DEFAULT_REMINDER_TIME, time);
        editor.commit();
    }

    public int getDefaultReminderTime() {
        return pref.getInt(DEFAULT_REMINDER_TIME, 60);
    }

    public void setDrawerStyle(int style){
        editor.putInt(DRAWER_STYLE, style);
        editor.commit();
    }

    public int getDrawerStyle() {
        return pref.getInt(DRAWER_STYLE, DRAWER_CLASSIC);
    }

    public void setPagerAnim(int anim){
        editor.putInt(PAGER_ANIM, anim);
        editor.commit();
    }

    public int getPagerAnim() {
        return pref.getInt(PAGER_ANIM, 0);
    }

    public void setNotify(boolean value){
        editor.putBoolean(NOTIFY, value);
        editor.commit();
    }

    public boolean getNotify() {
        return pref.getBoolean(NOTIFY, true);
    }

    public void setEventsFilterBy(int value){
        editor.putInt(EVENTS_FILTER_BY, value);
        editor.commit();
    }

    public int getEventsFilterBy() {
        return pref.getInt(EVENTS_FILTER_BY, EVENTS_FILTER_BY_BLOCK);
    }

    public void setEventsFilter(int value){
        editor.putInt(EVENTS_FILTER, value);
        editor.commit();
    }

    public int getEventsFilter() {
        return pref.getInt(EVENTS_FILTER, EVENTS_FILTER_ALL);
    }

    @Override
    public String toString() {
        String[] drawerStyles = {"Clásico", "Arco", "Sliding Root"};
        String[] pagerAnim = {"Cube In Depth", "Cube In Rotation", "Cube In Scaling", "Cube Out Depth",
                "Cube Out Rotation", "Cube Out Scaling", "Depth", "Fade Out", "Fan",
                "Fidget Spin", "Gate", "Swipe"};
        String[] eventsFilterBy = {"Bloque", "Brigada"};

        return "MyPreferenceManager:" +
                "\nContext: " + context.getClass().getName() +
                "\nIs First Time Launch: " + isFirstTimeLaunch() +
                "\nDefault Reminder Time: " + getDefaultReminderTime() + " min" +
                "\nDrawer Style: " + drawerStyles[getDrawerStyle()] +
                "\nPager Anim: " + pagerAnim[getPagerAnim()] +
                "\nNotify:" + getNotify()+
                "\nEventsFilterBy: " + eventsFilterBy[getEventsFilterBy()] +
                "\nEventsFilter: " + getEventsFilterBy();
    }

}

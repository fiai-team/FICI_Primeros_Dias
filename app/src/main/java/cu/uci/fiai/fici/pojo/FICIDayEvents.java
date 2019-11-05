package cu.uci.fiai.fici.pojo;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.util.FICIApplication;
import cu.uci.fiai.fici.util.Utils;

/**
 * Created by Tyto on 9/7/2018.
 */

public class FICIDayEvents implements Serializable {

    private static final String BLOCK_I = Utils.BLOCK_I;
    private static final String BLOCK_II = Utils.BLOCK_II;
    private static final String BLOCK_III = Utils.BLOCK_III;
    private static final String BLOCK_IV = Utils.BLOCK_IV;
    private static final String BLOCK_V = Utils.BLOCK_V;

    private static final int EVENTS_FILTER_BY_BLOCK = SettingsActivity.EVENTS_FILTER_BY_BLOCK;
    private static final int EVENTS_FILTER_BY_GROUP = SettingsActivity.EVENTS_FILTER_BY_GROUP;

    private static final int EVENTS_FILTER_ALL = SettingsActivity.EVENTS_FILTER_ALL;
    private static final int EVENTS_FILTER_BLOCK_I = SettingsActivity.EVENTS_FILTER_BLOCK_I;
    private static final int EVENTS_FILTER_BLOCK_II = SettingsActivity.EVENTS_FILTER_BLOCK_II;
    private static final int EVENTS_FILTER_BLOCK_III = SettingsActivity.EVENTS_FILTER_BLOCK_III;
    private static final int EVENTS_FILTER_BLOCK_IV = SettingsActivity.EVENTS_FILTER_BLOCK_IV;
    private static final int EVENTS_FILTER_BLOCK_V = SettingsActivity.EVENTS_FILTER_BLOCK_V;

    private String mDay;
    private ArrayList<FICIEvent> mEvents;

    public FICIDayEvents(String day) {
        this.mDay = day;
        this.mEvents = new ArrayList<>();
    }

    public FICIDayEvents(String day, ArrayList<FICIEvent> events) {
        this.mDay = day;
        this.mEvents = events;
    }

    public void addEvent(FICIEvent event) {
        mEvents.add(event);
    }

    public String getDay() {
        return mDay;
    }

    public String getTitle() {
        String title = Utils.dayAndMonthFromText(mDay).replace(" de ", "/");

        return title.replace(Utils.JANUARY,"01")
                .replace(Utils.FEBRARY,"02")
                .replace(Utils.MARCH,"03")
                .replace(Utils.APRIL,"04")
                .replace(Utils.MAY,"05")
                .replace(Utils.JUNE,"06")
                .replace(Utils.JULY,"07")
                .replace(Utils.AUGUST,"08")
                .replace(Utils.SEPTEMBER,"09")
                .replace(Utils.OCTOBER,"10")
                .replace(Utils.NOVEMBER,"11")
                .replace(Utils.DECEMBER,"12");
    }

    public ArrayList<FICIEvent> getEvents() {
        return mEvents;
    }

    public FICIEvent getEvent(int index) {
        return mEvents.get(index);
    }

    public int count() {
        return mEvents.size();
    }

    public void updateEvent(Activity activity, int pos) {
        if (pos != -1 && activity != null) {
            FICIEvent event = ((FICIApplication) activity.getApplication())
                    .getEvent(mEvents.get(pos).getId());

            if (event != null) {
                mEvents.set(pos, event);
            }
        }
    }

    public FICIDayEvents extractEvents(final int filterBy, final int filter,
                                       @Nullable final String group) {
        FICIDayEvents dayEvents = new FICIDayEvents(mDay);

        for (FICIEvent event : mEvents) {
            if (filterBy == EVENTS_FILTER_BY_BLOCK
                    && canAddEventByBlock(event, filter)) {
                dayEvents.addEvent(event);
            } else if (filterBy == EVENTS_FILTER_BY_GROUP
                    && canAddEventByGroup(event, filter, group)) {
                dayEvents.addEvent(event);
            }
        }

        return dayEvents;
    }

    private boolean canAddEventByBlock(final FICIEvent event,final int filter) {
        switch (filter) {
            case EVENTS_FILTER_ALL:
                return true;
            case EVENTS_FILTER_BLOCK_I:
                return event.isBlock1();
            case EVENTS_FILTER_BLOCK_II:
                return event.isBlock2();
            case EVENTS_FILTER_BLOCK_III:
                return event.isBlock3();
            case EVENTS_FILTER_BLOCK_IV:
                return event.isBlock4();
            case EVENTS_FILTER_BLOCK_V:
                return event.isBlock5();
            default:
                return false;
        }
    }

    private boolean canAddEventByGroup(final FICIEvent event, final int filter,
                                       final String group) {
        if (filter == EVENTS_FILTER_ALL || group == null) {
            return true;
        } else {
            String block = Utils.blockFromGroup(group, 8);

            return event.getParticipants().contains(group)
                    || (block.equals(BLOCK_I) && event.isBlock1())
                    || (block.equals(BLOCK_I) && event.isBlock1())
                    || (block.equals(BLOCK_II) && event.isBlock2())
                    || (block.equals(BLOCK_III) && event.isBlock3())
                    || (block.equals(BLOCK_IV) && event.isBlock4())
                    || (block.equals(BLOCK_V) && event.isBlock5());
        }
    }

}

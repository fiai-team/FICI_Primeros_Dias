package cu.uci.fiai.fici.pojo;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

import cu.uci.fiai.fici.util.Utils;

/**
 * Created by Tyto on 22/6/2018.
 */

public class FICIEvent implements Serializable {

    public static final String TAG = "F1C13V3NT";

    static final String TABLE_NAME = "events";

    static final String FIELD_ID = "id_event";
    static final String FIELD_START_DATE = "start_date";
    static final String FIELD_START_TIME = "start_time";
    static final String FIELD_END_DATE = "end_date";
    static final String FIELD_END_TIME = "end_time";
    static final String FIELD_ALL_DAY = "all_day";
    static final String FIELD_NAME = "name_event";
    static final String FIELD_DESCRIPTION = "description_event";
    static final String FIELD_LOCATION = "location";
    static final String FIELD_RESPONSABILITY = "responsability";
    static final String FIELD_PARTICIPANTS = "participants";
    static final String FIELD_TYPE = "type_event";
    static final String FIELD_SELECTED = "selected";
    static final String FIELD_URI = "event_uri";

    public static final int TYPE_INT_G = 0;
    public static final int TYPE_INT_B1 = 1;
    public static final int TYPE_INT_B2 = 2;
    public static final int TYPE_INT_B3 = 3;
    public static final int TYPE_INT_B4 = 4;
    public static final int TYPE_INT_B5 = 5;

    private long id;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private boolean allDay;
    private String name;
    private String description;
    private String location;
    private String responsability;
    private String participants;
    private String type;
    private boolean selected;
    private String eventUri;
    private int resID;

    public FICIEvent() {
        this.id = DatabaseHelper.INVALID_ID;
        this.startDate = null;
        this.startTime = null;
        this.endDate = null;
        this.endTime = null;
        this.allDay = false;
        this.name = null;
        this.description = null;
        this.location = null;
        this.responsability = null;
        this.participants = null;
        this.type = null;
        this.selected = false;
        this.eventUri = null;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public FICIEvent(long id, String startDate, String startTime, String endDate,
                     String endTime, boolean allDay, String name, String description,
                     String location, String responsability, String participants,
                     String type, boolean selected, String eventUri) {
        this.id = id;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.allDay = allDay;
        this.name = name;
        this.description = description;
        this.location = location;
        this.responsability = responsability;
        this.participants = participants;
        this.type = type;
        this.selected = selected;
        this.eventUri = eventUri;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public FICIEvent(long id, String startDate, String startTime, String endDate,
                     String endTime, String allDay, String name, String description,
                     String location, String responsability, String participants,
                     String type, String selected, String eventUri) {
        this(id, startDate, startTime, endDate, endTime, Boolean.valueOf(allDay), name,
                description, location, responsability, participants, type,
                Boolean.valueOf(selected), eventUri);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = Boolean.valueOf(allDay);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResponsability() {
        return responsability;
    }

    public void setResponsability(String responsability) {
        this.responsability = responsability;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getType() {
        return type;
    }

    public int getTypeInt() {
        if (type.equals("B1")) {
            return TYPE_INT_B1;
        } else if (type.equals("B2")) {
            return TYPE_INT_B2;
        } else if (type.equals("B3")) {
            return TYPE_INT_B3;
        } else if (type.equals("B4")) {
            return TYPE_INT_B4;
        } else if (type.equals("B5")) {
            return TYPE_INT_B5;
        } else {
            return TYPE_INT_G;
        }
    }

    public boolean isBlock1() {
        return getTypeInt() == TYPE_INT_B1 || getTypeInt() == TYPE_INT_G;
    }

    public boolean isBlock2() {
        return getTypeInt() == TYPE_INT_B2 || getTypeInt() == TYPE_INT_G;
    }

    public boolean isBlock3() {
        return getTypeInt() == TYPE_INT_B3 || getTypeInt() == TYPE_INT_G;
    }

    public boolean isBlock4() {
        return getTypeInt() == TYPE_INT_B4 || getTypeInt() == TYPE_INT_G;
    }

    public boolean isBlock5() {
        return getTypeInt() == TYPE_INT_B5 || getTypeInt() == TYPE_INT_G;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected() {
        selected = !selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSelected(String selected) {
        this.selected = Boolean.valueOf(selected);
    }

    public String getEventUri() {
        return eventUri;
    }

    public void setEventUri(String eventUri) {
        this.eventUri = eventUri;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getTime(boolean showEnd) {
        StringBuffer timeBuffer = new StringBuffer();
        int hoursStart = Integer.valueOf(startTime.substring(0, 2));
        int hoursEnd = Integer.valueOf(endTime.substring(0, 2));
        String minutesStart = startTime.substring(2);
        String minutesEnd = endTime.substring(2);
        String timeStart = hoursStart < 12 ? "AM" : "PM";
        String timeEnd = hoursEnd < 12 ? "AM" : "PM";

        hoursStart = Utils.hours12(hoursStart);
        hoursEnd = Utils.hours12(hoursEnd);

        timeBuffer.append(hoursStart);
        timeBuffer.append(":");
        timeBuffer.append(minutesStart);
        timeBuffer.append(" ");
        timeBuffer.append(timeStart);

        if ((hoursStart == hoursEnd && minutesStart.equals(minutesEnd)
                && timeStart.equals(timeEnd)) || !showEnd) {
            return timeBuffer.toString();
        } else {
            timeBuffer.append(" - ");
            timeBuffer.append(hoursEnd);
            timeBuffer.append(":");
            timeBuffer.append(minutesEnd);
            timeBuffer.append(" ");
            timeBuffer.append(timeEnd);

            return timeBuffer.toString();
        }
    }

    public String getKey() {
        return TAG + id;
    }

    public Calendar getCalendarStart() {
        Calendar calendar = Calendar.getInstance();

        int year = Integer.valueOf(startDate.substring(0, 4));
        int month = Integer.valueOf(startDate.substring(5, 7));
        int day = Integer.valueOf(startDate.substring(8));
        int hour = Integer.valueOf(startTime.substring(0, 2));
        int minute = Integer.valueOf(startTime.substring(2));

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public boolean isDifferent(FICIEvent event) {
        return !equals(event);
    }

    @Override
    public String toString() {
        return "FICIEvent{" +
                "\n  id=" + id +
                "\n  startDate='" + startDate + '\'' +
                "\n  startTime='" + startTime + '\'' +
                "\n  endDate='" + endDate + '\'' +
                "\n  endTime='" + endTime + '\'' +
                "\n  allDay=" + allDay +
                "\n  name='" + name + '\'' +
                "\n  description='" + description + '\'' +
                "\n  location='" + location + '\'' +
                "\n  responsability=" + responsability +
                "\n  participants=" + participants +
                "\n  type=" + type +
                "\n  selected=" + selected +
                "\n  eventUri='" + eventUri + '\'' +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FICIEvent event = (FICIEvent) o;

        if (getId() != event.getId()) return false;
        if (isAllDay() != event.isAllDay()) return false;
        if (isSelected() != event.isSelected()) return false;
        if (!getStartDate().equals(event.getStartDate())) return false;
        if (!getStartTime().equals(event.getStartTime())) return false;
        if (!getEndDate().equals(event.getEndDate())) return false;
        if (!getEndTime().equals(event.getEndTime())) return false;
        if (!getName().equals(event.getName())) return false;
        if (!getDescription().equals(event.getDescription())) return false;
        if (!getLocation().equals(event.getLocation())) return false;
        if (!getResponsability().equals(event.getResponsability())) return false;
        if (!getParticipants().equals(event.getParticipants())) return false;
        if (!getType().equals(event.getType())) return false;

        return getEventUri().equals(event.getEventUri());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getStartTime().hashCode();
        result = 31 * result + getEndDate().hashCode();
        result = 31 * result + getEndTime().hashCode();
        result = 31 * result + (isAllDay() ? 1 : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + getResponsability().hashCode();
        result = 31 * result + getParticipants().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + (isSelected() ? 1 : 0);
        result = 31 * result + getEventUri().hashCode();
        return result;
    }

}

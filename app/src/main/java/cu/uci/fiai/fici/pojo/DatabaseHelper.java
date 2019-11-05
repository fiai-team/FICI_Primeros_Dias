package cu.uci.fiai.fici.pojo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Tyto on 20/6/2018.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public static final int INVALID_ID = -1;

    private SQLiteDatabase database;
    private Context context;

    private static final String TABLE_CD = Boss.TABLE_NAME;
    private static final String TABLE_PG = ProfesorGuia.TABLE_NAME;
    private static final String TABLE_MAP = POIMap.TABLE_NAME;
    private static final String TABLE_EVENTS = FICIEvent.TABLE_NAME;
    private static final String TABLE_GTCE = GTCE.TABLE_NAME;
    private static final String TABLE_TUTORS = Tutor.TABLE_NAME;
    private static final String TABLE_PERFIL = Perfil.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public synchronized void close() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }

        super.close();
    }

    public void openForWriting() {
        database = getWritableDatabase();
    }

    public void openForReading() {
        database = getReadableDatabase();
    }

    private static final String CD_COLUMN_USER = Boss.FIELD_USER;
    private static final String CD_COLUMN_NAMES = Boss.FIELD_NAMES;
    private static final String CD_COLUMN_LAST_NAMES = Boss.FIELD_LAST_NAMES;
    private static final String CD_COLUMN_CARGO = Boss.FIELD_CARGO;
    private static final String CD_COLUMN_INVITATE = Boss.FIELD_INVITATE;
    private static final String CD_COLUMN_PERMANENT = Boss.FIELD_PERMANENT;

    private static final String[] allColumnsCD = {
            CD_COLUMN_USER, CD_COLUMN_NAMES, CD_COLUMN_LAST_NAMES, CD_COLUMN_CARGO,
            CD_COLUMN_INVITATE, CD_COLUMN_PERMANENT
    };

    public Boss getBoss(String user) {
        if (user == null){
            return null;
        }

        Cursor cursor = database.query(TABLE_CD, allColumnsCD, CD_COLUMN_USER + "=?",
                new String[]{user}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Boss boss = new Boss(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        boss.setResID(context.getResources().getIdentifier(boss.getUser(),
                "drawable","cu.uci.fiai.fici"));

        cursor.close();

        return boss;
    }

    public ArrayList<Boss> getAllBosses() {
        Cursor cursor = database.query(TABLE_CD, allColumnsCD, null, null, null, null, null);
        ArrayList<Boss> bosses = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Boss boss = new Boss();

                boss.setUser(cursor.getString(cursor.getColumnIndex(CD_COLUMN_USER)));
                boss.setNames(cursor.getString(cursor.getColumnIndex(CD_COLUMN_NAMES)));
                boss.setLastNames(cursor.getString(cursor.getColumnIndex(CD_COLUMN_LAST_NAMES)));
                boss.setCargo(cursor.getString(cursor.getColumnIndex(CD_COLUMN_CARGO)));
                boss.setInvitate(cursor.getString(cursor.getColumnIndex(CD_COLUMN_INVITATE)));
                boss.setPermanent(cursor.getString(cursor.getColumnIndex(CD_COLUMN_PERMANENT)));
                boss.setResID(context.getResources().getIdentifier(boss.getUser(),
                        "drawable","cu.uci.fiai.fici"));

                bosses.add(boss);
            }
        }

        cursor.close();

        return bosses;
    }

    private static final String PG_COLUMN_USER = ProfesorGuia.FIELD_USER;
    private static final String PG_COLUMN_NAMES = ProfesorGuia.FIELD_NAMES;
    private static final String PG_COLUMN_LAST_NAMES = ProfesorGuia.FIELD_LAST_NAMES;
    private static final String PG_COLUMN_GROUP = ProfesorGuia.FIELD_GROUP;
    private static final String PG_COLUMN_DEPARTAMENT = ProfesorGuia.FIELD_DEPARTAMENT;

    private static final String[] allColumnsPG = {
            PG_COLUMN_USER, PG_COLUMN_NAMES, PG_COLUMN_LAST_NAMES, PG_COLUMN_GROUP,
            PG_COLUMN_DEPARTAMENT
    };

    public ProfesorGuia getProfesorGuia(String user) {
        if (user == null){
            return null;
        }

        Cursor cursor = database.query(TABLE_PG, allColumnsPG, PG_COLUMN_USER + "=?",
                new String[]{user}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        ProfesorGuia profesorGuia = new ProfesorGuia(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        profesorGuia.setResID(context.getResources().getIdentifier(profesorGuia.getUser(),
                "drawable","cu.uci.fiai.fici"));

        cursor.close();

        return profesorGuia;
    }

    public ArrayList<ProfesorGuia> getAllProfesoresGuias() {
        Cursor cursor = database.query(TABLE_PG, allColumnsPG, null, null, null, null, null);
        ArrayList<ProfesorGuia> profesoresGuias = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                ProfesorGuia profesorGuia = new ProfesorGuia();

                profesorGuia.setUser(cursor.getString(cursor.getColumnIndex(PG_COLUMN_USER)));
                profesorGuia.setNames(cursor.getString(cursor.getColumnIndex(PG_COLUMN_NAMES)));
                profesorGuia.setLastNames(cursor.getString(cursor.getColumnIndex(PG_COLUMN_LAST_NAMES)));
                profesorGuia.setGroup(cursor.getString(cursor.getColumnIndex(PG_COLUMN_GROUP)));
                profesorGuia.setDepartament(cursor.getString(cursor.getColumnIndex(PG_COLUMN_DEPARTAMENT)));
                profesorGuia.setResID(context.getResources().getIdentifier(profesorGuia.getUser(),"drawable","cu.uci.fiai.fici"));

                profesoresGuias.add(profesorGuia);
            }
        }

        cursor.close();

        return profesoresGuias;
    }

    public ArrayList<String> getGroups() {
        Cursor cursor = database.query(TABLE_PG, new String[] {PG_COLUMN_GROUP},
                null, null, null, null, null);
        ArrayList<String> groups = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                groups.add(cursor.getString(cursor.getColumnIndex(PG_COLUMN_GROUP)));
            }
        }

        cursor.close();

        return groups;
    }

    private static final String MAP_COLUMN_ID = POIMap.FIELD_ID;
    private static final String MAP_COLUMN_PHONE = POIMap.FIELD_PHONE;
    private static final String MAP_COLUMN_NAME = POIMap.FIELD_NAME;
    private static final String MAP_COLUMN_DESCRIPTION = POIMap.FIELD_DESCRIPTION;
    private static final String MAP_COLUMN_LATITUDE = POIMap.FIELD_LATITUDE;
    private static final String MAP_COLUMN_LONGITUDE = POIMap.FIELD_LONGITUDE;
    private static final String MAP_COLUMN_ICON = POIMap.FIELD_ICON;
    private static final String MAP_COLUMN_PICTURE_1 = POIMap.FIELD_PICTURE_1;
    private static final String MAP_COLUMN_PICTURE_2 = POIMap.FIELD_PICTURE_2;
    private static final String MAP_COLUMN_PICTURE_3 = POIMap.FIELD_PICTURE_3;

    private static final String[] allColumnsMap = {
            MAP_COLUMN_ID,MAP_COLUMN_PHONE,MAP_COLUMN_NAME,MAP_COLUMN_DESCRIPTION,
            MAP_COLUMN_LATITUDE, MAP_COLUMN_LONGITUDE,MAP_COLUMN_ICON,
            MAP_COLUMN_PICTURE_1, MAP_COLUMN_PICTURE_2, MAP_COLUMN_PICTURE_3
    };

    public POIMap getPOIMap(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_MAP, allColumnsMap, MAP_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        POIMap poiMap = new POIMap(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getDouble(4),
                cursor.getDouble(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9));
        poiMap.setResID(context.getResources().getIdentifier(poiMap.getIcon(),
                "drawable","cu.uci.fiai.fici"));
        poiMap.setResIDPict1(context.getResources().getIdentifier(poiMap.getPicture1(),
                "drawable","cu.uci.fiai.fici"));
        poiMap.setResIDPict2(context.getResources().getIdentifier(poiMap.getPicture2(),
                "drawable","cu.uci.fiai.fici"));
        poiMap.setResIDPict3(context.getResources().getIdentifier(poiMap.getPicture3(),
                "drawable","cu.uci.fiai.fici"));

        cursor.close();

        return poiMap;
    }

    public ArrayList<POIMap> getAllPOIsMap() {
        Cursor cursor = database.query(TABLE_MAP, allColumnsMap, null, null,
                null, null, null);
        ArrayList<POIMap> pois = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                POIMap poiMap = new POIMap();

                poiMap.setId(cursor.getLong(cursor.getColumnIndex(MAP_COLUMN_ID)));
                poiMap.setPhone(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PHONE)));
                poiMap.setName(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_NAME)));
                poiMap.setDescription(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_DESCRIPTION)));
                poiMap.setLatitude(cursor.getDouble(cursor.getColumnIndex(MAP_COLUMN_LATITUDE)));
                poiMap.setLongitude(cursor.getDouble(cursor.getColumnIndex(MAP_COLUMN_LONGITUDE)));
                poiMap.setIcon(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_ICON)));
                poiMap.setPicture1(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_1)));
                poiMap.setPicture2(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_2)));
                poiMap.setPicture3(cursor.getString(cursor.getColumnIndex(MAP_COLUMN_PICTURE_3)));
                poiMap.setResID(context.getResources().getIdentifier(poiMap.getIcon(),
                        "drawable","cu.uci.fiai.fici"));
                poiMap.setResIDPict1(context.getResources().getIdentifier(
                        poiMap.getPicture1(), "drawable","cu.uci.fiai.fici"));
                poiMap.setResIDPict2(context.getResources().getIdentifier(
                        poiMap.getPicture2(), "drawable","cu.uci.fiai.fici"));
                poiMap.setResIDPict3(context.getResources().getIdentifier(
                        poiMap.getPicture3(), "drawable","cu.uci.fiai.fici"));

                pois.add(poiMap);
            }
        }

        cursor.close();

        return pois;
    }

    private static final String EVENTS_COLUMN_ID = FICIEvent.FIELD_ID;
    private static final String EVENTS_COLUMN_START_DATE = FICIEvent.FIELD_START_DATE;
    private static final String EVENTS_COLUMN_START_TIME = FICIEvent.FIELD_START_TIME;
    private static final String EVENTS_COLUMN_END_DATE = FICIEvent.FIELD_END_DATE;
    private static final String EVENTS_COLUMN_END_TIME = FICIEvent.FIELD_END_TIME;
    private static final String EVENTS_COLUMN_ALL_DAY = FICIEvent.FIELD_ALL_DAY;
    private static final String EVENTS_COLUMN_NAME = FICIEvent.FIELD_NAME;
    private static final String EVENTS_COLUMN_DESCRIPTION = FICIEvent.FIELD_DESCRIPTION;
    private static final String EVENTS_COLUMN_LOCATION = FICIEvent.FIELD_LOCATION;
    private static final String EVENTS_COLUMN_RESPONSABILITY = FICIEvent.FIELD_RESPONSABILITY;
    private static final String EVENTS_COLUMN_PARTICIPANTS = FICIEvent.FIELD_PARTICIPANTS;
    private static final String EVENTS_COLUMN_TYPE = FICIEvent.FIELD_TYPE;
    private static final String EVENTS_COLUMN_SELECTED = FICIEvent.FIELD_SELECTED;
    private static final String EVENTS_COLUMN_URI = FICIEvent.FIELD_URI;

    private static final String[] allColumnsEvents = {
            EVENTS_COLUMN_ID, EVENTS_COLUMN_START_DATE, EVENTS_COLUMN_START_TIME,
            EVENTS_COLUMN_END_DATE, EVENTS_COLUMN_END_TIME, EVENTS_COLUMN_ALL_DAY,
            EVENTS_COLUMN_NAME, EVENTS_COLUMN_DESCRIPTION, EVENTS_COLUMN_LOCATION,
            EVENTS_COLUMN_RESPONSABILITY, EVENTS_COLUMN_PARTICIPANTS,
            EVENTS_COLUMN_TYPE, EVENTS_COLUMN_SELECTED, EVENTS_COLUMN_URI
    };

    public FICIEvent getEvent(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_EVENTS, allColumnsEvents,
                EVENTS_COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        FICIEvent event = new FICIEvent(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13));

        cursor.close();

        return event;
    }

    public ArrayList<FICIEvent> getAllEvents() {
        Cursor cursor = database.query(TABLE_EVENTS, allColumnsEvents, null, null,
                null, null, null);
        ArrayList<FICIEvent> events = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                FICIEvent event = new FICIEvent();

                event.setId(cursor.getLong(cursor.getColumnIndex(EVENTS_COLUMN_ID)));
                event.setStartDate(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_START_DATE)));
                event.setStartTime(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_START_TIME)));
                event.setEndDate(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_END_DATE)));
                event.setEndTime(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_END_TIME)));
                event.setAllDay(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_ALL_DAY)));
                event.setName(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_NAME)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_DESCRIPTION)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_LOCATION)));
                event.setResponsability(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_RESPONSABILITY)));
                event.setParticipants(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_PARTICIPANTS)));
                event.setType(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_TYPE)));
                event.setSelected(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_SELECTED)));
                event.setEventUri(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_URI)));

                events.add(event);
            }
        }

        cursor.close();

        return events;
    }

    public FICIDayEvents getEventsByStartDay(String start){
        FICIDayEvents events = new FICIDayEvents(start);

        Cursor cursor = database.query(TABLE_EVENTS, allColumnsEvents,
                EVENTS_COLUMN_START_DATE + "=?", new String[]{String.valueOf(start)},
                null, null, EVENTS_COLUMN_START_TIME);

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                FICIEvent event = new FICIEvent();

                event.setId(cursor.getLong(cursor.getColumnIndex(EVENTS_COLUMN_ID)));
                event.setStartDate(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_START_DATE)));
                event.setStartTime(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_START_TIME)));
                event.setEndDate(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_END_DATE)));
                event.setEndTime(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_END_TIME)));
                event.setAllDay(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_ALL_DAY)));
                event.setName(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_NAME)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_DESCRIPTION)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_LOCATION)));
                event.setResponsability(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_RESPONSABILITY)));
                event.setParticipants(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_PARTICIPANTS)));
                event.setType(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_TYPE)));
                event.setSelected(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_SELECTED)));
                event.setEventUri(cursor.getString(cursor.getColumnIndex(EVENTS_COLUMN_URI)));

                events.addEvent(event);
            }
        }

        cursor.close();

        return events;
    }

    public int updateEvent(FICIEvent event) {
        ContentValues values = new ContentValues();
        values.put(EVENTS_COLUMN_START_DATE, event.getStartDate());
        values.put(EVENTS_COLUMN_START_TIME, event.getStartTime());
        values.put(EVENTS_COLUMN_END_DATE, event.getEndDate());
        values.put(EVENTS_COLUMN_END_TIME, event.getEndTime());
        values.put(EVENTS_COLUMN_ALL_DAY, String.valueOf(event.isAllDay()));
        values.put(EVENTS_COLUMN_NAME, event.getName());
        values.put(EVENTS_COLUMN_DESCRIPTION, event.getDescription());
        values.put(EVENTS_COLUMN_LOCATION, event.getLocation());
        values.put(EVENTS_COLUMN_RESPONSABILITY, event.getResponsability());
        values.put(EVENTS_COLUMN_PARTICIPANTS, event.getParticipants());
        values.put(EVENTS_COLUMN_TYPE, event.getType());
        values.put(EVENTS_COLUMN_SELECTED, String.valueOf(event.isSelected()));
        values.put(EVENTS_COLUMN_URI, event.getEventUri());

        return database.update(TABLE_EVENTS, values, EVENTS_COLUMN_ID + "=?",
                new String[]{String.valueOf(event.getId())});
    }

    public int removeEvent(FICIEvent event) {
        return database.delete(TABLE_EVENTS,
                EVENTS_COLUMN_ID + "=" + event.getId(),
                null);
    }

    private static final String GTCE_COLUMN_ID = GTCE.FIELD_ID;
    private static final String GTCE_COLUMN_TOPIC = GTCE.FIELD_TOPIC;
    private static final String GTCE_COLUMN_TUTORS = GTCE.FIELD_TUTORS;

    private static final String[] allColumnsGTCE = {
            GTCE_COLUMN_ID, GTCE_COLUMN_TOPIC, GTCE_COLUMN_TUTORS
    };

    public GTCE getGTCE(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_GTCE, allColumnsGTCE,
                GTCE_COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        GTCE gtce = new GTCE(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2));

        cursor.close();

        return gtce;
    }

    public ArrayList<GTCE> getAllGTCE() {
        Cursor cursor = database.query(TABLE_GTCE, allColumnsGTCE, null, null,
                null, null, null);
        ArrayList<GTCE> gtces = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                GTCE gtce = new GTCE();

                gtce.setId(cursor.getLong(cursor.getColumnIndex(GTCE_COLUMN_ID)));
                gtce.setTopic(cursor.getString(cursor.getColumnIndex(GTCE_COLUMN_TOPIC)));
                gtce.setTutors(cursor.getString(cursor.getColumnIndex(GTCE_COLUMN_TUTORS)));

                gtces.add(gtce);
            }
        }

        cursor.close();

        return gtces;
    }

    public ArrayList<GTCE> getAllGTCESorted() {
        Cursor cursor = database.query(TABLE_GTCE, allColumnsGTCE, null, null,
                null, null, GTCE_COLUMN_TOPIC);
        ArrayList<GTCE> gtces = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                GTCE gtce = new GTCE();

                gtce.setId(cursor.getLong(cursor.getColumnIndex(GTCE_COLUMN_ID)));
                gtce.setTopic(cursor.getString(cursor.getColumnIndex(GTCE_COLUMN_TOPIC)));
                gtce.setTutors(cursor.getString(cursor.getColumnIndex(GTCE_COLUMN_TUTORS)));

                gtces.add(gtce);
            }
        }

        cursor.close();

        return gtces;
    }

    private static final String TUTORS_COLUMN_USER = Tutor.FIELD_USER;
    private static final String TUTORS_COLUMN_NAMES = Tutor.FIELD_NAMES;
    private static final String TUTORS_COLUMN_LAST_NAMES = Tutor.FIELD_LAST_NAMES;
    private static final String TUTORS_COLUMN_DEPARTAMENT = Tutor.FIELD_DEPARTAMENT;
    private static final String TUTORS_COLUMN_IS_PERSON = Tutor.FIELD_IS_PERSON;

    private static final String[] allColumnsTutors = {
            TUTORS_COLUMN_USER, TUTORS_COLUMN_NAMES, TUTORS_COLUMN_LAST_NAMES,
            TUTORS_COLUMN_DEPARTAMENT, TUTORS_COLUMN_IS_PERSON
    };

    public Tutor getTutor(String user) {
        if (user == null) {
            return null;
        }

        if (user.trim().isEmpty()){
            return null;
        }

        Cursor cursor = database.query(TABLE_TUTORS, allColumnsTutors,
                TUTORS_COLUMN_USER + "=?", new String[]{user},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Tutor tutor = new Tutor(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        tutor.setResID(context.getResources()
                .getIdentifier(tutor.getUser(), "drawable", "cu.uci.fiai.fici"));

        cursor.close();

        return tutor;
    }

    public ArrayList<Tutor> getAllTutors() {
        Cursor cursor = database.query(TABLE_TUTORS, allColumnsTutors, null, null,
                null, null, null);
        ArrayList<Tutor> tutors = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Tutor tutor = new Tutor();

                tutor.setUser(cursor.getString(cursor.getColumnIndex(TUTORS_COLUMN_USER)));
                tutor.setNames(cursor.getString(cursor.getColumnIndex(TUTORS_COLUMN_NAMES)));
                tutor.setLastNames(cursor.getString(cursor.getColumnIndex(TUTORS_COLUMN_LAST_NAMES)));
                tutor.setDepartament(cursor.getString(cursor.getColumnIndex(TUTORS_COLUMN_DEPARTAMENT)));
                tutor.setIsPerson(cursor.getString(cursor.getColumnIndex(TUTORS_COLUMN_IS_PERSON)));
                tutor.setResID(context.getResources().getIdentifier(tutor.getUser(),
                        "drawable", "cu.uci.fiai.fici"));

                tutors.add(tutor);
            }
        }

        cursor.close();

        return tutors;
    }

    private static final String PERFIL_COLUMN_ID = Perfil.FIELD_ID;
    private static final String PERFIL_COLUMN_NAMES = Perfil.FIELD_NAMES;
    private static final String PERFIL_COLUMN_LAST_NAME = Perfil.FIELD_LAST_NAMES;
    private static final String PERFIL_COLUMN_BLOCK = Perfil.FIELD_BLOCK;
    private static final String PERFIL_COLUMN_GROUP = Perfil.FIELD_GROUP;
    private static final String PERFIL_COLUMN_PROFESOR = Perfil.FIELD_IS_PROFESSOR;
    private static final String PERFIL_COLUMN_USERNAME = Perfil.FIELD_USERNAME;

    private static final String[] allColumnsPerfil = {
            PERFIL_COLUMN_ID, PERFIL_COLUMN_NAMES, PERFIL_COLUMN_LAST_NAME,
            PERFIL_COLUMN_BLOCK, PERFIL_COLUMN_GROUP, PERFIL_COLUMN_PROFESOR,
            PERFIL_COLUMN_USERNAME
    };

    public Perfil getPerfil(long id) {
        if (id <= INVALID_ID){
            return null;
        }

        Cursor cursor = database.query(TABLE_PERFIL, allColumnsPerfil,
                PERFIL_COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Perfil perfil = new Perfil(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));

        cursor.close();

        return perfil;
    }

    public int updatePerfil(Perfil perfil) {
        ContentValues values = new ContentValues();
        values.put(PERFIL_COLUMN_NAMES, perfil.getNames());
        values.put(PERFIL_COLUMN_LAST_NAME, perfil.getLastNames());
        values.put(PERFIL_COLUMN_BLOCK, perfil.getBlock());
        values.put(PERFIL_COLUMN_GROUP, perfil.getGroup());
        values.put(PERFIL_COLUMN_PROFESOR, String.valueOf(perfil.isProfesor()));
        values.put(PERFIL_COLUMN_USERNAME, perfil.getUsername());

        return database.update(TABLE_PERFIL, values, PERFIL_COLUMN_ID + "=?",
                new String[]{String.valueOf(perfil.getId())});
    }

}

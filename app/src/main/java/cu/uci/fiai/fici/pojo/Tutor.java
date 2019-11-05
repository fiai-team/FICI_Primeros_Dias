package cu.uci.fiai.fici.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 29/6/2018.
 */

public class Tutor implements Serializable {

    public static final String TAG = "TUT0R35";

    static final String TABLE_NAME = "tutores";

    static final String FIELD_USER = "usuario_tutor";
    static final String FIELD_NAMES = "nombres_tutor";
    static final String FIELD_LAST_NAMES = "apellidos_tutor";
    static final String FIELD_DEPARTAMENT = "dpto_tutor";
    static final String FIELD_IS_PERSON = "es_persona_tutor";

    private String user;
    private String names;
    private String lastNames;
    private String departament;
    private boolean isPerson;
    private int resID;

    public Tutor() {
        this.user = null;
        this.names = null;
        this.lastNames = null;
        this.departament = null;
        this.isPerson = true;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public Tutor(String user, String names, String lastNames, String departament, boolean isPerson) {
        this.user = user;
        this.names = names;
        this.lastNames = lastNames;
        this.departament = departament;
        this.isPerson = isPerson;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public Tutor(String user, String names, String lastNames, String departament, String isPerson) {
        this(user, names, lastNames, departament, Boolean.valueOf(isPerson));
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public void setIsPerson(boolean person) {
        isPerson = person;
    }

    public void setIsPerson(String isPerson) {
        setIsPerson(Boolean.valueOf(isPerson));
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "\n user='" + user + '\'' +
                "\n names='" + names + '\'' +
                "\n lastNames='" + lastNames + '\'' +
                "\n departament='" + departament + '\'' +
                "\n isPerson='" + isPerson + '\'' +
                "\n}";
    }

}

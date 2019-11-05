package cu.uci.fiai.fici.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 20/6/2018.
 */

public class ProfesorGuia implements Serializable {

    public static final String TAG = "Pr0f3s0rGu14";

    static final String TABLE_NAME = "profesores_guias";

    static final String FIELD_USER = "usuario";
    static final String FIELD_NAMES = "nombres";
    static final String FIELD_LAST_NAMES = "apellidos";
    static final String FIELD_GROUP = "brigada";
    static final String FIELD_DEPARTAMENT = "departamento";

    private String user;
    private String names;
    private String lastNames;
    private String group;
    private String departament;
    private int resID;

    public ProfesorGuia() {
        this.user = null;
        this.names = null;
        this.lastNames = null;
        this.group = null;
        this.departament = null;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public ProfesorGuia(String user, String names, String lastNames, String group,
                        String departament) {
        this.user = user;
        this.names = names;
        this.lastNames = lastNames;
        this.group = group;
        this.departament = departament;
        this.resID = DatabaseHelper.INVALID_ID;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "ProfesorGuia{" +
                "user='" + user + '\'' +
                ", names='" + names + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", group='" + group + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }

}

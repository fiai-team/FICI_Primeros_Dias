package cu.uci.fiai.fici.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 20/6/2018.
 */

public class Boss implements Serializable {

    public static final String TAG = "Cu4dr0";

    static final String TABLE_NAME = "consejo_direccion";

    static final String FIELD_USER = "usuario";
    static final String FIELD_NAMES = "nombres";
    static final String FIELD_LAST_NAMES = "apellidos";
    static final String FIELD_CARGO = "cargo";
    static final String FIELD_INVITATE = "es_invitado";
    static final String FIELD_PERMANENT = "permanente";

    private String user;
    private String names;
    private String lastNames;
    private String cargo;
    private boolean isInvitate;
    private boolean isPermanent;
    private int resID;

    public Boss() {
        this.user = null;
        this.names = null;
        this.lastNames = null;
        this.cargo = null;
        this.isInvitate = false;
        this.isPermanent = true;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public Boss(String user, String names, String lastNames, String cargo, boolean isInvitate, boolean isPermanent) {
        this.user = user;
        this.names = names;
        this.lastNames = lastNames;
        this.cargo = cargo;
        this.isInvitate = isInvitate;
        this.isPermanent = isPermanent;
        this.resID = DatabaseHelper.INVALID_ID;
    }

    public Boss(String user, String names, String lastNames, String cargo, String isInvitate, String isPermanent) {
        this(user, names, lastNames, cargo, Boolean.valueOf(isInvitate), Boolean.valueOf(isPermanent));
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isInvitate() {
        return isInvitate;
    }

    public void setInvitate(boolean invitate) {
        isInvitate = invitate;
    }

    public void setInvitate(String invitate) {
        setInvitate(Boolean.valueOf(invitate));
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setPermanent(String permanent) {
        setPermanent(Boolean.valueOf(permanent));
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "user='" + user + '\'' +
                ", names='" + names + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", cargo='" + cargo + '\'' +
                ", isInvitate='" + isInvitate + '\'' +
                ", isPermanent='" + isPermanent + '\'' +
                '}';
    }

}

package cu.uci.fiai.fici.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 24/7/2018.
 */

public class Perfil implements Serializable {

    public static final String TABLE_NAME = "usuarios";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAMES = "nombres";
    public static final String FIELD_LAST_NAMES = "apellidos";
    public static final String FIELD_BLOCK = "bloque";
    public static final String FIELD_GROUP = "grupo";
    public static final String FIELD_IS_PROFESSOR = "es_profesor";
    public static final String FIELD_USERNAME = "usuario";

    public long id;
    public String names;
    public String lastNames;
    public String block;
    public String group;
    public boolean isProfesor;
    public String username;

    public Perfil() {
        this.id = DatabaseHelper.INVALID_ID;
        this.names = null;
        this.lastNames = null;
        this.block = null;
        this.group = null;
        this.isProfesor = false;
        this.username = null;
    }

    public Perfil(long id, String names, String lastNames, String block,
                  String group, boolean isProfesor, String username) {
        this.id = id;
        this.names = names;
        this.lastNames = lastNames;
        this.block = block;
        this.group = group;
        this.isProfesor = isProfesor;
        this.username = username;
    }

    public Perfil(long id, String names, String lastNames, String block,
                  String group, String isProfesor, String username) {
        this(id, names, lastNames, block, group, Boolean.valueOf(isProfesor),
                username);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isProfesor() {
        return isProfesor;
    }

    public void setProfesor(boolean profesor) {
        isProfesor = profesor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "\n id=" + id +
                "\n names='" + names + '\'' +
                "\n lastNames='" + lastNames + '\'' +
                "\n block='" + block + '\'' +
                "\n group='" + group + '\'' +
                "\n isProfesor=" + isProfesor +
                "\n username='" + username + '\'' +
                "\n}";
    }

}

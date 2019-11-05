package cu.uci.fiai.fici.pojo;

import java.io.Serializable;

/**
 * Created by Tyto on 21/6/2018.
 */

public class POIMap implements Serializable {

    public static final String TAG = "P01M4P";

    static final String TABLE_NAME = "mapa";

    static final String FIELD_ID = "id_lugar";
    static final String FIELD_PHONE = "telefono_lugar";
    static final String FIELD_NAME = "nombre_lugar";
    static final String FIELD_DESCRIPTION = "descripcion_lugar";
    static final String FIELD_LATITUDE = "latitud";
    static final String FIELD_LONGITUDE = "longitud";
    static final String FIELD_ICON = "icono_lugar";
    static final String FIELD_PICTURE_1 = "foto1_lugar";
    static final String FIELD_PICTURE_2 = "foto2_lugar";
    static final String FIELD_PICTURE_3 = "foto3_lugar";

    private long id;
    private String phone;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String icon;
    private String picture1;
    private String picture2;
    private String picture3;
    private int resID;
    private int resIDPict1;
    private int resIDPict2;
    private int resIDPict3;

    public POIMap() {
        this.id = DatabaseHelper.INVALID_ID;
        this.phone = null;
        this.name = null;
        this.description = null;
        this.latitude = 0;
        this.longitude = 0;
        this.icon = null;
        this.picture1 = null;
        this.picture2 = null;
        this.picture3 = null;
        this.resID = DatabaseHelper.INVALID_ID;
        this.resIDPict1 = DatabaseHelper.INVALID_ID;
        this.resIDPict2 = DatabaseHelper.INVALID_ID;
        this.resIDPict3 = DatabaseHelper.INVALID_ID;
    }

    public POIMap(long id, String phone, String name, String description,
                  double latitude, double longitude, String icon, String picture1,
                  String picture2, String picture3) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.resID = DatabaseHelper.INVALID_ID;
        this.resIDPict1 = DatabaseHelper.INVALID_ID;
        this.resIDPict2 = DatabaseHelper.INVALID_ID;
        this.resIDPict3 = DatabaseHelper.INVALID_ID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public void setResIDPict1(int resID) {
        this.resIDPict1 = resID;
    }

    public int getResIDPict1() {
        return resIDPict1;
    }

    public void setResIDPict2(int resID) {
        this.resIDPict2 = resID;
    }

    public int getResIDPict2() {
        return resIDPict2;
    }

    public void setResIDPict3(int resID) {
        this.resIDPict3 = resID;
    }

    public int getResIDPict3() {
        return resIDPict3;
    }

    @Override
    public String toString() {
        return "POIMap{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", icon='" + icon + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                '}';
    }

}

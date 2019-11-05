package cu.uci.fiai.fici.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import cu.uci.fiai.fici.util.Utils;

/**
 * Created by Tyto on 29/6/2018.
 */

public class GTCE implements Serializable {

    public static final String TAG = "GTC3";

    static final String TABLE_NAME = "gtce";

    static final String FIELD_ID = "id_gtce";
    static final String FIELD_TOPIC = "tema_gtce";
    static final String FIELD_TUTORS = "profesor_gtce";

    private long id;
    private String topic;
    private String tutors;

    public GTCE() {
        this.id = DatabaseHelper.INVALID_ID;
        this.topic = null;
        this.tutors = null;
    }

    public GTCE(long id, String topic, String tutors) {
        this.id = id;
        this.topic = topic;
        this.tutors = tutors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTutors() {
        return tutors;
    }

    public void setTutors(String tutors) {
        this.tutors = tutors;
    }

    public ArrayList<String> getTutorsArrayList() {
        return Utils.toArrayListFromString(tutors, ',');
    }

    @Override
    public String toString() {
        return "GTCE{" +
                "\n id=" + id +
                "\n topic='" + topic + '\'' +
                "\n tutors='" + tutors + '\'' +
                "\n}";
    }
}

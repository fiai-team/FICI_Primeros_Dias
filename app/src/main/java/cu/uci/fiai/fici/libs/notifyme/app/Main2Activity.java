package cu.uci.fiai.fici.libs.notifyme.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.libs.notifyme.NotifyMe;

public class Main2Activity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    EditText etTitle,etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnNotify = (Button) findViewById(R.id.btnNotify);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);

        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        dpd = new DatePickerDialog(
                this,
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        tpd = new TimePickerDialog(
                this,
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMe.cancel(getApplicationContext(),"test");
            }
        });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(/*getFragmentManager(), "Datepickerdialog"*/);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(/*getFragmentManager(), "Timepickerdialog"*/);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,0);
        Intent intent = new Intent(getApplicationContext(),TestActivity.class);
        intent.putExtra("test","I am a String");
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(etTitle.getText().toString())
                .content(etContent.getText().toString())
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(intent,"Snooze",false)
                .key("test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(intent,"Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .build();
    }

}

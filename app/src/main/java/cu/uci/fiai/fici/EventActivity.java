package cu.uci.fiai.fici;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.widget.NestedScrollView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.Calendar;

import cu.uci.fiai.fici.libs.notifyme.NotifyMe;
import cu.uci.fiai.fici.libs.swipeback.app.SwipeBackActivity;
import cu.uci.fiai.fici.pojo.FICIEvent;
import cu.uci.fiai.fici.preferences.SettingsActivity;
import cu.uci.fiai.fici.util.FICIApplication;
import cu.uci.fiai.fici.util.Utils;

public class EventActivity extends SwipeBackActivity implements View.OnClickListener {

    private FICIEvent event = null;

    private static final int DEFAULT_REMINDER_TIME = SettingsActivity.DEFAULT_REMINDER_TIME;

    @StringRes
    public static final int notification_enabled = R.string.notifications;
    @StringRes
    public static final int notification_disabled = R.string.notifications_off;

    private NestedScrollView nsvContainer;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvLocation;
    //private TextView tvDescription;
    //private TextView tvResponsability;
    private TextView tvParticipants;
    private IconTextView itvNotifyMe;
    private MenuItem menuItemNotif;

    private Calendar now;
    private SharedPreferences preferences;
    private boolean flagInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSwipeBackLayout().setEdgeTrackingEnabled();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().hasExtra(FICIEvent.TAG)){
            event = (FICIEvent) getIntent().getSerializableExtra(FICIEvent.TAG);
            now = event.getCalendarStart();
            preferences = ((FICIApplication) getApplication()).getPreferences();

            initViews();
            initListeners();
            fillViews();

            setTitle(Utils.smallDateAndTimeFromText(event.getStartDate(),
                    event.getStartTime()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Slide slideEnter = new Slide(Gravity.TOP);
                slideEnter.setDuration(1000);
                getWindow().setEnterTransition(slideEnter);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_event);
        getSwipeBackLayout().setEdgeTrackingEnabled();

        initViews();
        initListeners();
        fillViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);

        menuItemNotif = menu.findItem(R.id.notifymeEvent);

        if (menuItemNotif != null) {
            if (event.isSelected()) {
                menuItemNotif.setIcon(R.drawable.ic_notifications);
            } else {
                menuItemNotif.setIcon(R.drawable.ic_notifications_off);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.closeActivity:
                finish();
                return true;
            case R.id.notifymeEvent:
                event.setSelected();
                ((FICIApplication)getApplication()).updateEvent(event);

                if (event.isSelected()) {
                    enableNotification();
                } else {
                    disableNotification();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (flagInit) {
            event = ((FICIApplication) getApplication()).getEvent(event.getId());
            fillViews();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.itvNotifyMe:
                event.setSelected();
                ((FICIApplication)getApplication()).updateEvent(event);

                if (event.isSelected()) {
                    enableNotification();
                } else {
                    disableNotification();
                }
                break;
        }
    }

    private void initViews() {
        nsvContainer = (NestedScrollView) findViewById(R.id.nsvEvContainer);
        tvName = (TextView) findViewById(R.id.tvEvName);
        tvDate = (TextView) findViewById(R.id.tvEvDate);
        tvTime = (TextView) findViewById(R.id.tvEvTime);
        tvLocation = (TextView) findViewById(R.id.tvEvLocation);
        //tvDescription = (TextView) findViewById(R.id.tvEvDescription);
        //tvResponsability = (TextView) findViewById(R.id.tvEvResponsability);
        tvParticipants = (TextView) findViewById(R.id.tvEvParticipants);
        itvNotifyMe = (IconTextView) findViewById(R.id.itvNotifyMe);
    }

    private void fillViews() {
        tvName.setText(event.getName());
        tvDate.setText(Utils.dayAndMonthFromText(event.getStartDate()));
        tvTime.setText(event.getTime(true));
        tvLocation.setText(event.getLocation());
        //tvDescription.setText(event.getDescription());
        //tvResponsability.setText(event.getResponsability());
        tvParticipants.setText(event.getParticipants().replace(",", ", "));
        itvNotifyMe.setText(event.isSelected() ? notification_enabled
                : notification_disabled);

        flagInit = true;
    }

    private void initListeners(){
        itvNotifyMe.setOnClickListener(this);
    }

    private void enableNotification(){
        /*now = Calendar.getInstance();
        String textAfter = "Notify: " + Utils.stringFromCalendar(now);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);/**/

        Utils.vibrate(this);
        final int timeReminder = Integer.valueOf(preferences.getString(
                getString(DEFAULT_REMINDER_TIME), getString(R.string.time_reminder_default)));
        now.add(Calendar.MINUTE, -1 * timeReminder);

        /*String textBefore = Utils.stringFromCalendar(now);
        Snackbar.make(nsvContainer, textAfter + " - " + textBefore,
                Snackbar.LENGTH_INDEFINITE).show();/**/

        Intent intent = new Intent(getApplicationContext(), NotifEventActivity.class);
        intent.putExtra(event.getKey(), event.getId());

        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title("Actividad del " + Utils
                        .dateAndTimeFromText(event.getStartDate(), event.getStartTime()))
                .content(event.getName())
                .color(5, 35, 94,255)
                .led_color(0, 0, 255, 255)
                .time(now)
                .key(event.getKey())
                .addAction(new Intent(),"Descartar",true,false)
                .addAction(intent,"Ver detalles")
                .small_icon(R.mipmap.ic_fici)
                .large_icon(R.mipmap.ic_launcher_round)
                .build();

        itvNotifyMe.setText(notification_enabled);
        menuItemNotif.setIcon(R.drawable.ic_notifications);
    }

    private void disableNotification(){
        Utils.vibrate(this);
        itvNotifyMe.setText(notification_disabled);
        menuItemNotif.setIcon(R.drawable.ic_notifications_off);
        NotifyMe.cancel(getApplicationContext(), event.getKey());
    }

}

package cu.uci.fiai.fici;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cu.uci.fiai.fici.libs.swipeback.app.SwipeBackActivity;
import cu.uci.fiai.fici.pojo.FICIEvent;
import cu.uci.fiai.fici.util.FICIApplication;
import cu.uci.fiai.fici.util.Utils;

public class NotifEventActivity extends SwipeBackActivity {

    private FICIEvent event = null;

    private NestedScrollView nsvContainer;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvLocation;
    private TextView tvParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_event);
        getSwipeBackLayout().setEdgeTrackingEnabled();

        initViews();

        Bundle extras = getIntent().getExtras();

        if (getIntent().getExtras().keySet().toArray().length == 1) {
            String tag = extras.keySet().toArray()[0].toString();

            if (tag.contains(FICIEvent.TAG)) {
                long id = Long.valueOf(tag.substring(FICIEvent.TAG.length()));

                event = ((FICIApplication) getApplication()).getEvent(id);
                event.setSelected();

                if (event != null) {
                    fillViews();
                }

                ((FICIApplication) getApplication()).updateEvent(event);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.closeActivity:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        nsvContainer = (NestedScrollView) findViewById(R.id.nsvEvContainer);
        tvName = (TextView) findViewById(R.id.tvEvName);
        tvDate = (TextView) findViewById(R.id.tvEvDate);
        tvTime = (TextView) findViewById(R.id.tvEvTime);
        tvLocation = (TextView) findViewById(R.id.tvEvLocation);
        tvParticipants = (TextView) findViewById(R.id.tvEvParticipants);
    }

    private void fillViews(){
        int background;

        if (event.getType().equals("B1")) {
            background = R.drawable.background_b1;
        } else if (event.getType().equals("B2")) {
            background = R.drawable.background_b2;
        } else if (event.getType().equals("B3")) {
            background = R.drawable.background_b3;
        } else if (event.getType().equals("B4")) {
            background = R.drawable.background_b4;
        } else if (event.getType().equals("B5")) {
            background = R.drawable.background_b5;
        } else {
            background = R.drawable.background_fici;
        }

        nsvContainer.setBackground(getResources().getDrawable(background));
        tvName.setText(event.getName());
        tvDate.setText(Utils.dayAndMonthFromText(event.getStartDate()));
        tvTime.setText(event.getTime(true));
        tvLocation.setText(event.getLocation());
        tvParticipants.setText(event.getParticipants().replace(",", ", "));
    }

}

package cu.uci.fiai.fici;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import cu.uci.fiai.fici.adapter.TutorAdapter;
import cu.uci.fiai.fici.libs.swipeback.app.SwipeBackActivity;
import cu.uci.fiai.fici.pojo.GTCE;
import cu.uci.fiai.fici.pojo.Tutor;
import cu.uci.fiai.fici.util.FICIApplication;

public class InfoGTCEActivity extends SwipeBackActivity {

    private GTCE gtce;
    private ArrayList<Tutor> tutors;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    //private FloatingActionButton fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gtce);
        getSwipeBackLayout().setEdgeTrackingEnabled();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().hasExtra(GTCE.TAG)) {
            gtce = (GTCE) getIntent().getSerializableExtra(GTCE.TAG);

            initTutors();
            initViews();
            fillViews();

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

    private void initTutors() {
        tutors = new ArrayList<>();

        for (String tutorUser : gtce.getTutorsArrayList()) {
            Tutor tutor = ((FICIApplication) getApplication()).getTutor(tutorUser);

            if (tutor != null) {
                tutors.add(tutor);
            }
        }
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        //fabClose = (FloatingActionButton) findViewById(R.id.fabClose);
        recyclerView = (RecyclerView) findViewById(R.id.rvTutors);
    }

    private void fillViews(){
        setSupportActionBar(toolbar);

        ((TextView)findViewById(R.id.tvGTCETopic)).setText(gtce.getTopic());
        collapsingToolbar.setTitle("Grupo de Trabajo Científico Estudiantil");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(new TutorAdapter(this, tutors));

        /*fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

}

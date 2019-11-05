package cu.uci.fiai.fici.libs.notifyme.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.FICIEvent;

public class TestActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toast.makeText(this, "FICIEvent: " + getIntent().hasExtra(FICIEvent.TAG),
                Toast.LENGTH_LONG).show();

        if(getIntent().hasExtra("test")) {
            ((TextView) findViewById(R.id.testNotif))
                    .setText(getIntent().getStringExtra("test"));
        }
    }

}

package cu.uci.fiai.fici.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import cu.uci.fiai.fici.EventActivity;
import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.adapter.EventAdapter;
import cu.uci.fiai.fici.pojo.FICIDayEvents;
import cu.uci.fiai.fici.pojo.FICIEvent;
import cu.uci.fiai.fici.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_DAY_EVENTS = "D4Y3V3NT5";

    private FICIDayEvents events;
    private int positionClicked = -1;
    private FICIEvent lastClickedObj;

    private TextView weekDayView;
    private TextView dateView;
    private ListView lvEvents;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param events Parameter 1.
     * @return A new instance of fragment EventFragment.
     */
    public static EventFragment newInstance(FICIDayEvents events) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DAY_EVENTS, events);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            events = (FICIDayEvents) getArguments().getSerializable(ARG_DAY_EVENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        weekDayView = (TextView) view.findViewById(R.id.tvEventsWeekDay);
        dateView = (TextView) view.findViewById(R.id.tvEventsDate);
        lvEvents = (ListView) view.findViewById(R.id.lvEventsDay);
        lastClickedObj = null;

        weekDayView.setText(Utils.getDayWeek(events.getDay()));
        dateView.setText(Utils.dayAndMonthFromText(events.getDay()));

        lvEvents.setAdapter(new EventAdapter(getContext(), events.getEvents()));
        lvEvents.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lastClickedObj = events.getEvent(position);
        positionClicked = position;

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), null);

        Intent intent = new Intent(getContext(), EventActivity.class);
        intent.putExtra(FICIEvent.TAG, events.getEvent(position));
        startActivity(intent, compat.toBundle());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (positionClicked != -1) {
            events.updateEvent(getActivity(), positionClicked);
            boolean isDifferent = events.getEvent(positionClicked)
                    .isDifferent(lastClickedObj);

            if (isDifferent) {
                lvEvents.setAdapter(new EventAdapter(getContext(), events.getEvents()));
            }
        }

        positionClicked = -1;
    }

}

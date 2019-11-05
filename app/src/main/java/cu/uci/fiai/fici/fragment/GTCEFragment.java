package cu.uci.fiai.fici.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.InfoGTCEActivity;
import cu.uci.fiai.fici.adapter.GTCEAdapter;
import cu.uci.fiai.fici.pojo.GTCE;
import cu.uci.fiai.fici.util.FICIApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GTCEFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTCEFragment extends Fragment implements AdapterView.OnItemClickListener,
        RecyclerView.OnItemTouchListener {

    private ArrayList<GTCE> gtces;
    private GestureDetector gestureDetector;

    //private ListView listView;
    private RecyclerView recyclerView;

    public GTCEFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GTCEFragment.
     */
    public static GTCEFragment newInstance() {
        return new GTCEFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gtces = ((FICIApplication) getActivity().getApplication()).getGTCE();
        gestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
        });

        getActivity().setTitle(getResources().getString(R.string.string_gtce));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gtce, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.listGTCE);

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(new GTCEAdapter(getContext(), gtces));
        recyclerView.addOnItemTouchListener(this);

        /*listView = (ListView) view.findViewById(R.id.listGTCE);
        GTCEAdapterLV adapter = new GTCEAdapterLV(getContext(), gtces);
        listView.setAdapter(adapter);
        listView.setDivider(getResources().getDrawable(R.drawable.divider));
        listView.setOnItemClickListener(this);/**/

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), InfoGTCEActivity.class);
        intent.putExtra(GTCE.TAG, gtces.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (child != null && gestureDetector.onTouchEvent(e)) {
            ActivityOptionsCompat compat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), null);

            int position = rv.getChildAdapterPosition(child);
            Intent intent = new Intent(getContext(), InfoGTCEActivity.class);
            intent.putExtra(GTCE.TAG, gtces.get(position));
            getActivity().startActivity(intent, compat.toBundle());
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

}

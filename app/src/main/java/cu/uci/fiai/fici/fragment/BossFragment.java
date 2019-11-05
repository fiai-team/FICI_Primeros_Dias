package cu.uci.fiai.fici.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.Boss;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BossFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BossFragment extends Fragment {

    private static final String ARG_BOSS = "B055";

    private Boss boss;

    private CircleImageView coverView;
    private TextView viewCargo;
    private TextView nameView;
    private TextView userView;
    private View itvGuest;
    private View itvPermGuest;

    public BossFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param boss Boss del Consejo de Dirección.
     * @return A new instance of fragment BossFragment.
     */
    public static BossFragment newInstance(Boss boss) {
        BossFragment fragment = new BossFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOSS, boss);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            boss = (Boss) getArguments().getSerializable(ARG_BOSS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boss, container, false);

        initViews(view);
        setupViews();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initViews(View view) {
        coverView = (CircleImageView) view.findViewById(R.id.viewCircleBoss);
        viewCargo = (TextView) view.findViewById(R.id.viewBossCargo);
        nameView = (TextView) view.findViewById(R.id.viewBossName);
        userView = (TextView) view.findViewById(R.id.viewBossUser);
        itvGuest = view.findViewById(R.id.viewGuestBoss);
        itvPermGuest = view.findViewById(R.id.viewPermanentGuestBoss);
    }

    private void setupViews() {
        coverView.setImageResource(boss.getResID());
        viewCargo.setText(boss.getCargo());
        nameView.setText(boss.getNames() + "\n" + boss.getLastNames());
        userView.setText(boss.getUser().replace("_", ".") + "@uci.cu");

        if (boss.isInvitate()) {
            if (boss.isPermanent()) {
                itvGuest.setVisibility(View.INVISIBLE);
            } else {
                itvPermGuest.setVisibility(View.INVISIBLE);
            }
        } else {
            itvGuest.setVisibility(View.INVISIBLE);
            itvPermGuest.setVisibility(View.INVISIBLE);
        }
    }

}

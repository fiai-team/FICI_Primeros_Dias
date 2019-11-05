package cu.uci.fiai.fici.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.ProfesorGuia;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PGFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PGFragment extends Fragment {

    private static final String ARG_PG = "PG";

    private ProfesorGuia pg;

    public PGFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pg Profesores Guías.
     * @return A new instance of fragment PGFragment.
     */
    public static PGFragment newInstance(ProfesorGuia pg) {
        PGFragment fragment = new PGFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PG, pg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            pg = (ProfesorGuia) getArguments().getSerializable(ARG_PG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pg, container, false);

        CircleImageView coverView = (CircleImageView) view.findViewById(R.id.viewCirclePG);
        TextView namesView = (TextView) view.findViewById(R.id.viewPGName);
        TextView userView = (TextView) view.findViewById(R.id.viewPGUser);
        TextView departamentView = (TextView) view.findViewById(R.id.viewPGDepartament);
        TextView groupTextView = (TextView) view.findViewById(R.id.viewPGGroup);

        coverView.setImageResource(pg.getResID());
        namesView.setText(pg.getNames() + "\n" + pg.getLastNames());
        if (!pg.getUser().equals("nouser")) {
            userView.setText(pg.getUser().replace("_", ".") + "@uci.cu");
        } else {
            userView.setText(" ");
        }
        departamentView.setText(pg.getDepartament());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            groupTextView.setText(pg.getGroup());
        } else {
            String group = pg.getGroup();

            group = group.charAt(0) + "\n" + group.charAt(1) + "\n" +
                    group.charAt(2) + "\n" + group.charAt(3);

            groupTextView.setText(group);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

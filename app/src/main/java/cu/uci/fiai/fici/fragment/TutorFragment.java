package cu.uci.fiai.fici.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;
import com.github.abdularis.civ.CircleImageView;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.Tutor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorFragment extends Fragment {
    private static final String ARG_TUTOR = "TUT0R";

    private Tutor tutor;

    public TutorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tutor Tutor.
     * @return A new instance of fragment TutorFragment.
     */
    public static TutorFragment newInstance(Tutor tutor) {
        TutorFragment fragment = new TutorFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TUTOR, tutor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tutor = (Tutor) getArguments().getSerializable(ARG_TUTOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor, container, false);

        AvatarImageView aiv = (AvatarImageView) view.findViewById(R.id.viewAvatarTutor);
        aiv.setText(tutor.getNames());

        if (tutor.getResID() != 0) {
            aiv.setImageResource(tutor.getResID());
            aiv.setState(AvatarImageView.SHOW_IMAGE);
        }

        ((TextView) view.findViewById(R.id.tvTutorName))
                .setText(tutor.getNames() + "\n" + tutor.getLastNames());
        ((TextView) view.findViewById(R.id.tvTutorUser)).setText(tutor.getUser() + "@uci.cu");
        ((TextView) view.findViewById(R.id.tvTutorDpto)).setText(tutor.getDepartament());

        return view;
    }

}

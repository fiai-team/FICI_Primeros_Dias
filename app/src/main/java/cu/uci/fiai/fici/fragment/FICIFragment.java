package cu.uci.fiai.fici.fragment;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cu.uci.fiai.fici.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FICIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FICIFragment extends Fragment {

    private TextView textView;

    public FICIFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FICIFragment.
     */
    public static FICIFragment newInstance() {
        return new FICIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(getString(R.string.string_fici));
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fici, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

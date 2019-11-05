package cu.uci.fiai.fici.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import cu.uci.fiai.fici.PreferencesActivity;
import cu.uci.fiai.fici.R;

/**
 * Created by Tyto on 18/6/2018.
 */
public class MainFragment extends Fragment {

    private static final int CALL_PERMISSION_REQUEST = 0;

    private IconTextView viewEmail;
    private IconTextView viewWeb;
    private IconTextView viewPhone;

    public MainFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewEmail = (IconTextView) view.findViewById(R.id.viewUCIemail);
        viewWeb = (IconTextView) view.findViewById(R.id.viewUCIweb);
        viewPhone = (IconTextView)view.findViewById(R.id.viewUCIphone);

        viewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/html");
                emailIntent.putExtra(Intent.EXTRA_EMAIL , new String[]{"uci@uci.cu"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Para UCI");
                emailIntent.putExtra(Intent.EXTRA_TEXT , "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo con..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "No hay ningún cliente de correo instalado.", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.uci.cu/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webIntent);
            }
        });

        viewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCallPermission();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.string_uci);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (CALL_PERMISSION_REQUEST == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Marcando a la UCI", Toast.LENGTH_LONG)
                        .show();
                callUCI();
            } else {
                Toast.makeText(getContext(), "No se pueden realizar llamadas", Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkCallPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCallPhonePermission = getContext().checkSelfPermission(Manifest.permission.CALL_PHONE);;

            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_REQUEST);
            } else {
                callUCI();
            }
        } else {
            callUCI();
        }
    }

    private void callUCI() {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:+53 7 835 8358"));

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        startActivity(phoneIntent);
    }

}

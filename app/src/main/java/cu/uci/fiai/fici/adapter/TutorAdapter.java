package cu.uci.fiai.fici.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.Tutor;

/**
 * Created by Tyto on 26/7/2018.
 */

public class TutorAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final ArrayList<Tutor> tutors;

    public TutorAdapter(Context context, ArrayList<Tutor> tutors) {
        this.context = context;
        this.tutors = tutors;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_tutor, parent, false);

        return new TutorHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TutorHolder) holder).bindData(tutors.get(position));
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    private class TutorHolder extends RecyclerView.ViewHolder {

        AvatarImageView aiv;
        TextView tvName;
        TextView tvUser;
        TextView tvDpto;
        LinearLayout llMailTutor;

        TutorHolder(View itemView) {
            super(itemView);
            this.aiv = (AvatarImageView) itemView.findViewById(R.id.viewAvatarTutor);
            this.tvName = (TextView) itemView.findViewById(R.id.tvTutorName);
            this.tvUser = (TextView) itemView.findViewById(R.id.tvTutorUser);
            this.tvDpto = (TextView) itemView.findViewById(R.id.tvTutorDpto);
            this.llMailTutor = (LinearLayout) itemView.findViewById(R.id.llMailTutor);
        }

        void bindData(Tutor tutor) {
            aiv.setText(tutor.getNames());

            if (tutor.getResID() != 0) {
                aiv.setImageResource(tutor.getResID());
                aiv.setState(AvatarImageView.SHOW_IMAGE);
            }

            tvName.setText(tutor.getNames() + " " + tutor.getLastNames());
            tvUser.setText(tutor.getUser() + "@uci.cu");
            tvDpto.setText("Departamento de " + tutor.getDepartament());

            if (tutor.getUser().equals("dptocs") || tutor.getUser().equals("cham")) {
                llMailTutor.setVisibility(View.INVISIBLE);
            }
        }

    }

}

package cu.uci.fiai.fici.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.GTCE;

/**
 * Created by Tyto on 26/7/2018.
 */

public class GTCEAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final ArrayList<GTCE> gtces;

    public GTCEAdapter(Context context, ArrayList<GTCE> gtces) {
        this.context = context;
        this.gtces = gtces;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_gtce, parent, false);
        return new GTCEHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GTCEHolder) holder).bindData(gtces.get(position));
    }

    @Override
    public int getItemCount() {
        return gtces.size();
    }

    private class GTCEHolder extends RecyclerView.ViewHolder {

        TextView topicView;

        GTCEHolder(View itemView) {
            super(itemView);
            topicView = (TextView) itemView.findViewById(R.id.gtceTopicView);
        }

        void bindData(GTCE gtce) {
            topicView.setText(gtce.getTopic());
        }

    }

}

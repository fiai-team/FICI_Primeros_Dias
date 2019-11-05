package cu.uci.fiai.fici.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.GTCE;

/**
 * Created by Tyto on 6/7/2018.
 */

public class GTCEAdapterLV extends ArrayAdapter {

    private Context context;
    private ArrayList<GTCE> data;

    public GTCEAdapterLV(@NonNull Context context, ArrayList<GTCE> data) {
        super(context, R.layout.item_list_gtce_old, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        GTCEHolder holder;
        View item = convertView;

        if (item == null){
            holder = new GTCEHolder();
            item = LayoutInflater.from(context).inflate(R.layout.item_list_gtce_old, null);
            holder.topicView = (TextView) item.findViewById(R.id.gtceTopicView);
            item.setTag(holder);
        }

        holder = (GTCEHolder) item.getTag();
        holder.topicView.setText(data.get(position).getTopic());

        return item;
    }

    private class GTCEHolder {
        TextView topicView;
    }

}

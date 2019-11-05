package cu.uci.fiai.fici.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import java.util.ArrayList;

import cu.uci.fiai.fici.R;
import cu.uci.fiai.fici.pojo.FICIEvent;

/**
 * Created by Tyto on 9/7/2018.
 */

public class EventAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<FICIEvent> data;

    public EventAdapter(@NonNull Context context, ArrayList<FICIEvent> data){
        super(context, R.layout.item_list_event, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        EventHolder holder;
        View item = convertView;

        if (item == null){
            holder = new EventHolder();
            item = LayoutInflater.from(context).inflate(R.layout.item_list_event, null);
            holder.nameView = (TextView) item.findViewById(R.id.tvEventName);
            holder.notificationsImageView = (ImageView) item
                        .findViewById(R.id.viewNotifications);

            item.setTag(holder);
        }

        holder = (EventHolder) item.getTag();
        holder.nameView.setText(data.get(position).getName());

        if (data.get(position).isSelected()) {
            holder.notificationsImageView.setImageResource(
                        R.drawable.path_notifications_alt);
        }

        return item;
    }

    private class EventHolder {
        RichPathView notificationsRichPathView;
        ImageView notificationsImageView;
        TextView nameView;

        void animateNotifications(){
            final RichPath top = notificationsRichPathView.findRichPathByIndex(0);
            final RichPath bottom = notificationsRichPathView.findRichPathByIndex(1);

            top.setFillColor(0xFF05235E);
            bottom.setFillColor(0xFF05235E);

            RichPathAnimator.animate(top)
                    .interpolator(new DecelerateInterpolator())
                    .rotation(0, 20, -20, 10, -10, 5, -5, 2, -2, 0)
                    .duration(4000)
                    .andAnimate(bottom)
                    .interpolator(new DecelerateInterpolator())
                    .rotation(0, 10, -10, 5, -5, 2, -2, 0)
                    .startDelay(50)
                    .duration(4000)
                    .repeatModeSet(RichPathAnimator.RESTART)
                    .repeatCountSet(RichPathAnimator.INFINITE)
                    .start();
        }

    }

}

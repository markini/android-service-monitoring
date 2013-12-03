package at.marki.Client.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at.marki.Client.R;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;

import java.util.UUID;

/**
 * Created by marki on 30.10.13.
 */
public class AdapterMainFragment extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;

    public AdapterMainFragment(Fragment fragment) {
        inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = fragment.getActivity();
    }


    public static class ViewHolder {
        public TextView message;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Message message;
        if (i < Data.getMessages(context).size()) {
            message = Data.getMessages(context).get(i);
        } else {
            message = new Message(UUID.randomUUID().toString(), "faulty message", System.currentTimeMillis());
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lv_main, parent, false);
            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.tv_item_lv_main_message);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.message.setText(message.message);
        return convertView;
    }

    @Override
    public int getCount() {
        return Data.getMessages(context).size();
    }

    @Override
    public Object getItem(int i) {
        if (i < Data.getMessages(context).size()) {
            return Data.getMessages(context).get(i);
        } else {
            return "default message";
        }
    }

    @Override
    public long getItemId(int id) {
        return id;
    }
}

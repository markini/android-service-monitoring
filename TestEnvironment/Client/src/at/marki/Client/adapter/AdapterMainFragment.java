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
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by marki on 30.10.13.
 */
public class AdapterMainFragment extends BaseAdapter {

	private final LayoutInflater inflater;
	private Context context;
	public List<Integer> headerPositions = new ArrayList<Integer>();

	public AdapterMainFragment(Fragment fragment) {
		Data.sortMessages(fragment.getActivity());
		inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = fragment.getActivity();
	}

	public void calculateSections(ArrayList<SimpleSectionedListAdapter.Section> sections) {
		sections.clear();
		headerPositions.clear();
		Message message = null;
		String headerText = "";
		String tempText = "";
		for (int i = 0; i < getCount(); i++) {
			message = (Message) getItem(i);
			tempText = message.dateString;
			if (!tempText.equals(headerText)) {
				headerText = tempText;
				sections.add(new SimpleSectionedListAdapter.Section(i, headerText));
				headerPositions.add(i + headerPositions.size());
			}
		}
//		for (Integer in : headerPositions) {
//			System.out.println(in);
//		}
	}

//	@Override
//	public View getHeaderView(int position, View convertView, ViewGroup parent) {
//		ViewHolderHeader holder;
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.header_lv_main, parent, false);
//			holder = new ViewHolderHeader();
//			holder.headerText = (TextView) convertView.findViewById(R.id.tv_header_lv_main);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolderHeader) convertView.getTag();
//		}
//		Message message = Data.getMessages(context).get(position);
//
//		holder.headerText.setText(message.dateString);
//		return convertView;
//	}
//
//	@Override
//	public long getHeaderId(int position) {
//		//return position;
//		return Data.getMessages(context).get(position).dateString.hashCode();
//	}


	public static class ViewHolder {
		public TextView message;
	}

//	public static class ViewHolderHeader {
//		public TextView headerText;
//	}

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
			return new Message(UUID.randomUUID().toString(), "default message", System.currentTimeMillis());
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}

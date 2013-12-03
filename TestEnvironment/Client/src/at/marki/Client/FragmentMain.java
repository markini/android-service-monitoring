package at.marki.Client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import at.marki.Client.adapter.AdapterMainFragment;
import at.marki.Client.download.GetNewDataService;
import at.marki.Client.events.newMessageEvent;
import at.marki.Client.service.RegisterGcmIdService;
import at.marki.Client.swipeToDismiss.SwipeDismissListViewTouchListener;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.Message;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by marki on 24.10.13.
 */
class FragmentMain extends Fragment {

	@InjectView(R.id.lv_main_messages)
	StickyListHeadersListView messagesListView;

	@Inject
	private Bus bus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		((ClientApplication) getActivity().getApplication()).inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		Views.inject(this, view);

		messagesListView.setAdapter(new AdapterMainFragment(this));
		setUpSwipeToDismissListener(messagesListView);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void setUpSwipeToDismissListener(StickyListHeadersListView listView) {
		SwipeDismissListViewTouchListener touchListener =
				new SwipeDismissListViewTouchListener(
						listView,
						new SwipeDismissListViewTouchListener.DismissCallbacks() {
							@Override
							public boolean canDismiss(int position) {
								return true;
							}

							@Override
							public void onDismiss(StickyListHeadersListView listView, int[] reverseSortedPositions) {
								for (int position : reverseSortedPositions) {
									Data.deleteMessage(FragmentMain.this.getActivity(), (Message) listView.getAdapter().getItem(position));
								}
								((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
							}
						});
		listView.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during ListView scrolling,
		// we don't look for swipes.
		listView.setOnScrollListener(touchListener.makeScrollListener());
	}

	//--------------------------------------------------------------------------------------------------
	//PAUSE - RESUME -----------------------------------------------------------------------------------

	@Override
	public void onPause() {
		if (BuildConfig.DEBUG) {
			Timber.d("onPause");
		}
		bus.unregister(this);
		super.onPause();
	}

	@Override
	public void onResume() {
		if (BuildConfig.DEBUG) {
			Timber.d("onResume");
		}
		bus.register(this);

		//------------------------------------

		if (((MainActivity) getActivity()).newMessage) {
			((MainActivity) getActivity()).newMessage = false;
			((BaseAdapter) messagesListView.getAdapter()).notifyDataSetChanged();
		}


		super.onResume();
	}

	//CLICKLISTENER ---------------------------------------------------------------------
	//-----------------------------------------------------------------------------------

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {
			return false;
		}
		switch (item.getItemId()) {
			case android.R.id.home:
				break;
			case R.id.menu_settings:
				((MainActivity) getActivity()).startTransaction(R.id.fragment_frame, new FragmentPrefs(), MainActivity.TAG_PREFS_FRAGMENT, true);
				break;
			case R.id.menu_clear:
				Data.getMessages(getActivity()).clear();
				Data.deleteAllMessages(getActivity());
				((BaseAdapter) messagesListView.getAdapter()).notifyDataSetChanged();
				break;
			case R.id.menu_start_monitor:
				clickStartMonitoring();
				break;
			case R.id.menu_stop_monitor:
				clickStopMonitoring();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@OnClick(R.id.btn_ping_server)
	public void clickPingServerButton() {
		Timber.d("ping server");
		Intent intent = new Intent(getActivity(), GetNewDataService.class);
		getActivity().startService(intent);
	}

	@OnClick(R.id.btn_show_gcm)
	public void clickGCMButton() {
		GCMRegistrar.setRegisteredOnServer(getActivity(), true);
		Timber.d("gcm id = " + GCMRegistrar.getRegistrationId(getActivity()));

		//start register gcm id intent service
		Intent intent = new Intent(getActivity(), RegisterGcmIdService.class);
		intent.putExtra("id", GCMRegistrar.getRegistrationId(getActivity()));
		getActivity().startService(intent);

		//show toast
		Toast.makeText(getActivity(), "Registering GCM ID on Server", Toast.LENGTH_SHORT).show();
	}

	private void clickStartMonitoring() {
		clickStopMonitoring();
		((ClientApplication) getActivity().getApplication()).startMonitoring();
		Toast.makeText(getActivity(), "Starting Monitoring", Toast.LENGTH_SHORT).show();
	}

	private void clickStopMonitoring() {
		//((ClientApplication) getActivity().getApplication()).pingServerMonitor.stopMonitoring(getActivity());
		//((ClientApplication) getActivity().getApplication()).gcmCheckMonitor.stopMonitoring(getActivity());
		((ClientApplication) getActivity().getApplication()).serverMonitor.stopMonitoring(getActivity());
		((ClientApplication) getActivity().getApplication()).connectivityMonitor.stopMonitoring(getActivity());
		Toast.makeText(getActivity(), "Stopping Monitoring", Toast.LENGTH_SHORT).show();
	}

	//--------------------------------------------------------------------------------------------------
	//EVENTS -------------------------------------------------------------------------------------------

	@Subscribe
	public void onNewMessageEvent(newMessageEvent event) {
		if (BuildConfig.DEBUG) {
			Timber.d("onNewMessageEvent");
		}
		if (!Data.getMessages(getActivity()).contains(event.message)) {
			Data.getMessages(getActivity()).add(event.message);
			((BaseAdapter) messagesListView.getAdapter()).notifyDataSetChanged();
		}
	}

}

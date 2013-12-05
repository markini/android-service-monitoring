package at.marki.Client;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import at.marki.Client.adapter.AdapterMainFragment;
import at.marki.Client.download.GetNewDataService;
import at.marki.Client.events.FailedMessageDownloadEvent;
import at.marki.Client.events.deleteMessagesEvent;
import at.marki.Client.events.newMessageEvent;
import at.marki.Client.service.RegisterGcmIdService;
import at.marki.Client.utils.Data;
import at.marki.Client.utils.DialogStarter;
import at.marki.Client.utils.Message;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import de.timroes.android.listview.EnhancedListView;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter.Section;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by marki on 24.10.13.
 */
class FragmentMain extends Fragment {

	public static final String TAG = "at.marki.FragmentMain";

	private ArrayList<Section> sections = new ArrayList<Section>();
	private AdapterMainFragment adapter;
	private SimpleSectionedListAdapter simpleSectionedListAdapter;
	private boolean isRefreshing;

	@InjectView(R.id.list)
	EnhancedListView messagesListView;

	@InjectView(R.id.iv_refresh)
	ImageView getMessageRefresh;

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
		getMessageRefresh.setVisibility(View.INVISIBLE);
		isRefreshing = false;
		//View viewdd = getActivity().findViewById(R.id.menu_get_message);
		//refreshActionImageView = (ImageView) getActivity().findViewById(R.id.iv_refresh_action_image);

		setAdapter();
		setupSwipeToDismissListener2();
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_main, menu);

		if (isRefreshing) {
			//if we're refreshing, show the animation
			MenuItem item = menu.findItem(R.id.menu_get_message);
			item.setActionView(R.layout.refresh_action_image);
			ImageView iv = (ImageView) item.getActionView().findViewById(R.id.iv_refresh_action_image);
			((AnimationDrawable) iv.getDrawable()).start();
		}
	}

	private void setAdapter() {
		adapter = new AdapterMainFragment(this);

		adapter.calculateSections(sections);
		simpleSectionedListAdapter = new SimpleSectionedListAdapter(getActivity(), R.layout.header_lv_main, adapter);

		simpleSectionedListAdapter.setSections(sections.toArray(new Section[0]));
		messagesListView.setAdapter(simpleSectionedListAdapter);
	}

	private void updateAdapter() {
		adapter.calculateSections(sections);
		simpleSectionedListAdapter.setSections(sections.toArray(new Section[0]));
		simpleSectionedListAdapter.notifyDataSetChanged();
	}

	private void setupSwipeToDismissListener2() {

		messagesListView.setDismissCallback(new de.timroes.android.listview.EnhancedListView.OnDismissCallback() {
			/**
			 * This method will be called when the user swiped a way or deleted it via
			 * {@link de.timroes.android.listview.EnhancedListView#delete(int)}.
			 *
			 * @param listView The {@link EnhancedListView} the item has been deleted from.
			 * @param position The position of the item to delete from your adapter.
			 * @return An {@link de.timroes.android.listview.EnhancedListView.Undoable}, if you want
			 * to give the user the possibility to undo the deletion.
			 */
			@Override
			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

				// Get your item from the adapter (mAdapter being an adapter for MyItem objects)
				final Message deletedItem = (Message) listView.getAdapter().getItem(position);
				final int positionInArray = Data.getMessages(getActivity()).indexOf(deletedItem);
				// Delete item from adapter
				Data.getMessages(getActivity()).remove(deletedItem);
				updateAdapter();

				return new EnhancedListView.Undoable() {
					// Method is called when user undoes this deletion
					public void undo() {
						// Reinsert item to list
						if (positionInArray != -1) {
							Data.getMessages(getActivity()).add(positionInArray, deletedItem);
						} else {
							Data.getMessages(getActivity()).add(position, deletedItem);
						}
						updateAdapter();
					}

					// Return an undo message for that item
					public String getTitle() {
						return "Message from " + deletedItem.dateString + " deleted";
					}

					// Called when user cannot undo the action anymore
					public void discard() {
						// Use this place to e.g. delete the item from database
						Data.deleteMessage(getActivity(), deletedItem);
					}
				};
			}
		});
		messagesListView.enableSwipeToDismiss();
		messagesListView.setUndoStyle(EnhancedListView.UndoStyle.SINGLE_POPUP);
		messagesListView.setSwipeDirection(EnhancedListView.SwipeDirection.END);
		messagesListView.setSwipingLayout(R.id.lin_lay_item_lv_main);
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

	@Override
	public void onStop() {
		super.onStop();
		// Throw away all pending undos.
		if (messagesListView != null) {
			messagesListView.discardUndo();
		}
		stopRefreshAnimation();
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
				((MainActivity) getActivity()).startTransaction(R.id.fragment_frame, new FragmentPrefs(), FragmentPrefs.TAG, true);
				break;
			case R.id.menu_clear:
				DialogStarter.startDeleteDialog(getActivity());
				break;
			case R.id.menu_start_monitor:
				clickStartMonitoring();
				break;
			case R.id.menu_stop_monitor:
				clickStopMonitoring();
				break;
			case R.id.menu_create_mock_messages:
				Data.createMockMessages(getActivity());
				updateAdapter();
				break;
			case R.id.menu_get_message:
				if (isRefreshing) {
					return true;
				}
				getMessage();
				break;
			case R.id.menu_register_gcm:
				registerGcm();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@OnClick(R.id.tv_get_messages)
	public void getMessage() {
		Timber.d("ping server");
		if (isRefreshing) {
			return;
		}
		startRefreshAnimation();
		Intent intent = new Intent(getActivity(), GetNewDataService.class);
		getActivity().startService(intent);
	}

	@OnClick(R.id.btn_show_gcm)
	public void registerGcm() {
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
		((ClientApplication) getActivity().getApplication()).serverMonitor.stopMonitoring(getActivity());
		((ClientApplication) getActivity().getApplication()).connectivityMonitor.stopMonitoring(getActivity());
		Toast.makeText(getActivity(), "Stopping Monitoring", Toast.LENGTH_SHORT).show();
	}

	private void startRefreshAnimation() {
		isRefreshing = true;
		RotateAnimation anim = new RotateAnimation(0f, 360f, 24f, 24f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(700);

		// Start animating the image
		getMessageRefresh.setVisibility(View.VISIBLE);
		getMessageRefresh.startAnimation(anim);

		getActivity().invalidateOptionsMenu();
	}

	private void stopRefreshAnimation() {
		isRefreshing = false;
		if (getMessageRefresh != null) {
			getMessageRefresh.setVisibility(View.INVISIBLE);
			getMessageRefresh.setAnimation(null);
		}
		isRefreshing = false;
		getActivity().invalidateOptionsMenu();
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
			updateAdapter();
		} else {
			Toast.makeText(getActivity(), "No new message available", Toast.LENGTH_SHORT).show();
		}
		stopRefreshAnimation();
	}

	@Subscribe
	public void onNewMessageEvent(FailedMessageDownloadEvent event) {
		Toast.makeText(getActivity(), "Message download failed", Toast.LENGTH_LONG).show();
		stopRefreshAnimation();
	}

	@Subscribe
	public void onDeleteAllMessages(deleteMessagesEvent event) {
		Data.getMessages(getActivity()).clear();
		Data.deleteAllMessages(getActivity());
		updateAdapter();
	}
}

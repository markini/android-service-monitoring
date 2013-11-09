package at.marki.Client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ListView;
import at.marki.Client.adapter.AdapterMainFragment;
import at.marki.Client.download.GetNewDataService;
import at.marki.Client.events.newMessageEvent;
import at.marki.Client.utils.Data;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by marki on 24.10.13.
 */
class FragmentMain extends Fragment {

    @InjectView(R.id.lv_main_messages)
    ListView messagesListView;

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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                Data.messages.clear();
                ((BaseAdapter) messagesListView.getAdapter()).notifyDataSetChanged();
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
    }

    @OnClick(R.id.btn_start_monitoring)
    public void clickStartMonitoring() {
        clickStopMonitoring();
        ((ClientApplication) getActivity().getApplication()).startMonitoring();
    }

    @OnClick(R.id.btn_stop_monitoring)
    void clickStopMonitoring() {
        ((ClientApplication) getActivity().getApplication()).pingServerMonitor.stopMonitoring(getActivity());
        ((ClientApplication) getActivity().getApplication()).gcmCheckMonitor.stopMonitoring(getActivity());
    }

    //--------------------------------------------------------------------------------------------------
    //EVENTS -------------------------------------------------------------------------------------------

    @Subscribe
    public void onNewMessageEvent(newMessageEvent event) {
        if (BuildConfig.DEBUG) {
            Timber.d("onNewMessageEvent");
        }
        Data.messages.add(event.message);
        ((BaseAdapter) messagesListView.getAdapter()).notifyDataSetChanged();
    }

}

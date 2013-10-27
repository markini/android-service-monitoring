package at.marki.Client;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.google.android.gcm.GCMRegistrar;
import timber.log.Timber;

/**
 * Created by marki on 24.10.13.
 */
public class FragmentMain extends Fragment {

    @InjectView(R.id.btn_ping_server)
    Button buttonPing;
    @InjectView(R.id.btn_show_gcm)
    Button buttonGCM;
    @InjectView(R.id.tv_test)
    TextView textViewTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Views.inject(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.menu_new_execution_process:
                ((MainActivity)getActivity()).startTransaction(R.id.fragment_frame,new FragmentPrefs(),MainActivity.TAG_PREFS_FRAGMENT,true);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @OnClick(R.id.btn_ping_server)
    public void clickPingServerButton() {
        Timber.d("timber message");
    }

    @OnClick(R.id.btn_show_gcm)
    public void clickGCMButton() {
        GCMRegistrar.setRegisteredOnServer(getActivity(), true);
        Timber.d("gcm id = " + GCMRegistrar.getRegistrationId(getActivity()));
    }

}

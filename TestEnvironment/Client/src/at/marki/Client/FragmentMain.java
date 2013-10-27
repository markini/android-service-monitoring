package at.marki.Client;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @InjectView(R.id.btn_set_ip)
    Button buttonIP;
    @InjectView(R.id.tv_test)
    TextView textViewTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Views.inject(this, view);
        return view;
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

package at.marki.Client;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FragmentPrefs extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}

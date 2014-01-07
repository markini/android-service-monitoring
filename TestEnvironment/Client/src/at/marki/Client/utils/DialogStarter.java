package at.marki.Client.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import at.marki.Client.dialogs.DeleteDialog;
import at.marki.Client.dialogs.ShowLogDialog;

/**
 * Created by marki on 04.12.13.
 */
public class DialogStarter {
	public static void startDeleteDialog(Activity activity){
		FragmentTransaction fragmentTrans = activity.getFragmentManager().beginTransaction();
		Fragment prev = activity.getFragmentManager().findFragmentByTag(DeleteDialog.TAG);
		if (prev != null) {
			fragmentTrans.remove(prev);
		}
		fragmentTrans.addToBackStack(null);
		DialogFragment alertDialog = DeleteDialog.newInstance();
		alertDialog.show(fragmentTrans, DeleteDialog.TAG);
	}

    public static void startLogDialog(Activity activity){
        FragmentTransaction fragmentTrans = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(ShowLogDialog.TAG);
        if (prev != null) {
            fragmentTrans.remove(prev);
        }
        fragmentTrans.addToBackStack(null);
        DialogFragment logDialog = ShowLogDialog.newInstance();
        logDialog.show(fragmentTrans, ShowLogDialog.TAG);
    }
}

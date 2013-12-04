package at.marki.Client.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import at.marki.Client.dialogs.DeleteDialog;

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
}

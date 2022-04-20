package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

import co.com.ceiba.mobile.pruebadeingreso.R;

public class BaseActivity extends AppCompatActivity {

    ProgressDialog dialog;

    void showProgress() {
        dialog = ProgressDialog.show(
                this,
                "",
                getString(R.string.generic_message_progress), true
        );
    }

    void hideProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    void onError(Throwable throwable) {
        String title = getString(R.string.generic_error);
        String message = throwable != null ? throwable.getMessage() : "";
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.accept), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).create().show();
    }

}

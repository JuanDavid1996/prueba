package co.com.ceiba.mobile.pruebadeingreso.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import co.com.ceiba.mobile.pruebadeingreso.R
import android.content.DialogInterface

open class BaseActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    fun showProgress() {
        dialog = ProgressDialog.show(
            this,
            "",
            getString(R.string.generic_message_progress), true
        )
    }

    fun hideProgress() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun onError(throwable: Throwable?) {
        val title = getString(R.string.generic_error)
        val message = if (throwable != null) throwable.message else ""
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.accept)) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
            .create().show()
    }
}
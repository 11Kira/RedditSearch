package com.test.redditsearch.core

import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Base activity that contains reusable functions
 * @author Julius Villagracia
 */
open class BaseActivity : AppCompatActivity() {

    var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alertDialog = MaterialAlertDialogBuilder(this).create()
    }

    /**
     * Shows an alert dialog to the user with the passed title & message.
     * @param title The [String] title of this alert.
     * @param msg The [String] message to show the user.
     * @param positiveBtn The [Int] resource ID of the of the positive button to display.
     * @param negativeBtn The [Int] resource ID of the of the negative button to display.
     * @param positiveAction The function to execute when the [BUTTON_POSITIVE] is clicked.
     * @param negativeAction The function to execute when the [BUTTON_NEGATIVE] is clicked.
     */
    open fun showAlert(
        title: String,
        msg: String,
        positiveBtn: Int?,
        positiveAction: () -> Unit,
        negativeBtn: Int?,
        negativeAction: () -> Unit,
    ) {
        showAlertDialog(
            title = title,
            msg = msg,
            positiveBtn = positiveBtn,
            positiveAction = positiveAction,
            negativeBtn = negativeBtn,
            negativeAction = negativeAction
        )
    }

    /**
     * Shows an alert dialog to the user with the passed title & message.
     * @param title The [String] title of this alert.
     * @param msg The [String] message to show the user.
     * @param positiveBtn The [Int] resource ID of the of the positive button to display.
     * @param positiveAction The function to execute when the [BUTTON_POSITIVE] is clicked.
     */
    open fun showAlert(
        title: String,
        msg: String,
        positiveBtn: Int?,
        positiveAction: () -> Unit,
    ) {
        showAlertDialog(
            title = title,
            msg = msg,
            positiveBtn = positiveBtn,
            positiveAction = positiveAction,
        )
    }

    /**
     * Shows an alert dialog to the user with the passed parameters.
     * @param title The title of the dialog.
     * @param msg The message to be shown.
     * @param negativeBtn The text for that will be used for the negative button.
     * @param positiveBtn The text for that will be used for the positive button.
     * @param positiveAction The function to execute when the positive button is clicked.
     */
    private fun showAlertDialog(
        title: String? = null,
        msg: String? = null,
        negativeBtn: Int? = null,
        positiveBtn: Int? = null,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit
    ) {
        alertDialog = MaterialAlertDialogBuilder(this)
            .create()
        title?.let { alertDialog?.setTitle(title) }
        msg?.let { alertDialog?.setMessage(msg) }
        negativeBtn?.let {
            alertDialog?.setButton(BUTTON_NEGATIVE, getString(it)) { dialog, _ ->
                dialog.dismiss()
                negativeAction.invoke()
            }
        }
        positiveBtn?.let {
            alertDialog?.setButton(BUTTON_POSITIVE, getString(it)) { dialog, _ ->
                dialog.dismiss()
                positiveAction.invoke()
            }
        }
        if (positiveBtn == null) {
            alertDialog?.setButton(BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
                positiveAction.invoke()
            }
        }
        alertDialog?.show()
    }

    /**
     * Shows an alert dialog to the user with the passed parameters.
     * @param title The title of the dialog.
     * @param msg The message to be shown.
     * @param positiveBtn The text for that will be used for the positive button.
     * @param positiveAction The function to execute when the positive button is clicked.
     */
    private fun showAlertDialog(
        title: String? = null,
        msg: String? = null,
        positiveBtn: Int? = null,
        positiveAction: () -> Unit,
    ) {
        alertDialog = MaterialAlertDialogBuilder(this)
            .create()
        title?.let { alertDialog?.setTitle(title) }
        msg?.let { alertDialog?.setMessage(msg) }
        positiveBtn?.let {
            alertDialog?.setButton(BUTTON_POSITIVE, getString(it)) { dialog, _ ->
                dialog.dismiss()
                positiveAction.invoke()
            }
        }
        if (positiveBtn == null) {
            alertDialog?.setButton(BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
                positiveAction.invoke()
            }
        }
        alertDialog?.show()
    }
}
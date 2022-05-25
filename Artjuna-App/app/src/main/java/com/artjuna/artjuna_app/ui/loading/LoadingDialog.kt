package com.artjuna.artjuna_app.ui.loading

import android.app.Activity
import android.app.AlertDialog
import com.artjuna.artjuna_app.databinding.FragmentLoadingDialogBinding

class LoadingDialog(activity: Activity, isCancelable: Boolean) {

    private var dialog: AlertDialog? = null

    init {
        val binding = FragmentLoadingDialogBinding.inflate(activity.layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
        builder.setCancelable(isCancelable)
        dialog = builder.create()
    }

    fun show() {
        dialog!!.show()
    }

    fun dismiss() {
        dialog!!.dismiss()
    }
}
package com.artjuna.artjuna_app.ui.loading

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.artjuna.artjuna_app.R
import com.artjuna.artjuna_app.databinding.FragmentLoadingDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialog(activity: Activity, isCancelable: Boolean) {

    private var dialog: Dialog? = null

    init {
        val binding = FragmentLoadingDialogBinding.inflate(activity.layoutInflater)
        val builder = MaterialAlertDialogBuilder(activity, R.style.MaterialDialogRounded)
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
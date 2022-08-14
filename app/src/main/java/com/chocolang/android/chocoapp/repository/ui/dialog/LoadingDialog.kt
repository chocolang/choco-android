package com.chocolang.android.chocoapp.repository.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.chocolang.android.chocoapp.R

class LoadingDialog(context: Context): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.run {
            setCancelable(false)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
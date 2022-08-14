package com.chocolang.android.chocoapp.repository.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.chocolang.android.chocoapp.repository.ApiClient

open class BaseListFragment: Fragment() {
    var loadingDialog: Dialog? = null
    lateinit var repo: ApiClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repo = ApiClient(requireContext())
    }
}
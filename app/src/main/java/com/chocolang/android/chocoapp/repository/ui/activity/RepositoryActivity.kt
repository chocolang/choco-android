package com.chocolang.android.chocoapp.repository.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.chocolang.android.chocoapp.BR
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.databinding.ActivityRepositoryBinding
import com.chocolang.android.chocoapp.repository.ApiClient
import com.chocolang.android.chocoapp.viewmodel.RepositoryViewModel

const val ARG_REPO_ID = "arg_repo_id"

class RepositoryActivity: AppCompatActivity() {
    private lateinit var binding : ActivityRepositoryBinding
    private lateinit var viewModel: RepositoryViewModel
    private val id: Int by lazy {
        intent.getIntExtra(ARG_REPO_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository)

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        viewModel.repo = ApiClient(this)

        Toast.makeText(this, id.toString(), Toast.LENGTH_LONG).show()

        binding.setVariable(BR.repositoryDetailViewModel, viewModel)
        viewModel.getList("recycler")
    }
}
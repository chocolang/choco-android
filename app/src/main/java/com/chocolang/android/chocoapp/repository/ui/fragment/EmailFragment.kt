package com.chocolang.android.chocoapp.repository.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chocolang.android.chocoapp.BR
import com.chocolang.android.chocoapp.ListAdapter
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.adapter.UserListAdapter
import com.chocolang.android.chocoapp.databinding.FragmentEmailBinding
import com.chocolang.android.chocoapp.repository.result.GitUserResult
import com.chocolang.android.chocoapp.repository.ui.dialog.LoadingDialog
import com.chocolang.android.chocoapp.viewmodel.RepositoryViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EmailFragment : BaseListFragment() {
    private lateinit var binding: FragmentEmailBinding
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listLayoutmanager: LinearLayoutManager
    private lateinit var listAdapter: UserListAdapter

    private lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** data binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        binding.lifecycleOwner = this

        /** view binding */
        //binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        val resultObserver = Observer<GitUserResult> { result ->
            listAdapter.addAll(result.items)
        }
        val isLoadingObserver = Observer<Boolean> { isLoading ->
            loadingDialog?.run {
                if(isLoading) show() else dismiss()
            }
        }
        viewModel.repo = repo
        viewModel.userItems.observe(viewLifecycleOwner, resultObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)

        listLayoutmanager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listAdapter = UserListAdapter()
        binding.rvEmailList.apply {
            layoutManager = listLayoutmanager
            adapter = listAdapter
        }

        binding.setVariable(BR.repositoryViewModel, viewModel)
        viewModel.getUserList("temp")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
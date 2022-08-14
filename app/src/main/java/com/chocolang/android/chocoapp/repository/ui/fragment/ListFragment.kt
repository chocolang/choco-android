package com.chocolang.android.chocoapp.repository.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chocolang.android.chocoapp.databinding.FragmentListBinding
import com.chocolang.android.chocoapp.repository.result.GitRepositoryResult
import com.chocolang.android.chocoapp.viewmodel.RepositoryViewModel
import com.chocolang.android.chocoapp.BR.repositoryViewModel
import com.chocolang.android.chocoapp.ListAdapter
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.repository.ui.dialog.LoadingDialog

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : BaseListFragment() {
    private lateinit var binding: FragmentListBinding
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.lifecycleOwner = this
        /** view binding */
        //binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        val resultObserver = Observer<GitRepositoryResult> { result ->
            listAdapter.addAll(result.items)
        }
        val isLoadingObserver = Observer<Boolean> { isLoading ->
            loadingDialog?.run {
                if(isLoading) show() else dismiss()
            }
        }
        viewModel.repo = repo
        viewModel.repositoryItems.observe(viewLifecycleOwner, resultObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)

        listLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listAdapter = ListAdapter(requireContext())
        binding.rvList.apply {
            layoutManager = listLayoutManager
            adapter = listAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = listLayoutManager.itemCount
                    val lastVisibleItemPosition =
                        listLayoutManager.findLastCompletelyVisibleItemPosition()

                    if ((totalItemCount - 1) == lastVisibleItemPosition) {
                        viewModel.getRepositoryList("recycler")
                        //viewModel.getListByHttp("recycler")
                    }
                }
            })
        }
        binding.setVariable(repositoryViewModel, viewModel)
        viewModel.getRepositoryList("recycler")
        //viewModel.getListByHttp("recycler")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
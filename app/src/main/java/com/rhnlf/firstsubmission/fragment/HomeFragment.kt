package com.rhnlf.firstsubmission.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhnlf.firstsubmission.R
import com.rhnlf.firstsubmission.adapter.MainAdapter
import com.rhnlf.firstsubmission.data.Resource
import com.rhnlf.firstsubmission.data.remote.response.UserResponse
import com.rhnlf.firstsubmission.databinding.FragmentHomeBinding
import com.rhnlf.firstsubmission.helper.ViewModelFactory
import com.rhnlf.firstsubmission.view.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvGithub.layoutManager = layoutManager

        binding.searchView.apply {
            queryHint = context.getString(R.string.search_user)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.searchUser(query).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setListData(it.data)
                            }
                        }
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null || newText.isEmpty()) {
                        binding.rvGithub.visibility = View.INVISIBLE
                    }
                    return true
                }
            })
        }
    }

    private fun setListData(listGithub: List<UserResponse>) {
        val adapter = listGithub.let { MainAdapter(it) }
        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvGithub.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.apply {
            progressBar.visibility = if (isLoading == true) View.VISIBLE else View.INVISIBLE
            rvGithub.visibility = if (isLoading == true) View.INVISIBLE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
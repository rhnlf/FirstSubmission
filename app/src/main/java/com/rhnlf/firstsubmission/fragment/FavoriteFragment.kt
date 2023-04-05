package com.rhnlf.firstsubmission.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhnlf.firstsubmission.adapter.MainAdapter
import com.rhnlf.firstsubmission.data.remote.response.UserResponse
import com.rhnlf.firstsubmission.databinding.FragmentFavoriteBinding
import com.rhnlf.firstsubmission.helper.ViewModelFactory
import com.rhnlf.firstsubmission.view.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvFavorite.layoutManager = layoutManager

        favoriteViewModel.getAllChanges().observe(viewLifecycleOwner) {
            setListData(it)
        }
    }

    private fun setListData(listGithub: List<UserResponse>) {
        val adapter = MainAdapter(listGithub)
        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val toDetailFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
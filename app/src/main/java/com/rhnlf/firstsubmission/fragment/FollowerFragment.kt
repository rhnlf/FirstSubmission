package com.rhnlf.firstsubmission.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhnlf.firstsubmission.view.FollowerViewModel
import com.rhnlf.firstsubmission.adapter.MainAdapter
import com.rhnlf.firstsubmission.data.User
import com.rhnlf.firstsubmission.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowerViewModel>()

    private var username: String? = null

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager

        viewModel.getFollower(username)

        viewModel.followerList.observe(viewLifecycleOwner, { followerList ->
            binding.rvFollower.adapter = showFragmentRecycler(followerList)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun showFragmentRecycler(followerList: List<User>?): MainAdapter {
        val listFollow = ArrayList<User>()

        followerList?.let { listFollow.addAll(it) }
        return MainAdapter((listFollow))
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
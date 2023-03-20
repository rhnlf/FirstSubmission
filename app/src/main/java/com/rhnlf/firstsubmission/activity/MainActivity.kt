package com.rhnlf.firstsubmission.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhnlf.firstsubmission.view.MainViewModel
import com.rhnlf.firstsubmission.adapter.MainAdapter
import com.rhnlf.firstsubmission.data.User
import com.rhnlf.firstsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rvGithub.layoutManager = layoutManager
            rvGithub.setHasFixedSize(true)
        }

        mainViewModel.listGithub.observe(this, { listGithub ->
            setListData(listGithub)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Github User"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.apply {
                    clearUserList()
                    getGithub(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    private fun setListData(listGithub: List<User>) {
        val adapter = MainAdapter(listGithub)
        binding.rvGithub.adapter = adapter
    }
}
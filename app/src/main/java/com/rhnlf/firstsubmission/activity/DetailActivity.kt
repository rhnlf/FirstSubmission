@file:Suppress(
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused"
)

package com.rhnlf.firstsubmission.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rhnlf.firstsubmission.R
import com.rhnlf.firstsubmission.adapter.SectionPagerAdapter
import com.rhnlf.firstsubmission.data.Resource
import com.rhnlf.firstsubmission.data.remote.response.UserResponse
import com.rhnlf.firstsubmission.databinding.ActivityDetailBinding
import com.rhnlf.firstsubmission.helper.ViewModelFactory
import com.rhnlf.firstsubmission.view.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var username: String
    private var login: String = ""
    private var name: String? = ""

    private val args: DetailActivityArgs by navArgs()

    private var favStatus: Boolean = false
    private val factory = ViewModelFactory.getInstance(this)
    private val detailViewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)

        username = intent?.getStringExtra(EXTRA_USERNAME) ?: args.username

        detailViewModel.apply {
            getDetail(username).observe(this@DetailActivity) {
                when (it) {
                    is Resource.Success -> {
                        setDetailData(it.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                    is Resource.Loading -> showLoading(true)
                }
            }
        }
        setViewPager(username)

        binding.fabFav.setOnClickListener {
            detailViewModel.apply {
                lifecycleScope.launch(Dispatchers.IO) {
                    favStatus = isFavorite(username)
                }
                getDetail(username).observe(this@DetailActivity) {
                    when (it) {
                        is Resource.Success -> {
                            val user: UserResponse = it.data
                            when (favStatus) {
                                true -> {
                                    deleteFavUser(user)
                                    inactiveFavoriteButton()
                                    Toast.makeText(
                                        this@DetailActivity,
                                        getString(R.string.berhasil_dihapus),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                false -> {
                                    setFavUser(user)
                                    activeFavoriteButton()
                                    Toast.makeText(
                                        this@DetailActivity,
                                        getString(R.string.berhasil_ditambah),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            showLoading(false)
                        }
                        is Resource.Error -> {
                            showLoading(false)
                        }
                        is Resource.Loading -> showLoading(true)
                    }
                }
            }
        }
        setIconFav()
    }

    private fun setIconFav() {
        detailViewModel.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                favStatus = isFavorite(username)
            }
            getDetail(username).observe(this@DetailActivity) {
                when (it) {
                    is Resource.Success -> {
                        when (favStatus) {
                            true -> activeFavoriteButton()
                            false -> inactiveFavoriteButton()
                        }
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                    is Resource.Loading -> showLoading(true)
                }
            }
        }
    }

    private fun setDetailData(detail: UserResponse) {
        login = detail.login
        name = detail.name
        binding.apply {
            tvUsername.text = login
            tvName.text = name ?: ""
            tvFollower.text = detail.followers.toString()
            tvFollowing.text = detail.following.toString()
        }
        Glide.with(this@DetailActivity)
            .load(detail.avatar_url)
            .circleCrop()
            .into(binding.ivProfile)
    }

    private fun setViewPager(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun activeFavoriteButton() {
        binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite)
    }

    private fun inactiveFavoriteButton() {
        binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border)
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    companion object {
        private const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }
}
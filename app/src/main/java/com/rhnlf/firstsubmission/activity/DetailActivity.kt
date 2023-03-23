package com.rhnlf.firstsubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rhnlf.firstsubmission.view.DetailViewModel
import com.rhnlf.firstsubmission.R
import com.rhnlf.firstsubmission.adapter.SectionPagerAdapter
import com.rhnlf.firstsubmission.data.DetailUser
import com.rhnlf.firstsubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME)

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val item = intent.getStringExtra(EXTRA_USERNAME)
        if (item != null) {
            detailViewModel.getDetail(item)
        }

        detailViewModel.detail.observe(this) { detail ->
            setDetailData(detail)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailData(detail: DetailUser) {
        binding.apply {
            tvName.text = detail.name
            tvUsername.text = detail.login
            tvFollower.text = detail.followers.toString()
            tvFollowing.text = detail.following.toString()
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(ivProfile)
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }
}
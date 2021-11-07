package com.alfian.githubuserapp3.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.alfian.githubuserapp3.R
import com.alfian.githubuserapp3.adapter.SectionPagerAdapter
import com.alfian.githubuserapp3.database.FavoriteEntity
import com.alfian.githubuserapp3.databinding.ActivityDetailBinding
import com.alfian.githubuserapp3.datasource.UserResponse
import com.alfian.githubuserapp3.networking.NetworkConnection
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detailDataLayout.visibility = View.GONE
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        val user = intent.getParcelableExtra<UserResponse>(KEY_USER)
        val username = intent.getStringExtra(KEY_USERNAME)
        val favorite = FavoriteEntity()
        favorite.login = username
        favorite.id = intent.getIntExtra(KEY_ID, 0)
        favorite.avatar_url = user?.avatarUrl
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                showNoInternetAnimation(false)
                showFailedLoadData(false)
                val detailViewModel: DetailViewModel by viewModels {
                    DetailViewModelFactory(username.toString(), application)
                }
                detailViewModel.isLoading.observe(this@DetailActivity, {
                    showProgressBar(it)
                })
                detailViewModel.isNoInternet.observe(this@DetailActivity, {
                    showNoInternetAnimation(it)
                })
                detailViewModel.isDataFailed.observe(this@DetailActivity, {
                    showNoInternetAnimation(it)
                })
                detailViewModel.detailUser.observe(this@DetailActivity, { userResponse ->
                    if (userResponse != null) {
                        setData(userResponse)
                        setTabLayoutAdapter(userResponse)
                    }
                })
                detailViewModel.getFavoriteById(favorite.id!!)
                    .observe(this@DetailActivity, { listFav ->
                        isFavorite = listFav.isNotEmpty()

                        binding.detailFabFavorite.imageTintList = if (listFav.isEmpty()) {
                            ColorStateList.valueOf(Color.rgb(255, 255, 255))
                        } else {
                            ColorStateList.valueOf(Color.rgb(247, 106, 123))
                        }

                    })

                binding.detailFabFavorite.apply {
                    setOnClickListener {
                        if (isFavorite) {
                            detailViewModel.delete(favorite)
                            makeText(
                                this@DetailActivity,
                                "${favorite.login} telah dihapus dari data User Favorite ",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            detailViewModel.insert(favorite)
                            makeText(
                                this@DetailActivity,
                                "${favorite.login} telah ditambahkan ke data User Favorite",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            } else {
                binding.detailDataLayout.visibility = View.GONE
                binding.detailAnimationLayout.visibility = View.VISIBLE
                showFailedLoadData(true)
                showNoInternetAnimation(false)
            }
        })
    }

    private fun setTabLayoutAdapter(user: UserResponse) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        sectionPagerAdapter.model = user
        binding.detailViewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.detailTabs, binding.detailViewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setData(userResponse: UserResponse?) {
        if (userResponse != null) {
            with(binding) {
                detailDataLayout.visibility = View.VISIBLE
                detailImage.visibility = View.VISIBLE
                Glide.with(root)
                    .load(userResponse.avatarUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .circleCrop()
                    .into(binding.detailImage)
                detailName.visibility = View.VISIBLE
                detailUsername.visibility = View.VISIBLE
                detailName.text = userResponse.name
                detailUsername.text = userResponse.login
                if (userResponse.bio != null) {
                    detailBio.visibility = View.VISIBLE
                    detailBio.text = userResponse.bio
                } else {
                    detailBio.visibility = View.GONE
                }
                if (userResponse.company != null) {
                    detailCompany.visibility = View.VISIBLE
                    detailCompany.text = userResponse.company
                } else {
                    detailCompany.visibility = View.GONE
                }
                if (userResponse.location != null) {
                    detailLocation.visibility = View.VISIBLE
                    detailLocation.text = userResponse.location
                } else {
                    detailLocation.visibility = View.GONE
                }
                if (userResponse.blog != null) {
                    detailBlog.visibility = View.VISIBLE
                    detailBlog.text = userResponse.blog
                } else {
                    detailBlog.visibility = View.GONE
                }
                if (userResponse.followers != null) {
                    detailFollowersValue.visibility = View.VISIBLE
                    detailFollowersValue.text = userResponse.followers
                } else {
                    detailFollowersValue.visibility = View.GONE
                }
                if (userResponse.followers != null) {
                    detailFollowers.visibility = View.VISIBLE
                } else {
                    detailFollowers.visibility = View.GONE
                }
                if (userResponse.following != null) {
                    detailFollowingValue.visibility = View.VISIBLE
                    detailFollowingValue.text = userResponse.following
                } else {
                    detailFollowingValue.visibility = View.GONE
                }
                if (userResponse.following != null) {
                    detailFollowing.visibility = View.VISIBLE
                } else {
                    detailFollowing.visibility = View.GONE
                }
                if (userResponse.publicRepo != null) {
                    detailRepoValue.visibility = View.VISIBLE
                    detailRepoValue.text = userResponse.publicRepo
                } else {
                    detailRepoValue.visibility = View.GONE
                }
                if (userResponse.publicRepo != null) {
                    detailRepo.visibility = View.VISIBLE
                } else {
                    detailRepo.visibility = View.GONE
                }
            }
        } else {
            Log.i(TAG, "setData fun is error")
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.detailAnimLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoInternetAnimation(isNoInternet: Boolean) {
        binding.detailNoInternet.visibility = if (isNoInternet) View.VISIBLE else View.GONE
    }

    private fun showFailedLoadData(isFailed: Boolean) {
        binding.detailFailedDataLoad.visibility = if (isFailed) View.VISIBLE else View.GONE
        binding.tvFailed.visibility = if (isFailed) View.VISIBLE else View.GONE

    }

    companion object {
        const val KEY_USER = "user"
        private const val TAG = "DetailActivity"
        const val KEY_USERNAME = "username"
        const val KEY_ID = "extra id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}
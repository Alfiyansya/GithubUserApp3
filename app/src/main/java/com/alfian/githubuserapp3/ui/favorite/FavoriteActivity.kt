package com.alfian.githubuserapp3.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfian.githubuserapp3.adapter.FavoriteAdapter
import com.alfian.githubuserapp3.database.FavoriteEntity
import com.alfian.githubuserapp3.databinding.ActivityFavoriteBinding
import com.alfian.githubuserapp3.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        setUpList()
        setUserFavorite()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun setUpList() {
        with(binding) {
            val layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this?.rvFavorite?.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@FavoriteActivity, layoutManager.orientation)
            this?.rvFavorite?.addItemDecoration(itemDecoration)
            this?.rvFavorite?.adapter = adapter
            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(favEntity: FavoriteEntity) {
                    val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY_USERNAME, favEntity.login)
                    intent.putExtra(DetailActivity.KEY_ID, favEntity.id)
                    startActivity(intent)
                }
            })
        }
    }

    private fun setUserFavorite() {
        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this@FavoriteActivity, { favList ->
            if (favList !=null){
                adapter.setListFavorite(favList)
            }
            if (favList.isEmpty()){
                showNoDataSaved(true)
            }
            else{
                showNoDataSaved(false)

            }
        })
    }
    private fun showNoDataSaved(isNoData: Boolean) {
        binding?.favNoData?.visibility = if (isNoData) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
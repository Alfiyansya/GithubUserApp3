package com.alfian.githubuserapp3.ui.follows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfian.githubuserapp3.adapter.UserFollowsAdapter
import com.alfian.githubuserapp3.databinding.FragmentFollowsBinding
import com.alfian.githubuserapp3.datasource.UserResponse
import com.alfian.githubuserapp3.ui.detail.DetailActivity

class FollowsFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private val adapter: UserFollowsAdapter by lazy {
        UserFollowsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable<UserResponse>(ARG_PARCEL)

        if (index == 1) {
            if (user != null) {
                user.login?.let {
                    val mIndex = 1
                    setViewModel(it, mIndex)
                }
            }
        } else {
            if (user != null) {
                user.login?.let {
                    val mIndex = 2
                    setViewModel(it, mIndex)
                }
            }
        }
    }

    private fun setViewModel(username: String, index: Int) {

        val followsViewModel: FollowsViewModel by viewModels {
            FollowsViewModelFactory(username)

        }
        followsViewModel.isLoading.observe(viewLifecycleOwner, {
            showProgressBar(it)
        })
        followsViewModel.isDataFailed.observe(viewLifecycleOwner, {
            showFailedLoadData(it)
        })
        if (index == 1) {
            followsViewModel.followers.observe(viewLifecycleOwner, { follResponse ->
                follResponse?.let {
                    if (follResponse.isEmpty()) {
                        binding.animNoData.visibility = View.VISIBLE
                        makeText(context,"user ini belum di ikuti siapapun ( 0 followers )", LENGTH_SHORT).show()
                    }else{
                        binding.animNoData.visibility = View.GONE
                        adapter.addDataToList(follResponse)
                        setUserData(follResponse)
                    }
                }
            })
        } else {
            followsViewModel.following.observe(viewLifecycleOwner, { follResponse ->
                follResponse?.let{
                    if (follResponse.isEmpty()) {
                        binding.animNoData.visibility = View.VISIBLE
                        makeText(context,"User ini belum mengikuti siapapun ( 0 following )", LENGTH_SHORT).show()
                    }else{
                        binding.animNoData.visibility = View.GONE
                        adapter.addDataToList(follResponse)
                        setUserData(follResponse)

                    }
                }
            })

        }
    }

    private fun setUserData(userResponse: ArrayList<UserResponse>) {
        if (userResponse.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(view?.context)
            binding.rvFollows.layoutManager = layoutManager
            binding.rvFollows.adapter = adapter
            adapter.setOnItemClickCallback(object : UserFollowsAdapter.OnItemClickCallback {
                override fun onItemClicked(user: UserResponse) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY_USER, user)
                    intent.putExtra(DetailActivity.KEY_USERNAME, user.login)
                    intent.putExtra(DetailActivity.KEY_ID, user.id)
                    startActivity(intent)
                }
            })
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state)
            binding.animLoader.visibility = View.VISIBLE
        else
            binding.animLoader.visibility = View.GONE
    }

    private fun showFailedLoadData(isFailed: Boolean) {
        binding.animFailedDataLoad.visibility = if (isFailed) View.VISIBLE else View.GONE
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_PARCEL = "user_model"

        @JvmStatic
        fun newInstance(index: Int, userResponse: UserResponse?) =
            FollowsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putParcelable(ARG_PARCEL, userResponse)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
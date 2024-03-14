package com.project.group.rupp.dse.madquiz1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.group.rupp.dse.madquiz1.API.ApiService
import com.project.group.rupp.dse.madquiz1.Model.Main
import com.project.group.rupp.dse.madquiz1.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        getData()
    }


    private fun getData() {
        viewModel.loadMain()
        viewModel.mainUiState.observe(this) { main ->
            val userData = main.data
            userData?.let {
                binding.txtName.text = "${userData.firstName} ${userData.lastName}"
                binding.txtName2.text = "${userData.firstName} ${userData.lastName}"
                binding.txtBio.text = userData.bio
                binding.txtFriend.text = userData.friendCount.toString() // Convert to String
                binding.txtJob.text = userData.job
                binding.txtJobPlace.text = userData.workPlace
                binding.txtLove.text = userData.maritalStatus

                // Load profile image using Picasso
                Picasso.get().load(userData.profileImage).into(binding.picProfile)

                // Load cover image using Picasso
                Picasso.get().load(userData.coverImage).into(binding.picCover)

                showLoading(false)
            } ?: run {
                Toast.makeText(this@MainActivity, "Error: No data received", Toast.LENGTH_SHORT)
                    .show()
                showLoading(false)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

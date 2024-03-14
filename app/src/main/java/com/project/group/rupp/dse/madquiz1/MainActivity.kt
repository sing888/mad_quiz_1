package com.project.group.rupp.dse.madquiz1

import android.os.Bundle
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
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val main = retrofit.create<ApiService>()

        main.getMain().enqueue(object : Callback<Main> {
            override fun onResponse(call: Call<Main>, response: Response<Main>) {
                val userData = response.body()?.data
                userData?.let {
                    binding.txtName.text = "${userData.firstName} ${userData.lastName}"
                    binding.txtBio.text = userData.bio
                    binding.txtFriend.text = userData.friendCount.toString() // Convert to String
                    binding.txtJob.text = userData.job
                    binding.txtJobPlace.text = userData.workPlace
                    binding.txtLove.text = userData.maritalStatus

                    // Load profile image using Picasso
                    Picasso.get().load(userData.profileImage).into(binding.picProfile)

                    // Load cover image using Picasso
                    Picasso.get().load(userData.coverImage).into(binding.picCover)
                } ?: run {
                    Toast.makeText(this@MainActivity, "Error: No data received", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Main>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}

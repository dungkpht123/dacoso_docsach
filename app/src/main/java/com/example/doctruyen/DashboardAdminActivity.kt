package com.example.doctruyen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doctruyen.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityDashboardAdminBinding
//    firebase auth
    private  lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
//        handle click logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
//        handle click start add category page
        binding.addCategoryBtn.setOnClickListener {
            startActivity(Intent(this,CategoryAddActivity::class.java))

        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
//            not loggin in goto main screen
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            // logged in, get and show user info
            val email = firebaseUser.email
//            set to textview of toolbar
            binding.subTitleTv.text= email
        }
    }
}
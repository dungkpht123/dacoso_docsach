package com.example.doctruyen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.doctruyen.databinding.ActivityCategoryAddBinding
import com.example.doctruyen.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class CategoryAddActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityCategoryAddBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.submitBtn.setOnClickListener {
            validateData()
        }
    }
    private var category =""
    private fun validateData() {
//        get data
        category =binding.categoryEt.text.toString().trim()
//        validatedata
        if(category.isEmpty()){
            Toast.makeText(this,"Enter category ...",Toast.LENGTH_SHORT).show()
        } else{
            addCategoryFirebase()
        }
    }
//setup data to add in firebase db
    private fun addCategoryFirebase() {
        progressDialog.show()
        val timestamp = System.currentTimeMillis()
         val hashMap = HashMap<String,Any>()
        hashMap["category"]=category
        hashMap["timestamp"] = timestamp
        hashMap["uid"]="${firebaseAuth.uid}"
//    add to firebase db root->categories->categoryid> category info
    val ref = FirebaseDatabase.getInstance().getReference("Categories")
    ref.child("$timestamp")
        .setValue(hashMap)
        .addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(this,"Added successfully ...",Toast.LENGTH_SHORT).show()

        }
        .addOnFailureListener { e->
            progressDialog.dismiss()
            Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
}
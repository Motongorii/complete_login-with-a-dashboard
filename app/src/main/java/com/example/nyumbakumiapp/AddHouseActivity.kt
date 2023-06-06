package com.example.nyumbakumiapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddHouseActivity : AppCompatActivity() {
    lateinit var  edtHouseNumber:EditText
    lateinit var edtHouseSize:EditText
    lateinit var edtPrice:EditText
    lateinit var imageView:ImageView
    lateinit var btnUpload:Button
    lateinit var progress:ProgressDialog
    val PICK_IMAGE_REQUEST =100
    lateinit var filePath:Uri
    lateinit var firebaseStore:FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhoudeactivity)
        edtHouseNumber = findViewById(R.id.edthouseno)
        edtHouseSize = findViewById(R.id.edthousesize)
        edtPrice = findViewById(R.id.edtprice)
        imageView = findViewById(R.id.imgphoto)
        btnUpload = findViewById(R.id.btnupload)
        progress = ProgressDialog(this)
        progress.setTitle("Uploading")
        progress.setMessage("Please wait..")

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = firebaseStore.getReference()
        mAuth = FirebaseAuth.getInstance()
        // open the gallery to select an image

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select House Picture"),PICK_IMAGE_REQUEST)


    }
}
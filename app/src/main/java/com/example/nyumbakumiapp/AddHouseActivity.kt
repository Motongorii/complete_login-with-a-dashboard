package com.example.nyumbakumiapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

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

        btnUpload.setOnClickListener {
            var houseNumber = edtHouseNumber.text.toString().trim()
            var houseSize = edtHouseSize.text.toString().trim()
            var housePrice = edtPrice.text.toString().trim()
            var imageId = System.currentTimeMillis().toString()
            var userId = mAuth.currentUser?.uid

            // check if the user is submitting empty fields

            if (houseNumber.isEmpty()){
                edtHouseNumber.setError("Please Fill this input")
                edtHouseNumber.requestFocus()
            }else if (houseSize.isEmpty()){
                edtHouseSize.setError("Please fill this input")
                edtHouseSize.requestFocus()
            }else if (housePrice.isEmpty()){
                edtPrice.setError("Please fill this input")
                edtPrice.requestFocus()
            }else{
                // Proceed to upload data
                if (filePath == null){
                    Toast.makeText(applicationContext, "Choose image", Toast.LENGTH_SHORT).show()

                }else{
                    var ref = storageReference.child("Houses/$imageId")
                    progress.show()
                    ref.putFile(filePath).addOnCompleteListener{
                        progress.dismiss()
                        if(it.isSuccessful){
                            //proceed to store other data in the db
                            ref.downloadUrl.addOnSuccessListener {
                                var imageurl = it.toString()
                                var houseData = House(houseNumber,houseSize,
                                housePrice, userId!!, imageId, imageurl!!)
                                var dbRef = FirebaseDatabase.getInstance().getReference().child("House/$imageId")
                                dbRef.setValue(houseData)
                                Toast.makeText(applicationContext, "Upload Successful", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
        imageView.setOnClickListener {
            // open the gallery to select an image
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select House Picture"),PICK_IMAGE_REQUEST)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(resultCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
            if (data == null || data.data == null ){
                return
    }
        filePath = data?.data !!
       try {
           val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
           imageView.setImageBitmap(bitmap)

       }catch (e:IOException){
           e.printStackTrace()
       }
    }
}

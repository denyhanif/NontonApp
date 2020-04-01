package com.denyhanif.movie.sign.signup

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.app.usage.ExternalStorageStats
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denyhanif.movie.HomeActivity
import com.denyhanif.movie.R
import com.denyhanif.movie.utils.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.security.cert.CertPath
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {

    var REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean=false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReference:StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference();
        tv_hello.text = "Selamat Datang\n"+intent.getStringExtra("nama") // mengambil falue nama dari sign up activity bagian intent (put extra)

        iv_add.setOnClickListener {
            //validasi apakah user sudah perjah upload (default false seuai inisialisai)
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE
                iv_add.setImageResource(R.drawable.ic_add)
                iv_profile.setImageResource(R.drawable.user_pic)

            } else {
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    // permissiion memiliki 3 kelas interface yaitu(override fun onPermissionGranted,
//                    // onPermissionRationaleShouldBeShown,
//                    //override fun onPermissionDenied )
//                    .withListener(this)
//                    .check()
                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
            }
        }

        btn_home.setOnClickListener {

            finishAffinity()

            val intent = Intent(this@SignUpPhotoscreenActivity,
                HomeActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                //progres barr
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()
                // simpan gambar
                val ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoscreenActivity, "Uploaded", Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }

                        finishAffinity()
                        val intent = Intent(this@SignUpPhotoscreenActivity,
                            HomeActivity::class.java)
                        startActivity(intent)

                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoscreenActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
            }

        }

    }

    //fungsi jika permision di ijinkan
    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        //membukan fungsi bawan kamera
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }
        }
        //jika berhasil mengambil gambar maka langsung ke activityresult
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this,"Anda tidak dapat menambahkan foto",Toast.LENGTH_LONG).show()

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol Upload Nanti aja", Toast.LENGTH_LONG ).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//            //menampilkan gambar
//            filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(iv_profile)
//            //menamppilan icon save
//            btn_save.visibility = View.VISIBLE
//            iv_add.setImageResource(R.drawable.ic_delete_forever_black_24dp)
//        }
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        statusAdd=true
        //menampilkan gambar
        filePath = data?.data!!
        Glide.with(this)
            .load(filePath)
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)
        //Log.v("tamca","file uri upload+filePath")
        //menamppilan icon save
        btn_save.visibility = View.VISIBLE
        iv_add.setImageResource(R.drawable.ic_delete_forever_black_24dp)
    } else if(resultCode == ImagePicker.REQUEST_CODE){
        Toast.makeText(this,ImagePicker.getError(data),Toast.LENGTH_LONG).show()
    }else{
        Toast.makeText(this,"Taxk canceled",Toast.LENGTH_LONG).show()

    }
}
}

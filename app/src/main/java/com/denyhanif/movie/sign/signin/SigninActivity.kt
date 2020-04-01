package com.denyhanif.movie.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denyhanif.movie.HomeActivity
import com.denyhanif.movie.R
import com.denyhanif.movie.sign.signup.SignUpActivity
import com.denyhanif.movie.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SigninActivity : AppCompatActivity() {
    lateinit var iUsername: String
    lateinit var iPassword: String

    //koneksi ke firebase
    lateinit var mDatabase:DatabaseReference
    //pref dt local
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Write a message to the database
        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference("User")//inisialisasi mDatabase
        preferences = Preferences(this)

        //inisialisasi pref
        preferences.setValues("onboarding","1")
        if(preferences.getValues("status").equals("1")){
            finishAffinity()
            val  intent = Intent( this@SigninActivity,HomeActivity::class .java)
            startActivity(intent)
        }

       btn_home.setOnClickListener {
           iUsername= et_username.text.toString()
           iPassword= et_password.text.toString()

           if (iUsername.equals("")){
               et_username.error= "SIlajkan Tulis Nama Anda"
               et_username.requestFocus()
           }else if(iPassword.equals("")){
               et_password.error="Silahan masukkan passwprd anda"
               et_password.requestFocus()
           }else{
               pushLogin(iUsername,iPassword)
           }
       }

       btn_daftar.setOnClickListener {
           val intent= Intent( this@SigninActivity,
               SignUpActivity::class.java)
           startActivity(intent)
       }
    }

    private fun pushLogin(iUsername:String,iPassword:String){
    mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val user = dataSnapshot.getValue(User::class.java)
            if(user==null){
                Toast.makeText(this@SigninActivity,"User tdak ditemukan", Toast.LENGTH_LONG).show()
                finishAffinity()
                val intent = Intent(this@SigninActivity,HomeActivity::class.java)
                startActivity(intent)
            }else{
                if (user.password.equals(iPassword)){
                    Toast.makeText(this@SigninActivity,"Selamat Datang",Toast.LENGTH_LONG).show()
                    //data dari firebase
                    preferences.setValues("nama",user.nama.toString())
                    preferences.setValues("user",user.username.toString())
                    preferences.setValues("url",user.url.toString())
                    preferences.setValues("email",user.email.toString())
                    preferences.setValues("saldo",user.saldo.toString())
                    //status yang di set setealh login
                    preferences.setValues("status","1")
                    finishAffinity()

                    val intent = Intent(this@SigninActivity,HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@SigninActivity,"Pasword Anda salah",Toast.LENGTH_LONG).show()
                    }
                }
        }
        override fun onCancelled(error: DatabaseError) {
        Toast.makeText(this@SigninActivity,""+error.message, Toast.LENGTH_LONG).show()
        }


    })
    }


}

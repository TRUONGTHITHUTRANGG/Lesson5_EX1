package com.gmail.lesson5_exercise1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {
    val IMG_PICK_CODE = 100
    var imgURI: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        if(intent != null){
            val username = intent.getStringExtra("usernameValue")
            findViewById<TextView>(R.id.usernameTV).text = username
        }
        findViewById<Button>(R.id.backBtn).setOnClickListener{
            val intent = Intent()
            if(imgURI != null)
                intent.putExtra("imgURIValue",imgURI.toString())
            else
                intent.putExtra("imgURIValue","")
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        findViewById<Button>(R.id.sendEmailBtn).setOnClickListener{
            val mailIntent = Intent(Intent.ACTION_SEND)
            mailIntent.data = Uri.parse("mailto:")
            mailIntent.type = "text/plain"
            mailIntent.putExtra(Intent.EXTRA_SUBJECT,"Hello from Android class")
            mailIntent.putExtra(Intent.EXTRA_TEXT,"This is the body of the email")
            mailIntent.putExtra(Intent.EXTRA_EMAIL,
                arrayOf("nhhai@vku.udn.vn","haispdn@gmail.com"))
            startActivity(Intent.createChooser(mailIntent,"Send mail via..."))
        }
        findViewById<Button>(R.id.chooseLogoBtn).setOnClickListener{
            val imgIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(imgIntent,IMG_PICK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMG_PICK_CODE && resultCode == Activity.RESULT_OK){
            imgURI = data?.data
            findViewById<ImageView>(R.id.profileLogoIV).setImageURI(imgURI)
        }
    }
}
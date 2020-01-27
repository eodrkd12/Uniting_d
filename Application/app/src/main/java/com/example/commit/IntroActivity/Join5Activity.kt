package com.example.commit.IntroActivity

//프사 설정은 나중에 ..

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.ChatActivity
import com.example.commit.R
import com.example.commit.Singleton.VolleyService
import kotlinx.android.synthetic.main.activity_join5.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class Join5Activity : AppCompatActivity() {

    final val PICK_FROM_CAMERA=0
    final val PICK_FROM_ALBUM=1
    final val CROP_FROM_IMAGE=2

    var imageCaptureUri:Uri?=null
    var absolutePath:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join5)

        var getIntent=intent

        var id=getIntent.getStringExtra("id")
        var pw=getIntent.getStringExtra("pw")
        var name=getIntent.getStringExtra("name")
        var birthday=getIntent.getStringExtra("birthday")
        var gender=getIntent.getStringExtra("gender")
        var nickname=getIntent.getStringExtra("nickname")
        var webMail=getIntent.getStringExtra("univ_mail")
        var universityName=getIntent.getStringExtra("univ_name")
        var departmentName=getIntent.getStringExtra("dept_name")
        var enterYear=getIntent.getStringExtra("enter_year")

        image_profile.setOnClickListener {
            val builder =
                AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle("프로필 사진 선택")

            builder.setPositiveButton("사진 촬영") { _, _ ->
                var cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var url="tmp_${System.currentTimeMillis()}.jpg"

                var file=File(Environment.getExternalStorageDirectory(),url)
                imageCaptureUri=FileProvider.getUriForFile(this,"com.example.commit.Uniting.provider",file)
                startActivityForResult(cameraIntent,PICK_FROM_CAMERA)
            }
            builder.setNeutralButton("취소"){_,_->

            }
            builder.setNegativeButton("앨범에서 선택") { _, _ ->
                var albumIntent=Intent(Intent.ACTION_PICK)
                albumIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE)
                startActivityForResult(albumIntent,PICK_FROM_ALBUM)
            }
            builder.show()
        }

        btn_complete.setOnClickListener {
            VolleyService.joinReq(id,pw,name,birthday,gender,nickname,webMail,universityName,departmentName,enterYear,this,{success->
                if(success.equals("success")) {
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            PICK_FROM_CAMERA -> {
                var intent=Intent("com.android.camera.action.CROP")
                intent.setDataAndType(imageCaptureUri,"image/")

                intent.putExtra("outputX",200)
                intent.putExtra("outputY",200)
                intent.putExtra("aspectX",1)
                intent.putExtra("aspectY",2)
                intent.putExtra("scale",true)
                intent.putExtra("return-data",true)
                startActivityForResult(intent, CROP_FROM_IMAGE)
            }
            PICK_FROM_ALBUM -> {
                imageCaptureUri=data!!.data
            }
            CROP_FROM_IMAGE -> {
                var extras=data!!.getExtras()

                var filePath="${Environment.getExternalStorageDirectory().absolutePath}/Uniting/System.currentTimeMillis().jpg"

                if(extras!=null){
                    var photo=extras.getParcelable<Bitmap>("data")
                    image_profile.setImageBitmap(photo)

                    storeCropImage(photo,filePath)
                    absolutePath=filePath
                }

                var file=File(imageCaptureUri!!.path)
                if(file.exists()){
                    file.delete()
                }
            }
        }
    }

    fun storeCropImage(bitmap:Bitmap, filePath:String):Unit{
        var dirPath="${Environment.getExternalStorageDirectory().absolutePath}/Uniting"
        var dirUniting=File(dirPath)
        if(!dirUniting.exists()){
            dirUniting.mkdir()
        }

        var copyFile=File(filePath)
        var out:BufferedOutputStream?=null

        try{
            copyFile.createNewFile()
            out= BufferedOutputStream(FileOutputStream(copyFile))
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out)

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)))

            out.flush()
            out.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}

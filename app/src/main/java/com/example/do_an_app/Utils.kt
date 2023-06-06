package com.example.do_an_app

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


object Utils {
    @JvmStatic
    @BindingAdapter("setAvt")
    open fun setAvt(iv: ImageView, link:String?){
        if (link == null) {
            return
        }
        Glide.with(iv.context)
            .load(Const.BASE_URL+link.replace("\\","/"))
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher)
            .into(iv)
    }
//    @JvmStatic
//    @BindingAdapter("setImagePost")
//    open fun setImgPost(iv: ImageView, link:ArrayList<String>?){
//        if (link == null) {
//            return
//        }
//        Glide.with(iv.context)
//            .load(Const.BASE_URL+link.replace("\\","/"))
//            .placeholder(R.mipmap.ic_launcher_round)
//            .error(R.mipmap.ic_launcher)
//            .into(iv)
//    }

    @JvmStatic
    @BindingAdapter("setCoverImage")
    open fun setCoverImage(iv: ImageView, link:String?){
        if (link == null) {
            return
        }
        Glide.with(iv.context)
            .load(Const.BASE_URL+link.replace("\\","/"))
            .apply(bitmapTransform( BlurTransformation(22)))
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher)
            .into(iv)
    }


    @JvmStatic
    @BindingAdapter("setText")
    fun setText(tv: TextView, string: String?) {
        if (string == null) {
            tv.text = ""
        } else {
            tv.text = string
        }
    }

    @JvmStatic
    @BindingAdapter("setGender")
    fun setGender(tv: TextView, string: String?) {
        if (string == null) {
            tv.text = ""
        } else {
            if(string == "FEMALE"){
                tv.text = "Nữ"
            }
            else{
                tv.text = "Nam"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setInt")
    fun setInt(tv: TextView, int: Int?) {
        if (int == null) {
            tv.text = ""
        } else {
            tv.text = int.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("setTextGroups")
    fun setTextGroups(tv: TextView, string: String?) {
        if (string == null) {
            tv.text = "Thể loại: Tất cả"
        } else {
            tv.text =  "Thể loại:" + string
        }
    }

    @JvmStatic
    @BindingAdapter("setDate")
    fun setDate(tv: TextView, string: Date?) {
        if (string == null) {
            tv.text = ""
        } else {
            tv.text = string.toString()
        }
    }


    @JvmStatic
    @BindingAdapter("setEditText")
    fun setEditText(edt: EditText, string: String?) {
        if (string == null) {
            edt.setText("")
        } else {
            edt.setText(string)
        }
    }

    fun View.visible(isShow: Boolean){
        visibility= if(isShow) View.VISIBLE else View.GONE
    }

}
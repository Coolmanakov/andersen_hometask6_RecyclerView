package com.example.andersen_hometask6_recyclerview_contactapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ContactGenerator() {
    companion object {
        private const val IMAGES_URL = "https://picsum.photos/id/"

    }

    fun generateContactList(): ArrayList<Contact> {
        var tmpList = ArrayList<Contact>()
        for (i in 0 until 120) {
            tmpList.add(
                Contact(
                    "Иван$i", "Иванов$i",
                    "8 (911) $i$i$i $i$i $i$i", "$i"
                )
            )
            var tmpNumber = tmpList[i].phoneNumber
            if (tmpNumber.length > 19) {
                var tmStr = tmpNumber.substring(0, 19)
                tmpList[i].also {
                    it.phoneNumber = tmStr
                }
            }
        }
        return tmpList
    }

    fun getContactImage(context : Context, photoId: String, imageView: ImageView) {
        Glide.with(context)
            .load("$IMAGES_URL$photoId/100/100")
            .into(imageView)
    }


}
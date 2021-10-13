package com.example.andersen_hometask6_recyclerview_contactapp

import androidx.recyclerview.widget.DiffUtil

class ContactDiffUtilCallBack(oldList: ArrayList<Contact>, newList: ArrayList<Contact>): DiffUtil.Callback() {
    private val oldList = oldList
    private val newList = newList

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        var oldContact = oldList.get(oldItemPosition)
        var newContact = newList.get(newItemPosition)
        return oldContact.equals(newContact)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        var oldContact = oldList.get(oldItemPosition)
        var newContact = newList.get(newItemPosition)
        return oldContact.equals(newContact)
    }
}
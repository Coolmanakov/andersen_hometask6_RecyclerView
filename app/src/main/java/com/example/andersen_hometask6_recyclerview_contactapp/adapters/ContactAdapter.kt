package com.example.andersen_hometask6_recyclerview_contactapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_hometask6_recyclerview_contactapp.Contact
import com.example.andersen_hometask6_recyclerview_contactapp.ContactGenerator
import com.example.andersen_hometask6_recyclerview_contactapp.MainActivity
import com.example.andersen_hometask6_recyclerview_contactapp.MainActivity.Companion.itemClickListener
import com.example.andersen_hometask6_recyclerview_contactapp.R

class ContactAdapter(var context : Context) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    lateinit var contacts: ArrayList<Contact>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts.get(position))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ViewHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {
        private val nameHolder = view.findViewById<TextView>(R.id.name)
        private val surnameHolder = view.findViewById<TextView>(R.id.surname)
        private val phoneNumberHolder = view.findViewById<TextView>(R.id.phoneNumber)
        private val photo = view.findViewById<ImageView>(R.id.photo)

        fun bind(contact: Contact) {

            nameHolder.text = contact.name
            surnameHolder.text = contact.surname
            phoneNumberHolder.text = contact.phoneNumber

            ContactGenerator().getContactImage(context, contact.photoId, photo)

            view.setOnClickListener {
                itemClickListener.showDetails(
                    contact.name,
                    contact.surname,
                    contact.phoneNumber,
                    adapterPosition
                )
            }
            view.setOnLongClickListener {
                MainActivity.itemLongClickListener.deleteContact(adapterPosition)
            }
        }

    }
}
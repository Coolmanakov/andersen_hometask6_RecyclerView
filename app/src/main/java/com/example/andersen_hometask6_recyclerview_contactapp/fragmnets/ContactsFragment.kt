package com.example.andersen_hometask6_recyclerview_contactapp.fragmnets

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_hometask6_recyclerview_contactapp.Contact
import com.example.andersen_hometask6_recyclerview_contactapp.ContactDiffUtilCallBack
import com.example.andersen_hometask6_recyclerview_contactapp.MainActivity
import com.example.andersen_hometask6_recyclerview_contactapp.R
import com.example.andersen_hometask6_recyclerview_contactapp.adapters.ContactAdapter

class ContactsFragment : Fragment(R.layout.fragment_contact_recycle) {



    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    companion object {
        const val CONTACTS_FRAGMENT_TAG = "CONTACTS_FRAGMENT"

        fun newInstance() = ContactsFragment()



        @SuppressLint("StaticFieldLeak")
        private lateinit var adapter : ContactAdapter


        fun findDiff(oldList: ArrayList<Contact>, newList: ArrayList<Contact>){
            var contactDiffUtilCallBack = ContactDiffUtilCallBack(oldList, newList)
            var contactDiffResult = DiffUtil.calculateDiff(contactDiffUtilCallBack)
            adapter.contacts = newList
            contactDiffResult.dispatchUpdatesTo(adapter)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactAdapter(requireContext())

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //Called when the user submits the query
            override fun onQueryTextSubmit(newString: String?): Boolean {
                MainActivity.newContactList = MainActivity.oldContactList.filter {
                    it.name.contains(newString!!, true) ||
                            it.surname.contains(newString, true)
                } as ArrayList<Contact>
                findDiff(MainActivity.oldContactList, MainActivity.newContactList)

                return false
            }

            //Called when the query text is changed by the user.
            override fun onQueryTextChange(newString: String?): Boolean {
                if(newString == ""){
                    findDiff(MainActivity.oldContactList, MainActivity.newContactList)
                }
                return false
            }

        })
        recyclerView = view.findViewById(R.id.contact_recycler)


        if (arguments != null) {
            var pos = arguments?.getInt(DetailFragment.POS_EXTRA)!!
            var tmp = MainActivity.oldContactList.get(pos)

            var contact = Contact(
                arguments?.get(DetailFragment.NAME_EXTRA).toString(),
                arguments?.get(DetailFragment.SURNAME_EXTRA).toString(),
                arguments?.get(DetailFragment.PHONE_NUMBER_EXTRA).toString(),
                tmp.photoId
            )

            MainActivity.newContactList.removeAt(pos)
            MainActivity.newContactList.add(pos, contact)

            findDiff(MainActivity.oldContactList, MainActivity.newContactList)

        }
        findDiff(MainActivity.oldContactList, MainActivity.newContactList)

        recyclerView.adapter = adapter

    }

}

package com.example.andersen_hometask6_recyclerview_contactapp

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.example.andersen_hometask6_recyclerview_contactapp.fragmnets.ContactsFragment
import com.example.andersen_hometask6_recyclerview_contactapp.fragmnets.DetailFragment
import com.example.andersen_hometask6_recyclerview_contactapp.interfaces.LongClickListener
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(R.layout.activity_main), ContactDetailContract {

    //programmatically track the tablet mode
    private var isDualMode = false

    companion object {
        //the listener implements ContactDetailContract,
        // responsible for displaying and saving new data
        lateinit var itemClickListener: ContactDetailContract
        private const val NOT_INITIALIZED_POS = -10000
        var newContactList =  ContactGenerator().generateContactList()
        val oldContactList by lazy { ContactGenerator().generateContactList()}

        lateinit var itemLongClickListener: LongClickListener

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemLongClickListener = object: LongClickListener {
            override fun deleteContact(pos: Int): Boolean {
                showDeleteDialog(pos)
                return true
            }

        }
        itemClickListener = this

        val view = findViewById<FrameLayout>(R.id.contactFrame)
        if (view != null) {
            isDualMode = true
            dualModeTransaction()
        } else singleModeTransaction()

    }

    override fun onBackPressed() {
        //if current fragment is ContactsFragment -> close app
        // else -> popBackStack
        val contactFragment =
            supportFragmentManager.findFragmentByTag(ContactsFragment.CONTACTS_FRAGMENT_TAG)
        if (contactFragment != null && contactFragment.isVisible) {
            this.finishAffinity()
        }
        supportFragmentManager.popBackStack()

    }

    /**
     * start smartphone mode
     */
    private fun singleModeTransaction() {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactsFragment.newInstance()
            replace(R.id.mainFrame, contactFragment, ContactsFragment.CONTACTS_FRAGMENT_TAG)
            commit()
        }

    }

    /**
     * start tablet mode
     */
    private fun dualModeTransaction() {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactsFragment.newInstance()
            val detailFragment = DetailFragment.newInstance("", "", "", NOT_INITIALIZED_POS)
            setReorderingAllowed(true)
            replace(R.id.contactFrame, contactFragment, ContactsFragment.CONTACTS_FRAGMENT_TAG)
            replace(R.id.detailFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            commit()
        }

    }

    override fun showDetails(name: String, surname: String, phoneNumber: String, pos: Int) {
        supportFragmentManager.beginTransaction().run {
            val detailFragment = DetailFragment.newInstance(name, surname, phoneNumber, pos)
            if (isDualMode) {
                replace(R.id.detailFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            } else {
                setReorderingAllowed(true)
                addToBackStack("detailFragment")
                replace(R.id.mainFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            }
            commit()
        }
    }

    override fun saveDetails(name: String, surname: String, phoneNumber: String, pos: Int) {
        if(pos != NOT_INITIALIZED_POS) {
            if (isDualMode) {
                supportFragmentManager.beginTransaction().run {
                    val contactFragment = ContactsFragment.newInstance()
                    contactFragment.arguments = Bundle().apply {
                        putString(DetailFragment.NAME_EXTRA, name)
                        putString(DetailFragment.SURNAME_EXTRA, surname)
                        putString(DetailFragment.PHONE_NUMBER_EXTRA, phoneNumber)
                        putInt(DetailFragment.POS_EXTRA, pos)
                    }
                    replace(
                        R.id.contactFrame,
                        contactFragment,
                        ContactsFragment.CONTACTS_FRAGMENT_TAG
                    )
                    commit()
                }
            } else {
                supportFragmentManager
                    .findFragmentByTag(ContactsFragment.CONTACTS_FRAGMENT_TAG)?.apply {
                        arguments = Bundle().apply {
                            putString(DetailFragment.NAME_EXTRA, name)
                            putString(DetailFragment.SURNAME_EXTRA, surname)
                            putString(DetailFragment.PHONE_NUMBER_EXTRA, phoneNumber)
                            putInt(DetailFragment.POS_EXTRA, pos)
                        }
                    }
            }
        }
    }

    fun showDeleteDialog(pos : Int){
        val dialogFragment = DeleteContactDF(pos)
        val manager = supportFragmentManager
        dialogFragment.show(manager, "deleteDialogFragment")
    }

    class DeleteContactDF(var pos : Int): DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let{
                val builder = AlertDialog.Builder(it)
                builder.setTitle(R.string.delete_contact_title)
                    .setPositiveButton("Да") { _, _ ->
                        newContactList.removeAt(pos)
                        ContactsFragment.findDiff(oldContactList, newContactList)
                        oldContactList.removeAt(pos)
                    }
                    .setNegativeButton("Нет") { dialog, id ->
                        dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

}


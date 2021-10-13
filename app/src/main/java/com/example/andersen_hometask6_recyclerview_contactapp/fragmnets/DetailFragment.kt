package com.example.andersen_hometask6_recyclerview_contactapp.fragmnets

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.andersen_hometask6_recyclerview_contactapp.MainActivity
import com.example.andersen_hometask6_recyclerview_contactapp.R

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val name: String by lazy { requireArguments().getString(NAME_EXTRA, "") }
    private val surname by lazy { requireArguments().getString(SURNAME_EXTRA, "") }
    private val phoneNumber by lazy { requireArguments().getString(PHONE_NUMBER_EXTRA, "") }
    private val posLinearLayout by lazy { requireArguments().getInt(POS_EXTRA, 0) }

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneNumberEditText: EditText

    companion object {
        const val DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT"
        const val NAME_EXTRA = "NAME"
        const val SURNAME_EXTRA = "SURNAME"
        const val PHONE_NUMBER_EXTRA = "PHONE_NUMBER"
        const val POS_EXTRA = "CURRENT_POS"

        fun newInstance(name: String?, surname: String?, phoneNumber: String?, pos: Int) =
            DetailFragment().also {
                it.arguments = Bundle().apply {
                    putString(NAME_EXTRA, name)
                    putString(SURNAME_EXTRA, surname)
                    putString(PHONE_NUMBER_EXTRA, phoneNumber)
                    putInt(POS_EXTRA, pos)
                }

            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.name)
        nameEditText.setText(name)

        surnameEditText = view.findViewById(R.id.surname)
        surnameEditText.setText(surname)

        phoneNumberEditText = view.findViewById(R.id.phoneNumber)
        phoneNumberEditText.setText(phoneNumber)

        view.findViewById<Button>(R.id.save).setOnClickListener {
            MainActivity.itemClickListener.saveDetails(
                nameEditText.text.toString(),
                surnameEditText.text.toString(),
                phoneNumberEditText.text.toString(),
                posLinearLayout
            )
        }
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

    }

}


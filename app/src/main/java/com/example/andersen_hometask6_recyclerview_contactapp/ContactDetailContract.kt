package com.example.andersen_hometask6_recyclerview_contactapp

interface ContactDetailContract {

    fun showDetails(name: String, surname: String, phoneNumber: String, pos : Int)

    fun saveDetails(name: String, surname: String, phoneNumber: String, pos : Int)

}
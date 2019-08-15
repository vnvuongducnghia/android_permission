package com.example.permission_helper.ui.demo_recycler_view

import java.util.*

class Contact(val name: String, val isOnline: Boolean) {
    var firstItemInSection: Boolean = false

    companion object {

        private var lastContactId = 0
        private val contacts = ArrayList<Contact>()

        fun createContactsList(numContacts: Int): ArrayList<Contact> {
            for (i in 0 until numContacts) {
                contacts.add(Contact("Person $i", i <= numContacts / 2))
            }

            return contacts
        }

        fun addOneContact(numContacts: Int):  Contact  {
            return    Contact("Add One $numContacts",false)
        }
    }
}

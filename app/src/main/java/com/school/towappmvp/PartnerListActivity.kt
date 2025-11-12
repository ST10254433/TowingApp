package com.school.towappmvp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class PartnerListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dbRef: DatabaseReference
    private lateinit var partnerList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_list)

        listView = findViewById(R.id.listViewPartners)
        dbRef = FirebaseDatabase.getInstance().getReference("partners")
        partnerList = mutableListOf()

        dbRef.get().addOnSuccessListener {
            for (snap in it.children) {
                partnerList.add(snap.child("name").value.toString())
            }
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, partnerList)
        }
    }
}

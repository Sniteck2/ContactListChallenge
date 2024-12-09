package com.example.contactlistexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textName = findViewById<EditText>(R.id.editTextName)
        val textPhone = findViewById<EditText>(R.id.editTextPhone)
        val available = findViewById<CheckBox>(R.id.cbAvailable)
        val save = findViewById<Button>(R.id.buttonSave)
        val filter = findViewById<Button>(R.id.buttonFilter)
        val textViewGreeting = findViewById<TextView>(R.id.textViewAlert)

        save.setOnClickListener{
            val name = textName.text.toString()
            val phone = textPhone.text.toString()
            if(name.isBlank() || phone.isBlank()) {
                textViewGreeting.text = "Por favor, rellene todos los campos"
                textViewGreeting.visibility = TextView.VISIBLE
            } else {
                setRecyclerViewAdapter(addContact(name, phone, available.isChecked))
                textViewGreeting.visibility = TextView.GONE
            }
        }

        filter.setOnClickListener{
            if(contactList.size > 0) {
                adapter.updateContacts(contactList.filter { c -> c.isAvailable })
                textViewGreeting.visibility = TextView.GONE
            } else {
                textViewGreeting.text = "La lista esta vacia"
                textViewGreeting.visibility = TextView.VISIBLE
            }
        }



    }

    private fun addContact(name: String, phone: String, available: Boolean): List<Contact> {
        val contact = Contact(name, phone, available)
        contactList.add(contact)
        return contactList
    }

    // RecyclerView
    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}
package com.example.phonebook

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity2 : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: PersonAdapter
    private val people = mutableListOf<Person>()
    private val PICK_IMAGE = 1

    private var tempName: String = ""
    private var tempPhone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        adapter = PersonAdapter(this, people)
        listView.adapter = adapter

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)

        val nameInput = EditText(this)
        nameInput.hint = "Enter Name"
        layout.addView(nameInput)

        val phoneInput = EditText(this)
        phoneInput.hint = "Enter Phone"
        phoneInput.inputType = android.text.InputType.TYPE_CLASS_PHONE
        layout.addView(phoneInput)

        AlertDialog.Builder(this)
            .setTitle("New Contact")
            .setView(layout)
            .setPositiveButton("Select Image") { _, _ ->
                tempName = nameInput.text.toString()
                tempPhone = phoneInput.text.toString()

                if (TextUtils.isEmpty(tempName) || TextUtils.isEmpty(tempPhone)) {
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, PICK_IMAGE)
            }
            .setNegativeButton("Cancel", null)
            .setNeutralButton("Save Without Image") { _, _ ->
                tempName = nameInput.text.toString()
                tempPhone = phoneInput.text.toString()

                if (TextUtils.isEmpty(tempName) || TextUtils.isEmpty(tempPhone)) {
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    return@setNeutralButton
                }

                people.add(Person(tempName, tempPhone, null))
                adapter.notifyDataSetChanged()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            people.add(Person(tempName, tempPhone, uri))
            adapter.notifyDataSetChanged()
        }
    }
}

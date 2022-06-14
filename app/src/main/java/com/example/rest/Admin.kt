package com.example.rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase


class Admin : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val order=findViewById<Button>(R.id.button)
        val edit=findViewById<Button>(R.id.button2)
        val add=findViewById<Button>(R.id.button3)
        val text=findViewById<EditText>(R.id.editTextTextPersonName2)
        val spinner=findViewById<Spinner>(R.id.spinner)
        val day= arrayOf<String>("Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday")
        order.setOnClickListener{startActivity(Intent(this,Food::class.java))} //if admin press order,go to menu
        edit.setOnClickListener{text.visibility=View.VISIBLE
            spinner.visibility=View.VISIBLE}         //when admin press edit,show input

        val myAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,day)
        spinner.adapter=myAdapter
        spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                add.visibility=View.VISIBLE
                add.setOnClickListener{
                    val text = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
                    val database = FirebaseDatabase.getInstance()
                    val ref=database.getReference("Day")
                    ref.child(day.elementAt(p2)).child("3").setValue(text)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        text.addTextChangedListener(this)


    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val add=findViewById<Button>(R.id.button3)
        val text=findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
        add.isEnabled=text.trim().isNotEmpty()
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}
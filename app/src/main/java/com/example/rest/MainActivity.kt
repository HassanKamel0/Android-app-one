package com.example.rest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.*


class MainActivity : AppCompatActivity(),TextWatcher {
    private fun toast(msg:String){Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name=findViewById<EditText>(R.id.editTextTextPersonName)                 //get IDs
        val password=findViewById<EditText>(R.id.editTextTextPassword)
        val log=findViewById<Button>(R.id.log)
        val signup=findViewById<Button>(R.id.signup)

        name.addTextChangedListener(this)                                  //watch inputs
        password.addTextChangedListener(this)

        val sharedPreferences=getSharedPreferences("data",Context.MODE_PRIVATE)
        name.setText(sharedPreferences.getString("u",""))                           //load data
        password.setText(sharedPreferences.getString("p",""))

        val auth=FirebaseAuth.getInstance()
        log.setOnClickListener{
            val sharedPreferences= getSharedPreferences("data",Context.MODE_PRIVATE)
            val editor= sharedPreferences.edit()
            editor.putString("u",name.text.toString())
            editor.putString("p",password.text.toString())
            editor.apply()                                                   //apply new input and login
            if (name.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(name.text.toString(),password.text.toString())
                    .addOnCompleteListener()
                    {
                        if (it.isSuccessful)
                        {toast("Login Successful")
                            val database = FirebaseDatabase.getInstance()
                            val ref=database.getReference("Admin")
                            ref.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var f=0
                                    for (item in snapshot.children)
                                        if(item.child("email").value.toString()==name.text.toString())
                                        {f=1
                                            break}
                                        if (f==1)
                                            startActivity(Intent(this@MainActivity,Admin::class.java))
                                        else
                                            startActivity(Intent(this@MainActivity,Food::class.java))
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }

                            })

                        }
                        else
                            toast(it.exception.toString())
                    }

            }
            else
            {toast("Empty")}
        }


        signup.setOnClickListener(){
            if (name.text.toString().isNotEmpty() && password.text.toString().isNotEmpty())
            {
                auth.createUserWithEmailAndPassword(name.text.toString(),password.text.toString())
                    .addOnCompleteListener()
                    {
                        if (it.isSuccessful)
                            toast("SignUP Successful")
                        else
                            toast(it.exception.toString())
                    }
            }
            else
            {toast("Empty")}
        }


    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val name =findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val password=findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        val login=findViewById<Button>(R.id.log)
        val signup=findViewById<Button>(R.id.signup)
        login.isEnabled=name.trim().isNotEmpty() && password.trim().isNotEmpty()
        signup.isEnabled=name.trim().isNotEmpty() && password.trim().isNotEmpty()
    }

    override fun afterTextChanged(p0: Editable?) {

    }

}
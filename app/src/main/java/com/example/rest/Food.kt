package com.example.rest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Food : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)


        val spinner = findViewById<Spinner>(R.id.spinner)
        val day = arrayOf<String>(
            "Saturday",
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday"
        )

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, day)
        spinner.adapter = myAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Day")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        todayfood.clear()
                        for (item in snapshot.child(day.elementAt(p2)).children) {
                            for (p in foodlist)
                                if (item.value.toString() == p.name) {
                                    todayfood.add(p)
                                }
                        }
                        println("todayfood isn't empty? ${todayfood.isNotEmpty()}")
                        if (todayfood.isNotEmpty())
                            for (r in todayfood)
                                println(r.name)
                        display(todayfood)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    fun display(f:ArrayList<Foodmenu>) {
        val myAdapter2 = CustomAdapter(this, todayfood)
        val mylist = findViewById<GridView>(R.id.mylist)
        mylist.adapter = myAdapter2
        mylist.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(applicationContext, "You have ordered ${todayfood.elementAt(position).name}",
                Toast.LENGTH_SHORT).show() }
    }
}
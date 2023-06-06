package com.example.nyumbakumiapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class DashboardActivity : AppCompatActivity() {

    lateinit var cardAddHouse:CardView
    lateinit var  cardViewHouses:CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        cardAddHouse = findViewById(R.id.mcardhouses)
        cardViewHouses = findViewById(R.id.mcardviewhouses)
        cardAddHouse.setOnClickListener{
            startActivity(Intent(applicationContext,AddHouseActivity::class.java))

        }
        cardViewHouses.setOnClickListener {

        }

    }
}
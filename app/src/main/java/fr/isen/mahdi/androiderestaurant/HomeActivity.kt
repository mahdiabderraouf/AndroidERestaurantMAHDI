package fr.isen.mahdi.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val entriesCard = findViewById<CardView>(R.id.entriesCardView)
        val platsCard = findViewById<CardView>(R.id.platsCardView)
        val dessertCard = findViewById<CardView>(R.id.dessetCardView)

        entriesCard.setOnClickListener {
            this.cardViewClickHandler(entriesCard)
        }
        platsCard.setOnClickListener {
            this.cardViewClickHandler(platsCard)
        }
        dessertCard.setOnClickListener {
            this.cardViewClickHandler(dessertCard)
        }
    }

    private fun cardViewClickHandler(cardView: CardView) {
        cardView.setOnClickListener {
            Toast.makeText(this@HomeActivity, "You clicked the " + cardView.contentDescription , Toast.LENGTH_SHORT).show()
        }
    }
}
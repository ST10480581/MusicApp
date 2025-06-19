package vcmsa.nika.musicapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailedView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        val btnSongs = findViewById<Button>(R.id.btnSongs)
        val btnAverageRating = findViewById<Button>(R.id.btnAverageRating)
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val edtAverageRating = findViewById<EditText>(R.id.edtAverageRating)
        val tvMainMenuTitle: TextView = findViewById<EditText>(R.id.tvMainMenuTitle)

        val songTitle = intent.getStringArrayListExtra("SONG_TITLE") ?: arrayListOf()
        val artistName = intent.getStringArrayListExtra("ARTIST_NAME") ?: arrayListOf()
        val rating = intent.getIntegerArrayListExtra("RATING") ?: arrayListOf()
        val userComments = intent.getStringArrayListExtra("USER_COMMENTS") ?: arrayListOf()

        btnSongs.setOnClickListener {
            if (songTitle.isEmpty()) {
                showToast("Playlist is empty!")
            } else {
                val songsText = buildString {
                    for (i in songTitle.indices) {
                        append("""
                            Song ${i + 1}:
                            Title: ${songTitle[i]}
                            Artist: ${artistName[i]}
                            Rating: ${rating[i]}/5
                            Comments: ${userComments[i]}
                            
                            """.trimIndent())
                    }
                }
                showToast(songsText)
            }
        }

        btnAverageRating.setOnClickListener {
            if (rating.isEmpty()) {
                edtAverageRating.setText("No ratings")
            } else {
                val average = rating.average()
                edtAverageRating.setText("Average: ${"%.1f".format(average)}/5")
            }
        }

        btnPrevious.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
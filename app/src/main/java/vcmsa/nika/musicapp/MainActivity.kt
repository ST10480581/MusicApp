package vcmsa.nika.musicapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val songTitles = mutableListOf<String>()
    private val artistNames = mutableListOf<String>()
    private val ratings = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnAddToPlaylist = findViewById<Button>(R.id.btnAddToPlaylist)
        val btnViewPlaylist = findViewById<Button>(R.id.btnViewPlaylist)
        val btnExitApp = findViewById<Button>(R.id.btnExitApp)

        val edtSongTitle = findViewById<EditText>(R.id.edtSongTitle)
        val edtArtistName: EditText = findViewById<EditText>(R.id.edtArtistName)
        val edtRating = findViewById<EditText>(R.id.edtRating)
        val edtUserComments = findViewById<EditText>(R.id.edtUserComments)

        // Clear hint text when user focuses on EditText
        listOf(edtSongTitle, edtArtistName, edtRating, edtUserComments).forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && editText.text.toString() == editText.hint) {
                    editText.text.clear()
                }
            }
        }

        btnAddToPlaylist.setOnClickListener {
            try {
                val title = edtSongTitle.text.toString().takeIf { it.isNotBlank() } ?: edtSongTitle.hint.toString()
                val artist = edtArtistName.text.toString().takeIf { it.isNotBlank() } ?: edtArtistName.hint.toString()
                val rating = edtRating.text.toString().takeIf { it.isNotBlank() }?.toInt() ?: 0
                val comment = edtUserComments.text.toString().takeIf { it.isNotBlank() } ?: edtUserComments.hint.toString()

                when {
                    title.equals(edtSongTitle.hint.toString(), ignoreCase = true) ->
                        showToast("Please enter song title")
                    artist.equals(edtArtistName.hint.toString(), ignoreCase = true) ->
                        showToast("Please enter artist name")
                    rating !in 1..5 ->
                        showToast("Rating must be between 1-5")
                    else -> {
                        songTitles.add(title)
                        artistNames.add(artist)
                        ratings.add(rating)
                        comments.add(comment)

                        // Clear fields after adding
                        listOf(edtSongTitle, edtArtistName, edtRating, edtUserComments).forEach { it.text.clear() }
                        showToast("Song added to playlist!")
                    }
                }
            } catch (e: NumberFormatException) {
                showToast("Please enter a valid number for rating")
            }
        }

        btnViewPlaylist.setOnClickListener {
            if (songTitles.isEmpty()) {
                showToast("Playlist is empty!")
            } else {
                navigateToDetailedView()
            }
        }

        btnExitApp.setOnClickListener {
            finish()
        }
    }

    private fun navigateToDetailedView() {
        Intent(this, DetailedView::class.java).apply {
            putStringArrayListExtra("SONG_TITLES", ArrayList(songTitles))
            putStringArrayListExtra("ARTIST_NAMES", ArrayList(artistNames))
            putIntegerArrayListExtra("RATINGS", ArrayList(ratings))
            putStringArrayListExtra("COMMENTS", ArrayList(comments))
            startActivity(this)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}







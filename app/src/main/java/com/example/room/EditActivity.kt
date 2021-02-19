package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.room.room.Constant
import com.example.room.room.NoteDB
import com.example.room.room.note
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    private var noteId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        Save()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }

    fun Save(){
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    note(0, edit_title.text.toString(), edit_note.text.toString())
                )
                finish()
            }
        }

        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                    note(noteId, edit_title.text.toString(), edit_note.text.toString())
                )
                finish()
            }
        }
    }

    fun getNote(){
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId)[0]
            edit_title.setText(notes.tittle)
            edit_note.setText(notes.note)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
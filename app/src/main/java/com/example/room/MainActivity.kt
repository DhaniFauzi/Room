package com.example.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.room.Constant
import com.example.room.room.NoteDB
import com.example.room.room.note
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Create()
        Read()
    }

    override fun onStart() {
        super.onStart()
            loadnote()
    }

    fun loadnote(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse: $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    fun Create(){
        button_create.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun Read(){
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener {
            override fun onClick(note: note) {
                //read detail note
                intentEdit(note.id, Constant.TYPE_READ)
            }

            override fun onUpdate(note: note) {
                intentEdit(note.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(note: note) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadnote()
                }
            }

        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }
}
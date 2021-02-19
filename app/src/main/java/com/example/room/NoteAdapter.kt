package com.example.room
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.room.note
import kotlinx.android.synthetic.main.adapter_note.view.*
import java.util.ArrayList

class NoteAdapter(private val notes: ArrayList<note>, private val listener: OnAdapterListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_note,parent,false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.view.text_title.text = note.tittle
        holder.view.text_title.setOnClickListener{
            listener.onClick(note)
        }

        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(note)
        }

        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(note)
        }
    }

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<note>){
        notes.clear()
        notes.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(note: note)
        fun onUpdate(note: note)
        fun onDelete(note: note)
    }
}
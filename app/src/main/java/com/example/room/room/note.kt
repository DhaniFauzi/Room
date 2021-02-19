package com.example.room.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class note (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val tittle : String,
    val note : String
)
package com.example.fooding.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val displayName: String,
    val email: String,
    val photoUrl: String,
    val currentWeight: String,
    val goalWeight: String,
    val intakeCalories: String,
    val goalCalories: String,
): java.io.Serializable

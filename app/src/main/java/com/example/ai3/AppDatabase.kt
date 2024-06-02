package com.example.ai3

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ingredient::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
}
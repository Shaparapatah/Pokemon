package com.example.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.model.dto.Converters
import com.example.model.dto.PokemonDetailItem

@Database(entities = [PokemonDetailItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDAO

    companion object {

        @Volatile
        private var instance: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase? {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "Pokemon"
                ).fallbackToDestructiveMigration().build()
                instance = _instance
                instance
            }
        }
    }
}
package com.muryno.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.muryno.MainApplication
import com.muryno.model.dao.WeatherDao

@Database(entities = [CurrentWeatherData::class,SubsequenceWeatherData::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao?

    companion object{
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = MainApplication.instance?.applicationContext?.let {
                        Room.databaseBuilder(it, AppDatabase::class.java, "gidi_weather").fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
package com.app.searchcomposeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.searchcomposeapp.data.local.dao.FruitDao
import com.app.searchcomposeapp.data.local.entity.Fruit

@Database(entities = [Fruit::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao
}
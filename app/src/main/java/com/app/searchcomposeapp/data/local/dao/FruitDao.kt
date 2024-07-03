package com.app.searchcomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.searchcomposeapp.data.local.entity.Fruit
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Query("SELECT * FROM fruits WHERE name LIKE '%' || :searchText || '%' ORDER BY name ASC")
    fun getFilteredFruits(searchText: String): Flow<List<Fruit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFruits(fruits: List<Fruit>)

    @Query("SELECT * FROM fruits")
    suspend fun getAllFruits(): List<Fruit>

    @Update
    suspend fun updateFruit(fruit: Fruit)
}
package com.app.searchcomposeapp.data.repository

import com.app.searchcomposeapp.data.local.dao.FruitDao
import com.app.searchcomposeapp.data.local.entity.Fruit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FruitsRepository @Inject constructor(private val fruitDao: FruitDao) {

    fun getFilteredFruits(searchText: String): Flow<List<Fruit>> {
        return fruitDao.getFilteredFruits(searchText)
    }

    suspend fun insertFruit(fruit: List<Fruit>) {
        return fruitDao.insertAllFruits(fruit)
    }

    suspend fun getAllFruits(): List<Fruit> {
        return fruitDao.getAllFruits()
    }

    suspend fun updateFruit(fruit: Fruit) {
        fruitDao.updateFruit(fruit)
    }

    suspend fun getCheckedList(): List<Fruit> {
        return fruitDao.getAllFruits().filter { it.isChecked }
    }
}
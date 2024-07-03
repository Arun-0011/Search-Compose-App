package com.app.searchcomposeapp.data.di

import android.content.Context
import androidx.room.Room
import com.app.searchcomposeapp.common.Constants.DB_NAME
import com.app.searchcomposeapp.data.local.dao.FruitDao
import com.app.searchcomposeapp.data.local.db.MyDatabase
import com.app.searchcomposeapp.data.repository.FruitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(
            appContext,
            MyDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFruitDao(database: MyDatabase): FruitDao {
        return database.fruitDao()
    }

    @Provides
    @Singleton
    fun provideRepository(fruitDao: FruitDao): FruitsRepository {
        return FruitsRepository(fruitDao)
    }
}
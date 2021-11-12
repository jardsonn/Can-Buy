package com.jcs.canbuy.di

import android.content.Context
import com.jcs.canbuy.data.database.CanBuyDatabase
import com.jcs.canbuy.data.database.dao.ProductDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * Created by Jardson Costa on 12/11/2021.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCanBuyDatabase(@ApplicationContext context: Context): CanBuyDatabase{
        return CanBuyDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideProductDao(database: CanBuyDatabase): ProductDao{
        return database.productDao()
    }


}
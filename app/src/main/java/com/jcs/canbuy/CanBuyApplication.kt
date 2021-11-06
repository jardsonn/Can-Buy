package com.jcs.canbuy

import android.app.Application
import com.jcs.canbuy.data.database.CanBuyDatabase
import com.jcs.canbuy.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Jardson Costa on 05/11/2021.
 */

class CanBuyApplication : Application() {
    private val appScope = CoroutineScope(SupervisorJob())

    private val database by lazy { CanBuyDatabase.getDatabase(this, appScope) }
    val repoProduct by lazy { ProductRepository(database.productDao()) }
}
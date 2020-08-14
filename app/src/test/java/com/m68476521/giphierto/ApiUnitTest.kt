package com.m68476521.giphierto

import com.m68476521.giphierto.api.GiphyManager
import org.junit.Before
import org.junit.Test

private const val TOKEN = ""

class ApiUnitTest {
    @Before
    fun setupApi() {
        GiphyManager.setToken(TOKEN)
    }

    @Test
    fun getCategories() {
        GiphyManager.giphyApi.categories()
            .test()
            .assertNoErrors()
            .assertValue {
                it.data.isNotEmpty()
            }
    }

    @Test
    fun getSubCategories() {
        val category = "actions"
        GiphyManager.giphyApi.subCategories(category)
            .test()
            .assertNoErrors()
            .assertValue {
                it.data.isNotEmpty()
            }
    }
}

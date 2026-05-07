package com.m68476521.giphiertwo.di.module

import android.content.Context
import androidx.room.Room
import com.m68476521.networking.MainAPI
import com.m68476521.networking.MainAPIInterface
import com.morozco.data.MainRepository
import com.morozco.data.NetworkCategoriesRepository
import com.morozco.data.NetworkHomeRepository
import com.morozco.data.NetworkSearchRepository
import com.morozco.data.local.AppDatabase
import com.morozco.data.local.ImageDao
import com.morozco.data.local.LocalImageRepositoryImpl
import com.morozco.domain.giftevents.CategoriesRepository
import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.GiftUseCase
import com.morozco.domain.giftevents.HomeRepository
import com.morozco.domain.giftevents.SearchRepository
import com.morozco.domain.repository.LocalImageRepository
import com.morozco.domain.repository.SaveImageUseCase
import com.morozco.presentation.categories.domain.CategoriesUseCase
import com.morozco.presentation.dashboard.domain.HomeUseCase
import com.morozco.presentation.search.domain.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGiphRepository(api: MainAPI): GiftRepositoryInterface = MainRepository(api)

    @Provides
    @Singleton
    fun provideGiftUserCase(repository: GiftRepositoryInterface): GiftUseCase = GiftUseCase(repository)

    @Provides
    @Singleton
    fun provideHomeRepository(api: MainAPIInterface): HomeRepository = NetworkHomeRepository(api)

    @Provides
    @Singleton
    fun provideHomeUseCase(repository: HomeRepository): HomeUseCase = HomeUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchRepository(api: MainAPIInterface): SearchRepository = NetworkSearchRepository(api)

    @Provides
    @Singleton
    fun provideSearchUseCase(repository: SearchRepository): SearchUseCase = SearchUseCase(repository)

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: MainAPIInterface): CategoriesRepository = NetworkCategoriesRepository(api)

    @Provides
    @Singleton
    fun provideCategoriesUseCase(repository: CategoriesRepository): CategoriesUseCase = CategoriesUseCase(repository)

    @Provides
    @Singleton
    fun provideLocalImageRepository(imageDao: ImageDao): LocalImageRepository = LocalImageRepositoryImpl(imageDao)

    @Provides
    @Singleton
    fun provideSaveImageUseCase(repository: LocalImageRepository): SaveImageUseCase = SaveImageUseCase(repository)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "images.db",
            ).build()

    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao = appDatabase.imageDao()
}

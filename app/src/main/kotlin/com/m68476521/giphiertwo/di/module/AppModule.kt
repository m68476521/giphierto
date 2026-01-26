package com.m68476521.giphiertwo.di.module

//import com.m68476521.data.MainRepository
import com.m68476521.networking.MainAPI
import com.m68476521.networking.MainAPIInterface
import com.morozco.data.MainRepository
import com.morozco.data.NetworkHomeRepository
import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.GiftUseCase
import com.morozco.domain.giftevents.HomeRepository
import com.morozco.presentation.dashboard.domain.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideHomeRepository(api: MainAPIInterface): HomeRepository {
        return NetworkHomeRepository(api)
    }

    @Provides
    @Singleton
    fun provideHomeUseCase(repository: HomeRepository): HomeUseCase {
        return HomeUseCase(repository)
    }
}

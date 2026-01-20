package com.m68476521.giphiertwo.di.module

//import com.m68476521.giphiertwo.api.MainRepository
import com.m68476521.data.MainRepository
import com.m68476521.networking.MainAPI
import com.morozco.domain.giftevents.GiftRepositoryInterface
import com.morozco.domain.giftevents.GiftUseCase
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
    fun provideGiphRepository(
        api: MainAPI
    ): GiftRepositoryInterface {
        return MainRepository(api)
    }

    //fun provideOrderEventsRepository(api: CSSAPI): OrderEventsRepositoryInterface {
    //        return OrderEventsRepository(api)
    //    }
    @Provides
    @Singleton
    fun provideGiftUserCase(
        repository: GiftRepositoryInterface
    ): GiftUseCase {
        return GiftUseCase(repository)

    }
}
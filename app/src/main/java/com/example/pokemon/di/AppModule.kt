//package com.example.pokemon.di
//
//import android.content.Context
//import com.example.repository.api.RetrofitService
//import com.example.repository.main.DefaultRepository
//import com.example.repository.main.MainRepository
//import com.example.repository.room.PokemonDAO
//import com.example.repository.room.PokemonDatabase
//import com.example.utils.Constants
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Singleton
//    @Provides
//    fun providePokeApi(): RetrofitService = Retrofit.Builder()
//        .baseUrl(Constants.BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(RetrofitService::class.java)
//
//    @Provides
//    fun providePokemonDao(@ApplicationContext appContext: Context): PokemonDAO {
//        return PokemonDatabase.getDatabase(appContext)!!.pokemonDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideMainRepository(retrofit: RetrofitService, dao: PokemonDAO): DefaultRepository =
//        MainRepository(retrofit, dao)
//}
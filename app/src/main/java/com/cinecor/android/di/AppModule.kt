package com.cinecor.android.di

import android.content.Context
import com.cinecor.android.CinecorApp
import com.cinecor.android.common.repository.CinemasRepository
import com.cinecor.android.common.viewmodel.CinemaViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: CinecorApp): Context {
        return application
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun provideRepository(firebaseAuth: FirebaseAuth, firebaseDatabase: FirebaseDatabase): CinemasRepository{
        return CinemasRepository(firebaseAuth, firebaseDatabase)
    }

    @Provides
    fun provideCinemaViewModelFactory(repository: CinemasRepository): CinemaViewModelFactory {
        return CinemaViewModelFactory(repository)
    }
}

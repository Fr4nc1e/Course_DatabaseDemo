package com.course.databasedemo.di

import com.course.databasedemo.data.Repository
import com.course.databasedemo.data.RepositoryImpl
import com.course.databasedemo.data.Student
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(Student::class)
        )
            .compactOnLaunch()
            .build()

        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun provideRepository(realm: Realm): Repository {
        return RepositoryImpl(realm = realm)
    }
}

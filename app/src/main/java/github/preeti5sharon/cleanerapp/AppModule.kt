package github.preeti5sharon.cleanerapp

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().build()

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context, moshi: Moshi) = Room.databaseBuilder(
        context.applicationContext,
        CleanerDatabase::class.java,
        "cleaner_db",
    ).addTypeConverter(CleanerConverter(moshi)).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesDao(database: CleanerDatabase) = database.getCleanerDao()
}

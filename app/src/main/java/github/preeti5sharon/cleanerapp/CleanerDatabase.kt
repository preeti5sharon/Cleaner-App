package github.preeti5sharon.cleanerapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(CleanerConverter::class)
@Database(
    entities = [CleanerModel.Specification::class],
    version = 1,
)
abstract class CleanerDatabase : RoomDatabase() {
    abstract fun getCleanerDao(): CleanerDao
}

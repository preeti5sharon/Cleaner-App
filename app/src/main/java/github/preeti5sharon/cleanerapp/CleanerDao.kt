package github.preeti5sharon.cleanerapp

import androidx.room.*

@Dao
interface CleanerDao {
    @Query("select * from cleaning_package where type = 1")
    suspend fun getRadioItems(): List<CleanerModel.Specification>

    @Query("select * from cleaning_package where modifierId = :bhkID")
    suspend fun getCleaningListForBHK(bhkID: String): List<CleanerModel.Specification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDatabase(items: List<CleanerModel.Specification>)

    @Query("select exists(select * from cleaning_package)")
    suspend fun itemExists(): Boolean
}

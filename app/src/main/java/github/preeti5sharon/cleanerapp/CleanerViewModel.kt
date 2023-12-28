package github.preeti5sharon.cleanerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CleanerViewModel @Inject constructor(private val cleanerDao: CleanerDao) : ViewModel() {

    suspend fun getSpecificationList(id: Int): Flow<List<CleanerModel.Specification>> = flow {
        val radioList = cleanerDao.getRadioItems()
        val checkList = cleanerDao.getCleaningListForBHK(id)


        println(radioList)
        println(checkList)
        emit(radioList + checkList)
    }.stateIn(viewModelScope)

    fun stringToJsonObject(stringData: String?) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(CleanerModel::class.java)
        val jsonObject = stringData?.let { jsonAdapter.fromJson(it) }

        viewModelScope.launch {
            cleanerDao.insertDatabase(
                jsonObject?.specifications.orEmpty().filterNotNull(),
            )
        }
    }
}

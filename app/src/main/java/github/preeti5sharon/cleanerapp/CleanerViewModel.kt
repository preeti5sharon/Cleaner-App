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

    private var radioList = mutableListOf<CleanerModel.Specification>()
    suspend fun getSpecificationList(id: String): Flow<List<CleanerModel.Specification>> = flow {
        if (radioList.isEmpty()) {
            radioList = cleanerDao.getRadioItems().toMutableList()
        }
        val checkList = cleanerDao.getCleaningListForBHK(id)

        emit(radioList + checkList)
    }.stateIn(viewModelScope)

    fun stringToJsonObject(stringData: String?) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(CleanerModel::class.java)
        val jsonObject = stringData?.let { jsonAdapter.fromJson(it) }

        viewModelScope.launch {
            val itemsExist = cleanerDao.itemExists()
            if (!itemsExist) {
                cleanerDao.insertDatabase(
                    jsonObject?.specifications.orEmpty().filterNotNull(),
                )
            }
        }
    }
}

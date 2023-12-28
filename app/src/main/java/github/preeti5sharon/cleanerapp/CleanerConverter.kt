package github.preeti5sharon.cleanerapp

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class CleanerConverter(private val moshi: Moshi) {
    private val itemListType =
        Types.newParameterizedType(List::class.java, CleanerModel.Specification.Item::class.java)

    private val nameListStringType =
        Types.newParameterizedType(List::class.java, String::class.java)

    @TypeConverter
    fun listToJson(list: List<CleanerModel.Specification.Item?>?): String? {
        return moshi.adapter<List<CleanerModel.Specification.Item?>?>(itemListType).toJson(list)
    }

    @TypeConverter
    fun listFromJson(json: String): List<CleanerModel.Specification.Item?>? {
        return moshi.adapter<List<CleanerModel.Specification.Item?>?>(itemListType).fromJson(json)
    }

    @TypeConverter
    fun nameToJson(list: List<String?>?): String? {
        return moshi.adapter<List<String?>?>(nameListStringType).toJson(list)
    }

    @TypeConverter
    fun nameFromJson(json: String): List<String?>? {
        return moshi.adapter<List<String?>?>(nameListStringType).fromJson(json)
    }
}

package github.preeti5sharon.cleanerapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CleanerModel(
    @Json(name = "_id")
    val id: String?,
    @Json(name = "item_taxes")
    val itemTaxes: List<Int?>?,
    val name: List<String?>?,
    val price: Int?,
    val specifications: List<Specification?>?,
) {
    @JsonClass(generateAdapter = true)
    @Entity(tableName = "cleaning_package")
    data class Specification(
        @Json(name = "_id")
        @PrimaryKey(autoGenerate = false)
        val id: String,
        val isAssociated: Boolean?,
        val isParentAssociate: Boolean?,
        @Json(name = "is_required")
        val isRequired: Boolean?,
        val list: List<Item?>?,
        @Json(name = "max_range")
        val maxRange: Int?,
        val modifierGroupId: String?,
        val modifierGroupName: String?,
        val modifierId: String?,
        val modifierName: String?,
        val name: List<String?>?,
        val range: Int?,
        @Json(name = "sequence_number")
        val sequenceNumber: Int?,
        val type: Int?,
        @Json(name = "unique_id")
        val uniqueId: Int?,
        @Json(name = "user_can_add_specification_quantity")
        val userCanAddSpecificationQuantity: Boolean?,
    ) {
        @JsonClass(generateAdapter = true)
        data class Item(
            @Json(name = "_id")
            val id: String?,
            @Json(name = "is_default_selected")
            val isDefaultSelected: Boolean?,
            val name: List<String?>?,
            val price: Int?,
            @Json(name = "sequence_number")
            val sequenceNumber: Int?,
            @Json(name = "specification_group_id")
            val specificationGroupId: String?,
            @Json(name = "unique_id")
            val uniqueId: Int?,
        )
    }
}

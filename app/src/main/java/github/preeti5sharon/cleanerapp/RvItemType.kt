package github.preeti5sharon.cleanerapp

sealed class RvItemType() {
    data class HeaderItem(val item: String) :
        RvItemType()

    data class ListItem(val item: CleanerModel.Specification.Item, val itemType: Int?) :
        RvItemType()
}

package github.preeti5sharon.cleanerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import github.preeti5sharon.cleanerapp.databinding.RvItemBinding
import github.preeti5sharon.cleanerapp.databinding.RvItemListBinding

class CleanerAdapter(private val onClickApartmentSize: OnClickApartmentSize) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastRadioSelected: RadioButton? = null
    private var lastCheckboxSpecificationId = mutableMapOf<String, CheckBox>()

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class ListItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class PackageDiffer : DiffUtil.ItemCallback<RvItemType>() {
        override fun areItemsTheSame(
            oldItem: RvItemType,
            newItem: RvItemType,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: RvItemType,
            newItem: RvItemType,
        ): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDiffer = AsyncListDiffer(this, PackageDiffer())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONE) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false),
            )
        } else {
            return ListItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.rv_item_list, parent, false),
            )
        }
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = asyncDiffer.currentList.getOrNull(position)

        if (item is RvItemType.HeaderItem) {
            val binding = RvItemBinding.bind(holder.itemView)
            binding.title.text = item.item
        } else if (item is RvItemType.ListItem) {
            val binding = RvItemListBinding.bind(holder.itemView)
            if (item.itemType == 1) {
                binding.radiobutton.text = item.item.name?.joinToString()
                binding.price.text = item.item.price.toString()
                binding.radiobutton.visibility = View.VISIBLE
                binding.checkbox.visibility = View.GONE
                binding.radiobutton.setOnClickListener {
                    if (lastRadioSelected != null) {
                        lastRadioSelected?.isChecked = false
                    }
                    lastRadioSelected = binding.radiobutton
                    onClickApartmentSize(item.item.id)
                }
            } else {
                binding.checkbox.text = item.item.name?.joinToString()
                binding.price.text = item.item.price.toString()
                binding.checkbox.visibility = View.VISIBLE
                binding.radiobutton.visibility = View.GONE

                binding.checkbox.setOnClickListener {
                    val groupId = item.item.specificationGroupId
                    if (lastCheckboxSpecificationId.containsKey(groupId)) {
                        lastCheckboxSpecificationId.apply {
                            get(groupId)?.isChecked = false
                            remove(groupId)
                        }
                    }
                    lastCheckboxSpecificationId.put(groupId.orEmpty(), binding.checkbox)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val getItem = asyncDiffer.currentList.getOrNull(position)
        return when (getItem) {
            is RvItemType.HeaderItem -> 1
            else -> 2
        }
    }
}
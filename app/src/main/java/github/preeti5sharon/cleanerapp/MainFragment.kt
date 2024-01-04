package github.preeti5sharon.cleanerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import github.preeti5sharon.cleanerapp.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), OnClickApartmentSize {

    @Inject
    lateinit var cleanerAdapter: CleanerAdapter
    private val viewModel: CleanerViewModel by viewModels()
    private var binding: FragmentMainBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stringData = readJsonFromAssets()

        viewModel.stringToJsonObject(stringData)

        getCleaningDetails("621da764abb8a52242c181e5")

        binding?.recyclerView?.adapter = cleanerAdapter
    }

    private fun readJsonFromAssets(): String? {
        return context?.assets?.open("item_data.json")?.bufferedReader().use { it?.readText() }
    }

    override fun invoke(specGroupId: String?) {
        if (specGroupId != null) {
            getCleaningDetails(specGroupId)
        }
    }

    private fun getCleaningDetails(specGroupId: String) {
        lifecycleScope.launch {
            viewModel.getSpecificationList(specGroupId)
                .collectLatest { specificationList ->
                    val mergedList = mutableListOf<RvItemType>()

                    specificationList.forEach {
                        val headerItemType =
                            RvItemType.HeaderItem(
                                it.name.orEmpty().firstOrNull().orEmpty(),
                            )
                        val checkOrRadioType = it.type
                        val listItemType = it.list.orEmpty().filterNotNull().map {
                            RvItemType.ListItem(it, checkOrRadioType)
                        }

                        mergedList.add(headerItemType)
                        mergedList.addAll(listItemType)
                    }
                    cleanerAdapter.asyncDiffer.submitList(mergedList)
                }
        }
    }

    private fun getHeaderItem() {
    }
}

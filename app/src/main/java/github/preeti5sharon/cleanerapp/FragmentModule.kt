package github.preeti5sharon.cleanerapp

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    @FragmentScoped
    fun providesCleanAdapter(fragment: Fragment) = CleanerAdapter(fragment as OnClickApartmentSize)
}
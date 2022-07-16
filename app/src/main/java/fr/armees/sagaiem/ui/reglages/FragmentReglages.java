package fr.armees.sagaiem.ui.reglages;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.api.ConnexionClient;
import fr.armees.sagaiem.api.DataClient;
import fr.armees.sagaiem.databinding.FragmentConnexionBinding;
import fr.armees.sagaiem.databinding.FragmentReglagesBinding;
import fr.armees.sagaiem.entities.Stagiaire;
import fr.armees.sagaiem.ui.ViewModelShared;
import fr.armees.sagaiem.utils.Constantes;
import fr.armees.sagaiem.utils.StagiaireUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.prefs.Preferences;

public class FragmentReglages extends Fragment {

    private ListPreference mListPreference;
    private @NonNull FragmentReglagesBinding binding;

    private ViewModelShared viewModelShared;


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = FragmentReglagesBinding.inflate(inflater, container, false);

        viewModelShared = new ViewModelProvider(getActivity()).get(ViewModelShared.class);

        binding.btSettingsDisconnect.setOnClickListener(this::disconnect);
        binding.btSettingsUpdate.setOnClickListener(this::reload);
        binding.tableRow.setOnClickListener(this::showAPropos);

        return binding.getRoot();
    }

    private void disconnect(View view) {


    }

    private void reload(View view){

        Integer code = 0;

       // viewModelShared.set(StagiaireUtils.StagiaireFromJSON());
    }
    private void showAPropos(View view){
        new AlertDialog.Builder(getContext()).setTitle(R.string.about_popup_title).setMessage(R.string.about_popup_description).setIcon(R.drawable.ic_menu_accueil_default_48dp).show();
    }
}
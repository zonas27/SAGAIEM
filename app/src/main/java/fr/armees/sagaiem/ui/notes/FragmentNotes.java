package fr.armees.sagaiem.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.databinding.FragmentNotesBinding;
import fr.armees.sagaiem.entities.Matiere;
import fr.armees.sagaiem.entities.Uv;
import fr.armees.sagaiem.fakedata.Notes;
import fr.armees.sagaiem.ui.ViewModelShared;
import fr.armees.sagaiem.ui.notedetail.FragmentNoteDetail;
import fr.armees.sagaiem.ui.notedetail.ViewModelNoteDetail;
import fr.armees.sagaiem.utils.CustomExpandableUVAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentNotes extends Fragment {

    private ViewModelNotes viewModelNotes;
    private FragmentNotesBinding binding;

    private ViewModelShared viewModelShared;
    private DecimalFormat df;
    private ViewModelNoteDetail vmnd;
    private List<Integer> openedGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModelNotes =
                new ViewModelProvider(getActivity()).get(ViewModelNotes.class);

        binding = FragmentNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vmnd = new ViewModelProvider(requireActivity()).get(ViewModelNoteDetail.class);
        viewModelShared = new ViewModelProvider(getActivity()).get(ViewModelShared.class);

        df = new DecimalFormat("#.####");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(dfs);



//        final TextView textView = binding.textDashboard;
//        viewModelNotes.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ExpandableListView elv=(ExpandableListView) binding.elvNotes;
//        final ArrayList<Uv> listUv = Notes.getData();
        final ArrayList<Uv> listUv = viewModelShared.get().getValue().getStage().getLstUvs();
        CustomExpandableUVAdapter adapter = new CustomExpandableUVAdapter(getContext(),listUv);
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Matiere matiere = listUv.get(groupPosition).getLstMatieres().get(childPosition);
                vmnd.setMatiere(listUv.get(groupPosition).getLstMatieres().get(childPosition));
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_notes_to_notesDetail);

                return false;
            }
        });

        TextView tvMoyenneGenerale = (TextView) binding.getRoot().findViewById(R.id.tv_notes_moyenne);
        Float moyenne = viewModelShared.get().getValue().getMoyenneGenerale();
        tvMoyenneGenerale.setText((moyenne==-1?getContext().getResources().getString(R.string.note_default): df.format(moyenne.floatValue()))
                + getContext().getResources().getString(R.string.note_sur));

        ImageButton backButton = (ImageButton) binding.btnNoteBack;
        // Préouverture des liste déroulantes déjà ouverte
        openedGroup = viewModelNotes.getopenededUV().getValue();
        openedGroup.stream().forEach(i -> elv.expandGroup(i.intValue()));
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(openedGroup.stream().anyMatch(i -> i.intValue() == groupPosition)) {
                    openedGroup.remove(openedGroup.indexOf(groupPosition));
                    elv.collapseGroup(groupPosition);
                }else{
                    openedGroup.add(groupPosition);
                    elv.expandGroup(groupPosition);
                }
                viewModelNotes.setOpenededUV(openedGroup);
                return true;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package fr.armees.sagaiem.ui.notedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.databinding.FragmentNoteDetailBinding;
import fr.armees.sagaiem.entities.Matiere;
import fr.armees.sagaiem.ui.ViewModelShared;
import fr.armees.sagaiem.ui.notes.FragmentNotes;
import fr.armees.sagaiem.utils.EvaluationArrayAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FragmentNoteDetail extends Fragment {

    private ViewModelNoteDetail viewModelNoteDetail;
    private FragmentNoteDetailBinding binding;
    private ViewModelShared viewModelShared;
    private DecimalFormat df;
    private Matiere matiere;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModelNoteDetail = new ViewModelProvider(requireActivity()).get(ViewModelNoteDetail.class);

        binding = FragmentNoteDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModelShared = new ViewModelProvider(getActivity()).get(ViewModelShared.class);

        df = new DecimalFormat("#.####");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(dfs);

        binding.btnNoteDetailBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_notesDetail_to_notes);
        });

        matiere = viewModelNoteDetail.getMatiere().getValue();

        ((TextView) root.findViewById(R.id.tv_note_detail_matiere)).setText(matiere.getNomMatiere());
        ((TextView) root.findViewById(R.id.tv_notes_moyenne)).setText((matiere.getMoyenneMatiere()==-1?getContext().getResources().getString(R.string.note_default): df.format(matiere.getMoyenneMatiere().floatValue()))
                + getContext().getResources().getString(R.string.note_sur));

        EvaluationArrayAdapter evaluationArrayAdapter = new EvaluationArrayAdapter(getContext(), R.layout.layout_evaluation, matiere.getLstEvaluations());
        ListView lv = (ListView) binding.lvEvaluations;
        lv.setAdapter(evaluationArrayAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
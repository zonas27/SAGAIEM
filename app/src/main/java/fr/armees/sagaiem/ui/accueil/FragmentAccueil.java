package fr.armees.sagaiem.ui.accueil;

import android.graphics.Typeface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.databinding.FragmentAccueilBinding;
import fr.armees.sagaiem.entities.Matiere;
import fr.armees.sagaiem.entities.Stage;
import fr.armees.sagaiem.entities.Uv;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import fr.armees.sagaiem.entities.Stagiaire;
import fr.armees.sagaiem.entities.Uv;
import fr.armees.sagaiem.entities.Evaluation;
import fr.armees.sagaiem.ui.ViewModelShared;
import fr.armees.sagaiem.utils.MapUtils;

import java.util.*;

public class FragmentAccueil extends Fragment {

    private ViewModelAccueil viewModelAccueil;
    private FragmentAccueilBinding binding;

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private Integer maxGraph = 5;

    private Integer[] couleurs = {0xff002C8C,
            0xff1B2F59,
            0xff00143F,
            0xff2A498C,
            0xff0045D9};

    private ViewModelShared viewModelShared;

    DateTimeFormatter formatDateTime  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#.####");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModelAccueil =
                new ViewModelProvider(this).get(ViewModelAccueil.class);

        viewModelShared = new ViewModelProvider(getActivity()).get(ViewModelShared.class);

        binding = FragmentAccueilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.tvHello;

        chart = new BarChart(this.getContext());
        setupChart();
        setupData();

//        binding.boxLastNotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickGoToNotes(v);
//            }
//        });

        return root;
    }



    private void setupData(){
        viewModelShared.get().observe(getViewLifecycleOwner(), new Observer<Stagiaire>() {
            @Override
            public void onChanged(@Nullable Stagiaire stagiaire) {
                LinkedHashMap<Matiere,Evaluation> mapMatiereEvaluations = getMatiereEvaluationsDateOrdered(stagiaire);
                Iterator iterator = mapMatiereEvaluations.entrySet().iterator();

                Map.Entry entry = (Map.Entry) iterator.next();
                binding.tvLastNote1.setText(formatLastNote(((Matiere)entry.getKey()).getNomMatiere(),
                        ((Evaluation)entry.getValue()).getNote(),((Evaluation)entry.getValue()).getDateEvaluation()));
                entry = (Map.Entry) iterator.next();
                binding.tvLastNote2.setText(formatLastNote(((Matiere)entry.getKey()).getNomMatiere(),
                        ((Evaluation)entry.getValue()).getNote(),((Evaluation)entry.getValue()).getDateEvaluation()));
                entry = (Map.Entry) iterator.next();
                binding.tvLastNote3.setText(formatLastNote(((Matiere)entry.getKey()).getNomMatiere(),
                        ((Evaluation)entry.getValue()).getNote(),((Evaluation)entry.getValue()).getDateEvaluation()));
                entry = (Map.Entry) iterator.next();

                binding.tvAvgGeneral.setText(stagiaire.getMoyenneGenerale().toString());
                binding.tvRank.setText(stagiaire.getClassement());
                binding.tvHello.setText(getResources().getString(R.string.hello) + " " + stagiaire.getPrenom());
            }
        });
    }

    private LinkedHashMap<Matiere, Evaluation> getMatiereEvaluationsDateOrdered(Stagiaire stagiaire){

        Map<Matiere,Evaluation> map =  new HashMap<>();
        for (Uv uv:stagiaire.getStage().getLstUvs()) {
            for (Matiere matiere : uv.getLstMatieres()){
                for(Evaluation evaluation : matiere.getLstEvaluations()){
                   map.put(matiere,evaluation);
                }
            }
        }
        Map<Matiere, Evaluation> sortedMap = MapUtils.sortByValue(map);

        return (LinkedHashMap<Matiere, Evaluation>) sortedMap;
    }

    private void setupChart(){
        //Gestion Bar chart
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        // if more than 30 entries are displayed in the chart, no values will be drawn
        chart.setMaxVisibleValueCount(maxGraph);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisRight().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTextColor(Color.WHITE);
        //Fin Gestion bar chart
    }


    private void setData() {
        float start = 1f;
        //récuperation du stage TODO
        Stage stage = viewModelShared.get().getValue().getStage();
        Uv uv = stage.getLstUvs().get(0);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < uv.getLstMatieres().size() && i <= maxGraph; i++) {
            values.add(new BarEntry(i, uv.getLstMatieres().get(i).getMoyenneMatiere()));
            BarDataSet set1 = new BarDataSet(values, uv.getLstMatieres().get(i).getNomMatiere());
            set1.setColor(couleurs[i]);
            dataSets.add(set1);
        }
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        chart.setData(data);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private SpannableString formatLastNote(String matiere, float note, LocalDateTime date){
        int ld1= date.format(formatDateTime).length()+3;
        int lf1 = ld1 + matiere.length();

        int ld2 = lf1 + 5;
        int lf2 = ld2 + df.format(note).length();

        StringBuilder sb = new StringBuilder();
        sb.append(date.format(formatDateTime));
        sb.append(" - ");
        sb.append(matiere);
        sb.append(" ... ");
        sb.append(df.format(note));

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new StyleSpan(Typeface.BOLD),ld1,lf1,0);
        ss.setSpan(new StyleSpan(Typeface.BOLD),ld2,lf2,0);

        return ss;
    }

    private void onClickGoToNotes(final View view){
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_accueil_to_notes);
    }
}
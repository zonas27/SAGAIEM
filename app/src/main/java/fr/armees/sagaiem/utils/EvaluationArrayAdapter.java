package fr.armees.sagaiem.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.entities.Evaluation;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EvaluationArrayAdapter extends ArrayAdapter<Evaluation> {

    private final Context context;
    private final int layout_resource;

    private DateTimeFormatter formatDateTime  = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DecimalFormat df = new DecimalFormat("#.####");

    public EvaluationArrayAdapter(Context context, int resource, List<Evaluation> objects){
        super(context, resource, objects);
        this.context = context;
        this.layout_resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout_resource, null);
        }
        Evaluation currentEvaluation = getItem(position);

        if (currentEvaluation != null){
            ((TextView) convertView.findViewById(R.id.tv_evaluation_date)).setText(formatDateTime.format(currentEvaluation.getDateEvaluation()));
            ((TextView) convertView.findViewById(R.id.tv_evaluation_coefficient))
                    .setText(context.getResources().getString(R.string.coefficient_small) + " " + currentEvaluation.getCoefEval());
            ((TextView) convertView.findViewById(R.id.tv_evaluation_note))
                    .setText((!currentEvaluation.isEffectuer()?context.getResources().getString(R.string.note_default): df.format(currentEvaluation.getNote().floatValue())) + context.getResources().getString(R.string.note_sur));
        }
        return convertView;
    }
}

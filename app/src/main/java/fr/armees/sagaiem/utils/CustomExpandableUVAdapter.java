package fr.armees.sagaiem.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import fr.armees.sagaiem.R;
import fr.armees.sagaiem.entities.Matiere;
import fr.armees.sagaiem.entities.Uv;

public class CustomExpandableUVAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Uv> expandableListUv;
    private LayoutInflater inflater;
    private DecimalFormat df;


    public CustomExpandableUVAdapter(Context context, List<Uv> expandableListUv) {
        this.context = context;
        this.expandableListUv = expandableListUv;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        df = new DecimalFormat("#.####");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(dfs);
    }

    @Override
    public Object getChild(int groupPosition, int childPos) {
        return expandableListUv.get(groupPosition).getLstMatieres().get(childPos);
    }

    @Override
    public long getChildId(int arg0, int arg1) {
        return 0;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
                             ViewGroup parent){
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_note, null);
        }
        Matiere child=(Matiere) getChild(groupPos,childPos);
        TextView tvMatiere = (TextView) convertView.findViewById(R.id.tv_evaluation_date);
        TextView tvCoef = (TextView) convertView.findViewById(R.id.tv_evaluation_coefficient);
        TextView tvMoyenne = (TextView) convertView.findViewById(R.id.tv_evaluation_note);

        tvMatiere.setText(child.getNomMatiere());
        tvCoef.setText(context.getResources().getString(R.string.coefficient_small) + " " + child.getCoefMatiere().toString());
        tvMoyenne.setText((child.getCoefEvaluationsEffectuees()==-1?context.getResources().getString(R.string.note_default): df.format(child.getMoyenneMatiere().floatValue())) + context.getResources().getString(R.string.note_sur));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return this.expandableListUv.get(groupPos).getLstMatieres().size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return this.expandableListUv.get(groupPos);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListUv.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_uv, null);
        }
        Uv uv =(Uv) getGroup(groupPosition);

        TextView tvNom = (TextView) convertView.findViewById(R.id.tv_uv_nom);
        TextView tvMoyenne = (TextView) convertView.findViewById(R.id.tv_uv_moyenne);

        tvNom.setText(String.format("%s %d", context.getResources().getString(R.string.uv_prefix), uv.getNumUv()));
        tvMoyenne.setText((uv.getCoefMatieresEffectuees()==-1?context.getResources().getString(R.string.note_default): df.format(uv.getMoyenneUv().floatValue())) + context.getResources().getString(R.string.note_sur));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

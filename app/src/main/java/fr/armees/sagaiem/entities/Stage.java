package fr.armees.sagaiem.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Stage {

    //Attirbuts
    private String code;
    private ArrayList<Uv> lstUvs;

    public Stage(String code, ArrayList<Uv> lstUvs) {
        this.code = code;
        this.lstUvs = lstUvs;
    }

    public Stage() {
    }

    //GETTER SETTER
    public ArrayList<Uv> getLstUvs() {
        return lstUvs;
    }
    private void setLstUvs(ArrayList<Uv> lstUvs) {
        this.lstUvs = lstUvs;
    }

    //Methodes

    /**
     * revoit les notes à obteneir pour l'UV en cours afin d'atteindre la moyenne cible passée en paramètre,
     * revoit null si aucun UV en cours n'est trouvé,
     * -1 si la moyenne est impossible à atteindre
     *  et -2 si la moyenne cible est garantie d'être atteinte
     * @param moyenneCible
     * @return moyenneCalculer
     */
    public Float calculerPrevision(Float moyenneCible, Integer numUv, HashMap<Matiere, Float> mapMoyennesPrevisionnelles){
        for (Uv uv: lstUvs) {
            if(numUv == uv.getNumUv()){
                Float sommeMoyennesPrevisionnellesAvecCoef = 0f;
                if(!mapMoyennesPrevisionnelles.isEmpty()) {
                    for (Matiere matiere : mapMoyennesPrevisionnelles.keySet()) {
                        sommeMoyennesPrevisionnellesAvecCoef += mapMoyennesPrevisionnelles.get(matiere) * matiere.getCoefMatiere();
                    }
                }
                Float moyenneCalculer = (moyenneCible * uv.getCoefMatieresTotales()
                            - uv.getMoyenneUv() * uv.getCoefMatieresEffectuees()
                            - sommeMoyennesPrevisionnellesAvecCoef)
                        / uv.getCoefMatieresRaf();
                if(moyenneCalculer > 20f){
                    return -1f;
                } else if ( moyenneCalculer < 0f) {
                    return -2f;
                }
                return moyenneCalculer;
            }
        }
        return null;
    }
}

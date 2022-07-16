package fr.armees.sagaiem.entities;

import java.util.ArrayList;

public class Matiere {

    //Attribues

    private String nomMatiere;
    private ArrayList<Evaluation> lstEvaluations;

    private Integer coefMatiere;

    private Integer coefEvaluationsEffectuees;

    private Integer coefEvaluationsTotales;

    private Integer coefEvaluationsRaf;

    private Float moyenneMatiere;

    public Matiere(String nomMatiere, ArrayList<Evaluation> lstEvaluations, Integer coefMatiere) {
        this.nomMatiere = nomMatiere;
        this.lstEvaluations = lstEvaluations;
        this.coefMatiere = coefMatiere;
        this.setCoefEvaluationsTotales();
        this.setCoefEvaluationsEffectuees();
        this.setCoefEvaluationsRaf();
        this.setMoyenneMatiere();
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public Integer getCoefEvaluationsTotales() {
        return coefEvaluationsTotales;
    }

    //GETTER SETTER

    public Integer getCoefMatiere() {
        return coefMatiere;
    }

    private void setCoefMatiere(Integer coefMatiere) {
        this.coefMatiere = coefMatiere;
    }

    public void setCoefEvaluationsTotales() {
        Integer coefEvaluationsTotal = 0;
        for (Evaluation eval: lstEvaluations) {
            coefEvaluationsTotal += eval.getCoefEval();
        }
        this.coefEvaluationsTotales = coefEvaluationsTotal;
    }

    public Integer getCoefEvaluationsRaf() {
        return coefEvaluationsRaf;
    }

    public void setCoefEvaluationsRaf() {
        coefEvaluationsRaf = coefEvaluationsTotales - coefEvaluationsEffectuees;
    }

    public ArrayList<Evaluation> getLstEvaluations() {
        return lstEvaluations;
    }

    private void setLstEvaluations(ArrayList<Evaluation> lstEvaluations) {
        this.lstEvaluations = lstEvaluations;
    }

    public Integer getCoefEvaluationsEffectuees() {
        return coefEvaluationsEffectuees;
    }

    public void setCoefEvaluationsEffectuees() {

        Integer sommeCoef = 0;
        for (Evaluation eval: lstEvaluations) {
            if(eval.isEffectuer()) {
                sommeCoef += eval.getCoefEval();
            }
        }
        coefEvaluationsEffectuees = sommeCoef==0?-1:sommeCoef;
    }

    public Float getMoyenneMatiere(){
        return moyenneMatiere;
    }

    public void setMoyenneMatiere(){
        Float moyenne = 0f;
        Integer coefTotal = 0;
        for (Evaluation eval: lstEvaluations) {
            if(eval.isEffectuer()){
                moyenne += eval.getNote() * eval.getCoefEval();
                coefTotal += eval.getCoefEval();
            }
        }
        moyenneMatiere = coefTotal==0?-1: moyenne / coefTotal;
    }

    public Boolean isMatiereTerminer(){
        Boolean flag = true;
        for (Evaluation eval: lstEvaluations) {
            if(!eval.isEffectuer())
            {
                flag = false;
            }
        }
        return flag;
    }



}

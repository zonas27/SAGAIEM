package fr.armees.sagaiem.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Uv {

    //Attributs
    private ArrayList<Matiere> lstMatieres;

    private LocalDateTime dateDebutUv;

    private Integer coefMatieresEffectuees;

    private Integer coefMatieresTotales;

    private Integer coefMatieresRaf;

    private Integer numUv;

    private Float moyenneUv;

    public Uv(Integer numUv, ArrayList<Matiere> lstMatieres, LocalDateTime dateDebutUv) {
        this.lstMatieres = lstMatieres;
        this.dateDebutUv = dateDebutUv;
        this.numUv = numUv;

        this.setCoefMatieresEffectuees();
        this.setCoefMatieresTotales();
        this.setCoefMatieresRaf();
        this.setMoyenneUv();
    }

    //GETTER SETTTER

    public Integer getNumUv() {
        return numUv;
    }

    public void setNumUv(Integer numUv) {
        this.numUv = numUv;
    }
    public LocalDateTime getDateDebutUv() {
        return dateDebutUv;
    }

    private void setDateDebutUv(LocalDateTime dateDebutUv) {
        this.dateDebutUv = dateDebutUv;
    }

    public ArrayList<Matiere> getLstMatieres() {
        return lstMatieres;
    }

    private void setLstMatieres(ArrayList<Matiere> lstMatieres) {
        this.lstMatieres = lstMatieres;
    }

    public Integer getCoefMatieresEffectuees() {
        return coefMatieresEffectuees;
    }

    public Integer getCoefMatieresTotales() {
        return coefMatieresTotales;
    }

    public Integer getCoefMatieresRaf() {
        return coefMatieresRaf;
    }

    public Float getMoyenneUv() {
        return moyenneUv;
    }

    public void setMoyenneUv(){
        Float moyenne = 0f;
        Integer sommeCoef = 0;
        for (Matiere matiere: lstMatieres) {
            if(matiere.isMatiereTerminer()) {
                moyenne += matiere.getMoyenneMatiere() * matiere.getCoefMatiere();
                sommeCoef += matiere.getCoefMatiere();
            }
        }
        moyenneUv =  moyenne / sommeCoef;
    }

    public void setCoefMatieresEffectuees(){
        Integer moyenne = 0;
        Integer sommeCoef = 0;
        for (Matiere matiere: lstMatieres) {
            if(matiere.isMatiereTerminer()) {
                sommeCoef += matiere.getCoefEvaluationsEffectuees();
            }
        }
        coefMatieresEffectuees = sommeCoef==0?-1:sommeCoef;
    }

    public void setCoefMatieresTotales(){
        Integer sommeCoef = 0;
        for (Matiere matiere: lstMatieres) {
            sommeCoef += matiere.getCoefEvaluationsEffectuees();
        }
        coefMatieresTotales = sommeCoef;
    }

    public void setCoefMatieresRaf(){
        coefMatieresRaf = coefMatieresTotales - coefMatieresEffectuees;
    }

    public Boolean isUvTerminer()
    {
        Boolean flag = true;
        for (Matiere matiere: lstMatieres) {
            if(!matiere.isMatiereTerminer()){
                flag = false;
            }
        }
        return flag;
    }
}

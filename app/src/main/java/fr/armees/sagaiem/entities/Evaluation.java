package fr.armees.sagaiem.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Evaluation implements Comparable<Evaluation> {

    private UUID idEvaluation;

    //Attributs
    private Boolean isEffectuer;
    private Integer coefEval;
    private Float note;
    private LocalDateTime dateEvaluation;


    public Evaluation(Integer coefEval, Float note, LocalDateTime dateEvaluation, Boolean isEffectuer) {
        this.coefEval = coefEval;
        this.note = note;
        this.dateEvaluation = dateEvaluation;
        this.isEffectuer = isEffectuer;
    }

    public UUID getIdEvaluation() {
        return idEvaluation;
    }

    private void setIdEvaluation(UUID idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    //GETTER SETTER
    public Boolean isEffectuer() {
        return isEffectuer;
    }

    private void setEffectuer(Boolean effectuer) {
        isEffectuer = effectuer;
    }

    public Integer getCoefEval() {
        return coefEval;
    }

    private void setCoefEval(Integer coefEval) {
        this.coefEval = coefEval;
    }

    public Float getNote() {
        return note;
    }

    private void setNote(Float note) {
        this.note = note;
    }

    public LocalDateTime getDateEvaluation() {
        return dateEvaluation;
    }

    private void setDateEvaluation(LocalDateTime dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }



    @Override
    public int compareTo(Evaluation o) {
        if (getDateEvaluation().isAfter(o.getDateEvaluation())){return 1;}
        else if (getDateEvaluation().isBefore(o.getDateEvaluation())) {return -1;}
        else return 0;
    }
}

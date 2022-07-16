package fr.armees.sagaiem.entities;



import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import java.util.UUID;


public class Stagiaire  extends BaseObservable {

    private ObservableField<String> nom;

    private ObservableField<String> prenom;

    private ObservableField<String> classement;

    private ObservableField<Stage> stage;

    public Stagiaire(String nom, String prenom, String classement, Stage stage) {
        this.nom = new ObservableField<>();
        this.nom.set(nom);
        this.prenom = new ObservableField<>();
        this.prenom.set(prenom);
        this.classement = new ObservableField<>();
        this.classement.set(classement);
        this.stage = new ObservableField<>();
        this.stage.set(stage);
        this.id = UUID.randomUUID();
    }

    private UUID id;

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getClassement(){return classement.get();}

    public Stage getStage(){return stage.get();}

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public Float getMoyenneGenerale(){
        //TODO
        return 10.2f;
    }

}

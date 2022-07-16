package fr.armees.sagaiem.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import fr.armees.sagaiem.entities.Stagiaire;


public class ViewModelShared extends ViewModel{

    private MutableLiveData<Stagiaire> liveDataStagiaire;

    public ViewModelShared() {
        liveDataStagiaire = new MutableLiveData<>();
    }

    public void set(Stagiaire stagiaire){
        liveDataStagiaire.setValue(stagiaire);
    }
    public LiveData<Stagiaire> get() {
        return liveDataStagiaire;
    }
}
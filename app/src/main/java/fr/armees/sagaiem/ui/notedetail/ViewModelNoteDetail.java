package fr.armees.sagaiem.ui.notedetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import fr.armees.sagaiem.entities.Matiere;

public class ViewModelNoteDetail extends ViewModel {

    private MutableLiveData<Matiere> matiere;


    public ViewModelNoteDetail() {
        matiere = new MutableLiveData<>();
    }

    public void setMatiere(Matiere mat){
        matiere.setValue(mat);
    }

    public LiveData<Matiere> getMatiere() {
        return matiere;
    }
}

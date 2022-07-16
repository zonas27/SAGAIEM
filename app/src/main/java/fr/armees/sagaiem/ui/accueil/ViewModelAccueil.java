package fr.armees.sagaiem.ui.accueil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelAccueil extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModelAccueil() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
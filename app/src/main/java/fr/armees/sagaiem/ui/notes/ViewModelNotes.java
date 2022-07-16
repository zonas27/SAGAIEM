package fr.armees.sagaiem.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelNotes extends ViewModel {

    private MutableLiveData<List<Integer>> openededUV;

    public ViewModelNotes() {
        openededUV = new MutableLiveData<>();
        openededUV.setValue(new ArrayList<>());
    }

    public void setOpenededUV(List<Integer> lstOpenedUV){
        openededUV.setValue(lstOpenedUV);
    }

    public LiveData<List<Integer>> getopenededUV() {
        return openededUV;
    }
}
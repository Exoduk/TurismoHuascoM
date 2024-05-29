package com.example.turismohuascom.ui.rutas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RutasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de rutas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

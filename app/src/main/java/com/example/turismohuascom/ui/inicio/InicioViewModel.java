package com.example.turismohuascom.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InicioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InicioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de inicio");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

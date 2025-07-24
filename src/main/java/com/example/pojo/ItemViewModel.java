package com.example.pojo;

import android.media.MediaPlayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<MediaPlayer> selectedItem = new MutableLiveData<>();
    public void selectItem(MediaPlayer item) {
        selectedItem.setValue(item);
    }
    public LiveData<MediaPlayer> getSelectedItem() {
        return selectedItem;
    }
}

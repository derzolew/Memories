package com.memories.maxvi.memories.threadManager;

import android.app.Activity;

public abstract class IProgressCallback<Progress> {
    public abstract void onProgressChanged(Progress progress, Activity activity);
    public void onProgressChanged(Progress progress){
        onProgressChanged(progress, null);
    }
}

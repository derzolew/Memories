package com.memories.maxvi.memories.threadManager;

public abstract class IResultCallback<Result, Progress> extends IProgressCallback<Progress> {
    public abstract void onSuccess(Result result);

    public abstract void onError(Exception exception);
}

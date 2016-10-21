package com.memories.maxvi.memories.threadManager;

public interface IOperation<Params, Progress, Result> {
    Result doOperation(Params params, IProgressCallback<Progress> progressCallback) throws Exception;
}

package com.memories.maxvi.memories.threadManager;

import android.app.Activity;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CoolAsyncTask {
    public static final int COUNT_CORE = 3;
    private final ExecutorService mExecutorService;
    private Activity mLinkedActivity = null;
    private final Handler mHandler;

    public CoolAsyncTask() {
        mExecutorService = Executors.newFixedThreadPool(COUNT_CORE);
        mHandler = new Handler();
    }

    public void linkActivity(Activity activity) {
        mLinkedActivity = activity;
    }

    public void unlinkActivity() {
        mLinkedActivity = null;
    }

    public <Params, Progress, Result> void execute(final IOperation<Params, Progress, Result> operation, final Params param,
                                                   final IResultCallback<Result, Progress> onResultCallback) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Result result = operation.doOperation(param, new IProgressCallback<Progress>() {
                        @Override
                        public void onProgressChanged(final Progress progress, final Activity activity) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onResultCallback.onProgressChanged(progress, mLinkedActivity);
                                }
                            });
                        }
                    });
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onResultCallback.onSuccess(result);
                        }
                    });
                } catch (Exception e) {
                    onResultCallback.onError(e);
                }
            }
        });
    }
}

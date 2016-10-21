package com.memories.maxvi.memories.threadManager.operation;


import android.util.Log;

import com.memories.maxvi.memories.threadManager.IOperation;
import com.memories.maxvi.memories.threadManager.IProgressCallback;

import java.io.IOException;

public class TestOperation implements IOperation<String, Integer, TestOperation.Result> {

    private static final int N = 3;
    public static final String TAG = TestOperation.class.getSimpleName();

    @Override
    public Result doOperation(final String whatYouDoing, final IProgressCallback<Integer> progressCallback) throws Exception {
        for (int i = 0; i < N; i++) {
            try {
                Thread.currentThread().sleep(1000L);
            } catch (InterruptedException e) {
                throw new IOException(e);
        }
            Log.d(TAG, whatYouDoing + i);
            progressCallback.onProgressChanged(i);
        }
        Result result = new Result();
        result.n = N;
        result.result = "I did " + whatYouDoing + N + " sec ";
        return  result;
    }

    public static class Result {
        private int n;

        private String result;

        public int getN() {
            return n;
        }

        public String getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "n=" + n +
                    ", result='" + result + '\'' +
                    '}';
        }


    }

}

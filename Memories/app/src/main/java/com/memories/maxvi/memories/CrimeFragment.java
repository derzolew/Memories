package com.memories.maxvi.memories;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.memories.maxvi.memories.threadManager.CoolAsyncTask;
import com.memories.maxvi.memories.threadManager.IResultCallback;
import com.memories.maxvi.memories.threadManager.operation.TestOperation;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CoolAsyncTask mTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });


        if (mTask == null) {
            mTask = new CoolAsyncTask();
            mTask.linkActivity(getActivity());
            mTask.execute(new TestOperation(), "doing stuff", new IResultCallback<TestOperation.Result, Integer>() {
                @Override
                public void onSuccess(TestOperation.Result result) {
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(getActivity(), "error " + exception, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgressChanged(Integer integer, Activity activity) {
                    Toast.makeText(activity, "progress " + integer, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mTask.linkActivity(getActivity());
        }

        return v;
    }
}
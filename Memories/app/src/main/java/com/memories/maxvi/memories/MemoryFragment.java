package com.memories.maxvi.memories;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.memories.maxvi.memories.threadManager.CoolAsyncTask;

import java.util.UUID;

public class MemoryFragment extends Fragment {

    private static final String ARG_MEMORY_ID = "com.memories.maxvi.memories.memory_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Memory mMemory;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CoolAsyncTask mTask;

    public static MemoryFragment newInstance(UUID memoryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEMORY_ID, memoryId);

        MemoryFragment fragment = new MemoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID memoryId = (UUID) getActivity().getIntent().getSerializableExtra(ARG_MEMORY_ID);
        mMemory = MemoryLab.get(getActivity()).getMemory(memoryId);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memory, container, false);

        mTitleField = (EditText) v.findViewById(R.id.memory_title);
        mTitleField.setText(mMemory.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMemory.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.memory_date);
        mDateButton.setText(mMemory.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mMemory.getDate());
                dialog.setTargetFragment(MemoryFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.memory_solved);
        mSolvedCheckBox.setChecked(mMemory.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMemory.setSolved(isChecked);
            }
        });


        /*if (mTask == null) {
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
*/
        return v;
    }
}
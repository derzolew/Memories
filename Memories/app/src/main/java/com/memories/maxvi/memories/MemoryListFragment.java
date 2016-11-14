package com.memories.maxvi.memories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MemoryListFragment extends Fragment {
    private RecyclerView mMemoryRecyclerView;
    private MemoryAdapter mMemoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory_list, container, false);
        mMemoryRecyclerView = (RecyclerView) view.findViewById(R.id.memory_recycler_view);
        mMemoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        MemoryLab memoryLab = MemoryLab.get(getActivity());
        final List<Memory> memories = memoryLab.getMemories();

        if (mMemoryAdapter == null) {
            mMemoryAdapter = new MemoryAdapter(memories);
            mMemoryRecyclerView.setAdapter(mMemoryAdapter);
        }
        else {
            //TODO reload exact list item
            mMemoryAdapter.notifyDataSetChanged();
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                memories.remove(viewHolder.getAdapterPosition());
                mMemoryRecyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mMemoryRecyclerView);
    }

    private class MemoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Memory mMemory;

        public MemoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_memory_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_memory_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_memory_solved_check_box);
        }

        public void bindMemory(Memory memory) {
            mMemory = memory;
            mTitleTextView.setText(mMemory.getTitle());
            mDateTextView.setText(mMemory.getDate().toString());
            mSolvedCheckBox.setChecked(mMemory.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MemoryPagerActivity.newIntent(getActivity(), mMemory.getId());
            startActivity(intent);
        }
    }

    private class MemoryAdapter extends RecyclerView.Adapter<MemoryHolder> {
        private List<Memory> mMemories;

        public MemoryAdapter(List<Memory> memories) {
            mMemories = memories;
        }

        @Override
        public MemoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_memory, parent, false);
            return new MemoryHolder(view);
        }

        @Override
        public void onBindViewHolder(MemoryHolder holder, int position) {
            Memory memory = mMemories.get(position);
            holder.bindMemory(memory);
        }

        @Override
        public int getItemCount() {
            return mMemories.size();
        }
    }
}

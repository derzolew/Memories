package com.memories.maxvi.memories;

import android.support.v4.app.Fragment;

public class MemoryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MemoryListFragment();
    }
}

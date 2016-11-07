package com.memories.maxvi.memories;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class MemoryActivity extends SingleFragmentActivity {

    private static final String EXTRA_MEMORY_ID = "com.memories.maxvi.memories.memory_id";

    public static Intent newIntent(Context context, UUID CrimeId) {
        Intent intent = new Intent(context, MemoryActivity.class);
        intent.putExtra(EXTRA_MEMORY_ID, CrimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID memoryId = (UUID) getIntent().getSerializableExtra(EXTRA_MEMORY_ID);
        return MemoryFragment.newInstance(memoryId);
    }
}


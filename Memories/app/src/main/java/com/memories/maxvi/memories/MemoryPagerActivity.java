package com.memories.maxvi.memories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

public class MemoryPagerActivity extends FragmentActivity {

    private static final String EXTRA_MEMORY_ID = "com.memories.maxvi.memories.memory_id";

    private ViewPager mViewPager;
    private List<Memory> mMemories;

    public static Intent newIntent(Context packageContext, UUID memoryId) {
        Intent intent = new Intent(packageContext, MemoryPagerActivity.class);
        intent.putExtra(EXTRA_MEMORY_ID, memoryId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_pager);

        UUID memoryId = (UUID) getIntent().getSerializableExtra(EXTRA_MEMORY_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_memory_view_pager);
        mMemories = MemoryLab.get(this).getMemories();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Memory memory = mMemories.get(position);
                return MemoryFragment.newInstance(memory.getId());
            }

            @Override
            public int getCount() {
                return mMemories.size();
            }
        });

        for (int i = 0; i < mMemories.size(); i++) {
            if (mMemories.get(i).getId().equals(memoryId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}

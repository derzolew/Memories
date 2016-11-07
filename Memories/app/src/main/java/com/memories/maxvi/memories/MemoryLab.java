package com.memories.maxvi.memories;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryLab {
    private static MemoryLab sMemoryLab;
    private List<Memory> mMemories;

    public static MemoryLab get(Context context) {
        if (sMemoryLab == null) {
            sMemoryLab = new MemoryLab(context);
        }
        return sMemoryLab;
    }
    private MemoryLab(Context context) {
        mMemories = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Memory memory = new Memory();
            memory.setTitle("Memory #" + i);
            memory.setSolved(i % 2 == 0);
            mMemories.add(memory);
        }
    }

    public List<Memory> getMemories() {
        return mMemories;
    }

    public Memory getCrime(UUID id) {
        for (Memory memory : mMemories) {
            if (memory.getId().equals(id)) {
                return memory;
            }
        }
        return null;
    }
}

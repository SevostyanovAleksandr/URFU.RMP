package com.example.android.roomwordssample;


import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.executor.TaskExecutor;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Правило тестирования JUnit, которое заменяет фоновый исполнитель, используемый компонентами архитектуры, на
 * другой, который выполняет каждую задачу синхронно.
 * Вы можете использовать это правило для тестов на стороне хоста, которые используют компоненты архитектуры.
 */
public class InstantTaskExecutorRule extends TestWatcher {
    @Override
    protected void starting(Description description) {
        super.starting(description);
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }
        });
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        ArchTaskExecutor.getInstance().setDelegate(null);
    }
}
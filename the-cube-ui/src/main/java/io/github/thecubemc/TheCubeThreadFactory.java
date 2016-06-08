package io.github.thecubemc;

import com.google.inject.Singleton;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
final class TheCubeThreadFactory
implements ThreadFactory{
  private final ThreadGroup group = new ThreadGroup("TheCube");
  private final AtomicInteger count = new AtomicInteger(0);

  @Override
  public Thread newThread(Runnable runnable) {
    return new Thread(this.group, runnable, "Thread-" + count.incrementAndGet());
  }
}
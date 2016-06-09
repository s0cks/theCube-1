package io.github.thecubemc.boot.sequences;

import com.google.inject.Inject;
import io.github.thecubemc.TheCube;
import io.github.thecubemc.account.AccountFactory;
import io.github.thecubemc.boot.BootDependency;
import io.github.thecubemc.boot.BootResult;

import java.io.IOException;

@BootDependency("SetupFileSystem")
final class ResolveProfileTask {
  private final AccountFactory factory;

  @Inject
  private ResolveProfileTask(AccountFactory factory){
    this.factory = factory;
  }

  public BootResult doTask()
  throws IOException {
    this.factory.login(TheCube.injector, "asyncronous16@gmail.com");
    System.out.println("Logged in");
    return BootResult.PASS;
  }
}
package io.github.thecubemc.aspects;

import io.github.thecubemc.TheCube;
import io.github.thecubemc.account.AccountStub;
import io.github.thecubemc.annotations.RequiresAccount;
import io.github.thecubemc.exception.NotLoggedInException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public final class RequiresAccountAspect {
  @Before("execution(* *(..)) && @annotation(req)")
  public void before(JoinPoint point, RequiresAccount req)
  throws NotLoggedInException {
    AccountStub stub = TheCube.injector.getInstance(AccountStub.class);
    if(stub.getUsername().equals("Default")){
      throw new NotLoggedInException();
    }
  }
}
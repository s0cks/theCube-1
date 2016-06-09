package io.github.thecubemc.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public final class RequiresAccountInterceptor
implements MethodInterceptor{
  @Override
  public Object invoke(MethodInvocation invocation)
  throws Throwable {
    return invocation.proceed();
  }
}
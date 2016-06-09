package io.github.thecubemc.aspects;

import io.github.thecubemc.TheCube;
import io.github.thecubemc.annotations.Repaint;
import io.github.thecubemc.ui.TheCubeFrame;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public final class RepaintAspect {
  @After("execution(* *(..)) && @annotation(rep)")
  public void after(JoinPoint point, Repaint rep) {
    TheCube.injector.getInstance(TheCubeFrame.class)
                    .repaint();
  }
}
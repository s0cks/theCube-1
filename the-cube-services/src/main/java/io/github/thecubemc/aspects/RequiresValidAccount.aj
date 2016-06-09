package io.github.thecubemc.aspects;

public aspect RequiresValidAccount{
  pointcut any(): execution(* *.*(..));

  before(): any(){
    System.out.println("Test");
  }
}
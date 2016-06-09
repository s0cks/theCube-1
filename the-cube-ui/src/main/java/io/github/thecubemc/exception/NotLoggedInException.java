package io.github.thecubemc.exception;

public final class NotLoggedInException
extends Exception{
  public NotLoggedInException(){
    super("Not logged in");
  }
}
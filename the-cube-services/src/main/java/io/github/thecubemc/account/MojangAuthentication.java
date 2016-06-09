package io.github.thecubemc.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import java.net.Proxy;

final class MojangAuthentication{
  public Account login(Account acc){
    System.out.println("Getting authentication");
    YggdrasilUserAuthentication auth = null;
    try{
      auth = ((YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1").createUserAuthentication(Agent.MINECRAFT));
    } catch(Exception e){
      e.printStackTrace(System.err);
      return acc;
    }

    System.out.println("Logging in with: " + acc.getUsername());
    try{
      if(auth.isLoggedIn()){
        auth.logOut();
      }

      auth.setUsername(acc.getUsername());
      auth.setPassword(acc.getPassword());
      auth.logIn();
      acc.setAccessToken(auth.getAuthenticatedToken());
      acc.setUUID(auth.getSelectedProfile().getId().toString());
      System.out.println("Logged in with: " + acc.getUsername());
    } catch(AuthenticationException e){
      // TODO: Toaster Message
      e.printStackTrace(System.err);
    }

    return acc;
  }
}
package io.github.thecubemc.boot.sequences;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.thecubemc.boot.BootDependency;
import io.github.thecubemc.boot.BootResult;
import io.github.thecubemc.http.Network;
import io.github.thecubemc.model.response.MojangProfileResponse;
import io.github.thecubemc.ui.TheCubeSplashScreen;

import java.io.IOException;

@BootDependency("SetupFileSystem")
final class ResolveProfileTask {
  private final Network network;
  private final TheCubeSplashScreen splash;
  private final Gson gson;

  @Inject
  private ResolveProfileTask(Network network, TheCubeSplashScreen splash, Gson gson) {
    this.splash = splash;
    this.network = network;
    this.gson = gson;
  }

  public BootResult doTask()
  throws IOException {
    Request req = new Request.Builder()
                  .addHeader("User-Agent", this.network.userAgent())
                  .addHeader("Expires", "0")
                  .url("https://api.mojang.com/users/profiles/minecraft/Asyncronous")
                  .build();
    System.out.println("Calling");
    Response resp = this.network.call(req)
                                .execute();
    if(resp.code() != 200){
      System.out.println(resp.body().string());
      return BootResult.FAIL;
    }

    MojangProfileResponse profile = this.gson.fromJson(resp.body().charStream(), MojangProfileResponse.class);
    System.out.println("Profile: " + profile.id);
    return resp.isSuccessful() ? BootResult.PASS : BootResult.FAIL;
  }
}
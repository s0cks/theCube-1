package io.github.thecubemc.http;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public abstract class Network{
  private final OkHttpClient client = new OkHttpClient();

  protected Network(){}

  public final Call call(Request request){
    return this.client.newCall(request);
  }

  public abstract String userAgent();
}
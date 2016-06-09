package io.github.thecubemc.account;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Injector;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.github.thecubemc.http.Network;
import io.github.thecubemc.model.mojang.api.NameHistory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public final class AccountStub {
  private final Account owner;
  private String minecraftUsername;

  protected AccountStub(Account owner) {
    this.owner = owner;
  }

  public String getUsername() {
    return this.owner == null
           ? "Default"
           : this.owner.getUsername();
  }

  public String getUUID() {
    return this.owner == null
           ? "0"
           : this.owner.getUUID();
  }

  public String getMinecraftUsername(Injector injector)
  throws IOException {
    if (this.minecraftUsername == null) {
      if (this.owner == null) return "Default";
      Network network = injector.getInstance(Network.class);
      Request req = new Request.Builder().addHeader("User-Agent", network.userAgent())
                                         .url("https://api.mojang.com/user/profiles/" + this.getUUID().replace("-", "") + "/names")
                                         .build();
      Response resp = network.call(req)
                             .execute();
      Type type = new TypeToken<List<NameHistory>>() {}.getType();

      List<NameHistory> history = injector.getInstance(Gson.class)
                                          .fromJson(resp.body()
                                                        .charStream(), type);
      if (history == null) return "Default";
      if (history.size() == 1) return history.get(0).name;

      long time = 0;
      for (NameHistory name : history) {
        if (!name.isChange()) {
          return name.name;
        } else if (time < name.changedToAt) {
          time = name.changedToAt;
        }
      }

      return (this.minecraftUsername = history.get(0).name);
    }

    return this.minecraftUsername;
  }
}

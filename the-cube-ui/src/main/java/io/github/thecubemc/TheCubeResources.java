package io.github.thecubemc;

import io.github.thecubemc.annotations.TheCubeResource;

import java.io.Serializable;
import java.lang.annotation.Annotation;

final class TheCubeResources{
  public static TheCubeResource resource(String path) {
    return new TheCubeResourceImpl(path);
  }

  private static final class TheCubeResourceImpl
  implements TheCubeResource,
             Serializable {
    private final String path;

    private TheCubeResourceImpl(String path) {
      this.path = path;
    }

    @Override
    public String value() {
      return this.path;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof TheCubeResource)) return false;
      TheCubeResource resource = ((TheCubeResource) obj);
      return resource.value()
                     .equals(this.value());
    }

    @Override
    public int hashCode() {
      return (127 * "value".hashCode() ^ this.path.hashCode());
    }

    @Override
    public String toString() {
      return "@" + TheCubeResource.class.getName() + "(value=" + this.path + ")";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return TheCubeResource.class;
    }
  }
}
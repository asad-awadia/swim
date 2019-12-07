// Copyright 2015-2019 SWIM.AI inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package swim.dynamic.structure;

import swim.dynamic.*;
import swim.structure.*;

public final class HostAttr {
  private HostAttr() {
    // static
  }

  public static final HostObjectType<Attr> TYPE;

  static {
    final JavaHostClassType<Attr> type = new JavaHostClassType<>(Attr.class);
    TYPE = type;
    type.extendType(HostField.TYPE);
    type.addMember(new HostAttrName());
    type.addMember(new HostAttrCompareTo());
    type.addStaticMember(new HostAttrOf());
  }
}

final class HostAttrName implements swim.dynamic.HostField<Attr> {
  @Override
  public String key() {
    return "name";
  }

  @Override
  public Object get(Bridge bridge, Attr attr) {
    return attr.name();
  }
}

final class HostAttrCompareTo implements HostMethod<Attr> {
  @Override
  public String key() {
    return "compareTo";
  }

  @Override
  public Object invoke(Bridge bridge, Attr attr, Object... arguments) {
    return attr.compareTo((Attr) arguments[0]);
  }
}

final class HostAttrOf implements HostStaticMethod {
  @Override
  public String key() {
    return "of";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments.length == 1) {
      Object key = arguments[0];
      if (key == null) {
        throw new NullPointerException("key");
      }
      else if (key instanceof String) {
        key = Text.from((String) key);
      }
      return Attr.of((Text) key, Value.extant());
    }
    else {
      Object key = arguments[0];
      Object value = arguments[1];

      if (key == null) {
        throw new NullPointerException("key");
      }
      if (value == null) {
        throw new NullPointerException("value");
      }
      else {
        if (key instanceof String) {
          key = Text.from((String) key);
        }
        if (value instanceof String) {
          value = Text.from((String) value);
        } else if (value instanceof Number) {
          value = Num.from((Number) value);
        } else if (value instanceof Boolean) {
          value = Bool.from((Boolean) value);
        }
        return Attr.of((Text) key, (Value) value);
      }
    }
  }
}

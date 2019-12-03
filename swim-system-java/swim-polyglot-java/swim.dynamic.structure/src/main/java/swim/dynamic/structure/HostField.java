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
import swim.structure.Field;
import swim.structure.Value;

import java.util.Map;

public final class HostField {
  private HostField() {
    // static
  }

  public static final HostObjectType<Field> TYPE;

  static {
    final JavaHostClassType<Field> type = new JavaHostClassType<>(Field.class);
    TYPE = type;
    type.extendType(HostItem.TYPE);
    type.addMember(new HostFieldValue());
    type.addMember(new HostFieldUpdatedValue());
    type.addMember(new HostFieldConditional());
    type.addMember(new HostFieldOr());
    type.addMember(new HostFieldAnd());
    type.addStaticMember(new HostFieldOf());
  }
}

final class HostFieldValue implements swim.dynamic.HostField<Field> {
  @Override
  public String key() {
    return "value";
  }

  @Override
  public Object get(Bridge bridge, Field field) {
    return field.value();
  }
}

final class HostFieldUpdatedValue implements HostMethod<Field> {
  @Override
  public String key() {
    return "updatedValue";
  }

  @Override
  public Object invoke(Bridge bridge, Field field, Object... arguments) {
    return field.updatedValue((Value) arguments[0]);
  }
}

final class HostFieldConditional implements HostMethod<Field> {
  @Override
  public String key() {
    return "conditional";
  }

  @Override
  public Object invoke(Bridge bridge, Field field, Object... arguments) {
    return field.conditional((Field) arguments[0], (Field) arguments[1]);
  }
}

final class HostFieldOr implements HostMethod<Field> {
  @Override
  public String key() {
    return "or";
  }

  @Override
  public Object invoke(Bridge bridge, Field field, Object... arguments) {
    return field.or((Field) arguments[0]);
  }
}

final class HostFieldAnd implements HostMethod<Field> {
  @Override
  public String key() {
    return "and";
  }

  @Override
  public Object invoke(Bridge bridge, Field field, Object... arguments) {
    return field.and((Field) arguments[0]);
  }
}

final class HostFieldOf implements HostStaticMethod {
  @Override
  public String key() {
    return "of";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments[0] instanceof Field) {
      return Field.of(arguments[0]);
    } else if (arguments[0] instanceof Map) {
      return Field.of(arguments[0]);
    } else {
      throw new IllegalArgumentException(arguments[0].toString());
    }
  }
}
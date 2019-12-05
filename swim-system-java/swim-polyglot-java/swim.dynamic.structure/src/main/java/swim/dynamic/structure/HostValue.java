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
import swim.structure.Value;

public final class HostValue {
  private HostValue() {
    // static
  }

  public static final HostObjectType<Value> TYPE;

  static {
    final JavaHostClassType<Value> type = new JavaHostClassType<>(Value.class);
    TYPE = type;
    type.extendType(HostItem.TYPE);
    type.addMember(new HostValueConditional());
    type.addMember(new HostValueOr());
    type.addMember(new HostValueAnd());
    type.addMember(new HostValueBitwiseOr());
    type.addMember(new HostValueBitwiseXor());
    type.addMember(new HostValueBitwiseAnd());
    type.addMember(new HostValueLt());
    type.addMember(new HostValueLe());
    type.addMember(new HostValueEq());
    type.addMember(new HostValueNe());
    type.addMember(new HostValueGe());
    type.addMember(new HostValueGt());
    type.addMember(new HostValuePlus());
    type.addMember(new HostValueMinus());
    type.addMember(new HostValueTimes());
    type.addMember(new HostValueDivide());
    type.addMember(new HostValueModulo());
    type.addStaticMember(new HostValueBuilder());
    type.addStaticMember(new HostValueEmpty());
    type.addStaticMember(new HostValueExtant());
    type.addStaticMember(new HostValueAbsent());
    type.addStaticMember(new HostValueFromObject());
  }
}
final class HostValueConditional implements HostMethod<Value> {
  @Override
  public String key() {
    return "conditional";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.conditional((Value) arguments[0], (Value) arguments[1]);
  }
}

final class HostValueOr implements HostMethod<Value> {
  @Override
  public String key() {
    return "or";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.or((Value) arguments[0]);
  }
}

final class HostValueAnd implements HostMethod<Value> {
  @Override
  public String key() {
    return "and";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.and((Value) arguments[0]);
  }
}

final class HostValueBitwiseOr implements HostMethod<Value> {
  @Override
  public String key() {
    return "bitwiseOr";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.bitwiseOr((Value) arguments[0]);
  }
}

final class HostValueBitwiseXor implements HostMethod<Value> {
  @Override
  public String key() {
    return "bitwiseXor";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.bitwiseXor((Value) arguments[0]);
  }
}

final class HostValueBitwiseAnd implements HostMethod<Value> {
  @Override
  public String key() {
    return "bitwiseAnd";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.bitwiseAnd((Value) arguments[0]);
  }
}

final class HostValueLt implements HostMethod<Value> {
  @Override
  public String key() {
    return "lt";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.lt((Value) arguments[0]);
  }
}

final class HostValueLe implements HostMethod<Value> {
  @Override
  public String key() {
    return "le";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.le((Value) arguments[0]);
  }
}

final class HostValueEq implements HostMethod<Value> {
  @Override
  public String key() {
    return "eq";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.eq((Value) arguments[0]);
  }
}

final class HostValueNe implements HostMethod<Value> {
  @Override
  public String key() {
    return "ne";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.ne((Value) arguments[0]);
  }
}

final class HostValueGe implements HostMethod<Value> {
  @Override
  public String key() {
    return "ge";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.ge((Value) arguments[0]);
  }
}

final class HostValueGt implements HostMethod<Value> {
  @Override
  public String key() {
    return "gt";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.gt((Value) arguments[0]);
  }
}

final class HostValuePlus implements HostMethod<Value> {
  @Override
  public String key() {
    return "plus";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.plus((Value) arguments[0]);
  }
}

final class HostValueMinus implements HostMethod<Value> {
  @Override
  public String key() {
    return "minus";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.minus((Value) arguments[0]);
  }
}

final class HostValueTimes implements HostMethod<Value> {
  @Override
  public String key() {
    return "times";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.times((Value) arguments[0]);
  }
}

final class HostValueDivide implements HostMethod<Value> {
  @Override
  public String key() {
    return "divide";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.divide((Value) arguments[0]);
  }
}

final class HostValueModulo implements HostMethod<Value> {
  @Override
  public String key() {
    return "modulo";
  }

  @Override
  public Object invoke(Bridge bridge, Value value, Object... arguments) {
    return value.modulo((Value) arguments[0]);
  }
}

final class HostValueBuilder implements HostStaticMethod {
  @Override
  public String key() {
    return "builder";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Value.builder();
  }
}

final class HostValueEmpty implements HostStaticMethod {
  @Override
  public String key() {
    return "empty";
  }

  @Override
  public Object invoke (Bridge bridge, Object... arguments) {
    return Value.empty();
  }
}

final class HostValueExtant implements HostStaticMethod {
  @Override
  public String key() {
    return "extant";
  }

  @Override
  public Object invoke (Bridge bridge, Object... arguments) {
    return Value.extant();
  }
}

final class HostValueAbsent implements HostStaticMethod {
  @Override
  public String key() {
    return "absent";
  }

  @Override
  public Object invoke (Bridge bridge, Object... arguments) {
    return Value.absent();
  }
}

final class HostValueFromObject implements HostStaticMethod {
  @Override
  public String key() {
    return "fromObject";
  }

  @Override
  public Object invoke (Bridge bridge, Object... arguments) {
    return Value.fromObject(arguments[0]);
  }
}
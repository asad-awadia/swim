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
import swim.structure.Bool;
import swim.structure.Value;

public final class HostBool {
  private HostBool() {
    // static
  }

  public static final HostObjectType<Bool> TYPE;

  static {
    final JavaHostClassType<Bool> type = new JavaHostClassType<>(Bool.class);
    TYPE = type;
    type.extendType(HostValue.TYPE);
    type.addMember(new HostBoolConditional());
    type.addMember(new HostBoolOr());
    type.addMember(new HostBoolAnd());
    type.addMember(new HostBoolCompareTo());
    type.addStaticMember(new HostBoolFrom());
  }
}

final class HostBoolConditional implements HostMethod<Bool> {
  @Override
  public String key() {
    return "conditional";
  }

  @Override
  public Object invoke(Bridge bridge, Bool bool, Object... arguments) {
    return bool.conditional((Value) arguments[0], (Value) arguments[1]);
  }
}

final class HostBoolOr implements HostMethod<Bool> {
  @Override
  public String key() { return  "or"; }

  @Override
  public Object invoke(Bridge bridge, Bool bool, Object... arguments) {
    return bool.or((Value) arguments[0]);
  }
}

final class HostBoolAnd implements HostMethod<Bool> {
  @Override
  public String key() {return "and"; }

  @Override
  public Object invoke(Bridge bridge, Bool bool, Object... arguments) {
    return bool.and((Value) arguments[0]);
  }
}

final class HostBoolCompareTo implements HostMethod<Bool> {
  @Override
  public String key() {return "compareTo"; }

  @Override
  public Object invoke(Bridge bridge, Bool bool, Object... arguments) {
    return bool.compareTo((Bool) arguments[0]);
  }
}

final class HostBoolFrom implements HostStaticMethod {
  @Override
  public String key() { return "from"; }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Bool.from((boolean) arguments[0]);
  }
}

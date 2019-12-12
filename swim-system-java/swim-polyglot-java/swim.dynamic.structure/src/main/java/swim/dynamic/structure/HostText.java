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

import swim.codec.OutputSettings;
import swim.dynamic.*;
import swim.dynamic.HostField;
import swim.structure.Text;

public final class HostText {
  private HostText() {
    // static
  }

  public static final HostObjectType<Text> TYPE;

  static {
    final JavaHostClassType<Text> type = new JavaHostClassType<>(Text.class);
    TYPE = type;
    type.extendType(HostValue.TYPE);
    type.addMember(new HostTextSize());
    type.addStaticMember(new HostTextOutput());
    type.addStaticMember(new HostTextEmpty());
    type.addStaticMember(new HostTextFrom());
    type.addStaticMember(new HostTextFromObject());
  }
}

final class HostTextSize implements HostField<Text> {
  @Override
  public String key() {
    return "size";
  }

  @Override
  public Object get(Bridge bridge, Text text) {
    return text.size();
  }
}

final class HostTextOutput implements HostStaticMethod {
  @Override
  public String key() {
    return "output";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments.length != 0) {
      return Text.output((OutputSettings) arguments[0]);
    }
    return Text.output();
  }
}

final class HostTextEmpty implements HostStaticMethod {
  @Override
  public String key() {
    return "empty";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Text.empty();
  }
}

final class HostTextFrom implements HostStaticMethod {
  @Override
  public String key() {
    return "from";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Text.from((String) arguments[0]);
  }
}

final class HostTextFromObject implements HostStaticMethod {
  @Override
  public String key() {
    return "fromObject";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Text.fromObject(arguments[0]);
  }
}

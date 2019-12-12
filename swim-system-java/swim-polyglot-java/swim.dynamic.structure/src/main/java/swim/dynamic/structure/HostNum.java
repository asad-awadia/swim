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
import swim.structure.Num;
import swim.structure.Text;

public final class HostNum {
  private HostNum() {
    // static
  }

  public static final HostObjectType<Num> TYPE;

  static {
    final JavaHostClassType<Num> type = new JavaHostClassType<>(Num.class);
    TYPE = type;
    type.extendType(HostValue.TYPE);
    type.addMember(new HostNumIsUint32());
    type.addMember(new HostNumIsUint64());
    type.addMember(new HostNumIsNaN());
    type.addMember(new HostNumIsInfinite());
    type.addMember(new HostNumIsValidByte());
    type.addMember(new HostNumIsValidShort());
    type.addMember(new HostNumIsValidInt());
    type.addMember(new HostNumIsValidLong());
    type.addMember(new HostNumIsValidFloat());
    type.addMember(new HostNumIsValidDouble());
    type.addMember(new HostNumIsValidInteger());
    type.addMember(new HostNumBitwiseOr());
    type.addMember(new HostNumBitwiseXor());
    type.addMember(new HostNumBitwiseAnd());
    type.addMember(new HostNumPlus());
    type.addMember(new HostNumMinus());
    type.addMember(new HostNumTimes());
    type.addMember(new HostNumDivide());
    type.addMember(new HostNumModulo());
    type.addMember(new HostNumAbs());
    type.addMember(new HostNumCeil());
    type.addMember(new HostNumFloor());
    type.addMember(new HostNumRound());
    type.addMember(new HostNumSqrt());
    type.addMember(new HostNumPow());
    type.addMember(new HostNumMax());
    type.addMember(new HostNumMin());
    type.addMember(new HostNumCompareTo());
    type.addMember(new HostNumEquals());
    type.addStaticMember(new HostNumFrom());
    type.addStaticMember(new HostNumUint32());
    type.addStaticMember(new HostNumUint64());
  }
}

final class HostNumIsUint32 implements HostMethod<Num> {
  @Override
  public String key() {
    return "isUint32";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isUint32();
  }
}

final class HostNumIsUint64 implements HostMethod<Num> {
  @Override
  public String key() {
    return "isUint64";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isUint64();
  }
}

final class HostNumIsNaN implements HostMethod<Num> {
  @Override
  public String key() {
    return "isNaN";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isNaN();
  }
}

final class HostNumIsInfinite implements HostMethod<Num> {
  @Override
  public String key() {
    return "isInfinite";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isInfinite();
  }
}

final class HostNumIsValidByte implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidByte";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidByte();
  }
}

final class HostNumIsValidShort implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidShort";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidShort();
  }
}

final class HostNumIsValidInt implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidInt";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidInt();
  }
}

final class HostNumIsValidLong implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidLong";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidLong();
  }
}

final class HostNumIsValidFloat implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidFloat";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidFloat();
  }
}

final class HostNumIsValidDouble implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidDouble";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidDouble();
  }
}

final class HostNumIsValidInteger implements HostMethod<Num> {
  @Override
  public String key() {
    return "isValidInteger";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.isValidInteger();
  }
}

final class HostNumBitwiseOr implements HostMethod<Num> {
  @Override
  public String key() {
    return "bitwiseOr";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.bitwiseOr((Num) arguments[0]);
  }
}

final class HostNumBitwiseXor implements HostMethod<Num> {
  @Override
  public String key() {
    return "bitwiseXor";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.bitwiseXor((Num) arguments[0]);
  }
}

final class HostNumBitwiseAnd implements HostMethod<Num> {
  @Override
  public String key() {
    return "bitwiseAnd";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.bitwiseAnd((Num) arguments[0]);
  }
}

final class HostNumPlus implements HostMethod<Num> {
  @Override
  public String key() {
    return "plus";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.plus((Num) arguments[0]);
  }
}

final class HostNumMinus implements HostMethod<Num> {
  @Override
  public String key() {
    return "minus";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.minus((Num) arguments[0]);
  }
}

final class HostNumTimes implements HostMethod<Num> {
  @Override
  public String key() {
    return "minus";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.minus((Num) arguments[0]);
  }
}

final class HostNumDivide implements HostMethod<Num> {
  @Override
  public String key() {
    return "divide";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.divide((Num) arguments[0]);
  }
}

final class HostNumModulo implements HostMethod<Num> {
  @Override
  public String key() {
    return "modulo";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.modulo((Num) arguments[0]);
  }
}


final class HostNumAbs implements HostMethod<Num> {
  @Override
  public String key() {
    return "abs";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.abs();
  }
}

final class HostNumCeil implements HostMethod<Num> {
  @Override
  public String key() {
    return "ceil";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.ceil();
  }
}

final class HostNumFloor implements HostMethod<Num> {
  @Override
  public String key() {
    return "floor";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.floor();
  }
}

final class HostNumRound implements HostMethod<Num> {
  @Override
  public String key() {
    return "round";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.round();
  }
}


final class HostNumSqrt implements HostMethod<Num> {
  @Override
  public String key() {
    return "sqrt";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.sqrt();
  }
}

final class HostNumPow implements HostMethod<Num> {
  @Override
  public String key() {
    return "pow";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.pow((Num) arguments[0]);
  }
}

final class HostNumMax implements HostMethod<Num> {
  @Override
  public String key() {
    return "max";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.max((Num) arguments[0]);
  }
}

final class HostNumMin implements HostMethod<Num> {
  @Override
  public String key() {
    return "min";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.min((Num) arguments[0]);
  }
}

final class HostNumCompareTo implements HostMethod<Num> {
  @Override
  public String key() {
    return "compareTo";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.compareTo((Num) arguments[0]);
  }
}

final class HostNumEquals implements HostMethod<Num> {
  @Override
  public String key() {
    return "equals";
  }

  @Override
  public Object invoke(Bridge bridge,  Num num, Object... arguments) {
    return num.equals((Num) arguments[0]);
  }
}

final class HostNumFrom implements HostStaticMethod {
  @Override
  public String key() {
    return "from";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    Object object = arguments[0];
    if (object instanceof Number) {
      object = Num.from((Number) object);
      return Num.from((Number) object);
    } else if (object instanceof String) {
      object = Text.from((String) object);
      return Num.from((String) object);
    }
    return null;
  }
}

final class HostNumUint32 implements HostStaticMethod {
  @Override
  public String key() {
    return "uint32";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Num.uint32((int) arguments[0]);
  }
}

final class HostNumUint64 implements HostStaticMethod {
  @Override
  public String key() {
    return "uint64";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Num.uint64((long) arguments[0]);
  }
}
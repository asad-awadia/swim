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

import swim.codec.Base16;
import swim.codec.Base64;
import swim.codec.Output;
import swim.dynamic.*;
import swim.dynamic.HostField;
import swim.structure.Data;
import swim.structure.Num;
import java.nio.ByteBuffer;

public final class HostData {
  private HostData() {
    // static
  }

  public static final HostObjectType<Data> TYPE;

  static {
    final JavaHostClassType<Data> type = new JavaHostClassType<>(Data.class);
    TYPE = type;
    type.extendType(HostValue.TYPE);
    type.addMember(new HostDataSize());
    type.addMember(new HostDataGetByte());
    type.addMember(new HostDataSetByte());
    type.addMember(new HostDataAddByte());
    type.addMember(new HostDataAddByteArray());
    type.addMember(new HostDataAddData());
    type.addMember(new HostDataClear());
    type.addMember(new HostDataToByteArray());
    type.addMember(new HostDataAsByteArray());
    type.addMember(new HostDataToByteBuffer());
    type.addMember(new HostDataAsByteBuffer());
    type.addMember(new HostDataToInputBuffer());
    type.addMember(new HostDataWriter());
    type.addMember(new HostDataWrite());
    type.addMember(new HostDataWriteBase16());
    type.addMember(new HostDataToBase16());
    type.addMember(new HostDataWriteBase64());
    type.addMember(new HostDataToBase64());
    type.addMember(new HostDataCompareTo());
    type.addStaticMember(new HostDataOutput());
    type.addStaticMember(new HostDataEmpty());
    type.addStaticMember(new HostDataCreate());
    type.addStaticMember(new HostDataWrap());
    type.addStaticMember(new HostDataFrom());
    type.addStaticMember(new HostDataFromBase16());
    type.addStaticMember(new HostDataFromBase64());
    type.addStaticMember(new HostDataFromUtf8());
  }
}

final class HostDataSize implements HostField<Data> {
  @Override
  public String key() {
    return "size";
  }

  @Override
  public Object get(Bridge bridge, Data data) {
    return data.size();
  }
}

final class HostDataGetByte implements HostMethod<Data> {
  @Override
  public String key() {
    return "getByte";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.getByte((int) arguments[0]);
  }
}

final class HostDataSetByte implements HostMethod<Data> {
  @Override
  public String key() {
    return "setByte";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.setByte((int) arguments[0], (byte) arguments[1]);
  }
}

final class HostDataAddByte implements HostMethod<Data> {
  @Override
  public String key() {
    return "addByte";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.addByte((byte) arguments[0]);
  }
}

final class HostDataAddByteArray implements HostMethod<Data> {
  @Override
  public String key() {
    return "addByteArray";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    if (arguments.length == 1) {
      return data.addByteArray((byte[]) arguments[0]);
    }
    else return data.addByteArray((byte[]) arguments[0], (int) arguments[1], (int) arguments[2]);
  }
}

final class HostDataAddData implements HostMethod<Data> {
  @Override
  public String key() {
    return "addData";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
   return data.addData((Data) arguments[0]);
  }
}

final class HostDataClear implements HostMethod<Data> {
  @Override
  public String key() {
    return "clear";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    data.clear();
    return null;
  }
}

final class HostDataToByteArray implements HostMethod<Data> {
  @Override
  public String key() {
    return "toByteArray";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.toByteArray();
  }
}

final class HostDataAsByteArray implements HostMethod<Data> {
  @Override
  public String key() {
    return "asByteArray";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.asByteArray();
  }
}

final class HostDataToByteBuffer implements HostMethod<Data> {
  @Override
  public String key() {
    return "toByteBuffer";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.toByteBuffer();
  }
}

final class HostDataAsByteBuffer implements HostMethod<Data> {
  @Override
  public String key() {
    return "asByteBuffer";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.asByteBuffer();
  }
}

final class HostDataToInputBuffer implements HostMethod<Data> {
  @Override
  public String key() {
    return "toInputBuffer";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.toInputBuffer();
  }
}

final class HostDataWriter implements HostMethod<Data> {
  @Override
  public String key() {
    return "writer";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.writer();
  }
}

final class HostDataWrite implements HostMethod<Data> {
  @Override
  public String key() {
    return "write";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.write((Output) arguments[0]);
  }
}

final class HostDataWriteBase16 implements HostMethod<Data> {
  @Override
  public String key() {
    return "writeBase16";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    if (arguments.length == 1) {
      return data.writeBase16((Output) arguments[0]);
    } else return data.writeBase16((Output) arguments[0], (Base16) arguments[1]);
  }
}

final class HostDataToBase16 implements HostMethod<Data> {
  @Override
  public String key() {
    return "toBase16";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    if (arguments.length == 0) {
      return data.toBase16();
    } else return data.toBase16((Base16) arguments[0]);
  }
}

final class HostDataWriteBase64 implements HostMethod<Data> {
  @Override
  public String key() {
    return "writeBase64";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    if (arguments.length == 1) {
      return data.writeBase64((Output) arguments[0]);
    } else return data.writeBase64((Output) arguments[0], (Base64) arguments[1]);
  }
}

final class HostDataToBase64 implements HostMethod<Data> {
  @Override
  public String key() {
    return "toBase64";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    if (arguments.length == 0) {
      return data.toBase64();
    } else return data.toBase64((Base64) arguments[0]);
  }
}

final class HostDataCompareTo implements HostMethod<Data> {
  @Override
  public String key() {
    return "compareTo";
  }

  @Override
  public Object invoke(Bridge bridge, Data data, Object... arguments) {
    return data.compareTo((Data) arguments[0]);
  }
}

final class HostDataOutput implements HostStaticMethod {
  @Override
  public String key() {
    return "output";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    Object object = arguments[0];
    if (arguments.length == 1) {
      if (object instanceof Data) {
        object = Data.fromObject(object);
        return Data.output((Data) object);
      } else if (object instanceof Integer) {
        object = Num.from((Integer) object);
        return Data.output((int) object);
      }
    }
    return Data.output();
  }
}

final class HostDataEmpty implements HostStaticMethod {
  @Override
  public String key() {
    return "empty";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Data.empty();
  }
}

final class HostDataCreate implements HostStaticMethod {
  @Override
  public String key() {
    return "create";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments.length != 0) {
      return Data.create((int) arguments[0]);
    }
    else return Data.create();
  }
}

final class HostDataWrap implements HostStaticMethod {
  @Override
  public String key() {
    return "wrap";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments.length == 1) {
      Object object = arguments[0];
      if (object instanceof ByteBuffer) {
        return Data.wrap((ByteBuffer) object);
      }
      else if (object instanceof byte[]) {
        return Data.wrap((byte[]) arguments[0]);
      }
    }
    return Data.wrap((byte[]) arguments[0], (int) arguments[1], (int) arguments[2]);
  }
}

final class HostDataFrom implements HostStaticMethod {
  @Override
  public String key() {
    return "from";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Data.from((ByteBuffer) arguments[0]);
  }
}

final class HostDataFromBase16 implements HostStaticMethod {
  @Override
  public String key() {
    return "fromBase16";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Data.fromBase16((String) arguments[0]);
  }
}

final class HostDataFromBase64 implements HostStaticMethod {
  @Override
  public String key() {
    return "fromBase64";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments.length == 1) {
      return Data.fromBase64((String) arguments[0]);
    }
    else return Data.fromBase64((String) arguments[0], (Base64) arguments[1]);
  }
}

final class HostDataFromUtf8 implements HostStaticMethod {
  @Override
  public String key() {
    return "fromUtf8";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Data.fromUtf8((String) arguments[0]);
  }
}
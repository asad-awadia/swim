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
import java.util.Map;

public final class HostRecord {
  private HostRecord() {
    // static
  }

  public static final HostObjectType<Record> TYPE;

  static {
    final JavaHostClassType<Record> type = new JavaHostClassType<>(Record.class);
    TYPE = type;
    type.extendType(HostValue.TYPE);
    type.addMember(new HostRecordIsArray());
    type.addMember(new HostRecordIsObject());
    type.addMember(new HostRecordFieldCount());
    type.addMember(new HostRecordValueCount());
    type.addMember(new HostRecordIndexOf());
    type.addMember(new HostRecordLastIndexOf());
    type.addMember(new HostRecordPut());
    type.addMember(new HostRecordPutAttr());
    type.addMember(new HostRecordPutSlot());
    type.addMember(new HostRecordPutAll());
    type.addMember(new HostRecordSetItem());
    type.addMember(new HostRecordAdd());
    type.addMember(new HostRecordAttr());
    type.addMember(new HostRecordSlot());
    type.addMember(new HostRecordItem());
    type.addMember(new HostRecordRemoveKey());
    type.addMember(new HostRecordIsAliased());
    type.addMember(new HostRecordIsMutable());
    type.addMember(new HostRecordAlias());
    type.addMember(new HostRecordEntrySet());
    type.addMember(new HostRecordFieldSet());
    type.addMember(new HostRecordKeySet());
    type.addMember(new HostRecordValues());
    type.addMember(new HostRecordKeyIterator());
    type.addMember(new HostRecordValueIterator());
    type.addMember(new HostRecordFieldIterator());
    type.addMember(new HostRecordCompareTo());
    type.addStaticMember(new HostRecordEmpty());
    type.addStaticMember(new HostRecordCreate());
    type.addStaticMember(new HostRecordOf());
  }
}

final class HostRecordIsArray implements HostMethod<Record> {
  @Override
  public String key() {
    return "isArray";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.isArray();
  }
}

final class HostRecordIsObject implements HostMethod<Record> {
  @Override
  public String key() {
    return "isObject";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.isObject();
  }
}

final class HostRecordFieldCount implements HostMethod<Record> {
  @Override
  public String key() {
    return "fieldCount";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.fieldCount();
  }
}

final class HostRecordValueCount implements HostMethod<Record> {
  @Override
  public String key() {
    return "valueCount";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.valueCount();
  }
}

final class HostRecordIndexOf implements HostMethod<Record> {
  @Override
  public String key() {
    return "indexOf";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.indexOf((Item) arguments[0]);
  }
}

final class HostRecordLastIndexOf implements HostMethod<Record> {
  @Override
  public String key() {
    return "lastIndexOf";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.lastIndexOf((Item) arguments[0]);
  }
}

final class HostRecordPut implements HostMethod<Record> {
  @Override
  public String key() {
    return "put";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object key = arguments[0];
    Object newValue = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    if (newValue instanceof String) {
      newValue = Text.from((String) newValue);
    } else if (newValue instanceof Number) {
      newValue = Num.from((Number) newValue);
    } else if (newValue instanceof Boolean) {
      newValue = Bool.from((Boolean) newValue);
    }
    return record.put((Value) key, (Value) newValue);
  }
}

final class HostRecordPutAttr implements HostMethod<Record> {
  @Override
  public String key() {
    return "putAttr";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object key = arguments[0];
    Object newValue = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    if (newValue instanceof String) {
      newValue = Text.from((String) newValue);
    } else if (newValue instanceof Number) {
      newValue = Num.from((Number) newValue);
    } else if (newValue instanceof Boolean) {
      newValue = Bool.from((Boolean) newValue);
    }
    return record.putAttr((Text) key, (Value) newValue);
  }
}

final class HostRecordPutSlot implements HostMethod<Record> {
  @Override
  public String key() {
    return "putSlot";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object key = arguments[0];
    Object newValue = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    if (newValue instanceof String) {
      newValue = Text.from((String) newValue);
    } else if (newValue instanceof Number) {
      newValue = Num.from((Number) newValue);
    } else if (newValue instanceof Boolean) {
      newValue = Bool.from((Boolean) newValue);
    }
    return record.putSlot((Value) key, (Value) newValue);
  }
}

final class HostRecordPutAll implements HostMethod<Record> {
  @Override
  public String key() {
    return "putAll";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    record.putAll((Map) arguments[0]);
    return null;
  }
}

final class HostRecordSetItem implements HostMethod<Record> {
  @Override
  public String key() {
    return "setItem";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object index = arguments[0];
    Object value = arguments[1];
    if (index instanceof Integer) {
      index = Num.from((Integer) index);
    }
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    } else if (value instanceof Boolean) {
      value = Bool.from((Boolean) value);
    }
    return record.setItem((int) index, (Item) value);
  }
}

final class HostRecordAdd implements HostMethod<Record> {
  @Override
  public String key() {
    return "add";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    if (arguments.length == 1) {
      Object item = arguments[0];
      if (item instanceof String) {
        item = Text.from((String) item);
      } else if (item instanceof Number) {
        item = Num.from((Number) item);
      } else if (item instanceof Boolean) {
        item = Bool.from((Boolean) item);
      }
      return record.add((Item) item);
    }
    else {
      Object index = arguments[0];
      Object item = arguments[1];
      if (index instanceof Integer) {
        index = Num.from((Integer) index);
      }
      if (item instanceof String) {
        item = Text.from((String) item);
      } else if (item instanceof Number) {
        item = Num.from((Number) item);
      }
      record.add((int) index, (Item) item);
      return null;
    }
  }
}

final class HostRecordAttr implements HostMethod<Record> {
  @Override
  public String key() {
    return "attr";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    if (arguments.length == 1) {
      Object key = arguments[0];
      if (key instanceof String) {
        key = Text.from((String) key);
      }
      return record.attr((Text) key);
    }
    else {
      Object key = arguments[0];
      Object value = arguments[1];
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
      return record.attr((Text) key, (Value) value);
    }
  }
}

final class HostRecordSlot implements HostMethod<Record> {
  @Override
  public String key() {
    return "slot";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    if (arguments.length == 1) {
      Object key = arguments[0];
      if (key instanceof String) {
        key = Text.from((String) key);
      }
      return record.slot((Value) key);
    }
    else {
      Object key = arguments[0];
      Object value = arguments[1];
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
      return record.slot((Value) key, (Value) value);
    }
  }
}

final class HostRecordItem implements HostMethod<Record> {
  @Override
  public String key() {
    return "item";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object item = arguments[0];
    if (item instanceof String) {
      item = Text.from((String) item);
    } else if (item instanceof Number) {
      item = Num.from((Number) item);
    } else if (item instanceof Boolean) {
      item = Bool.from((Boolean) item);
    }
    return record.item((Item) item);
  }
}

final class HostRecordRemoveKey implements HostMethod<Record> {
  @Override
  public String key() {
    return "removeKey";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    Object key = arguments[0];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    return record.removeKey((Value) key);
  }
}

final class HostRecordIsAliased implements HostMethod<Record> {
  @Override
  public String key() {
    return "isAliased";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.isAliased();
  }
}

final class HostRecordIsMutable implements HostMethod<Record> {
  @Override
  public String key() {
    return "isMutable";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.isMutable();
  }
}

final class HostRecordAlias implements HostMethod<Record> {
  @Override
  public String key() {
    return "alias";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    record.alias();
    return null;
  }
}

final class HostRecordEntrySet implements HostMethod<Record> {
  @Override
  public String key() {
    return "entrySet";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.entrySet();
  }
}

final class HostRecordFieldSet implements HostMethod<Record> {
  @Override
  public String key() {
    return "fieldSet";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.fieldSet();
  }
}

final class HostRecordKeySet implements HostMethod<Record> {
  @Override
  public String key() {
    return "keySet";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.keySet();
  }
}

final class HostRecordValues implements HostMethod<Record> {
  @Override
  public String key() {
    return "values";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.values();
  }
}

final class HostRecordKeyIterator implements HostMethod<Record> {
  @Override
  public String key() {
    return "keyIterator";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.keyIterator();
  }
}

final class HostRecordValueIterator implements HostMethod<Record> {
  @Override
  public String key() {
    return "valueIterator";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments){
    return record.valueIterator();
  }
}

final class HostRecordFieldIterator implements HostMethod<Record> {
  @Override
  public String key() {
    return "fieldIterator";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.fieldIterator();
  }
}

final class HostRecordCompareTo implements HostMethod<Record> {
  @Override
  public String key() {
    return "compareTo";
  }

  @Override
  public Object invoke(Bridge bridge, Record record, Object... arguments) {
    return record.compareTo((Record) arguments[0]);
  }
}

final class HostRecordEmpty implements HostStaticMethod {
  @Override
  public String key() {
    return "empty";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Record.empty();
  }
}

final class HostRecordCreate implements HostStaticMethod {
  @Override
  public String key() {
    return "create";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments == null) {
      return Record.create();
    } else {
      return Record.create((int) arguments[0]);
    }
  }
}

final class HostRecordOf implements HostStaticMethod {
  @Override
  public String key() {
    return "of";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    if (arguments == null) {
      return Record.of();
    } else {
      return Record.of(arguments);
    }
  }
}
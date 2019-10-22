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

import swim.codec.Output;
import swim.dynamic.*;
import swim.dynamic.HostField;
import swim.dynamic.java.lang.HostObject;
import swim.structure.*;
import java.math.BigInteger;

public final class HostItem {
  private HostItem() {
    // static
  }

  public static final HostObjectType<Item> TYPE;

  static {
    final JavaHostClassType<Item> type = new JavaHostClassType<>(Item.class);
    TYPE = type;
    type.extendType(HostObject.TYPE);
    type.addMember(new HostItemIsDefined());
    type.addMember(new HostItemIsDistinct());
    type.addMember(new HostItemIsConstant()); //fixme: test case
    type.addMember(new HostItemKey());
    type.addMember(new HostItemToValue());
    type.addMember(new HostItemTag());
    type.addMember(new HostItemTarget());
    type.addMember(new HostItemFlattened());
    type.addMember(new HostItemUnflattened());
    type.addMember(new HostItemHeader());
    type.addMember(new HostItemHeaders());
    type.addMember(new HostItemHead());
    type.addMember(new HostItemTail());
    type.addMember(new HostItemBody());
    type.addMember(new HostItemLength());
    type.addMember(new HostItemContains());
    type.addMember(new HostItemContainsKey());
    type.addMember(new HostItemContainsValue());
    type.addMember(new HostItemGet());
    type.addMember(new HostItemGetAttr());
    type.addMember(new HostItemGetSlot());
    type.addMember(new HostItemGetField());
    type.addMember(new HostItemGetItem());
    type.addMember(new HostItemUpdated());
    type.addMember(new HostItemUpdatedAttr());
    type.addMember(new HostItemUpdatedSlot());
    type.addMember(new HostItemAppended());
    type.addMember(new HostItemPrepended());
    type.addMember(new HostItemRemoved());
    type.addMember(new HostItemConcat());
    type.addMember(new HostItemConditional());
    type.addMember(new HostItemOr());
    type.addMember(new HostItemAnd());
    type.addMember(new HostItemBitwiseOr());
    type.addMember(new HostItemBitwiseXor());
    type.addMember(new HostItemBitwiseAnd());
    type.addMember(new HostItemLt());
    type.addMember(new HostItemLe());
    type.addMember(new HostItemEq());
    type.addMember(new HostItemNe());
    type.addMember(new HostItemGe());
    type.addMember(new HostItemGt());
    type.addMember(new HostItemPlus());
    type.addMember(new HostItemMinus());
    type.addMember(new HostItemTimes());
    type.addMember(new HostItemDivide());
    type.addMember(new HostItemModulo());
    type.addMember(new HostItemNot());
    type.addMember(new HostItemBitwiseNot());
    type.addMember(new HostItemNegative());
    type.addMember(new HostItemPositive());
    type.addMember(new HostItemInverse());
    type.addMember(new HostItemInvoke());

    // ToDo: add and fix testing case
    type.addMember(new HostItemLambda()); //todo
    type.addMember(new HostItemFilter()); //todo


    type.addMember(new HostItemMax());
    type.addMember(new HostItemMin());

    // ToDo: add and fix testing case
    type.addMember(new HostItemEvaluate()); //todo
    type.addMember(new HostItemSubstitute()); //todo


    type.addMember(new HostItemStringValue());
    type.addMember(new HostItemByteValue());
    type.addMember(new HostItemShortValue());
    type.addMember(new HostItemIntValue());
    type.addMember(new HostItemLongValue());
    type.addMember(new HostItemFloatValue());
    type.addMember(new HostItemDoubleValue());
    type.addMember(new HostItemIntegerValue()); //fixme: test case
    type.addMember(new HostItemNumberValue());
    type.addMember(new HostItemCharValue());
    type.addMember(new HostItemBooleanValue());

    type.addMember(new HostItemCast()); //fixme: test case
    type.addMember(new HostItemCoerce()); //fixme: test case
    type.addMember(new HostItemIsAliased());
    type.addMember(new HostItemIsMutable());
    type.addMember(new HostItemAlias());
    type.addMember(new HostItemBranch());
    type.addMember(new HostItemCommit());
    type.addMember(new HostItemPrecedence());
    type.addMember(new HostItemTypeOrder());
    type.addMember(new HostItemKeyEquals());
    type.addMember(new HostItemDebug()); //fixme: test case
    type.addMember(new HostItemDisplay()); //fixme: test case
    type.addMember(new HostItemToString()); //fixme: test case

    type.addStaticMember(new HostItemEmpty());
    type.addStaticMember(new HostItemAbsent());
    type.addStaticMember(new HostItemExtant());
    type.addStaticMember(new HostItemFromObject()); //fixme: test case
    type.addStaticMember(new HostItemGlobalScope()); //fixme: test case

  }
}

final class HostItemIsDefined implements HostMethod<Item> {
  @Override
  public String key() {
    return "isDefined";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.isDefined();
  }
}

final class HostItemIsDistinct implements HostMethod<Item> {
  @Override
  public String key() {
    return "isDistinct";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.isDistinct();
  }
}

final class HostItemIsConstant implements HostMethod<Item> {
  @Override
  public String key() {
    return "isConstant";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.isConstant();
  }
}

final class HostItemKey implements HostField<Item> {
  @Override
  public String key() {
    return "key";
  }

  @Override
  public Object get(Bridge bridge, Item item) {
    return item.key();
  }
}

final class HostItemToValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "toValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.toValue();
  }
}

final class HostItemTag implements HostMethod<Item> {
  @Override
  public String key() {
    return "tag";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.tag();
  }
}

final class HostItemTarget implements HostMethod<Item> {
  @Override
  public String key() {
    return "target";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.target();
  }
}

final class HostItemFlattened implements HostMethod<Item> {
  @Override
  public String key() {
    return "flattened";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.flattened();
  }
}

final class HostItemUnflattened implements HostMethod<Item> {
  @Override
  public String key() {
    return "unflattened";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.unflattened();
  }
}

final class HostItemHeader implements HostMethod<Item> {
  @Override
  public String key() {
    return "header";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.header((String) arguments[0]);
  }
}

final class HostItemHeaders implements HostMethod<Item> {
  @Override
  public String key() {
    return "headers";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.headers((String) arguments[0]);
  }
}

final class HostItemHead implements HostMethod<Item> {
  @Override
  public String key() {
    return "head";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.head();
  }
}

final class HostItemTail implements HostMethod<Item> {
  @Override
  public String key() {
    return "tail";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.tail();
  }
}

final class HostItemBody implements HostMethod<Item> {
  @Override
  public String key() {
    return "body";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.body();
  }
}

final class HostItemLength implements HostField<Item> {
  @Override
  public String key() {
    return "length";
  }

  @Override
  public Object get(Bridge bridge, Item item) {
    return item.length();
  }
}

final class HostItemContains implements HostMethod<Item> {
  @Override
  public String key() {
    return "contains";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.contains((Item) arguments[0]);
  }
}

final class HostItemContainsKey implements HostMethod<Item> {
  @Override
  public String key() {
    return "containsKey";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object key = arguments[0];
    if (key instanceof String) {
      key = Text.from((String) key);
    } else if (key instanceof Number) {
      key = Num.from((Number) key);
    }
    return item.containsKey((Value) key);
  }
}

final class HostItemContainsValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "containsValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.containsValue((Value) value);
  }
}

final class HostItemGet implements HostMethod<Item> {
  @Override
  public String key() {
    return "get";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.get((Value) value);
  }
}

final class HostItemGetAttr implements HostMethod<Item> {
  @Override
  public String key() {
    return "getAttr";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof String) {
      value = Text.from((String) value);
    }
    return item.getAttr((Text) value);
  }
}

final class HostItemGetSlot implements HostMethod<Item> {
  @Override
  public String key() {
    return "getSlot";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.getSlot((Value) value);
  }
}

final class HostItemGetField implements HostMethod<Item> {
  @Override
  public String key() {
    return "getField";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.getField((Value) value);
  }
}

final class HostItemGetItem implements HostMethod<Item> {
  @Override
  public String key() {
    return "getItem";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.getItem((int) arguments[0]);
  }
}

final class HostItemUpdated implements HostMethod<Item> {
  @Override
  public String key() {
    return "updated";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object key = arguments[0];
    Object value = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    } else if (key instanceof Number) {
      key = Num.from((Number) key);
    }
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.updated((Value) key, (Value) value);
  }
}

final class HostItemUpdatedAttr implements HostMethod<Item> {
  @Override
  public String key() { return "updatedAttr"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object key = arguments[0];
    Object value = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.updatedAttr((Text) key, (Value) value);
  }
}

final class HostItemUpdatedSlot implements HostMethod<Item> {
  @Override
  public String key() { return "updatedSlot"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object key = arguments[0];
    Object value = arguments[1];
    if (key instanceof String) {
      key = Text.from((String) key);
    }
    if (value instanceof String) {
      value = Text.from((String) value);
    } else if (value instanceof Number) {
      value = Num.from((Number) value);
    }
    return item.updatedSlot((Value) key, (Value) value);
  }
}

final class HostItemAppended implements HostMethod<Item> {
  @Override
  public String key() { return "appended"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object[] objects = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++)  {
      if (arguments[i] instanceof String) {
        objects[i] = Text.from((String) arguments[i]);
      } else if (arguments[i]  instanceof Number) {
        objects[i] = Num.from((Number) arguments[i]);
      } else if (arguments[i] instanceof Boolean) {
        objects[i] = Bool.from((Boolean) arguments[i]);
      } else {
        objects[i] = arguments[i];
      }
    }
    return item.appended(objects);
  }
}

final class HostItemPrepended implements HostMethod<Item> {
  @Override
  public String key() { return "prepended"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object[] objects = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++)  {
      if (arguments[i] instanceof String) {
        objects[i] = Text.from((String) arguments[i]);
      } else if (arguments[i]  instanceof Number) {
        objects[i] = Num.from((Number) arguments[i]);
      } else if (arguments[i] instanceof Boolean) {
        objects[i] = Bool.from((Boolean) arguments[i]);
      } else {
        objects[i] = arguments[i];
      }
    }
    return item.prepended(objects);
  }
}

final class HostItemRemoved implements HostMethod<Item> {
  @Override
  public String key() { return "removed"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object key = arguments[0];
    if (key instanceof String) {
      key = Text.from((String) key);
    } else if (key instanceof Number) {
      key = Num.from((Number) key);
    }
    return item.removed((Value) key);
  }
}

final class HostItemConcat implements HostMethod<Item> {
  @Override
  public String key() { return "concat"; }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    Object value = arguments[0];
    if (value instanceof Record) {
      value = Record.fromObject(value);
    }

    return item.concat((Item) value);
  }
}

final class HostItemConditional implements HostMethod<Item> {
  @Override
  public String key() {
    return "conditional";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.conditional((Item) arguments[0], (Item) arguments[1]);
  }
}

final class HostItemOr implements HostMethod<Item> {
  @Override
  public String key() {
    return "or";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.or((Item) arguments[0]);
  }
}

final class HostItemAnd implements HostMethod<Item> {
  @Override
  public String key() {
    return "and";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.and((Item) arguments[0]);
  }
}

final class HostItemBitwiseOr implements HostMethod<Item> {
  @Override
  public String key() {
    return "bitwiseOr";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.bitwiseOr((Item) arguments[0]);
  }
}

final class HostItemBitwiseXor implements HostMethod<Item> {
  @Override
  public String key() {
    return "bitwiseXor";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.bitwiseXor((Item) arguments[0]);
  }
}

final class HostItemBitwiseAnd implements HostMethod<Item> {
  @Override
  public String key() {
    return "bitwiseAnd";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.bitwiseAnd((Item) arguments[0]);
  }
}

final class HostItemLt implements HostMethod<Item> {
  @Override
  public String key() {
    return "lt";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.lt((Item) arguments[0]);
  }
}

final class HostItemLe implements HostMethod<Item> {
  @Override
  public String key() {
    return "le";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.le((Item) arguments[0]);
  }
}

final class HostItemEq implements HostMethod<Item> {
  @Override
  public String key() {
    return "eq";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.eq((Item) arguments[0]);
  }
}

final class HostItemNe implements HostMethod<Item> {
  @Override
  public String key() {
    return "ne";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.ne((Item) arguments[0]);
  }
}

final class HostItemGe implements HostMethod<Item> {
  @Override
  public String key() {
    return "ge";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.ge((Item) arguments[0]);
  }
}

final class HostItemGt implements HostMethod<Item> {
  @Override
  public String key() {
    return "gt";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.gt((Item) arguments[0]);
  }
}

final class HostItemPlus implements HostMethod<Item> {
  @Override
  public String key() {
    return "plus";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.plus((Item) arguments[0]);
  }
}

final class HostItemMinus implements HostMethod<Item> {
  @Override
  public String key() {
    return "minus";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.minus((Item) arguments[0]);
  }
}

final class HostItemTimes implements HostMethod<Item> {
  @Override
  public String key() {
    return "times";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.times((Item) arguments[0]);
  }
}

final class HostItemDivide implements HostMethod<Item> {
  @Override
  public String key() {
    return "divide";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.divide((Item) arguments[0]);
  }
}

final class HostItemModulo implements HostMethod<Item> {
  @Override
  public String key() {
    return "modulo";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.modulo((Item) arguments[0]);
  }
}

final class HostItemNot implements HostMethod<Item> {
  @Override
  public String key() {
    return "not";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.not();
  }
}

final class HostItemBitwiseNot implements HostMethod<Item> {
  @Override
  public String key() {
    return "bitwiseNot";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.bitwiseNot();
  }
}

final class HostItemNegative implements HostMethod<Item> {
  @Override
  public String key() {
    return "negative";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.negative();
  }
}

final class HostItemPositive implements HostMethod<Item> {
  @Override
  public String key() {
    return "positive";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.positive();
  }
}

final class HostItemInverse implements HostMethod<Item> {
  @Override
  public String key() {
    return "inverse";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.inverse();
  }
}

final class HostItemInvoke implements HostMethod<Item> {
  @Override
  public String key() {
    return "invoke";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.invoke((Value) arguments[0]);
  }
}

final class HostItemLambda implements HostMethod<Item> {
  @Override
  public String key() {
    return "lambda";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.lambda((Value) arguments[0]);
  }
}

//fixme: need to check with method filter, evaluate, and substitute
final class HostItemFilter implements HostMethod<Item> {
  @Override
  public String key() {
    return "filter";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.filter();
    } else {
      return item.filter((Item) arguments[0]);
    }
  }
}

final class HostItemMax implements HostMethod<Item> {
  @Override
  public String key() {
    return "max";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.max((Item) arguments[0]);
  }
}

final class HostItemMin implements HostMethod<Item> {
  @Override
  public String key() {
    return "min";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.min((Item) arguments[0]);
  }
}

final class HostItemEvaluate implements HostMethod<Item> {
  @Override
  public String key() {
    return "evaluate";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments[0] instanceof Interpreter) {
      return item.evaluate((Interpreter) arguments[0]);
    } else {
      return item.evaluate((Item) arguments[0]);
    }
  }
}

final class HostItemSubstitute implements HostMethod<Item> {
  @Override
  public String key() {
    return "substitute";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments[0] instanceof Interpreter) {
      return item.substitute((Interpreter) arguments[0]);
    } else {
      return item.substitute((Item) arguments[0]);
    }
  }
}

final class HostItemStringValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "stringValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.stringValue();
    } else {
      return item.stringValue((String) arguments[0]);
    }
  }
}

final class HostItemByteValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "byteValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.byteValue();
    } else {
      return item.byteValue((byte) arguments[0]);
    }
  }
}

final class HostItemShortValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "shortValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.shortValue();
    } else {
      return item.shortValue((short) arguments[0]);
    }
  }
}

final class HostItemIntValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "intValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.intValue();
    } else {
      return item.intValue((int) arguments[0]);
    }
  }
}

final class HostItemLongValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "longValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.longValue();
    } else {
      return item.longValue((long) arguments[0]);
    }
  }
}

final class HostItemFloatValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "floatValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.floatValue();
    } else {
      return item.floatValue((float) arguments[0]);
    }
  }
}

final class HostItemDoubleValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "doubleValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.doubleValue();
    } else {
      return item.doubleValue((double) arguments[0]);
    }
  }
}

final class HostItemIntegerValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "integerValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.integerValue();
    } else {
      return item.integerValue((BigInteger) arguments[0]);
    }
  }
}

final class HostItemNumberValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "numberValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.numberValue();
    } else {
      return item.numberValue((Number) arguments[0]);
    }
  }
}

final class HostItemCharValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "charValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.charValue();
    } else {
      return item.charValue((char) arguments[0]);
    }
  }
}

final class HostItemBooleanValue implements HostMethod<Item> {
  @Override
  public String key() {
    return "booleanValue";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 0) {
      return item.booleanValue();
    } else {
      return item.booleanValue((Boolean) arguments[0]);
    }
  }
}

final class HostItemCast implements HostMethod<Item> {
  @Override
  public String key() {
    return "cast";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 1) {
      return item.cast((Form<Item>) arguments[0]);
    }
    else {
      return item.cast((Form<Item>) arguments[0], (Item) arguments[1]);
    }
  }
}

final class HostItemCoerce implements HostMethod<Item> {
  @Override
  public String key() {
    return "coerce";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    if (arguments.length == 1) {
      return item.coerce((Form<Item>) arguments[0]);
    }
    else {
      return item.coerce((Form<Item>) arguments[0], (Item) arguments[1]);
    }
  }
}

final class HostItemIsAliased implements HostMethod<Item> {
  @Override
  public String key() {
    return "isAliased";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.isAliased();
  }
}

final class HostItemIsMutable implements HostMethod<Item> {
  @Override
  public String key() {
    return "isMutable";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.isMutable();
  }
}

final class HostItemAlias implements HostMethod<Item> {
  @Override
  public String key() {
    return "alias";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    item.alias();
    return null;
  }
}

final class HostItemBranch implements HostMethod<Item> {
  @Override
  public String key() {
    return "branch";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.branch();
  }
}

final class HostItemCommit implements HostMethod<Item> {
  @Override
  public String key() {
    return "commit";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.commit();
  }
}

final class HostItemPrecedence implements HostMethod<Item> {
  @Override
  public String key() {
    return "precedence";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.precedence();
  }
}

final class HostItemTypeOrder implements HostMethod<Item> {
  @Override
  public String key() {
    return "typeOrder";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.typeOrder();
  }
}


final class HostItemKeyEquals implements HostMethod<Item> {
  @Override
  public String key() {
    return "keyEquals";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.keyEquals(arguments[0]);
  }
}

final class HostItemDebug implements HostMethod<Item> {
  @Override
  public String key() {
    return "debug";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    item.debug((Output<?>) arguments[0]);
    return null;
  }
}

final class HostItemDisplay implements HostMethod<Item> {
  @Override
  public String key() {
    return "display";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    item.display((Output<?>) arguments[0]);
    return null;
  }
}

final class HostItemToString implements HostMethod<Item> {
  @Override
  public String key() {
    return "toString";
  }

  @Override
  public Object invoke(Bridge bridge, Item item, Object... arguments) {
    return item.toString();
  }
}
final class HostItemEmpty implements HostStaticMethod {
  @Override
  public String key() { return "empty"; }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) { return Item.empty(); }
}

final class HostItemAbsent implements HostStaticMethod {
  @Override
  public String key() {
    return "absent";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Item.absent();
  }
}

final class HostItemExtant implements HostStaticMethod {
  @Override
  public String key() {
    return "extant";
  }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Item.extant();
  }
}

final class HostItemFromObject implements HostStaticMethod {
  @Override
  public String key() { return "fromObject"; }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Item.fromObject(arguments[0]);
  }
}

final class HostItemGlobalScope implements HostStaticMethod {
  @Override
  public String key() { return "globalScope"; }

  @Override
  public Object invoke(Bridge bridge, Object... arguments) {
    return Item.globalScope();
  }
}

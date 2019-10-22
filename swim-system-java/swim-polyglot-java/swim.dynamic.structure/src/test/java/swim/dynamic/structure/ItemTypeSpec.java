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

import java.util.HashMap;
import java.util.Map;
import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.*;
import swim.vm.VmBridge;
import static org.testng.Assert.assertEquals;

public class ItemTypeSpec {
  @Test
  public void testAbsent() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("absent", bridge.hostToGuest(Item.absent()));

      assertEquals(bridge.guestToHost(context.eval("js", "absent")), Item.absent());
      assertEquals(context.eval("js", "absent.isDefined()").asBoolean(), false);
      assertEquals(context.eval("js", "absent.isDistinct()").asBoolean(), false);
    }
  }

  @Test
  public void testExtant() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("extant", bridge.hostToGuest(Item.extant()));

      assertEquals(bridge.guestToHost(context.eval("js", "extant")), Item.extant());
      assertEquals(context.eval("js", "extant.isDefined()").asBoolean(), true);
      assertEquals(context.eval("js", "extant.isDistinct()").asBoolean(), false);
    }
  }

  @Test
  public void testEmptyRecord() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));

      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Item.empty());
      assertEquals(context.eval("js", "empty.isDefined()").asBoolean(), true);
      assertEquals(context.eval("js", "empty.isDistinct()").asBoolean(), true);
    }
  }

  @Test
  public void testFromObject() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("str", bridge.hostToGuest(Item.fromObject("hello")));
      Map<String, String> map = new HashMap<>();
      map.put("foo", "bar");
      //bindings.putMember("mapOb", bridge.hostToGuest(Item.fromObject(map)));

      //System.out.println(Item.fromObject(map));

      assertEquals(bridge.guestToHost(context.eval("js", "str")), Text.from("hello"));
      //assertEquals(bridge.guestToHost(context.eval("js", "mapOb")), Slot.of("foo", "bar"));
    }
  }

  @Test
  public void testUpdateRecord() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.create()));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "record.updated('foo', 'bar')")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updated('foo', 2)")), Record.of(Slot.of("foo", 2)));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updated('foo', eight)")), Record.of(Slot.of("foo", 8)));
    }
  }

  @Test
  public void testSlot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("foo", "bar")));

      assertEquals(bridge.guestToHost(context.eval("js", "slot")), Slot.of("foo", "bar"));
      assertEquals(context.eval("js", "slot.isDefined()").asBoolean(), true);
      assertEquals(context.eval("js", "slot.isDistinct()").asBoolean(), true);
      assertEquals(context.eval("js", "slot.key.stringValue()").asString(), "foo");
      assertEquals(context.eval("js", "slot.toValue().stringValue()").asString(), "bar");
    }
  }

  @Test
  public void testTag() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("attr",
          bridge.hostToGuest(Record.of(Attr.of("test", Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2"))))));
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2"))));

      assertEquals(bridge.guestToHost(context.eval("js", "attr")),
          Record.of(Attr.of("test", Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2")))));
      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2")));
      assertEquals(context.eval("js", "attr.tag()").asString(), "test");
      assertEquals(context.eval("js", "record.tag()").asString(),null);
    }
  }

  @Test
  public void testTarget() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("valueText", bridge.hostToGuest(Value.fromObject("valueText")));
      bindings.putMember("slotRecord", bridge.hostToGuest(Slot.of("test", "test2")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "valueText")),
          Value.fromObject("valueText"));
      assertEquals(bridge.guestToHost(context.eval("js", "slotRecord")),
          Slot.of("test", "test2"));

      assertEquals(bridge.guestToHost(context.eval("js", "record.target()")), Item.extant());
      assertEquals(context.eval("js", "valueText.target().stringValue()").asString(),
          "valueText");
      assertEquals(context.eval("js", "slotRecord.target().stringValue()").asString(),
          "test2");
    }
  }

  @Test
  public void testFlattened() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2"))));
      bindings.putMember("record2", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("emptyRecord", bridge.hostToGuest(Record.create()));
      bindings.putMember("emptyField", bridge.hostToGuest(Slot.of("")));


      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2")));
      assertEquals(bridge.guestToHost(context.eval("js", "record2")),
          Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.flattened()")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2")));
      assertEquals(bridge.guestToHost(context.eval("js", "record2.flattened()")),
          Record.of(Slot.of("foo", "bar")));

      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord.flattened()")), Item.extant());
      assertEquals(bridge.guestToHost(context.eval("js", "emptyField.flattened()")), Item.absent());
    }
  }

  @Test
  public void testUnflattened() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("absent", bridge.hostToGuest(Item.absent()));
      bindings.putMember("extant", bridge.hostToGuest(Item.extant()));
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "absent")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "extant")), Item.extant());
      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Item.empty());

      assertEquals(bridge.guestToHost(context.eval("js", "record.unflattened()")),
          Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "absent.unflattened()")), Item.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "extant.unflattened()")), Item.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "empty.unflattened()")), Item.empty());
    }
  }

  @Test
  public void testHeader() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "A"))));
      bindings.putMember("emptyAttr", bridge.hostToGuest(Record.of(Attr.of("test"))));
      bindings.putMember("attr", bridge.hostToGuest(Record.of(Attr.of("test", "test2"))));
      bindings.putMember("recordAttr",
          bridge.hostToGuest(Record.of(Attr.of("recordAttr", Record.of(Slot.of("t", "B"))))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "A")));
      assertEquals(bridge.guestToHost(context.eval("js", "emptyAttr")), Record.of(Attr.of("test")));
      assertEquals(bridge.guestToHost(context.eval("js", "attr")), Record.of(Attr.of("test", "test2")));
      assertEquals(bridge.guestToHost(context.eval("js", "recordAttr")),
          Record.of(Attr.of("recordAttr", Record.of(Slot.of("t", "B")))));
      assertEquals(bridge.guestToHost(context.eval("js", "record.header('foo')")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "emptyAttr.header('test')")), Item.extant());
      assertEquals(context.eval("js", "attr.header('test').stringValue()").asString(), "test2");
      assertEquals(bridge.guestToHost(context.eval("js", "recordAttr.header('recordAttr')")),
          Record.of(Slot.of("t", "B")));
    }
  }

  @Test
  public void testHeaders() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

      bindings.putMember("slotRecord", bridge.hostToGuest(Record.of(Slot.of("test", "test1"))));
      bindings.putMember("attrRecord",
          bridge.hostToGuest(Record.of(Attr.of("foo", Record.of(Attr.of("bar", Record.of(Slot.of("testA", "A"))))))));

      assertEquals(bridge.guestToHost(context.eval("js", "slotRecord")),
          Record.of(Slot.of("test", "test1")));
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord")),
          Record.of(Attr.of("foo", Record.of(Attr.of("bar", Record.of(Slot.of("testA", "A")))))));
      assertEquals(bridge.guestToHost(context.eval("js", "slotRecord.headers('test')")), null);
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord.headers('foo')")),
          Record.of(Attr.of("bar", Record.of(Slot.of("testA", "A")))));
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord.headers('bar')")), null);
    }
  }

  @Test
  public void testHead() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("emptyRecord", bridge.hostToGuest(Record.of()));
      bindings.putMember("attrRecord", bridge.hostToGuest(Record.of(Attr.of("test"))));
      bindings.putMember("slotRecord", bridge.hostToGuest(Record.of(Slot.of("test1", "test2"), Slot.of("C", "D"))));

      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord")), Record.of());
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord")), Record.of(Attr.of("test")));
      assertEquals(bridge.guestToHost(context.eval("js", "slotRecord")),
          Record.of(Slot.of("test1", "test2"), Slot.of("C", "D")));
      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord.head()")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord.head()")), Attr.of("test"));
      assertEquals(bridge.guestToHost(context.eval("js", "slotRecord.head()")),
          Slot.of("test1", "test2"));
    }
  }

  @Test
  public void testTail() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("singleSlot", bridge.hostToGuest(Record.of(Slot.of("E", "E"))));
      bindings.putMember("slotsRecord", bridge.hostToGuest(
          Record.of(Slot.of("A", "A"), Slot.of("B", "B"), Slot.of("C", "C"))));

      assertEquals(bridge.guestToHost(context.eval("js", "singleSlot")), Record.of(Slot.of("E", "E")));
      assertEquals(bridge.guestToHost(context.eval("js", "slotsRecord")),
          Record.of(Slot.of("A", "A"), Slot.of("B", "B"), Slot.of("C", "C")));
      assertEquals(bridge.guestToHost(context.eval("js", "singleSlot.tail()")), Item.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "slotsRecord.tail()")),
          Record.of(Slot.of("B", "B"), Slot.of("C", "C")));
    }
  }

  @Test
  public void testBody() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("attrRecord",
          bridge.hostToGuest(Record.of(Attr.of("test", Record.of(Slot.of("foo", "bar"))))));
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("A", "A"), Slot.of("B", "B"), Slot.of("C", "C"))));

      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord")),
          Record.of(Attr.of("test", Record.of(Slot.of("foo", "bar")))));
      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("A", "A"), Slot.of("B", "B"), Slot.of("C", "C")));
      assertEquals(bridge.guestToHost(context.eval("js", "attrRecord.body()")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "record.body()")),
          Record.of(Slot.of("B", "B"), Slot.of("C", "C")));
    }
  }

  @Test
  public void testLength() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2"))));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("testA", "A")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test2")));
      assertEquals(bridge.guestToHost(context.eval("js", "slot")), Slot.of("testA", "A"));
      assertEquals(context.eval("js", "record.length").asInt(),2);
      assertEquals(context.eval("js", "slot.length").asInt(), 0);
    }
  }

  @Test
  public void testContains() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.create().slot("foo", "bar").slot("test", "test1")));

      bindings.putMember("fooSlot", bridge.hostToGuest(Slot.of("foo", "bar")));
      bindings.putMember("testSlot", bridge.hostToGuest(Slot.of("test", "test2")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test1")));
      assertEquals(context.eval("js", "record.contains(fooSlot)").asBoolean(), true);
      assertEquals(context.eval("js", "record.contains(testSlot)").asBoolean(), false);
    }
  }

  @Test
  public void testContainsKey() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"),
          Slot.of(Num.from(7877), "number")));
      assertEquals(context.eval("js", "record.containsKey('foo')").asBoolean(), true);
      assertEquals(context.eval("js", "record.containsKey('bar')").asBoolean(), false);
      assertEquals(context.eval("js", "record.containsKey(7877)").asBoolean(), true);
    }
  }

  @Test
  public void testContainsValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number")));
      assertEquals(context.eval("js", "record.containsValue('foo')").asBoolean(), false);
      assertEquals(context.eval("js", "record.containsValue('bar')").asBoolean(), true);
      assertEquals(context.eval("js", "record.containsValue('number')").asBoolean(), true);
    }
  }

  @Test
  public void testGet() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.create().slot("foo", "bar").slot(Num.from(7877), "number")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('test')")), Item.absent());
      assertEquals(context.eval("js", "record.get('foo').stringValue()").asString(), "bar");
      assertEquals(context.eval("js", "record.get(7877).stringValue()").asString(), "number");
    }
  }
  @Test
  public void testGetAttr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Attr.of("unittest"), Slot.of("foo", "bar"))));
      bindings.putMember("attr", bridge.hostToGuest(
          Record.of(Attr.of("test2", "test"))));
      bindings.putMember("attr2",
          bridge.hostToGuest(Record.of(Attr.of("test", Record.of(Slot.of("test", "test"))))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Attr.of("unittest"), Slot.of("foo", "bar")));
      assertEquals(context.eval("js", "record.getAttr('unittest').stringValue()").asString(), "");
      assertEquals(context.eval("js", "attr.getAttr('test2').stringValue()").asString(), "test");
      assertEquals(bridge.guestToHost(context.eval("js", "attr2.getAttr('test')")), Record.of(Slot.of("test", "test")));
      assertEquals(bridge.guestToHost(context.eval("js", "attr2.getAttr('test2')")), Item.absent());
    }
  }

  @Test
  public void testGetSlot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"),
          Slot.of(Num.from(7877), "number")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.getSlot('test')")), Item.absent());
      assertEquals(context.eval("js", "record.getSlot('foo').stringValue()").asString(), "bar");
      assertEquals(context.eval("js", "record.getSlot(7877).stringValue()").asString(), "number");
    }
  }

  @Test
  public void testGetField() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number"))));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("test", "test")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number")));
      assertEquals(context.eval("js", "record.getField('foo').stringValue()").asString(), "bar");
      assertEquals(context.eval("js", "record.getField(7877).stringValue()").asString(), "number");
      assertEquals(bridge.guestToHost(context.eval("js", "slot.getField('test')")), null);
    }
  }

  @Test
  public void testGetItem() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(7877), "number")));
      assertEquals(context.eval("js", "record.getItem(0).stringValue()").asString(), "bar");
      assertEquals(context.eval("js", "record.getItem(0).key.stringValue()").asString(), "foo");
      assertEquals(bridge.guestToHost(context.eval("js", "record.getItem(1)")), Slot.of(Num.from(7877), "number"));
      assertEquals(bridge.guestToHost(context.eval("js", "record.getItem(2)")), Item.absent());
    }
  }

  @Test
  public void testUpdatedAttr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.create()));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedAttr('foo', 'bar')")),
          Record.of(Attr.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedAttr('foo', 2)")),
          Record.of(Attr.of("foo", 2)));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedAttr('foo', eight)")),
          Record.of(Attr.of("foo", 8)));
    }
  }

  @Test
  public void testUpdatedSlot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.create()));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedSlot('foo', 'bar')")),
          Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedSlot('foo', 2)")),
          Record.of(Slot.of("foo", 2)));
      assertEquals(bridge.guestToHost(context.eval("js", "record.updatedSlot('foo', eight)")),
          Record.of(Slot.of("foo", 8)));
    }
  }

  @Test
  public void testAppended() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("test", "test")));
      bindings.putMember("record2", bridge.hostToGuest(Record.of(Slot.of("foo2", "bar2"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "slot")), Slot.of("test", "test"));
      assertEquals(bridge.guestToHost(context.eval("js", "record2")), Record.of(Slot.of("foo2", "bar2")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.appended(slot)")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.appended(record2)")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test"), Record.of(Slot.of("foo2", "bar2"))));

    }
  }

  @Test
  public void testPrepended() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("test", "test")));
      bindings.putMember("record2", bridge.hostToGuest(Record.of(Slot.of("foo2", "bar2"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "slot")), Slot.of("test", "test"));
      assertEquals(bridge.guestToHost(context.eval("js", "record2")), Record.of(Slot.of("foo2", "bar2")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.prepended(slot)")),
          Record.of(Slot.of("test", "test"), Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.prepended(record2)")),
          Record.of(Record.of(Slot.of("foo2", "bar2")), Slot.of("test", "test"), Slot.of("foo", "bar")));
    }
  }

  @Test
  public void testRemoved() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(1111), "test"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("foo", "bar"), Slot.of(Num.from(1111), "test")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.removed(1111)")),
          Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.removed('foo')")),
          Item.empty());
    }
  }

  @Test
  public void testConcat() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("emptyRecord", bridge.hostToGuest(Record.create()));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("testA", "testA")));
      bindings.putMember("keySlot", bridge.hostToGuest(Record.of(Slot.of("testB"))));
      bindings.putMember("attr", bridge.hostToGuest(Attr.of("testC", "testC")));

      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord.concat(slot)")),
          Record.of(Slot.of("testA", "testA")));
      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord.concat(keySlot)")),
          Record.of(Slot.of("testB")));
      assertEquals(bridge.guestToHost(context.eval("js", "emptyRecord.concat(attr)")),
          Record.of(Attr.of("testC", "testC")));
    }
  }

  @Test
  public void testConditional() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 5), Slot.of("b", true), Slot.of("c", false))));
      bindings.putMember("bRecord", bridge.hostToGuest(Record.of("b")));
      bindings.putMember("cRecord", bridge.hostToGuest(Record.of("c")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 5), Slot.of("b", true), Slot.of("c", false)));
      assertEquals(context.eval("js",
          "record.get('a').conditional(record.get('b'), record.get('c')).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('d').conditional(bRecord, cRecord)")),
          Record.of("c"));
    }
  }

  @Test
  public void testOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3)));
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('d').or(record.get('e'))")), Item.absent());
      assertEquals(context.eval("js", "record.get('a').or(record.get('d')).numberValue()").asInt(), 5);
      assertEquals(context.eval("js", "record.get('a').or(record.get('b')).numberValue()").asInt(), 5);
      assertEquals(context.eval("js", "record.get('b').or(record.get('a')).numberValue()").asInt(), 2);
    }
  }

  @Test
  public void testAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3)));
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('d').and(record.get('e'))")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('a').and(record.get('d'))")), Item.absent());
      assertEquals(context.eval("js", "record.get('a').and(record.get('b')).numberValue()").asInt(), 2);
      assertEquals(context.eval("js", "record.get('b').and(record.get('a')).numberValue()").asInt(), 5);
    }
  }

  @Test
  public void testBitwiseOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3)));
      assertEquals(context.eval("js", "record.get('a').bitwiseOr(record.get('b')).numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "record.get('b').bitwiseOr(record.get('c')).numberValue()").asInt(), 3);
      assertEquals(context.eval("js",
          "record.get('a').bitwiseOr(record.get('b')).bitwiseOr(record.get('c')).numberValue()").asInt(), 7);
      assertEquals(bridge.guestToHost(context.eval("js", "record.get('c').and(record.get('d'))")), Item.absent());
    }
  }

  @Test
  public void testBitwiseXor() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3))));
      bindings.putMember("ten", bridge.hostToGuest(Num.from(10)));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 5), Slot.of("b", 2), Slot.of("c", 3)));
      assertEquals(context.eval("js", "record.get('a').bitwiseXor(ten).numberValue()").asInt(), 15);
      assertEquals(context.eval("js", "record.get('a').bitwiseXor(record.get('b')).numberValue()").asInt(), 7);
      assertEquals(context.eval("js",
          "record.get('a').bitwiseXor(record.get('b')).bitwiseXor(record.get('c')).numberValue()").asInt(), 4);
    }
  }

  @Test
  public void testBitwiseAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record",
          bridge.hostToGuest(Record.of(Slot.of("a", 155), Slot.of("b", 275), Slot.of("c", 3377))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", 155), Slot.of("b", 275), Slot.of("c", 3377)));
      assertEquals(context.eval("js", "record.get('a').bitwiseAnd(record.get('b')).numberValue()").asInt(), 19);
      assertEquals(context.eval("js", "record.get('b').bitwiseAnd(record.get('c')).numberValue()").asInt(), 273);
      assertEquals(context.eval("js",
          "record.get('a').bitwiseAnd(record.get('b')).bitwiseAnd(record.get('c')).numberValue()").asInt(), 17);
    }
  }

  @Test
  public void testLT() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "six.lt(seven).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "seven.lt(six)")), Item.absent());
    }
  }

  @Test
  public void testLE() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "six.le(seven).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "seven.le(six)")), Item.absent());
      assertEquals(context.eval("js", "seven.le(six).booleanValue()").asBoolean(), false);
      assertEquals(context.eval("js", "seven.le(seven).booleanValue()").asBoolean(), true);
    }
  }

  @Test
  public void testEQ() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "six.eq(six).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "seven.eq(six)")), Item.absent());
    }
  }

  @Test
  public void testNE() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.ne(six).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "six.ne(six)")), Item.absent());
    }
  }

  @Test
  public void testGE() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.ge(six).booleanValue()").asBoolean(), true);
      assertEquals(context.eval("js", "six.ge(six).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "six.ge(seven)")), Item.absent());
    }
  }

  @Test
  public void testGT() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.gt(six).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "six.gt(six)")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "six.gt(seven)")), Item.absent());
    }
  }

  @Test
  public void testPlus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.plus(six).numberValue()").asInt(), 13);
      assertEquals(context.eval("js", "six.plus(seven).numberValue()").asInt(), 13);
    }
  }

  @Test
  public void testMinus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.minus(six).numberValue()").asInt(), 1);
      assertEquals(context.eval("js", "six.minus(seven).numberValue()").asInt(), -1);
    }
  }

  @Test
  public void testTimes() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "seven.times(six).numberValue()").asInt(), 42);
      assertEquals(context.eval("js", "six.times(seven).numberValue()").asInt(), 42);
    }
  }

  @Test
  public void testDivide() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));
      bindings.putMember("two", bridge.hostToGuest(Num.from(2)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "seven.numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "two.numberValue()").asInt(), 2);
      assertEquals(context.eval("js", "seven.divide(two).numberValue()").asDouble(), 3.5);
      assertEquals(context.eval("js", "six.divide(two).numberValue()").asInt(), 3);
    }
  }

  @Test
  public void testModulo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("seventeen", bridge.hostToGuest(Num.from(17)));
      bindings.putMember("twentyTwo", bridge.hostToGuest(Num.from(22)));
      bindings.putMember("two", bridge.hostToGuest(Num.from(2)));

      assertEquals(context.eval("js", "seventeen.numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.numberValue()").asInt(), 22);
      assertEquals(context.eval("js", "two.numberValue()").asInt(), 2);
      assertEquals(context.eval("js", "seventeen.modulo(two).numberValue()").asInt(), 1);
      assertEquals(context.eval("js", "twentyTwo.modulo(two).numberValue()").asInt(), 0);
    }
  }

  @Test
  public void testNot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("trueValue", bridge.hostToGuest(Bool.from(true)));
      bindings.putMember("falseValue", bridge.hostToGuest(Bool.from(false)));
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));

      assertEquals(context.eval("js", "six.numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "trueValue.booleanValue()").asBoolean(), true);
      assertEquals(context.eval("js", "falseValue.booleanValue()").asBoolean(), false);
      assertEquals(context.eval("js", "trueValue.not().booleanValue()").asBoolean(), false);
      assertEquals(context.eval("js", "falseValue.not().booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "six.not()")), Item.absent());
    }
  }

  @Test
  public void testBitwiseNot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("seventeen", bridge.hostToGuest(Num.from(17)));
      bindings.putMember("twentyTwo", bridge.hostToGuest(Num.from(22)));
      bindings.putMember("bigNumber", bridge.hostToGuest(Num.from(2265453)));

      assertEquals(context.eval("js", "bigNumber.numberValue()").asInt(), 2265453);
      assertEquals(context.eval("js", "seventeen.numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.numberValue()").asInt(), 22);
      assertEquals(context.eval("js", "bigNumber.bitwiseNot().numberValue()").asInt(), -2265454);
      assertEquals(context.eval("js", "seventeen.bitwiseNot().numberValue()").asInt(), -18);
      assertEquals(context.eval("js", "twentyTwo.bitwiseNot().numberValue()").asInt(),  -23);
    }
  }

  @Test
  public void testNegative() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("seventeen", bridge.hostToGuest(Num.from(17)));
      bindings.putMember("twentyTwo", bridge.hostToGuest(Num.from(22)));
      bindings.putMember("bigNumber", bridge.hostToGuest(Num.from(2265453)));

      assertEquals(context.eval("js", "bigNumber.numberValue()").asInt(), 2265453);
      assertEquals(context.eval("js", "seventeen.numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.numberValue()").asInt(), 22);
      assertEquals(context.eval("js", "bigNumber.negative().numberValue()").asInt(), -2265453);
      assertEquals(context.eval("js", "seventeen.negative().numberValue()").asInt(), -17);
      assertEquals(context.eval("js", "twentyTwo.negative().numberValue()").asInt(),  -22);
    }
  }

  @Test
  public void testPositive() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("seventeen", bridge.hostToGuest(Num.from(17)));
      bindings.putMember("twentyTwo", bridge.hostToGuest(Num.from(22)));
      bindings.putMember("bigNumber", bridge.hostToGuest(Num.from(2265453)));
      bindings.putMember("neBigNumber", bridge.hostToGuest(Num.from(-2265453)));

      assertEquals(context.eval("js", "bigNumber.numberValue()").asInt(), 2265453);
      assertEquals(context.eval("js", "seventeen.numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.numberValue()").asInt(), 22);
      assertEquals(context.eval("js", "neBigNumber.positive().numberValue()").asInt(), -2265453);
      assertEquals(context.eval("js", "bigNumber.positive().numberValue()").asInt(), 2265453);
      assertEquals(context.eval("js", "seventeen.positive().numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.positive().numberValue()").asInt(),  22);
      assertEquals(context.eval("js", "neBigNumber.positive().numberValue()").asInt(), -2265453);
    }
  }

  @Test
  public void testInverse() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("seventeen", bridge.hostToGuest(Num.from(17)));
      bindings.putMember("twentyTwo", bridge.hostToGuest(Num.from(22)));
      bindings.putMember("bigNumber", bridge.hostToGuest(Num.from(2265453)));

      assertEquals(context.eval("js", "bigNumber.numberValue()").asInt(), 2265453);
      assertEquals(context.eval("js", "seventeen.numberValue()").asInt(), 17);
      assertEquals(context.eval("js", "twentyTwo.numberValue()").asInt(), 22);
      assertEquals(context.eval("js", "bigNumber.inverse().doubleValue()").asDouble(), 4.414128211885217E-7);
      assertEquals(context.eval("js", "seventeen.inverse().floatValue()").asFloat(), 0.058823529411764705f);
      assertEquals(context.eval("js", "twentyTwo.inverse().numberValue()").asDouble(),  0.045454545454545456);
    }
  }

  @Test
  public void testInvoke() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("a", "a"), Slot.of("b", "b"))));
      bindings.putMember("slot", bridge.hostToGuest(Slot.of("c", "c")));
      bindings.putMember("text", bridge.hostToGuest(Text.from("st")));

      assertEquals(bridge.guestToHost(context.eval("js", "record")),
          Record.of(Slot.of("a", "a"), Slot.of("b", "b")));
      assertEquals(bridge.guestToHost(context.eval("js", "slot")), Slot.of("c", "c"));
      assertEquals(bridge.guestToHost(context.eval("js", "text")), Text.from("st"));
      assertEquals(bridge.guestToHost(context.eval("js", "record.invoke(record.get('a'))")), Item.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "slot.invoke(text)")), Item.absent());
    }
  }

//  //fixme:
//  @Test
//  public void testLambda() {
//    try (Context context = Context.create()) {
//      final JavaHostRuntime runtime = new JavaHostRuntime();
//      final VmBridge bridge = new VmBridge(runtime, "js");
//      runtime.addHostLibrary(SwimStructure.LIBRARY);
//
//      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
//      Record record = Record.of(Slot.of("x", 3), Slot.of("y", 2), Slot.of("y", 40));
//      System.out.println(Text.from("x").lambda(Record.of(record.get("x").plus(record.get("x")))));
//      System.out.println(Record.of(Slot.of("f", Text.from("x").lambda(record.get("x")))));
//
//      Record scope = Record.of(Slot.of("f", Text.from("x").lambda(record.get("x"))));
//
//      System.out.println(record.get("f").invoke(Num.from(3)).evaluate(scope));
//    }
//  }

//  //fixme:
//  @Test
//  public void testFilter() {
//    try (Context context = Context.create()) {
//      final JavaHostRuntime runtime = new JavaHostRuntime();
//      final VmBridge bridge = new VmBridge(runtime, "js");
//      runtime.addHostLibrary(SwimStructure.LIBRARY);
//
//      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
//    }
//  }

  @Test
  public void testMax() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Num.from(5)));
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.max(seven).intValue()").asInt(), 7);
      assertEquals(context.eval("js", "six.max(five).intValue()").asInt(), 6);
    }
  }

  @Test
  public void testMin() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Num.from(5)));
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("seven", bridge.hostToGuest(Num.from(7)));

      assertEquals(context.eval("js", "six.min(seven).intValue()").asInt(), 6);
      assertEquals(context.eval("js", "six.min(five).intValue()").asInt(), 5);
    }
  }

  @Test
  public void testValueConvertion() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

      bindings.putMember("byte1", bridge.hostToGuest(Num.from(127)));
      bindings.putMember("byte2", bridge.hostToGuest(Num.from(130)));
      bindings.putMember("shortNum", bridge.hostToGuest(Num.from(1000)));
      bindings.putMember("longNum", bridge.hostToGuest(Num.from(12345678)));
      bindings.putMember("doubleNum", bridge.hostToGuest(Num.from(1023d)));
      bindings.putMember("integerNum", bridge.hostToGuest(Num.from((Integer) 17)));
      bindings.putMember("charVal", bridge.hostToGuest(Num.from('c')));

      assertEquals(context.eval("js", "byte1.byteValue()").asByte(), 127);
      assertEquals(context.eval("js", "byte2.byteValue()").asByte(), -126);
      assertEquals(context.eval("js", "shortNum.shortValue()").asShort(), 1000);
      assertEquals(context.eval("js", "longNum.longValue()").asLong(), 12345678);
      //assertEquals(context.eval("js", "integerNum.integerValue()").asInt(), 17);
      assertEquals(context.eval("js", "charVal.charValue()").asString(), "c");
    }
  }

  //fixme
  @Test
  public void testCast() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

    }
  }

  //fixme
  @Test
  public void testCoerce() {
  }

  @Test
  public void testIsAliased() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));
      bindings.putMember("extant", bridge.hostToGuest(Item.extant()));
      bindings.putMember("emptyRecord", bridge.hostToGuest(Record.create()));
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(context.eval("js", "empty.isAliased()").asBoolean(), true);
      assertEquals(context.eval("js", "extant.isAliased()").asBoolean(), false);
      assertEquals(context.eval("js", "emptyRecord.isAliased()").asBoolean(), true);
      assertEquals(context.eval("js", "record.isAliased()").asBoolean(), false);
    }
  }

  @Test
  public void testIsMutable() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));
      bindings.putMember("extant", bridge.hostToGuest(Item.extant()));
      bindings.putMember("emptyRecord", bridge.hostToGuest(Record.create()));
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(context.eval("js", "empty.isMutable()").asBoolean(), false);
      assertEquals(context.eval("js", "extant.isMutable()").asBoolean(), false);
      assertEquals(context.eval("js", "emptyRecord.isMutable()").asBoolean(), true);
      assertEquals(context.eval("js", "record.isMutable()").asBoolean(), true);
    }
  }

  @Test
  public void testAlias() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      context.eval("js", "record.alias()");
      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
    }
  }

  @Test
  public void testBranch() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record.branch()")), Record.of(Slot.of("foo", "bar")));
    }
  }

  @Test
  public void testCommit() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(bridge.guestToHost(context.eval("js", "empty.commit()")), Item.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "record.commit()")), Record.of(Slot.of("foo", "bar")));
    }
  }

  @Test
  public void testPreceence() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(context.eval("js", "empty.precedence()").asInt(), 12);
      assertEquals(context.eval("js", "record.precedence()").asInt(), 12);
    }
  }

  @Test
  public void testTypeOrder() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Item.empty()));
      bindings.putMember("extant", bridge.hostToGuest(Item.extant()));
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("record1", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "test1"), Slot.of("A", "A"))));

      assertEquals(context.eval("js", "empty.typeOrder()").asInt(), 3);
      assertEquals(context.eval("js", "extant.typeOrder()").asInt(), 98);
      assertEquals(context.eval("js", "record.typeOrder()").asInt(), 3);
      assertEquals(context.eval("js", "record1.typeOrder()").asInt(), 3);

    }
  }

  @Test
  public void testKeyEquals() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));
      bindings.putMember("record1", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("test", "test1"), Slot.of("A", "A"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "record1")),
          Record.of(Slot.of("foo", "bar"), Slot.of("test", "test1"), Slot.of("A", "A")));
      assertEquals(context.eval("js", "record.getItem(0).keyEquals(record1.getItem(0))").asBoolean(), true);

    }
  }

  //fixme
  @Test
  public void testDebug() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

    }
  }

  //fixme
  @Test
  public void testDisplay() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

    }
  }

  //fixme
  @Test
  public void testToString() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar")));
//      assertEquals(context.eval("js", "record.toString()"), "Record.of(Slot.of('foo', 'bar'))");
    }
  }
}

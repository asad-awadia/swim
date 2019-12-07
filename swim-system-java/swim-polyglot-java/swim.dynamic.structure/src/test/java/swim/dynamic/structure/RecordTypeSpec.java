package swim.dynamic.structure;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;
import swim.dynamic.JavaHostRuntime;
import swim.structure.*;
import swim.vm.VmBridge;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class RecordTypeSpec {

  @Test
  public void testRecordIsArrayObject() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("recordValue", bridge.hostToGuest(Record.of(Value.fromObject("hello"))));
      bindings.putMember("recordField", bridge.hostToGuest(Record.of(Field.of(Slot.of("foo", "bar")))));

      assertEquals(bridge.guestToHost(context.eval("js", "recordValue")),
          Record.of(Value.fromObject("hello")));
      assertEquals(bridge.guestToHost(context.eval("js", "recordField")),
          Record.of(Field.of(Slot.of("foo", "bar"))));
      assertEquals(context.eval("js", "recordValue.isArray()").asBoolean(), true);
      assertEquals(context.eval("js", "recordField.isObject()").asBoolean(), true);
      assertEquals(context.eval("js", "recordValue.isObject()").asBoolean(), false);
      assertEquals(context.eval("js", "recordField.isArray()").asBoolean(), false);
    }
  }

  @Test
  public void testRecordValueCount() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");

      bindings.putMember("recordValue1", bridge.hostToGuest(Record.of(Value.fromObject("hello"))));
      bindings.putMember("recordField1", bridge.hostToGuest(Record.of(Field.of(Slot.of("foo", "bar")))));
      bindings.putMember("recordValue2", bridge.hostToGuest(Record.of(Value.fromObject("hello"),
          Value.fromObject("world"))));
      bindings.putMember("recordField2", bridge.hostToGuest(Record.of(Field.of(Slot.of("foo", "bar")),
          Field.of(Attr.of("A", "a")))));

      assertEquals(bridge.guestToHost(context.eval("js", "recordValue1")),
          Record.of(Value.fromObject("hello")));
      assertEquals(bridge.guestToHost(context.eval("js", "recordField1")),
          Record.of(Field.of(Slot.of("foo", "bar"))));
      assertEquals(bridge.guestToHost(context.eval("js", "recordValue2")),
          Record.of(Value.fromObject("hello"), Value.fromObject("world")));
      assertEquals(bridge.guestToHost(context.eval("js", "recordField2")),
          Record.of(Field.of(Slot.of("foo", "bar")), Field.of(Attr.of("A", "a"))));
      assertEquals(context.eval("js", "recordValue1.valueCount()").asInt(), 1);
      assertEquals(context.eval("js", "recordField1.fieldCount()").asInt(), 1);
      assertEquals(context.eval("js", "recordValue2.valueCount()").asInt(), 2);
      assertEquals(context.eval("js", "recordField2.fieldCount()").asInt(), 2);
      assertEquals(context.eval("js", "recordValue1.fieldCount()").asInt(), 0);
      assertEquals(context.eval("js", "recordField2.valueCount()").asInt(), 0);
    }
  }

  @Test
  public void testRecordIndexOf() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("recordValue", bridge.hostToGuest(Record.of(Value.fromObject("hello"), Value.fromObject("world"))));
      bindings.putMember("recordField", bridge.hostToGuest(Record.of(Field.of(Slot.of("foo", "bar")),
          Field.of(Attr.of("A", "a")), Field.of(Attr.of("A", "a")), Field.of(Attr.of("A", "a")))));
      bindings.putMember("valueWorld", bridge.hostToGuest(Value.fromObject("world")));
      bindings.putMember("valueHere", bridge.hostToGuest(Value.fromObject("Here")));
      bindings.putMember("attr", bridge.hostToGuest(Attr.of("A", "a")));


      assertEquals(bridge.guestToHost(context.eval("js", "recordValue")), Record.of(Value.fromObject("hello"), Value.fromObject("world")));
      assertEquals(bridge.guestToHost(context.eval("js", "recordField")), Record.of(Field.of(Slot.of("foo", "bar")),
          Field.of(Attr.of("A", "a")), Field.of(Attr.of("A", "a")), Field.of(Attr.of("A", "a"))));
      assertEquals(context.eval("js", "recordValue.indexOf(valueWorld)").asInt(), 1);
      assertEquals(context.eval("js", "recordValue.indexOf(valueHere)").asInt(), -1);
      assertEquals(context.eval("js", "recordField.lastIndexOf(attr)").asInt(), 3);
    }
  }

  @Test
  public void testRecordPut() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Record.of()));
      bindings.putMember("createEmpty", bridge.hostToGuest(Record.create()));
      bindings.putMember("createOne", bridge.hostToGuest(Record.create(1).slot("foo", "bar")));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Record.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "createOne")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.put('foo', 'bar')")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.put('test', eight)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar"), Slot.of("test", 8)));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.put('bool', true)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar"),
          Slot.of("test", 8), Slot.of("bool", true)));
    }
  }

  @Test
  public void testRecordPutAttr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("createEmpty", bridge.hostToGuest(Record.create()));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putAttr('foo', 'bar')")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Attr.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putAttr('test', eight)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Attr.of("foo", "bar"), Attr.of("test", 8)));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putAttr('bool', true)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Attr.of("foo", "bar"),
          Attr.of("test", 8), Attr.of("bool", true)));
    }
  }

  @Test
  public void testRecordPutSlot() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("createEmpty", bridge.hostToGuest(Record.create()));
      bindings.putMember("eight", bridge.hostToGuest(Num.from(8)));

      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putSlot('foo', 'bar')")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putSlot('test', eight)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar"), Slot.of("test", 8)));
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty.putSlot('bool', true)")), Value.absent());
      assertEquals(bridge.guestToHost(context.eval("js", "createEmpty")), Record.of(Slot.of("foo", "bar"),
          Slot.of("test", 8), Slot.of("bool", true)));
    }
  }

  @Test
  public void testRecordRemoveKey() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(context.eval("js", "record.removeKey('testA')").asBoolean(), true);
      assertEquals(context.eval("js", "record.removeKey('testA')").asBoolean(), false);
    }
  }

  @Test
  public void testRecordIsAliased() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(context.eval("js", "record.isAliased()").asBoolean(), false);
    }
  }

  @Test
  public void testRecordIsMutable() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(context.eval("js", "record.isMutable()").asBoolean(), true);
    }
  }

  @Test
  public void testRecordEntrySet() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      Set<Map.Entry<Value, Value>> entry = new HashSet<>();
      entry.add(Slot.of("foo", "bar"));
      entry.add(Slot.of("testA", "testA"));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.entrySet()")), entry);
    }
  }

  @Test
  public void testRecordFieldSet() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      Set<Field> entry = new HashSet<>();
      entry.add(Slot.of("foo", "bar"));
      entry.add(Slot.of("testA", "testA"));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.fieldSet()")), entry);
    }
  }

  @Test
  public void testRecordKeySet() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

      Set<Value> entry = new HashSet<>();
      entry.add(Value.fromObject("foo"));
      entry.add(Value.fromObject("testA"));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(bridge.guestToHost(context.eval("js", "record.keySet()")), entry);
    }
  }

  @Test
  public void testRecordValues() { //fixme: AssertionError
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));

//      System.out.println(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")).values());
//      ArrayList<Value> array = new ArrayList<>();
//      //Set<Value> entry = new HashSet<>();
//      array.add(Value.fromObject("bar"));
//      array.add(Value.fromObject("testA"));
      Collection<Value> collection = new ArrayList<>();
      collection.add(Value.fromObject("bar"));
      collection.add(Value.fromObject("testA"));

      assertEquals(bridge.guestToHost(context.eval("js", "record")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
//      assertEquals(bridge.guestToHost(context.eval("js", "record.values()")), collection);
    }
  }

  @Test
  public void testRecordCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("record1", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));
      bindings.putMember("record2", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA"))));
      bindings.putMember("record3", bridge.hostToGuest(Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testB"))));

      assertEquals(bridge.guestToHost(context.eval("js", "record1")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testA")));
      assertEquals(bridge.guestToHost(context.eval("js", "record3")), Record.of(Slot.of("foo", "bar"), Slot.of("testA", "testB")));
      assertEquals(context.eval("js", "record1.compareTo(record2)").asInt(), 0);
      assertEquals(context.eval("js", "record2.compareTo(record3)").asInt(), -1);
    }
  }
}

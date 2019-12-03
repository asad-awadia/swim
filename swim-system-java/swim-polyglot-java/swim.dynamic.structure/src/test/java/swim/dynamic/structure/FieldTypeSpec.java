package swim.dynamic.structure;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Field;
import swim.structure.Item;
import swim.structure.Slot;
import swim.structure.Value;
import swim.vm.VmBridge;
import static org.testng.Assert.assertEquals;

public class FieldTypeSpec {
  @Test
  public void testValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("field", bridge.hostToGuest(Field.of(Slot.of("foo", "bar"))));

      assertEquals(bridge.guestToHost(context.eval("js", "field")), Field.of(Slot.of("foo", "bar")));
      assertEquals(context.eval("js", "field.value.stringValue()").asString(), "bar");
      assertEquals(bridge.guestToHost(context.eval("js", "field.get('foo')")), Item.absent());
    }
  }

  @Test
  public void testUpdatedValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("field", bridge.hostToGuest(Field.of(Slot.of("foo", "bar"))));
      bindings.putMember("bar2", bridge.hostToGuest(Value.fromObject("bar2")));

      assertEquals(bridge.guestToHost(context.eval("js", "field")), Field.of(Slot.of("foo", "bar")));
      assertEquals(bridge.guestToHost(context.eval("js", "field.updatedValue(bar2)")), Slot.of("foo", "bar2"));
    }
  }

  @Test
  public void testConditional() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("fieldA", bridge.hostToGuest(Field.of(Slot.of("a", 5))));
      bindings.putMember("fieldB", bridge.hostToGuest(Field.of(Slot.of("b", true))));
      bindings.putMember("fieldC", bridge.hostToGuest(Field.of(Slot.of("c", false))));

      assertEquals(bridge.guestToHost(context.eval("js", "fieldA")), Field.of(Slot.of("a", 5)));
      assertEquals(bridge.guestToHost(context.eval("js", "fieldB")), Field.of(Slot.of("b", true)));
      assertEquals(bridge.guestToHost(context.eval("js", "fieldC")), Field.of(Slot.of("c", false)));
      assertEquals(context.eval("js", "fieldA.value.conditional(fieldB.value, fieldC.value).booleanValue()").asBoolean(), true);
    }
  }

  @Test
  public void testOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("fieldA", bridge.hostToGuest(Field.of(Slot.of("a", 5))));
      bindings.putMember("fieldB", bridge.hostToGuest(Field.of(Slot.of("b", 2))));

      assertEquals(context.eval("js", "fieldA.value.or(fieldB.value).numberValue()").asInt(), 5);
      assertEquals(context.eval("js", "fieldB.value.or(fieldA.value).numberValue()").asInt(), 2);


    }
  }

  @Test
  public void testAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("fieldA", bridge.hostToGuest(Field.of(Slot.of("a", 5))));
      bindings.putMember("fieldB", bridge.hostToGuest(Field.of(Slot.of("b", 2))));

      assertEquals(context.eval("js", "fieldA.value.and(fieldB.value).numberValue()").asInt(), 2);
      assertEquals(context.eval("js", "fieldB.value.and(fieldA.value).numberValue()").asInt(), 5);
    }
  }
}

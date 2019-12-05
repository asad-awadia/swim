package swim.dynamic.structure;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Slot;
import swim.vm.VmBridge;
import static org.testng.Assert.assertEquals;

public class SlotTypeSpec {
  @Test
  public void testCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("slot1", bridge.hostToGuest(Slot.of("foo", 2)));
      bindings.putMember("slot2", bridge.hostToGuest(Slot.of("bar", true)));
      bindings.putMember("slot3", bridge.hostToGuest(Slot.of("foo", "bar")));
      bindings.putMember("slot4", bridge.hostToGuest(Slot.of("foo", 2)));

      assertEquals(bridge.guestToHost(context.eval("js", "slot1")), Slot.of("foo", 2));
      assertEquals(bridge.guestToHost(context.eval("js", "slot2")), Slot.of("bar", true));
      assertEquals(bridge.guestToHost(context.eval("js", "slot3")), Slot.of("foo", "bar"));
      assertEquals(context.eval("js", "slot1.compareTo(slot2)").asInt(), 4);
      assertEquals(context.eval("js", "slot1.compareTo(slot4)").asInt(), 0);
      assertEquals(context.eval("js", "slot2.compareTo(slot3)").asInt(), -4);
    }
  }
}

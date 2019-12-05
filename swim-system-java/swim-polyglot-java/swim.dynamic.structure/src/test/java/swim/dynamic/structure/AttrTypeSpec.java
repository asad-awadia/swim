package swim.dynamic.structure;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Attr;
import swim.vm.VmBridge;
import static org.testng.Assert.assertEquals;

public class AttrTypeSpec {
  @Test
  public void testName() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("attr", bridge.hostToGuest(Attr.of("foo", "bar")));

      assertEquals(bridge.guestToHost(context.eval("js", "attr")), Attr.of("foo", "bar"));
      assertEquals(context.eval("js", "attr.name").asString(), "foo");
    }
  }

  @Test
  public void testCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("attr1", bridge.hostToGuest(Attr.of("foo", 2)));
      bindings.putMember("attr2", bridge.hostToGuest(Attr.of("bar", true)));
      bindings.putMember("attr3", bridge.hostToGuest(Attr.of("foo", "bar")));
      bindings.putMember("attr4", bridge.hostToGuest(Attr.of("foo", 2)));

      assertEquals(bridge.guestToHost(context.eval("js", "attr1")), Attr.of("foo", 2));
      assertEquals(bridge.guestToHost(context.eval("js", "attr2")), Attr.of("bar", true));
      assertEquals(bridge.guestToHost(context.eval("js", "attr3")), Attr.of("foo", "bar"));
      assertEquals(context.eval("js", "attr1.compareTo(attr2)").asInt(), 4);
      assertEquals(context.eval("js", "attr1.compareTo(attr4)").asInt(), 0);
      assertEquals(context.eval("js", "attr2.compareTo(attr3)").asInt(), -4);
    }
  }
}

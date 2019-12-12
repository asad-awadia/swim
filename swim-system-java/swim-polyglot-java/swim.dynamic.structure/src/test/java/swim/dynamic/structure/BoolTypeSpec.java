package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Bool;
import swim.structure.Value;
import swim.vm.VmBridge;

import static org.testng.Assert.assertEquals;

public class BoolTypeSpec {
  @Test
  public void testBoolConditional() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("boolTrue", bridge.hostToGuest(Bool.from(true)));
      bindings.putMember("boolFalse", bridge.hostToGuest(Bool.from(false)));
      bindings.putMember("hello", bridge.hostToGuest(Value.fromObject("hello")));
      bindings.putMember("six", bridge.hostToGuest(Value.fromObject(6)));

      assertEquals(bridge.guestToHost(context.eval("js", "boolTrue.conditional(hello, six)")),
          Value.fromObject("hello"));
      assertEquals(bridge.guestToHost(context.eval("js", "boolFalse.conditional(hello, six)")),
          Value.fromObject(6));
    }
  }

  @Test
  public void testBoolOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("boolTrue", bridge.hostToGuest(Bool.from(true)));
      bindings.putMember("boolFalse", bridge.hostToGuest(Bool.from(false)));
      bindings.putMember("hello", bridge.hostToGuest(Value.fromObject("hello")));
      bindings.putMember("six", bridge.hostToGuest(Value.fromObject(6)));

      assertEquals(bridge.guestToHost(context.eval("js", "boolFalse.or(hello)")),
          Value.fromObject("hello"));
      assertEquals(bridge.guestToHost(context.eval("js", "boolFalse.or(six)")),
          Value.fromObject(6));
      assertEquals(bridge.guestToHost(context.eval("js", "boolTrue.or(hello)")),
          Bool.from(true));
    }
  }

  @Test
  public void testBoolAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("boolTrue", bridge.hostToGuest(Bool.from(true)));
      bindings.putMember("boolFalse", bridge.hostToGuest(Bool.from(false)));
      bindings.putMember("hello", bridge.hostToGuest(Value.fromObject("hello")));
      bindings.putMember("six", bridge.hostToGuest(Value.fromObject(6)));

      assertEquals(bridge.guestToHost(context.eval("js", "boolFalse.and(hello)")),
          Bool.from(false));
      assertEquals(bridge.guestToHost(context.eval("js", "boolFalse.and(six)")),
          Bool.from(false));
      assertEquals(bridge.guestToHost(context.eval("js", "boolTrue.and(hello)")),
          Value.fromObject("hello"));
    }
  }

  @Test
  public void testBoolCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("boolTrue", bridge.hostToGuest(Bool.from(true)));
      bindings.putMember("boolFalse", bridge.hostToGuest(Bool.from(false)));

      assertEquals(context.eval("js", "boolTrue.compareTo(boolTrue)").asInt(), 0);
      assertEquals(context.eval("js", "boolFalse.compareTo(boolFalse)").asInt(), 0);
      assertEquals(context.eval("js", "boolTrue.compareTo(boolFalse)").asInt(), -1);
      assertEquals(context.eval("js", "boolFalse.compareTo(boolTrue)").asInt(), 1);
    }
  }
}

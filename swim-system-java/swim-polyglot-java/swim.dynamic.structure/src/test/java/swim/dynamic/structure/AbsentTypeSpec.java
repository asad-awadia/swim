package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Absent;
import swim.vm.VmBridge;

import static org.testng.Assert.assertEquals;

public class AbsentTypeSpec {
  @Test
  public void testAbsent() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("absent", bridge.hostToGuest(Absent.absent()));

      assertEquals(bridge.guestToHost(context.eval("js", "absent")), Absent.absent());
    }
  }
}

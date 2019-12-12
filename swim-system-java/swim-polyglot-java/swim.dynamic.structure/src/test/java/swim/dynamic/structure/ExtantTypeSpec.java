package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Extant;
import swim.vm.VmBridge;

import static org.testng.Assert.assertEquals;

public class ExtantTypeSpec {
  @Test
  public void testExtant() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("extant", bridge.hostToGuest(Extant.extant()));

      assertEquals(bridge.guestToHost(context.eval("js", "extant")), Extant.extant());
    }
  }
}

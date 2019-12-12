package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Text;
import swim.vm.VmBridge;

import static org.testng.Assert.assertEquals;

public class TextTypeSpec {
  @Test
  public void testTextSize() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("text", bridge.hostToGuest(Text.from("hello world")));
      bindings.putMember("textObject", bridge.hostToGuest(Text.fromObject("hello world")));

      assertEquals(context.eval("js", "text.size").asInt(), 11);
      assertEquals(context.eval("js", "textObject.size").asInt(), 11);
    }
  }

  //todo: output test case
//  @Test
//  public void testTextOutput() {
//    try (Context context = Context.create()) {
//      final JavaHostRuntime runtime = new JavaHostRuntime();
//      final VmBridge bridge = new VmBridge(runtime, "js");
//      runtime.addHostLibrary(SwimStructure.LIBRARY);
//
//      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
//
//      OutputSettings settings = new OutputSettings(";", true, true);
//      System.out.println(Text.output());
//      System.out.println(Text.output());
//    }
//  }

  @Test
  public void testTextEmpty() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Text.empty()));

      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Text.empty());
    }
  }
}

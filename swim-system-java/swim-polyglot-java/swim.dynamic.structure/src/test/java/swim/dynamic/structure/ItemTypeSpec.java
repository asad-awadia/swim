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

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Item;
import swim.structure.Num;
import swim.structure.Record;
import swim.structure.Slot;
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
}

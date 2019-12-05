package swim.dynamic.structure;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Value;
import swim.vm.VmBridge;

import static org.testng.Assert.assertEquals;

public class ValueTypeSpec {
  @Test
  public void testEmptyValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Value.empty()));

      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Value.empty());
      assertEquals(context.eval("js", "empty.isDefined()").asBoolean(), true);
      assertEquals(context.eval("js", "empty.isDistinct()").asBoolean(), true);

    }
  }

  @Test
  public void testExtantValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("extant", bridge.hostToGuest(Value.extant()));

      assertEquals(bridge.guestToHost(context.eval("js", "extant")), Value.extant());
      assertEquals(context.eval("js", "extant.isDefined()").asBoolean(), true);
      assertEquals(context.eval("js", "extant.isDistinct()").asBoolean(), false);
    }
  }

  @Test
  public void testAbsentValue() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("absent", bridge.hostToGuest(Value.absent()));

      assertEquals(bridge.guestToHost(context.eval("js", "absent")), Value.absent());
      assertEquals(context.eval("js", "absent.isDefined()").asBoolean(), false);
      assertEquals(context.eval("js", "absent.isDistinct()").asBoolean(), false);
    }
  }

  @Test
  public void testConditional() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("stateT", bridge.hostToGuest(Value.fromObject(true)));
      bindings.putMember("stateF", bridge.hostToGuest(Value.fromObject(false)));
      bindings.putMember("thenTerm", bridge.hostToGuest(Value.fromObject("Hello World")));
      bindings.putMember("elseTerm", bridge.hostToGuest(Value.fromObject("False Term")));

      assertEquals(bridge.guestToHost(context.eval("js", "stateT")), Value.fromObject(true));
      assertEquals(bridge.guestToHost(context.eval("js", "stateF")), Value.fromObject(false));
      assertEquals(bridge.guestToHost(context.eval("js", "thenTerm")), Value.fromObject("Hello World"));
      assertEquals(bridge.guestToHost(context.eval("js", "elseTerm")), Value.fromObject("False Term"));
      assertEquals(context.eval("js", "stateT.conditional(thenTerm, elseTerm).stringValue()").asString(), "Hello World");
      assertEquals(context.eval("js", "stateF.conditional(thenTerm, elseTerm).stringValue()").asString(), "False Term");
    }
  }

  @Test
  public void testOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));

      assertEquals(bridge.guestToHost(context.eval("js", "five")), Value.fromObject(5));
      assertEquals(bridge.guestToHost(context.eval("js", "two")), Value.fromObject(2));
      assertEquals(context.eval("js", "five.or(two).numberValue()").asInt(), 5);
      assertEquals(context.eval("js", "two.or(five).numberValue()").asInt(), 2);
    }
  }

  @Test
  public void testAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));

      assertEquals(context.eval("js", "five.and(two).numberValue()").asInt(), 2);
      assertEquals(context.eval("js", "two.and(five).numberValue()").asInt(), 5);
    }
  }

  @Test
  public void testBitwiseOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.bitwiseOr(two).numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "three.bitwiseOr(ten).numberValue()").asInt(), 11);
    }
  }

  @Test
  public void testBitwiseXor() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.bitwiseXor(two).numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "three.bitwiseXor(ten).numberValue()").asInt(), 9);
    }
  }

  @Test
  public void testBitwiseAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.bitwiseAnd(two).numberValue()").asInt(), 0);
      assertEquals(context.eval("js", "three.bitwiseAnd(ten).numberValue()").asInt(), 2);
    }
  }

  @Test
  public void testLt() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.lt(two).booleanValue()").asBoolean(), false);
      assertEquals(bridge.guestToHost(context.eval("js", "five.lt(two)")), Value.absent());
      assertEquals(context.eval("js", "three.lt(ten).booleanValue()").asBoolean(), true);
    }
  }

  @Test
  public void testLe() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.le(two).booleanValue()").asBoolean(), false);
      assertEquals(bridge.guestToHost(context.eval("js", "five.le(two)")), Value.absent());
      assertEquals(context.eval("js", "three.le(three).booleanValue()").asBoolean(), true);
    }
  }

  @Test
  public void testEq() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.eq(two).booleanValue()").asBoolean(), false);
      assertEquals(bridge.guestToHost(context.eval("js", "five.eq(two)")), Value.absent());
      assertEquals(context.eval("js", "three.eq(three).booleanValue()").asBoolean(), true);
    }
  }

  @Test
  public void testNe() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.ne(two).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "three.ne(three)")), Value.absent());
      assertEquals(context.eval("js", "three.ne(three).booleanValue()").asBoolean(), false);
    }
  }

  @Test
  public void testGe() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.ge(two).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "three.ge(ten)")), Value.absent());
      assertEquals(context.eval("js", "three.ge(ten).booleanValue()").asBoolean(), false);
    }
  }

  @Test
  public void testGt() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));

      assertEquals(context.eval("js", "five.gt(two).booleanValue()").asBoolean(), true);
      assertEquals(bridge.guestToHost(context.eval("js", "three.gt(three)")), Value.absent());
      assertEquals(context.eval("js", "three.gt(ten).booleanValue()").asBoolean(), false);
    }
  }

  @Test
  public void testPlus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));
      bindings.putMember("hundred", bridge.hostToGuest(Value.fromObject(100)));

      assertEquals(context.eval("js", "five.plus(two).numberValue()").asInt(), 7);
      assertEquals(context.eval("js", "three.plus(three).numberValue()").asInt(), 6);
      assertEquals(context.eval("js", "ten.plus(hundred).numberValue()").asInt(), 110);
    }
  }

  @Test
  public void testMinus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));
      bindings.putMember("hundred", bridge.hostToGuest(Value.fromObject(100)));

      assertEquals(context.eval("js", "five.minus(two).numberValue()").asInt(), 3);
      assertEquals(context.eval("js", "five.minus(five).numberValue()").asInt(), 0);
      assertEquals(context.eval("js", "ten.minus(hundred).numberValue()").asInt(), -90);
      assertEquals(context.eval("js", "three.minus(ten).numberValue()").asInt(), -7);
    }
  }

  @Test
  public void testTimes() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));
      bindings.putMember("hundred", bridge.hostToGuest(Value.fromObject(100)));

      assertEquals(context.eval("js", "five.times(two).numberValue()").asInt(), 10);
      assertEquals(context.eval("js", "five.times(five).numberValue()").asInt(), 25);
      assertEquals(context.eval("js", "ten.times(hundred).numberValue()").asInt(), 1000);
      assertEquals(context.eval("js", "three.times(ten).numberValue()").asInt(), 30);
    }
  }

  @Test
  public void testDivide() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));
      bindings.putMember("hundred", bridge.hostToGuest(Value.fromObject(100)));

      assertEquals(context.eval("js", "five.divide(two).numberValue()").asDouble(), 2.5);
      assertEquals(context.eval("js", "five.divide(five).numberValue()").asInt(), 1);
      assertEquals(context.eval("js", "ten.divide(hundred).numberValue()").asDouble(), 0.1);
      assertEquals(context.eval("js", "three.divide(ten).numberValue()").asDouble(), 0.3);
    }
  }

  @Test
  public void testeModulo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("five", bridge.hostToGuest(Value.fromObject(5)));
      bindings.putMember("two", bridge.hostToGuest(Value.fromObject(2)));
      bindings.putMember("three", bridge.hostToGuest(Value.fromObject(3)));
      bindings.putMember("ten", bridge.hostToGuest(Value.fromObject(10)));
      bindings.putMember("hundred", bridge.hostToGuest(Value.fromObject(100)));

      assertEquals(context.eval("js", "five.modulo(two).numberValue()").asInt(), 1);
      assertEquals(context.eval("js", "five.modulo(five).numberValue()").asInt(), 0);
      assertEquals(context.eval("js", "ten.modulo(hundred).numberValue()").asInt(), 10);
      assertEquals(context.eval("js", "three.modulo(ten).numberValue()").asInt(), 3);
    }
  }
}

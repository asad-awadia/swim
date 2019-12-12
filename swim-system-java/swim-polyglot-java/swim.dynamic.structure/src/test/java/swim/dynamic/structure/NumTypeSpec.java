package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Num;
import swim.vm.VmBridge;

import static java.lang.Float.NaN;
import static org.testng.Assert.assertEquals;

public class NumTypeSpec {
  @Test
  public void testNumIsUint32() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("num", bridge.hostToGuest(Num.from(6)));

      assertEquals(context.eval("js","num.isUint32()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsUint64() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("num", bridge.hostToGuest(Num.from(6)));

      assertEquals(context.eval("js","num.isUint64()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsNaN() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("five", bridge.hostToGuest(Num.from('5')));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from("67")));

      assertEquals(context.eval("js","six.isNaN()").asBoolean(), false);
      assertEquals(context.eval("js","five.isNaN()").asBoolean(), false);
      assertEquals(context.eval("js","sixtySeven.isNaN()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsInfinite() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("maxValue", bridge.hostToGuest(Num.from(Integer.MAX_VALUE)));
      bindings.putMember("infiniteDouble", bridge.hostToGuest(Num.from(Double.POSITIVE_INFINITY)));

      assertEquals(context.eval("js","six.isInfinite()").asBoolean(), false);
      assertEquals(context.eval("js","maxValue.isInfinite()").asBoolean(), false);
      assertEquals(context.eval("js","infiniteDouble.isInfinite()").asBoolean(), true);
    }
  }

  @Test
  public void testNumIsValidByte() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("oneTwoEight", bridge.hostToGuest(Num.from(128)));
      bindings.putMember("oneTwoSeven", bridge.hostToGuest(Num.from(127)));
      bindings.putMember("minusOneTwoNine", bridge.hostToGuest(Num.from(-129)));

      assertEquals(context.eval("js","six.isValidByte()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidByte()").asBoolean(), false);
      assertEquals(context.eval("js","oneTwoEight.isValidByte()").asBoolean(), false);
      assertEquals(context.eval("js","oneTwoSeven.isValidByte()").asBoolean(), true);
      assertEquals(context.eval("js","minusOneTwoNine.isValidByte()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsValidShort() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("short1", bridge.hostToGuest(Num.from(-32768)));
      bindings.putMember("short2", bridge.hostToGuest(Num.from(32767)));
      bindings.putMember("short3", bridge.hostToGuest(Num.from(32768)));

      assertEquals(context.eval("js","six.isValidShort()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidShort()").asBoolean(), false);
      assertEquals(context.eval("js","short1.isValidShort()").asBoolean(), true);
      assertEquals(context.eval("js","short2.isValidShort()").asBoolean(), true);
      assertEquals(context.eval("js","short3.isValidShort()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsValidInt() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("int1", bridge.hostToGuest(Num.from(-32768)));
      bindings.putMember("int2", bridge.hostToGuest(Num.from(32767)));
      bindings.putMember("int3", bridge.hostToGuest(Num.from(32768)));

      assertEquals(context.eval("js","six.isValidInt()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidInt()").asBoolean(), false);
      assertEquals(context.eval("js","int1.isValidInt()").asBoolean(), true);
      assertEquals(context.eval("js","int2.isValidInt()").asBoolean(), true);
      assertEquals(context.eval("js","int3.isValidInt()").asBoolean(), true);
    }
  }

  @Test
  public void testNumIsValidLong() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("long1", bridge.hostToGuest(Num.from(Math.pow(-2, 63))));
      bindings.putMember("long2", bridge.hostToGuest(Num.from(Math.pow(2, 63) - 1)));
      bindings.putMember("long3", bridge.hostToGuest(Num.from(Math.pow(2, 64))));

      assertEquals(context.eval("js","six.isValidLong()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidLong()").asBoolean(), false);
      assertEquals(context.eval("js","long1.isValidLong()").asBoolean(), true);
      assertEquals(context.eval("js","long2.isValidLong()").asBoolean(), true);
      assertEquals(context.eval("js","long3.isValidLong()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsValidFloat() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3f)));
      bindings.putMember("float1", bridge.hostToGuest(Num.from(12.12345f)));
      bindings.putMember("float2", bridge.hostToGuest(Num.from(12.123456789f)));

      //fixme: Num.from(12.123456789f).isValidFloat()) should be false
//      System.out.println(Num.from(6).isValidFloat());
//      System.out.println(Num.from(6.3f).isValidFloat());
//      System.out.println(Num.from(12.12345f).isValidFloat());
//      System.out.println(Num.from(12.123456789f).isValidFloat());

      assertEquals(context.eval("js","six.isValidFloat()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidFloat()").asBoolean(), true);
      assertEquals(context.eval("js","float1.isValidFloat()").asBoolean(), true);
//      assertEquals(context.eval("js","float2.isValidFloat()").asBoolean(), false);
    }
  }

  @Test
  public void testNumIsValidDouble() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("double1", bridge.hostToGuest(Num.from(12.12345)));
      bindings.putMember("double2", bridge.hostToGuest(Num.from(12.123456789)));

      assertEquals(context.eval("js","six.isValidDouble()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidDouble()").asBoolean(), true);
      assertEquals(context.eval("js","double1.isValidDouble()").asBoolean(), true);
      assertEquals(context.eval("js","double2.isValidDouble()").asBoolean(), true);
    }
  }

  @Test
  public void testNumIsValidInteger() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));
      bindings.putMember("twelve", bridge.hostToGuest(Num.from(12.0)));

      assertEquals(context.eval("js","six.isValidInteger()").asBoolean(), true);
      assertEquals(context.eval("js","six3.isValidInteger()").asBoolean(), false);
      assertEquals(context.eval("js","twelve.isValidInteger()").asBoolean(), true);
    }
  }

  @Test
  public void testNumBitwiseOr() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.bitwiseOr(sixtySeven)")), Num.from(71));
      assertEquals(bridge.guestToHost(context.eval("js","hundred.bitwiseOr(fiveHundred)")), Num.from(500));
    }
  }

  @Test
  public void testNumBitwiseXor() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.bitwiseXor(sixtySeven)")), Num.from(69));
      assertEquals(bridge.guestToHost(context.eval("js","hundred.bitwiseXor(fiveHundred)")), Num.from(400));
    }
  }

  @Test
  public void testNumBitwiseAnd() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.bitwiseAnd(sixtySeven)")), Num.from(2));
      assertEquals(bridge.guestToHost(context.eval("js","hundred.bitwiseAnd(fiveHundred)")), Num.from(100));
    }
  }

  @Test
  public void testNumPlus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.plus(sixtySeven)")), Num.from(73));
      assertEquals(bridge.guestToHost(context.eval("js","hundred.plus(fiveHundred)")), Num.from(600));
    }
  }

  @Test
  public void testNumMinus() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.minus(sixtySeven)")), Num.from(-61));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.minus(hundred)")), Num.from(400));
    }
  }

  @Test
  public void testNumTimes() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.times(sixtySeven)")), Num.from(402));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.times(hundred)")), Num.from(50000));
    }
  }

  @Test
  public void testNumDivide() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.divide(sixtySeven)")), Num.from(0.08955223880597014));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.divide(hundred)")), Num.from(5.0));
    }
  }

  @Test
  public void testNumModulo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.modulo(sixtySeven)")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.modulo(hundred)")), Num.from(0));
    }
  }

  @Test
  public void testNumAbs() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.abs()")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.abs()")), Num.from(500));
    }
  }

  @Test
  public void testNumCeil() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));
      bindings.putMember("sixSeven", bridge.hostToGuest(Num.from(6.67)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));

      assertEquals(bridge.guestToHost(context.eval("js","six.ceil()")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.ceil()")), Num.from(-500));
      assertEquals(bridge.guestToHost(context.eval("js", "sixSeven.ceil()")), Num.from(7.0));
      assertEquals(bridge.guestToHost(context.eval("js", "six3.ceil()")), Num.from(7.0));
    }
  }

  @Test
  public void testNumFloor() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));
      bindings.putMember("sixSeven", bridge.hostToGuest(Num.from(6.67)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));

      assertEquals(bridge.guestToHost(context.eval("js","six.floor()")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.floor()")), Num.from(-500));
      assertEquals(bridge.guestToHost(context.eval("js", "sixSeven.floor()")), Num.from(6.0));
      assertEquals(bridge.guestToHost(context.eval("js", "six3.floor()")), Num.from(6.0));
    }
  }

  @Test
  public void testNumRound() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));
      bindings.putMember("sixSeven", bridge.hostToGuest(Num.from(6.67)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));

      assertEquals(bridge.guestToHost(context.eval("js","six.round()")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.round()")), Num.from(-500));
      assertEquals(bridge.guestToHost(context.eval("js", "sixSeven.round()")), Num.from(7L));
      assertEquals(bridge.guestToHost(context.eval("js", "six3.round()")), Num.from(6L));
    }
  }

  @Test
  public void testNumSqrt() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));
      bindings.putMember("sixSeven", bridge.hostToGuest(Num.from(6.67)));
      bindings.putMember("six3", bridge.hostToGuest(Num.from(6.3)));

      assertEquals(bridge.guestToHost(context.eval("js","six.sqrt()")), Num.from(2.449489742783178));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.sqrt()")), Num.from(NaN));
      assertEquals(bridge.guestToHost(context.eval("js", "sixSeven.sqrt()")), Num.from(2.582634314028992));
      assertEquals(bridge.guestToHost(context.eval("js", "six3.sqrt()")), Num.from(2.5099800796022267));
    }
  }

  @Test
  public void testNumPow() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(-500)));
      bindings.putMember("two", bridge.hostToGuest(Num.from(2)));

      assertEquals(bridge.guestToHost(context.eval("js","six.pow(two)")), Num.from(36.0));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.pow(two)")), Num.from(250000.0));
    }
  }

  @Test
  public void testNumMax() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.max(sixtySeven)")), Num.from(67));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.max(hundred)")), Num.from(500));
    }
  }

  @Test
  public void testNumMin() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(bridge.guestToHost(context.eval("js","six.min(sixtySeven)")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js","fiveHundred.min(hundred)")), Num.from(100));
    }
  }

  @Test
  public void testNumCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(context.eval("js","six.compareTo(sixtySeven)").asInt(), -61);
      assertEquals(context.eval("js","fiveHundred.compareTo(hundred)").asInt(), 400);
    }
  }

  @Test
  public void testNumEquals() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.from(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.from(67)));
      bindings.putMember("hundred", bridge.hostToGuest(Num.from(100)));
      bindings.putMember("fiveHundred", bridge.hostToGuest(Num.from(500)));

      assertEquals(context.eval("js","six.equals(sixtySeven)").asBoolean(), false);
      assertEquals(context.eval("js", "six.equals(six)").asBoolean(), true);
      assertEquals(context.eval("js","fiveHundred.equals(hundred)").asBoolean(), false);
    }
  }

  @Test
  public void testNumUint() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("six", bridge.hostToGuest(Num.uint32(6)));
      bindings.putMember("sixtySeven", bridge.hostToGuest(Num.uint32(67)));
      bindings.putMember("long1", bridge.hostToGuest(Num.uint64(10000000L)));
      bindings.putMember("long2", bridge.hostToGuest(Num.uint64(523423424234L)));

      assertEquals(bridge.guestToHost(context.eval("js","six")), Num.from(6));
      assertEquals(bridge.guestToHost(context.eval("js", "sixtySeven")), Num.from(67));
      assertEquals(bridge.guestToHost(context.eval("js","long1")), Num.from(10000000L));
      assertEquals(bridge.guestToHost(context.eval("js", "long2")), Num.from(523423424234L));
    }
  }
}

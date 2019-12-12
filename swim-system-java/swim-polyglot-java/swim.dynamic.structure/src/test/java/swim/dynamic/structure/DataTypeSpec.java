package swim.dynamic.structure;

import org.graalvm.polyglot.Context;
import org.testng.annotations.Test;
import swim.dynamic.JavaHostRuntime;
import swim.structure.Data;
import swim.structure.Num;
import swim.vm.VmBridge;

import java.nio.ByteBuffer;

import static org.testng.Assert.assertEquals;

public class DataTypeSpec {

  @Test
  public void testDataSize() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(context.eval("js","byteBuffer.size").asInt(), 5);
    }
  }

  @Test
  public void testDataGetByte() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(context.eval("js","byteBuffer.getByte(2)").asByte(), 108);
    }
  }

  //fixme: org.graalvm.polyglot.PolyglotException: java.lang.Integer incompatible with java.lang.Byte
  @Test
  public void testDataSetByte() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));
      byte b = 0b100;
      bindings.putMember("by", bridge.hostToGuest(Num.from(b)));
//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).setByte(2, b));
//
//      assertEquals(context.eval("js", "by.byteValue()").asByte(), 0b100);
//      assertEquals(bridge.guestToHost(context.eval("js","byteBuffer.setByte(2, by.byteValue())")),
//          Data.fromBase16("6865046C6F"));
    }
  }

  //fixme: org.graalvm.polyglot.PolyglotException: java.lang.Integer incompatible with java.lang.Byte
  @Test
  public void testDataAddByte() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));
      byte byt = 100;
      bindings.putMember("hundred", bridge.hostToGuest(byt));

//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).addByte(byt));
//
//      assertEquals(bridge.guestToHost(context.eval("js", "byteBuffer.addByte(hundred)")),
//          Data.fromBase16("68656C6C6F64"));
    }
  }

  @Test
  public void testDataAddByteArray() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));
      bindings.putMember("byteArray", bridge.hostToGuest("world".getBytes()));

      assertEquals(bridge.guestToHost(context.eval("js", "byteBuffer.addByteArray(byteArray)")),
          Data.fromBase16("68656C6C6F776F726C64"));
    }
  }

  @Test
  public void testDataAddData() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));
      bindings.putMember("byteBuffer2", bridge.hostToGuest(Data.from(ByteBuffer.wrap("world".getBytes()))));

      assertEquals(bridge.guestToHost(context.eval("js", "byteBuffer.addData(byteBuffer2)")),
          Data.fromBase16("68656C6C6F776F726C64"));
    }
  }

  @Test
  public void testDataClear() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(bridge.guestToHost(context.eval("js", "byteBuffer.clear()")), null);
    }
  }

  @Test
  public void testDataToByteArray() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      //todo: find unit case
//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).toByteArray());
//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).asByteArray());
//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).toByteBuffer());
//      System.out.println(Data.from(ByteBuffer.wrap("hello".getBytes())).asByteBuffer());

//      assertEquals(bridge.guestToHost(context.eval("js", "byteBuffer.clear()")), null);
    }
  }

  @Test
  public void testDataToBase16() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(context.eval("js", "byteBuffer.toBase16()").asString(), "68656C6C6F");
    }
  }

  @Test
  public void testDataToBase64() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(context.eval("js", "byteBuffer.toBase64()").asString(), "aGVsbG8=");
    }
  }

  @Test
  public void testDataCompareTo() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));
      bindings.putMember("byteBuffer2", bridge.hostToGuest(Data.from(ByteBuffer.wrap("world".getBytes()))));

      assertEquals(context.eval("js", "byteBuffer.compareTo(byteBuffer)").asInt(), 0);
      assertEquals(context.eval("js", "byteBuffer.compareTo(byteBuffer2)").asInt(), -1);
    }
  }

  @Test
  public void testDataEmpty() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("empty", bridge.hostToGuest(Data.empty()));

      assertEquals(bridge.guestToHost(context.eval("js", "empty")), Data.empty());
    }
  }

  @Test
  public void testDataCreate() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("create", bridge.hostToGuest(Data.create()));
      bindings.putMember("createCapacity", bridge.hostToGuest(Data.create(5)));

      assertEquals(bridge.guestToHost(context.eval("js", "create")), Data.empty());
      assertEquals(bridge.guestToHost(context.eval("js", "createCapacity")), Data.empty());
    }
  }

  @Test
  public void testDataWrap() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("array", bridge.hostToGuest(Data.wrap("hello".getBytes())));
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.wrap(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(bridge.guestToHost(context.eval("js","array")),
          Data.fromBase16("68656C6C6F"));
      assertEquals(bridge.guestToHost(context.eval("js","byteBuffer")),
          Data.fromBase16("68656C6C6F"));
    }
  }

  @Test
  public void testDataFrom() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("byteBuffer", bridge.hostToGuest(Data.from(ByteBuffer.wrap("hello".getBytes()))));

      assertEquals(bridge.guestToHost(context.eval("js","byteBuffer")),
          Data.fromBase16("68656C6C6F"));
    }
  }

  @Test
  public void testDataFromBase16() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("base16", bridge.hostToGuest(Data.fromBase16("68656C6C6F")));

      assertEquals(bridge.guestToHost(context.eval("js","base16")),
          Data.fromBase16("68656C6C6F"));
    }
  }

  @Test
  public void testDataFromBase64() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("base64", bridge.hostToGuest(Data.fromBase64("aGVsbG8=")));

      assertEquals(bridge.guestToHost(context.eval("js","base64")),
          Data.fromBase16("68656C6C6F"));
    }
  }

  @Test
  public void testDataFromUtf8() {
    try (Context context = Context.create()) {
      final JavaHostRuntime runtime = new JavaHostRuntime();
      final VmBridge bridge = new VmBridge(runtime, "js");
      runtime.addHostLibrary(SwimStructure.LIBRARY);

      final org.graalvm.polyglot.Value bindings = context.getBindings("js");
      bindings.putMember("utf8", bridge.hostToGuest(Data.fromUtf8("hello")));

      assertEquals(bridge.guestToHost(context.eval("js", "utf8")), Data.fromBase16("68656C6C6F"));
    }
  }
}

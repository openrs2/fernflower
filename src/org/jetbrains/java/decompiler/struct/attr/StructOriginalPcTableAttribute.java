package org.jetbrains.java.decompiler.struct.attr;

import org.jetbrains.java.decompiler.struct.consts.ConstantPool;
import org.jetbrains.java.decompiler.struct.consts.PrimitiveConstant;
import org.jetbrains.java.decompiler.util.DataInputFullStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class StructOriginalPcTableAttribute extends StructGeneralAttribute {
  private final Map<Integer, Integer> pcs = new HashMap<>();
  private final Map<Integer, String> names = new HashMap<>();

  @Override
  public void initContent(DataInputFullStream data, ConstantPool pool) throws IOException {
    int pcsLen = data.readUnsignedShort();

    for (int i = 0; i < pcsLen; i++) {
      int pc = data.readUnsignedShort();
      int originalPc = data.readUnsignedShort();
      pcs.put(pc, originalPc);
    }

    int namesLen = data.readUnsignedShort();

    for (int i = 0; i < namesLen; i++) {
      int originalPc = data.readUnsignedShort();

      PrimitiveConstant constant = pool.getPrimitiveConstant(data.readUnsignedShort());
      if (constant.type != PrimitiveConstant.CONSTANT_Utf8) {
        throw new IllegalArgumentException();
      }

      String name = (String) constant.value;
      names.put(originalPc, name);
    }
  }

  public boolean hasOriginalPc(int pc) {
    return pcs.containsKey(pc);
  }

  public int getOriginalPc(int pc) {
    return pcs.get(pc);
  }

  public boolean hasName(int originalPc) {
    return names.containsKey(originalPc);
  }

  public String getName(int originalPc) {
    return names.get(originalPc);
  }
}

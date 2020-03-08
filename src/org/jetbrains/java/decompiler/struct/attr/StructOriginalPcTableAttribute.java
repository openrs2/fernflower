package org.jetbrains.java.decompiler.struct.attr;

import org.jetbrains.java.decompiler.struct.consts.ConstantPool;
import org.jetbrains.java.decompiler.util.DataInputFullStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class StructOriginalPcTableAttribute extends StructGeneralAttribute {
  private final Map<Integer, Integer> pcs = new HashMap<>();

  @Override
  public void initContent(DataInputFullStream data, ConstantPool pool) throws IOException {
    int len = data.readUnsignedShort();

    for (int i = 0; i < len; i++) {
      int pc = data.readUnsignedShort();
      int originalPc = data.readUnsignedShort();
      pcs.put(pc, originalPc);
    }
  }

  public boolean hasOriginalPc(int pc) {
    return pcs.containsKey(pc);
  }

  public int getOriginalPc(int pc) {
    return pcs.get(pc);
  }
}

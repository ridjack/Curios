/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.curios.common;

import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CurioType;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

public class CuriosIMC {

  public static void processCurioTypes(Stream<InterModComms.IMCMessage> register,
      Stream<InterModComms.IMCMessage> modify, Stream<InterModComms.IMCMessage> icons) {

    register.filter(msg -> msg.getMessageSupplier().get() instanceof CurioIMCMessage)
        .map(msg -> (CurioIMCMessage) msg.getMessageSupplier().get())
        .forEach(msg -> processType(msg, true));

    modify.filter(msg -> msg.getMessageSupplier().get() instanceof CurioIMCMessage)
        .map(msg -> (CurioIMCMessage) msg.getMessageSupplier().get())
        .forEach(msg -> processType(msg, false));

    icons.filter(msg -> {
      Object obj = msg.getMessageSupplier().get();

      if (obj instanceof Tuple) {
        Tuple tup = (Tuple) obj;
        return tup.getA() instanceof String && tup.getB() instanceof ResourceLocation;
      }
      return false;
    })
        .map(msg -> (Tuple<String, ResourceLocation>) msg.getMessageSupplier().get())
        .forEach(msg -> CuriosAPI.idToIcon.put(msg.getA(), msg.getB()));
  }

  private static void processType(CurioIMCMessage message, boolean create) {

    String identifier = message.getIdentifier();

    if (CuriosAPI.idToType.containsKey(identifier)) {
      CurioType presentType = CuriosAPI.idToType.get(identifier);

      if (message.getSize() > presentType.getSize()) {
        presentType.defaultSize(message.getSize());
      }

      if (!message.isEnabled() && presentType.isEnabled()) {
        presentType.enabled(false);
      }

      if (message.isHidden() && !presentType.isHidden()) {
        presentType.hide(true);
      }

    } else if (create) {
      CuriosAPI.idToType.put(identifier, new CurioType(identifier).defaultSize(message.getSize())
          .enabled(message.isEnabled())
          .hide(message.isHidden()));
    }
  }
}

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

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.common.inventory.CuriosContainer;
import top.theillusivec4.curios.common.item.AmuletItem;
import top.theillusivec4.curios.common.item.CrownItem;
import top.theillusivec4.curios.common.item.KnucklesItem;
import top.theillusivec4.curios.common.item.RingItem;

@Mod.EventBusSubscriber(modid = Curios.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosRegistry {

  @ObjectHolder("curios:ring")
  public static final Item RING = null;

  @ObjectHolder("curios:amulet")
  public static final Item AMULET = null;

  @ObjectHolder("curios:crown")
  public static final Item CROWN = null;

  @ObjectHolder("curios:knuckles")
  public static final Item KNUCKLES = null;

  @ObjectHolder("curios:curios_container")
  public static final ContainerType<CuriosContainer> CONTAINER_TYPE = null;

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> evt) {

    evt.getRegistry()
        .registerAll(new RingItem(), new AmuletItem(), new CrownItem(), new KnucklesItem());
  }

  @SubscribeEvent
  public static void registerContainer(RegistryEvent.Register<ContainerType<?>> evt) {

    evt.getRegistry().register(
        IForgeContainerType.create(CuriosContainer::new).setRegistryName("curios_container"));
  }
}

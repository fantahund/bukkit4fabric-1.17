package org.bukkit.craftbukkit.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

import com.javazilla.bukkitfabric.impl.WorldImpl;

public class CraftChest extends CraftLootable<ChestBlockEntity> implements Chest {

    public CraftChest(final Block block) {
        super(block, ChestBlockEntity.class);
    }

    public CraftChest(final Material material, final ChestBlockEntity te) {
        super(material, te);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getBlockInventory() {
        if (!this.isPlaced())
            return this.getSnapshotInventory();

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public Inventory getInventory() {
        CraftInventory inventory = (CraftInventory) this.getBlockInventory();
        if (!isPlaced())
            return inventory;

        // The logic here is basically identical to the logic in BlockChest.interact
        WorldImpl world = (WorldImpl) this.getWorld();

        ChestBlock blockChest = (ChestBlock) (this.getType() == Material.CHEST ? Blocks.CHEST : Blocks.TRAPPED_CHEST);
        NamedScreenHandlerFactory nms = blockChest.createScreenHandlerFactory(data, world.getHandle(), this.getPosition());

        if (nms instanceof DoubleInventory)
            inventory = new CraftInventoryDoubleChest((DoubleInventory) nms);
        return inventory;
    }

    @Override
    public void open() {
        // TODO Auto-generated method stub
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

}
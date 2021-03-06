package com.github.se7_kn8.wool.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public abstract class BaseBlockEntity extends BlockEntity implements Inventory {

	protected final DefaultedList<ItemStack> inventory = DefaultedList.create(getInvSize(), ItemStack.EMPTY);

	public BaseBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public void fromTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		Inventories.fromTag(compoundTag_1, this.inventory);
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		Inventories.toTag(compoundTag_1, inventory);
		return compoundTag_1;
	}

	@Override
	public boolean isInvEmpty() {
		boolean empty = true;
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) {
				empty = false;
				break;
			}
		}
		return empty;
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount) {
		return Inventories.splitStack(this.inventory, slot, amount);
	}

	@Override
	public ItemStack removeInvStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}

	@Override
	public void setInvStack(int slot, ItemStack itemStack) {
		this.inventory.set(slot, itemStack);
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		return this.world.getBlockEntity(this.pos) == this;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	public abstract String getContainerId();
}

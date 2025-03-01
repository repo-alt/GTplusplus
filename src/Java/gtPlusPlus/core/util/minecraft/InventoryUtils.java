package gtPlusPlus.core.util.minecraft;

import java.util.Random;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy_RTG;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryUtils {
	
	private final static Random mRandom = new Random();
	
	public static void dropInventoryItems(World world, int x, int y, int z, Block block) {
		TileEntity tileentity = world.getTileEntity(x, y, z);

		if (tileentity != null && tileentity instanceof IInventory && ((IInventory) tileentity).getSizeInventory() > 0) {
			
			IInventory aTileInv = (IInventory) tileentity;			
			int aMinSlot = 0;
			int aMaxSlot = aTileInv.getSizeInventory()-1;
			
			for (int i1 = aMinSlot; i1 < aMaxSlot; ++i1) {
				ItemStack itemstack = aTileInv.getStackInSlot(i1);

				if (itemstack != null) {
					float f = mRandom.nextFloat() * 0.8F + 0.1F;
					float f1 = mRandom.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = mRandom.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
						int j1 = mRandom.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world, x + f, y + f1, z + f2,
								new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) mRandom.nextGaussian() * f3;
						entityitem.motionY = (float) mRandom.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) mRandom.nextGaussian() * f3;

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem()
									.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}

	}

	public static void sortInventoryItems(MetaTileEntity aTile) {
		sortInventoryItems(aTile.getBaseMetaTileEntity());		
	}

	public static void sortInventoryItems(IGregTechTileEntity aBaseMetaTileEntity) {
		IInventory mInv = aBaseMetaTileEntity.getIInventory(aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord());
		AutoMap<ItemStack> aInvContents = new AutoMap<ItemStack>();
		int aSize = mInv.getSizeInventory();
		for (int slot=0; slot<aSize; slot++) {
			aInvContents.put(mInv.getStackInSlot(slot));
		}		
		ItemStack[] mInventory = aInvContents.toArray();
		for (int i = 0; i < mInventory.length; i++) {
			for (int j = i + 1; j < mInventory.length; j++) {
				if (mInventory[j] != null && (mInventory[i] == null || GT_Utility.areStacksEqual(mInventory[i], mInventory[j]))) {
					GT_Utility.moveStackFromSlotAToSlotB(aBaseMetaTileEntity, aBaseMetaTileEntity, j, i, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
				}
			}
		}
	}
	
}

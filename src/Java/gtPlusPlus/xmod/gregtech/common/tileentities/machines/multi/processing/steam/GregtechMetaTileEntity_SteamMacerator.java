package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing.steam;

import static gregtech.api.GregTech_API.sBlockCasings1;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_SteamMultiBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class GregtechMetaTileEntity_SteamMacerator extends GregtechMeta_SteamMultiBase {

	private String mCasingName = "Bronze Plated Bricks";
	
	public GregtechMetaTileEntity_SteamMacerator(String aName) {
		super(aName);
	}
	
	public GregtechMetaTileEntity_SteamMacerator(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
		return new GregtechMetaTileEntity_SteamMacerator(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}

	@Override
	protected GT_RenderedTexture getFrontOverlay() {
		return new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR);
	}

	@Override
	protected GT_RenderedTexture getFrontOverlayActive() {
		return new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR_ACTIVE);
	}

	@Override
	public String getMachineType() {
		return "Macerator";
	}

	@Override
	public String[] getTooltip() {
		if (mCasingName.contains("gt.blockcasings")) {
			mCasingName = ItemList.Casing_BronzePlatedBricks.get(1).getDisplayName();
		}    	
		return new String[]{
                "Controller Block for the Steam Macerator",
                "Macerates "+getMaxParallelRecipes()+" ores at a time",
                "Size(WxHxD): 3x3x3 (Hollow), Controller (Front centered)",
                "1x Input Bus (Any casing)",
                "1x Output Bus (Any casing)",
                "1x Steam Hatch (Any casing)",
                mCasingName+" for the rest (14 at least!)",
                TAG_HIDE_MAINT
                };
	}

	@Override
	public int getMaxParallelRecipes() {
		return 8;
	}
	

	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
	}

	@Override
	public boolean checkMultiblock(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
		int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
		int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
		int tAmount = 0;
		if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
			return false;
		} else {
			for (int i = -1; i < 2; ++i) {
				for (int j = -1; j < 2; ++j) {
					for (int h = -1; h < 2; ++h) {
						if (h != 0 || (xDir + i != 0 || zDir + j != 0) && (i != 0 || j != 0)) {
							IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i,
									h, zDir + j);
							Block aBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
							int aMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);

							if (!isValidBlockForStructure(tTileEntity, 10, true, aBlock, aMeta,
									sBlockCasings1, 10)) {
								Logger.INFO("Bad macerator casing");
								return false;
							}
							++tAmount;

						}
					}
				}
			}
			if (tAmount >= 14) {
				fixAllMaintenanceIssue();
			}
			return tAmount >= 14;
		}
	}
	
	@Override
	public ItemStack[] getOutputItems(GT_Recipe aRecipe) {
		// Collect output item types
		ItemStack[] tOutputItems = new ItemStack[1];
		for (int h = 0; h < 1; h++) {
			if (aRecipe.getOutput(h) != null) {
				tOutputItems[h] = aRecipe.getOutput(h).copy();
				tOutputItems[h].stackSize = 0;
			}
		}
		return tOutputItems;
	}
	
	@Override
	public int getOutputCount(ItemStack[] aOutputs) {
		return 1;
	}



}

package gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.processing;

import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.Pair;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import gtPlusPlus.xmod.gregtech.common.blueprint.Blueprint_Generic_3x3;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class GregtechMetaTileEntity_IndustrialElectrolyzer
extends GregtechMeta_MultiBlockBase {
	public GregtechMetaTileEntity_IndustrialElectrolyzer(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	public GregtechMetaTileEntity_IndustrialElectrolyzer(final String aName) {
		super(aName);
	}

	@Override
	public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
		return new GregtechMetaTileEntity_IndustrialElectrolyzer(this.mName);
	}

	@Override
	public String getMachineType() {
		return "Electrolyzer";
	}

	@Override
	public String[] getTooltip() {
		return new String[]{"Controller Block for the Industrial Electrolyzer",
				"180% faster than using single block machines of the same voltage",
				"Only uses 90% of the eu/t normally required",
				"Processes two items per voltage tier",
				"Size: 3x3x3 (Hollow)",
				"Electrolyzer Casings for the rest (10 at least!)",
				"Controller (front centered)",
				"1x Input Bus",
				"1x Output Bus",
				"1x Input Hatch",
				"1x Output Hatch",
				"1x Energy Hatch",
				};
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(TAE.GTPP_INDEX(5)), new GT_RenderedTexture(aActive ? TexturesGtBlock.Overlay_Machine_Controller_Default_Active : TexturesGtBlock.Overlay_Machine_Controller_Default)};
		}
		return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(TAE.GTPP_INDEX(5))};
	}

	@Override
	public boolean hasSlotInGUI() {
		return false;
	}

	@Override
	public String getCustomGUIResourceName() {
		return "IndustrialElectrolyzer";
	}

	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return gregtech.api.util.GTPP_Recipe.GTPP_Recipe_Map.sMultiblockElectrolyzerRecipes_GT;
	}

	@Override
	public boolean isFacingValid(final byte aFacing) {
		return aFacing > 1;
	}

	@Override
	public boolean checkRecipe(final ItemStack aStack) {
		return checkRecipeGeneric(2* GT_Utility.getTier(this.getMaxInputVoltage()), 90, 180);
	}

	private class Blueprint_Electrolyzer extends Blueprint_Generic_3x3 {

		public Blueprint_Electrolyzer() {
			super(new Pair<Block, Integer>(ModBlocks.blockCasingsMisc, 5), TAE.GTPP_INDEX(5));
		}

		@Override
		public int getMinimumInputBus() {
			return 0;
		}

		@Override
		public int getMinimumInputHatch() {
			return 0;
		}

		@Override
		public int getMinimumOutputBus() {
			return 0;
		}

		@Override
		public int getMinimumOutputHatch() {
			return 0;
		}
		
	}
	
	private static Blueprint_Electrolyzer mBluePrint;
	
	@Override
	public boolean checkMultiblock(final IGregTechTileEntity aBaseMetaTileEntity, final ItemStack aStack) {	
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

							if (!isValidBlockForStructure(tTileEntity, TAE.GTPP_INDEX(5), true, aBlock, aMeta,
									ModBlocks.blockCasingsMisc, 5)) {
								Logger.INFO("Bad Electrolyzer casing");
								return false;
							}
							++tAmount;

						}
					}
				}
			}
			return tAmount >= 10;
		}
	}

	@Override
	public int getMaxEfficiency(final ItemStack aStack) {
		return 10000;
	}

	@Override
	public int getPollutionPerTick(final ItemStack aStack) {
		return 15;
	}

	@Override
	public int getAmountOfOutputs() {
		return 1;
	}

	@Override
	public boolean explodesOnComponentBreak(final ItemStack aStack) {
		return false;
	}

	@Override
	public int getMaxParallelRecipes() {
		return 2* GT_Utility.getTier(this.getMaxInputVoltage());
	}

	@Override
	public int getEuDiscountForParallelism() {
		return 90;
	}
}
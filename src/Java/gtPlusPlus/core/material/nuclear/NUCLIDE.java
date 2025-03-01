package gtPlusPlus.core.material.nuclear;

import gtPlusPlus.core.client.CustomTextureSet.TextureSets;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.material.MaterialStack;
import gtPlusPlus.core.material.state.MaterialState;
import gtPlusPlus.core.util.data.StringUtils;


public final class NUCLIDE {

	public static final Material LiFBeF2ThF4UF4 = new Material(
			"LiFBeF2ThF4UF4", //Material Name
			MaterialState.LIQUID, //State
			TextureSets.NUCLEAR.get(),
			null, //Material Colour
			566, //Melting Point in C
			870, //Boiling Point in C
			-1, //Protons
			-1, //Neutrons
			false, //Uses Blast furnace?
			StringUtils.subscript(StringUtils.superscript("7")+"LiFBeF2ThF4UF4"), //Chemical Formula
			5, //Radioactivity Level
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(FLUORIDES.LITHIUM_FLUORIDE, 65),
					new MaterialStack(FLUORIDES.BERYLLIUM_FLUORIDE, 28),
					new MaterialStack(FLUORIDES.THORIUM_TETRAFLUORIDE, 1),
					new MaterialStack(FLUORIDES.URANIUM_TETRAFLUORIDE, 1)
			});
	
	public static final Material LiFBeF2ZrF4UF4 = new Material(
			"LiFBeF2ZrF4UF4", //Material Name
			MaterialState.LIQUID, //State
			TextureSets.NUCLEAR.get(),
			null, //Material Colour
			650, //Melting Point in C
			940, //Boiling Point in C
			-1, //Protons
			-1, //Neutrons
			false, //Uses Blast furnace?
			StringUtils.subscript(StringUtils.superscript("7")+"LiFBeF2ZrF4UF4"), //Chemical Formula
			5, //Radioactivity Level
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(FLUORIDES.LITHIUM_FLUORIDE, 65),
					new MaterialStack(FLUORIDES.BERYLLIUM_FLUORIDE, 28),
					new MaterialStack(FLUORIDES.ZIRCONIUM_TETRAFLUORIDE, 5),
					new MaterialStack(FLUORIDES.URANIUM_TETRAFLUORIDE, 2)
			});

	public static final Material LiFBeF2ZrF4U235 = new Material(
			"LiFBeF2ZrF4U235", //Material Name
			MaterialState.LIQUID, //State
			TextureSets.NUCLEAR.get(),
			null, //Material Colour
			590, //Melting Point in C
			890, //Boiling Point in C
			-1, //Protons
			-1, //Neutrons
			false, //Uses Blast furnace?
			StringUtils.subscript(StringUtils.superscript("7")+"LiFBeF2ZrF4")+StringUtils.superscript("235U"), //Chemical Formula
			5, //Radioactivity Level
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(FLUORIDES.LITHIUM_FLUORIDE, 55),
					new MaterialStack(FLUORIDES.BERYLLIUM_FLUORIDE, 25),
					new MaterialStack(FLUORIDES.ZIRCONIUM_TETRAFLUORIDE, 6),
					new MaterialStack(ELEMENT.getInstance().URANIUM235, 14)
			});

	// Secondary material is molten metal
	public static final Material NAQ_FUEL_T1 = new Material(
			"Naquadah Fuel",
			MaterialState.PURE_LIQUID, //State
			null, //Material Colour
			-1, //Melting Point in C
			-1, //Boiling Point in C
			-1, //Protons
			-1,
			false, //Uses Blast furnace?
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(ELEMENT.getInstance().NAQUADAH, 2),
					new MaterialStack(ELEMENT.getInstance().TANTALUM, 3)
			});

	// Secondary material is a plasma
	public static final Material NAQ_FUEL_T2 = new Material(
			"Enriched Naquadah Fuel",
			MaterialState.PURE_LIQUID, //State
			null, //Material Colour
			-1, //Melting Point in C
			-1, //Boiling Point in C
			-1, //Protons
			-1,
			false, //Uses Blast furnace?
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(ELEMENT.getInstance().NAQUADAH_ENRICHED, 2),
					new MaterialStack(ELEMENT.getInstance().TITANIUM, 3)
			});
	
	// Secondary material is a plasma
	public static final Material NAQ_FUEL_T3 = new Material(
			"Naquadria Fuel",
			MaterialState.PURE_LIQUID, //State
			null, //Material Colour
			-1, //Melting Point in C
			-1, //Boiling Point in C
			-1, //Protons
			-1,
			false, //Uses Blast furnace?
			//Material Stacks with Percentage of required elements.
			new MaterialStack[]{
					new MaterialStack(ELEMENT.getInstance().NAQUADRIA, 2),
					new MaterialStack(ELEMENT.getInstance().AMERICIUM, 3)
			});
	


	private static final NUCLIDE INSTANCE = new NUCLIDE();
	
	public static NUCLIDE getInstance(){
		return INSTANCE;
		}

}

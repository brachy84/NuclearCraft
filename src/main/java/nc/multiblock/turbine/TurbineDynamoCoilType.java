package nc.multiblock.turbine;

import nc.enumm.*;
import nc.tile.turbine.TileTurbineDynamoCoil;
import net.minecraft.util.IStringSerializable;

import static nc.config.NCConfig.turbine_coil_conductivity;

public enum TurbineDynamoCoilType implements IStringSerializable, IMetaEnum, ITileEnum<TileTurbineDynamoCoil.Meta> {
	
	MAGNESIUM("magnesium", 0, turbine_coil_conductivity[0], TileTurbineDynamoCoil.Magnesium.class),
	BERYLLIUM("beryllium", 1, turbine_coil_conductivity[1], TileTurbineDynamoCoil.Beryllium.class),
	ALUMINUM("aluminum", 2, turbine_coil_conductivity[2], TileTurbineDynamoCoil.Aluminum.class),
	GOLD("gold", 3, turbine_coil_conductivity[3], TileTurbineDynamoCoil.Gold.class),
	COPPER("copper", 4, turbine_coil_conductivity[4], TileTurbineDynamoCoil.Copper.class),
	SILVER("silver", 5, turbine_coil_conductivity[5], TileTurbineDynamoCoil.Silver.class);
	
	private final String name;
	private final int id;
	private final double conductivity;
	private final Class<? extends TileTurbineDynamoCoil.Meta> tileClass;
	
	TurbineDynamoCoilType(String name, int id, double conductivity, Class<? extends TileTurbineDynamoCoil.Meta> tileClass) {
		this.name = name;
		this.id = id;
		this.conductivity = conductivity;
		this.tileClass = tileClass;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public double getConductivity() {
		return conductivity;
	}
	
	@Override
	public Class<? extends TileTurbineDynamoCoil.Meta> getTileClass() {
		return tileClass;
	}
}

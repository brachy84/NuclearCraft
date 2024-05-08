package nc.multiblock.turbine;

import static nc.config.NCConfig.*;

import java.util.Iterator;

import nc.enumm.ITileEnum;
import nc.tile.turbine.TileTurbineRotorBlade;
import nc.tile.turbine.TileTurbineRotorStator;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

public class TurbineRotorBladeUtil {
	
	public enum TurbineRotorBladeType implements IRotorBladeType, ITileEnum<TileTurbineRotorBlade.Variant> {
		
		STEEL("steel", turbine_blade_efficiency[0], turbine_blade_expansion[0], TileTurbineRotorBlade.Steel.class),
		EXTREME("extreme", turbine_blade_efficiency[1], turbine_blade_expansion[1], TileTurbineRotorBlade.Extreme.class),
		SIC_SIC_CMC("sic_sic_cmc", turbine_blade_efficiency[2], turbine_blade_expansion[2], TileTurbineRotorBlade.SicSicCMC.class);
		
		private final String name;
		private final double efficiency;
		private final double expansion;
		private final Class<? extends TileTurbineRotorBlade.Variant> tileClass;
		
		TurbineRotorBladeType(String name, double efficiency, double expansion, Class<? extends TileTurbineRotorBlade.Variant> tileClass) {
			this.name = name;
			this.efficiency = efficiency;
			this.expansion = expansion;
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
		public double getEfficiency() {
			return efficiency;
		}

		@Override
		public double getExpansionCoefficient() {
			return expansion;
		}

		@Override
		public Class<? extends TileTurbineRotorBlade.Variant> getTileClass() {
			return tileClass;
		}
	}
	
	public interface IRotorBladeType extends IStringSerializable {
		
		double getEfficiency();
		
		double getExpansionCoefficient();
		
		default boolean eq(IRotorBladeType other) {
			return other != null && getName().equals(other.getName()) && getEfficiency() == other.getEfficiency() && getExpansionCoefficient() == other.getExpansionCoefficient();
		}
	}
	
	public enum TurbineRotorStatorType implements IRotorStatorType, ITileEnum<TileTurbineRotorStator.Variant> {
		
		STANDARD("standard", turbine_stator_expansion, TileTurbineRotorStator.Standard.class);
		
		private final String name;
		private final double expansion;
		private final Class<? extends TileTurbineRotorStator.Variant> tileClass;
		
		TurbineRotorStatorType(String name, double expansion, Class<? extends TileTurbineRotorStator.Variant> tileClass) {
			this.name = name;
			this.expansion = expansion;
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
		public double getExpansionCoefficient() {
			return expansion;
		}

		@Override
		public Class<? extends TileTurbineRotorStator.Variant> getTileClass() {
			return tileClass;
		}
	}
	
	public interface IRotorStatorType extends IRotorBladeType {
		
		@Override
        default double getEfficiency() {
			return -1D;
		}
	}
	
	public interface ITurbineRotorBlade<BLADE extends ITurbineRotorBlade<?>> {
		
		BlockPos bladePos();
		
		TurbinePartDir getDir();
		
		void setDir(TurbinePartDir newDir);
		
		IRotorBladeType getBladeType();
		
		IBlockState getRenderState();
		
		void onBearingFailure(Iterator<BLADE> bladeIterator);
	}
	
	public interface IBlockRotorBlade {
		
	}
	
	public enum TurbinePartDir implements IStringSerializable {
		
		INVISIBLE("invisible"),
		X("x"),
		Y("y"),
		Z("z");
		
		private final String name;
		
		TurbinePartDir(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public static TurbinePartDir fromFacingAxis(EnumFacing.Axis axis) {
            return switch (axis) {
                case X -> X;
                case Y -> Y;
                case Z -> Z;
            };
		}
	}
	
	public static final PropertyEnum<TurbinePartDir> DIR = PropertyEnum.create("dir", TurbinePartDir.class);
}

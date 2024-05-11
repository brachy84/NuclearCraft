package nc.capability.radiation.resistance;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.*;

import javax.annotation.*;

public class RadiationResistanceProvider implements ICapabilitySerializable<NBTBase> {
	
	private final IRadiationResistance radiationResistance;
	
	public RadiationResistanceProvider(double resistance) {
		radiationResistance = new RadiationResistance(resistance);
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE;
	}
	
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE) {
			return IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE.cast(radiationResistance);
		}
		return null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE.writeNBT(radiationResistance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {
		IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE.readNBT(radiationResistance, null, nbt);
	}
}

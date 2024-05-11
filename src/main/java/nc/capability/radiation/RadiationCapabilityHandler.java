package nc.capability.radiation;

import nc.capability.radiation.entity.*;
import nc.capability.radiation.resistance.*;
import nc.capability.radiation.sink.*;
import nc.capability.radiation.source.*;
import nc.init.NCItems;
import nc.radiation.*;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RadiationCapabilityHandler {
	
	@SubscribeEvent
	public void attachEntityRadiationCapability(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		
		if (entity instanceof EntityPlayer) {
			addCapability(event, IEntityRads.CAPABILITY_ENTITY_RADS_NAME, new EntityRadsProvider());
		}
		else if (entity instanceof EntityLivingBase entityLiving) {
			addCapability(event, IEntityRads.CAPABILITY_ENTITY_RADS_NAME, new EntityRadsProvider(entityLiving));
		}
	}
	
	@SubscribeEvent
	public void attachChunkRadiationCapability(AttachCapabilitiesEvent<Chunk> event) {
		addCapability(event, IRadiationSource.CAPABILITY_RADIATION_SOURCE_NAME, new RadiationSourceProvider(0D));
	}
	
	@SubscribeEvent
	public void attachTileRadiationCapability(AttachCapabilitiesEvent<TileEntity> event) {
		addCapability(event, IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE_NAME, new RadiationResistanceProvider(0D));
	}
	
	@SubscribeEvent
	public void attachStackRadiationCapability(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		
		if (stack.getItem() == NCItems.radiation_badge) {
			addCapability(event, IRadiationSink.CAPABILITY_RADIATION_SINK_NAME, new RadiationSinkProvider(0D));
		}
		
		int packed = RecipeItemHelper.pack(stack);
		if (RadSources.STACK_MAP.containsKey(packed)) {
			addCapability(event, IRadiationSource.CAPABILITY_RADIATION_SOURCE_NAME, new RadiationSourceStackProvider(stack));
		}
		if (RadArmor.ARMOR_RAD_RESISTANCE_MAP.containsKey(packed)) {
			addCapability(event, IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE_NAME, new RadiationResistanceStackProvider(stack));
		}
	}
	
	public static <T> void addCapability(AttachCapabilitiesEvent<T> event, ResourceLocation key, ICapabilityProvider capabilityProvider) {
		if (!event.getCapabilities().containsKey(key)) {
			event.addCapability(key, capabilityProvider);
		}
	}
}

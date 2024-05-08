package nc.init;

import it.unimi.dsi.fastutil.objects.*;
import nc.Global;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NCSounds {
	
	public static final ObjectSet<String> TICKABLE_SOUNDS = new ObjectOpenHashSet<>();
	
	public static SoundEvent fusion_run;
	public static SoundEvent turbine_run;
	
	public static SoundEvent geiger_tick;
	public static SoundEvent radaway;
	public static SoundEvent rad_x;
	public static SoundEvent chems_wear_off;
	public static SoundEvent rad_poisoning;
	
	// public static SoundEvent feral_ghoul_ambient;
	// public static SoundEvent feral_ghoul_hurt;
	public static SoundEvent feral_ghoul_death;
	// public static SoundEvent feral_ghoul_fall;
	// public static SoundEvent feral_ghoul_step;
	public static SoundEvent feral_ghoul_charge;
	
	public static SoundEvent wanderer;
	public static SoundEvent end_of_the_world;
	public static SoundEvent money_for_nothing;
	public static SoundEvent hyperspace;
	
	public static void init() {
		fusion_run = register(Global.MOD_ID, "block.nc.fusion_run", true);
		turbine_run = register(Global.MOD_ID, "block.nc.turbine_run", true);
		
		geiger_tick = register(Global.MOD_ID, "player.nc.geiger_tick");
		radaway = register(Global.MOD_ID, "player.nc.radaway");
		rad_x = register(Global.MOD_ID, "player.nc.rad_x");
		chems_wear_off = register(Global.MOD_ID, "player.nc.chems_wear_off");
		rad_poisoning = register(Global.MOD_ID, "player.nc.rad_poisoning");
		
		// feral_ghoul_ambient = register(Global.MOD_ID, "entity.nc.feral_ghoul_ambient");
		// feral_ghoul_hurt = register(Global.MOD_ID, "entity.nc.feral_ghoul_hurt");
		feral_ghoul_death = register(Global.MOD_ID, "entity.nc.feral_ghoul_death");
		// feral_ghoul_step = register(Global.MOD_ID, "entity.nc.feral_ghoul_step");
		// feral_ghoul_fall = register(Global.MOD_ID, "entity.nc.feral_ghoul_fall");
		feral_ghoul_charge = register(Global.MOD_ID, "entity.nc.feral_ghoul_charge");
		
		wanderer = register(Global.MOD_ID, "music.nc.wanderer");
		end_of_the_world = register(Global.MOD_ID, "music.nc.end_of_the_world");
		money_for_nothing = register(Global.MOD_ID, "music.nc.money_for_nothing");
		hyperspace = register(Global.MOD_ID, "music.nc.hyperspace");
	}
	
	private static SoundEvent register(String modId, String name) {
		return register(modId, name, false);
	}
	
	private static SoundEvent register(String modId, String name, boolean tickable) {
		ResourceLocation location = new ResourceLocation(modId, name);
		if (tickable) {
			TICKABLE_SOUNDS.add(location.toString());
		}
		SoundEvent event = new SoundEvent(location);
		
		ForgeRegistries.SOUND_EVENTS.register(event.setRegistryName(location));
		return event;
	}
}

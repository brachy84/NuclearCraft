package nc.radiation;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.objects.*;
import nc.util.NCMath;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.*;

import java.util.*;
import java.util.function.ObjDoubleConsumer;
import java.util.stream.IntStream;

import static nc.config.NCConfig.*;

public class RadPotionEffects {
	
	public interface RadEffect extends ObjDoubleConsumer<EntityLivingBase> {}
	
	public static final List<Double> PLAYER_RAD_LEVEL_LIST = new DoubleArrayList();
	public static final List<List<RadEffect>> PLAYER_RAD_EFFECT_LISTS = new ArrayList<>();
	public static final Object2ObjectMap<AttributeModifier, String> PLAYER_RAD_ATTRIBUTE_MAP = new Object2ObjectOpenHashMap<>();
	
	public static final List<Double> ENTITY_RAD_LEVEL_LIST = new DoubleArrayList();
	public static final List<List<RadEffect>> ENTITY_RAD_EFFECT_LISTS = new ArrayList<>();
	public static final Object2ObjectMap<AttributeModifier, String> ENTITY_RAD_ATTRIBUTE_MAP = new Object2ObjectOpenHashMap<>();
	
	public static final List<Double> MOB_RAD_LEVEL_LIST = new DoubleArrayList();
	public static final List<List<RadEffect>> MOB_RAD_EFFECT_LISTS = new ArrayList<>();
	public static final Object2ObjectMap<AttributeModifier, String> MOB_RAD_ATTRIBUTE_MAP = new Object2ObjectOpenHashMap<>();
	
	public static void init() {
		parseEffects(radiation_player_debuff_lists, PLAYER_RAD_LEVEL_LIST, PLAYER_RAD_EFFECT_LISTS, PLAYER_RAD_ATTRIBUTE_MAP, Math.max(radiation_player_tick_rate, 19));
		parseEffects(radiation_passive_debuff_lists, ENTITY_RAD_LEVEL_LIST, ENTITY_RAD_EFFECT_LISTS, ENTITY_RAD_ATTRIBUTE_MAP, Math.max(100 / radiation_world_chunks_per_tick, 39));
		parseEffects(radiation_mob_buff_lists, MOB_RAD_LEVEL_LIST, MOB_RAD_EFFECT_LISTS, MOB_RAD_ATTRIBUTE_MAP, Math.max(100 / radiation_world_chunks_per_tick, 39));
	}
	
	private static void parseEffects(String[] effectsArray, List<Double> radLevelList, List<List<RadEffect>> radEffectLists, Object2ObjectMap<AttributeModifier, String> radAttributeMap, int rawDuration) {
		List<Double> radLevelListUnordered = new ArrayList<>();
		List<List<RadEffect>> radEffectListsUnordered = new ArrayList<>();
		
		for (int i = 0; i < effectsArray.length; ++i) {
			String effects = effectsArray[i];
			
			int charIndex = effects.indexOf('_');
			if (charIndex == -1) {
				continue;
			}
			
			double radLevel = Double.parseDouble(effects.substring(0, charIndex));
			effects = effects.substring(charIndex + 1);
			
			List<RadEffect> radEffectList = new ArrayList<>();
			
			String[] effectsSplit = effects.split(",");
			for (int j = 0; j < effectsSplit.length; ++j) {
				String effect = effectsSplit[j];
				
				charIndex = effect.indexOf('@');
				if (charIndex == -1) {
					continue;
				}
				
				String effectName = effect.substring(0, charIndex);
				String effectInfo = effect.substring(charIndex + 1);
				
				charIndex = effectInfo.indexOf('@');
				if (charIndex == -1) {
					Potion potion = Potion.getPotionFromResourceLocation(effectName);
					if (potion != null) {
						int amplifier = Integer.parseInt(effectInfo);
						int duration = getModifiedPotionDuration(effectName, rawDuration, amplifier - 1);
						radEffectList.add((x, y) -> x.addPotionEffect(new PotionEffect(potion, NCMath.toInt(y * duration), amplifier, false, false)));
					}
				}
				else {
					String attributeName = "RadEffect[" + i + "][" + j + "]";
					double amount = Double.parseDouble(effectInfo.substring(0, charIndex));
					int operation = Integer.parseInt(effectInfo.substring(charIndex + 1));
					AttributeModifier modifier = new AttributeModifier(attributeName, amount, operation);
					radEffectList.add((x, y) -> {
						IAttributeInstance attributeInstance = x.getAttributeMap().getAttributeInstanceByName(effectName);
						if (attributeInstance != null && !attributeInstance.hasModifier(modifier)) {
							attributeInstance.applyModifier(modifier);
						}
					});
					radAttributeMap.put(modifier, effectName);
				}
			}
			
			if (!radEffectList.isEmpty()) {
				radLevelListUnordered.add(radLevel);
				radEffectListsUnordered.add(radEffectList);
			}
		}
		
		Double[] radLevelArray = radLevelListUnordered.toArray(new Double[0]);
		int[] orderedIndices = IntStream.range(0, radLevelArray.length).boxed().sorted(Comparator.comparing(i -> radLevelArray[i])).mapToInt(e -> e).toArray();
		
		for (int index : orderedIndices) {
			radLevelList.add(radLevelListUnordered.get(index));
			radEffectLists.add(radEffectListsUnordered.get(index));
		}
		
		// Descending Order
		radLevelList = Lists.reverse(radLevelList);
		radEffectLists = Lists.reverse(radEffectLists);
	}
	
	private static int getModifiedPotionDuration(String potionName, int rawDuration, int amplifier) {
		return switch (potionName) {
			case "regeneration", "minecraft:regeneration" -> Math.max(rawDuration, 50 >> amplifier);
			case "wither", "minecraft:wither" -> Math.max(rawDuration, 40 >> amplifier);
			case "poison", "minecraft:poison" -> Math.max(rawDuration, 25 >> amplifier);
			case "blindness", "minecraft:blindness" -> rawDuration + 25;
			default -> rawDuration;
		};
	}
}

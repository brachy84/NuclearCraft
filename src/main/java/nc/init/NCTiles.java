package nc.init;

import nc.Global;
import nc.block.quantum.BlockQuantumComputerCodeGenerator;
import nc.enumm.ITileEnum;
import nc.enumm.MetaEnums;
import nc.multiblock.hx.HeatExchangerTubeType;
import nc.multiblock.quantum.QuantumGateEnums;
import nc.multiblock.turbine.TurbineDynamoCoilType;
import nc.multiblock.turbine.TurbineRotorBladeUtil;
import nc.tile.battery.TileBattery;
import nc.tile.fission.*;
import nc.tile.fission.manager.TileFissionShieldManager;
import nc.tile.generator.TileSolarPanel;
import nc.tile.hx.*;
import nc.tile.processor.TileProcessorImpl.TileBasicEnergyProcessorDyn;
import nc.tile.processor.TileProcessorImpl.TileBasicUpgradableEnergyProcessorDyn;
import nc.tile.quantum.TileQuantumComputerConnector;
import nc.tile.quantum.TileQuantumComputerController;
import nc.tile.quantum.TileQuantumComputerPort;
import nc.tile.quantum.TileQuantumComputerQubit;
import nc.tile.rtg.TileRTG;
import nc.tile.turbine.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class NCTiles {
	
	public static void register() {
		registerTile(Global.MOD_ID, "basic_energy_processor_dyn", TileBasicEnergyProcessorDyn.class);
		registerTile(Global.MOD_ID, "basic_upgradable_energy_processor_dyn", TileBasicUpgradableEnergyProcessorDyn.class);
		
		registerTile(Global.MOD_ID, "fission_casing", TileFissionCasing.class);
		registerTile(Global.MOD_ID, "fission_glass", TileFissionGlass.class);
		registerTile(Global.MOD_ID, "fission_conductor", TileFissionConductor.class);
		registerTile(Global.MOD_ID, "fission_monitor", TileFissionMonitor.class);
		registerTile(Global.MOD_ID, "fission_power_port", TileFissionPowerPort.class);
		registerTile(Global.MOD_ID, "fission_vent", TileFissionVent.class);
		
		registerTile(Global.MOD_ID, "fission_source", TileFissionSource.class);
		registerTileVariants(Global.MOD_ID, "fission_source", MetaEnums.NeutronSourceType.class);
		
		registerTile(Global.MOD_ID, "fission_shield", TileFissionShield.class);
		registerTileVariants(Global.MOD_ID, "fission_shield", MetaEnums.NeutronShieldType.class);
		
		registerTile(Global.MOD_ID, "fission_computer_port", TileFissionComputerPort.class);

		registerTileVariants(Global.MOD_ID, "fission_heater_port", MetaEnums.CoolantHeaterType.class, x -> Pair.of(x.toString(), x.getPortClass()));
		registerTileVariants(Global.MOD_ID, "fission_heater_port", MetaEnums.CoolantHeaterType2.class, x -> Pair.of(x.toString(), x.getPortClass()));
		
		registerTile(Global.MOD_ID, "fission_shield_manager", TileFissionShieldManager.class);

		registerTile(Global.MOD_ID, "solid_fission_sink", TileSolidFissionSink.class);
		registerTileVariants(Global.MOD_ID, "solid_fission_sink", MetaEnums.HeatSinkType.class);
		registerTileVariants(Global.MOD_ID, "solid_fission_sink", MetaEnums.HeatSinkType2.class);

		registerTileVariants(Global.MOD_ID, "salt_fission_heater", MetaEnums.CoolantHeaterType.class);
		registerTileVariants(Global.MOD_ID, "salt_fission_heater", MetaEnums.CoolantHeaterType2.class);

		registerTile(Global.MOD_ID, "heat_exchanger_casing", TileHeatExchangerCasing.class);
		registerTile(Global.MOD_ID, "heat_exchanger_glass", TileHeatExchangerGlass.class);
		registerTile(Global.MOD_ID, "heat_exchanger_vent", TileHeatExchangerVent.class);

		registerTile(Global.MOD_ID, "heat_exchanger_tube", TileHeatExchangerTube.class);
		registerTileVariants(Global.MOD_ID, "heat_exchanger_tube", HeatExchangerTubeType.class);

		registerTile(Global.MOD_ID, "heat_exchanger_computer_port", TileHeatExchangerComputerPort.class);
		
		registerTile(Global.MOD_ID, "condenser_controller", TileCondenserController.class);

		registerTile(Global.MOD_ID, "condenser_tube", TileCondenserTube.class);
		registerTileVariants(Global.MOD_ID, "condenser_tube", HeatExchangerTubeType.class, x -> Pair.of(x.toString(), x.getCondenserClass()));

		registerTile(Global.MOD_ID, "turbine_casing", TileTurbineCasing.class);
		registerTile(Global.MOD_ID, "turbine_glass", TileTurbineGlass.class);
		registerTile(Global.MOD_ID, "turbine_rotor_shaft", TileTurbineRotorShaft.class);
		
		registerTile(Global.MOD_ID, "turbine_rotor_blade_", TileTurbineRotorBlade.class);
		registerTileVariants(Global.MOD_ID, "turbine_rotor_blade", TurbineRotorBladeUtil.TurbineRotorBladeType.class);
		
		registerTile(Global.MOD_ID, "turbine_rotor_stator_", TileTurbineRotorStator.class);
		registerTileVariants(Global.MOD_ID, "turbine_rotor_stator", TurbineRotorBladeUtil.TurbineRotorStatorType.class);
		
		registerTile(Global.MOD_ID, "turbine_rotor_bearing", TileTurbineRotorBearing.class);
		
		registerTile(Global.MOD_ID, "turbine_dynamo_coil", TileTurbineDynamoCoil.class);
		registerTileVariants(Global.MOD_ID, "turbine_dynamo_coil", TurbineDynamoCoilType.class);
		
		registerTile(Global.MOD_ID, "turbine_coil_connector", TileTurbineCoilConnector.class);
		registerTile(Global.MOD_ID, "turbine_inlet", TileTurbineInlet.class);
		registerTile(Global.MOD_ID, "turbine_outlet", TileTurbineOutlet.class);
		registerTile(Global.MOD_ID, "turbine_computer_port", TileTurbineComputerPort.class);
		
		registerTile(Global.MOD_ID, "rtg", TileRTG.class);
		registerTile(Global.MOD_ID, "rtg_uranium", TileRTG.Uranium.class);
		registerTile(Global.MOD_ID, "rtg_plutonium", TileRTG.Plutonium.class);
		registerTile(Global.MOD_ID, "rtg_americium", TileRTG.Americium.class);
		registerTile(Global.MOD_ID, "rtg_californium", TileRTG.Californium.class);

		registerTile(Global.MOD_ID, "solar_panel", TileSolarPanel.class);
		
		registerTile(Global.MOD_ID, "battery", TileBattery.class);
		registerTile(Global.MOD_ID, "voltaic_pile_basic", TileBattery.VoltaicPileBasic.class);
		registerTile(Global.MOD_ID, "voltaic_pile_advanced", TileBattery.VoltaicPileAdvanced.class);
		registerTile(Global.MOD_ID, "voltaic_pile_du", TileBattery.VoltaicPileDU.class);
		registerTile(Global.MOD_ID, "voltaic_pile_elite", TileBattery.VoltaicPileElite.class);
		registerTile(Global.MOD_ID, "lithium_ion_battery_basic", TileBattery.LithiumIonBatteryBasic.class);
		registerTile(Global.MOD_ID, "lithium_ion_battery_advanced", TileBattery.LithiumIonBatteryAdvanced.class);
		registerTile(Global.MOD_ID, "lithium_ion_battery_du", TileBattery.LithiumIonBatteryDU.class);
		registerTile(Global.MOD_ID, "lithium_ion_battery_elite", TileBattery.LithiumIonBatteryElite.class);
		
		registerTile(Global.MOD_ID, "quantum_computer_controller", TileQuantumComputerController.class);
		registerTile(Global.MOD_ID, "quantum_computer_qubit", TileQuantumComputerQubit.class);

		registerTileVariants(Global.MOD_ID, "quantum_computer_gate_single", QuantumGateEnums.SingleType.class);
		registerTileVariants(Global.MOD_ID, "quantum_computer_gate_control", QuantumGateEnums.ControlType.class);
		registerTileVariants(Global.MOD_ID, "quantum_computer_gate_swap", QuantumGateEnums.SwapType.class);
		
		registerTile(Global.MOD_ID, "quantum_computer_connector", TileQuantumComputerConnector.class);
		registerTile(Global.MOD_ID, "quantum_computer_port", TileQuantumComputerPort.class);

		registerTileVariants(Global.MOD_ID, "quantum_computer_code_generator", BlockQuantumComputerCodeGenerator.Type.class);
	}

	public static void registerTile(String modId, String name, Class<? extends TileEntity> clazz) {
		GameRegistry.registerTileEntity(clazz, modId + ":" + name);
	}

	public static <T extends ITileEnum<?>> void registerTileVariants(String modId, String name, Class<T> enumm, Function<T, Pair<String, Class<? extends TileEntity>>> function) {
		for (T type : enumm.getEnumConstants()) {
			Pair<String, Class<? extends TileEntity>> pair = function.apply(type);
			registerTile(modId, name + "_" + pair.getLeft(), pair.getRight());
		}
	}

	public static <T extends TileEntity, V extends ITileEnum<T>> void registerTileVariants(String modId, String name, Class<V> enumm) {
		registerTileVariants(modId, name, enumm, x -> Pair.of(x.toString(), x.getTileClass()));
	}
}

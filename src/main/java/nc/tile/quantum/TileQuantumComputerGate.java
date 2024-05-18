package nc.tile.quantum;

import it.unimi.dsi.fastutil.ints.*;
import nc.multiblock.quantum.*;
import nc.render.BlockHighlightTracker;
import nc.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import static nc.multiblock.quantum.QuantumGate.*;

public abstract class TileQuantumComputerGate extends TileQuantumComputerPart implements ITickable {
	
	protected final String gateID;
	protected String toolMode = "";
	public boolean pulsed = false;
	
	public static abstract class Single extends TileQuantumComputerGate {
		
		protected final IntSet n;
		
		public Single(String gateID) {
			super(gateID);
			n = new IntOpenHashSet();
			toolMode = "getTarget";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, n);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.single_gate_info", getTileBlockDisplayName(), intSetToString(n))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getTarget") && player.isSneaking()) {
					n.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, n, "qubitIDSet");
					highlightQubits(player, n);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intSetToString(n))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, n, "nQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, n, "nQubits");
		}
	}
	
	public static class X extends Single {
		
		public X() {
			super("x");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.X(qc, n);
		}
	}
	
	public static class Y extends Single {
		
		public Y() {
			super("y");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.Y(qc, n);
		}
	}
	
	public static class Z extends Single {
		
		public Z() {
			super("z");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.Z(qc, n);
		}
	}
	
	public static class H extends Single {
		
		public H() {
			super("h");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.H(qc, n);
		}
	}
	
	public static class S extends Single {
		
		public S() {
			super("s");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.S(qc, n);
		}
	}
	
	public static class Sdg extends Single {
		
		public Sdg() {
			super("sdg");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.Sdg(qc, n);
		}
	}
	
	public static class T extends Single {
		
		public T() {
			super("t");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.T(qc, n);
		}
	}
	
	public static class Tdg extends Single {
		
		public Tdg() {
			super("tdg");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.Tdg(qc, n);
		}
	}
	
	public static abstract class SingleAngle extends Single {
		
		protected double angle = 0;
		
		public SingleAngle(String gateID) {
			super(gateID);
			toolMode = "getAngle";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, n);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.single_angle_gate_info", getTileBlockDisplayName(), intSetToString(n), NCMath.decimalPlaces(angle, 5))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getAngle") && player.isSneaking()) {
					angle = 0D;
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_angle", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "angle");
					toolMode = "setAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setAngle") && !player.isSneaking()) {
					angle = nbt.getDouble("qGateAngle");
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_angle", getTileBlockDisplayName(), NCMath.decimalPlaces(angle, 5))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					n.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, n, "qubitIDSet");
					highlightQubits(player, n);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intSetToString(n))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			nbt.setDouble("qGateAngle", angle);
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			angle = nbt.getDouble("qGateAngle");
		}
	}
	
	public static class P extends SingleAngle {
		
		public P() {
			super("p");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.P(qc, angle, n);
		}
	}
	
	public static class RX extends SingleAngle {
		
		public RX() {
			super("rx");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.RX(qc, angle, n);
		}
	}
	
	public static class RY extends SingleAngle {
		
		public RY() {
			super("ry");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.RY(qc, angle, n);
		}
	}
	
	public static class RZ extends SingleAngle {
		
		public RZ() {
			super("rz");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.RZ(qc, angle, n);
		}
	}
	
	public static abstract class Control extends TileQuantumComputerGate {
		
		protected final IntSet c, t;
		
		public Control(String gateID) {
			super(gateID);
			c = new IntOpenHashSet();
			t = new IntOpenHashSet();
			toolMode = "getControl";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, c);
			highlightQubits(player, t);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_gate_info", getTileBlockDisplayName(), intSetToString(t), intSetToString(c))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getControl") && player.isSneaking()) {
					c.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, c, "qubitIDSet");
					highlightQubits(player, c);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intSetToString(c))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					t.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, t, "qubitIDSet");
					highlightQubits(player, t);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intSetToString(t))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, c, "cQubits");
			NBTHelper.writeIntCollection(nbt, t, "tQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, c, "cQubits");
			NBTHelper.readIntCollection(nbt, t, "tQubits");
		}
	}
	
	public static class CX extends Control {
		
		public CX() {
			super("cx");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CX(qc, c, t);
		}
	}
	
	public static class CY extends Control {
		
		public CY() {
			super("cy");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CY(qc, c, t);
		}
	}
	
	public static class CZ extends Control {
		
		public CZ() {
			super("cz");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CZ(qc, c, t);
		}
	}
	
	public static class CH extends Control {
		
		public CH() {
			super("ch");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CH(qc, c, t);
		}
	}
	
	public static class CS extends Control {
		
		public CS() {
			super("cs");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CS(qc, c, t);
		}
	}
	
	public static class CSdg extends Control {
		
		public CSdg() {
			super("csdg");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CSdg(qc, c, t);
		}
	}
	
	public static class CT extends Control {
		
		public CT() {
			super("ct");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CT(qc, c, t);
		}
	}
	
	public static class CTdg extends Control {
		
		public CTdg() {
			super("ctdg");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CTdg(qc, c, t);
		}
	}
	
	public static abstract class ControlAngle extends Control {
		
		protected double angle = 0;
		
		public ControlAngle(String gateID) {
			super(gateID);
			toolMode = "getAngle";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, c);
			highlightQubits(player, t);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_angle_gate_info", getTileBlockDisplayName(), intSetToString(t), NCMath.decimalPlaces(angle, 5), intSetToString(c))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getAngle") && player.isSneaking()) {
					angle = 0D;
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_angle", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "angle");
					toolMode = "setAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setAngle") && !player.isSneaking()) {
					angle = nbt.getDouble("qGateAngle");
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_angle", getTileBlockDisplayName(), NCMath.decimalPlaces(angle, 5))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getControl") && player.isSneaking()) {
					c.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, c, "qubitIDSet");
					highlightQubits(player, c);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intSetToString(c))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					t.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, t, "qubitIDSet");
					highlightQubits(player, t);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intSetToString(t))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			nbt.setDouble("qGateAngle", angle);
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			angle = nbt.getDouble("qGateAngle");
		}
	}
	
	public static class CP extends ControlAngle {
		
		public CP() {
			super("cp");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CP(qc, angle, c, t);
		}
	}
	
	public static class CRX extends ControlAngle {
		
		public CRX() {
			super("crx");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CRX(qc, angle, c, t);
		}
	}
	
	public static class CRY extends ControlAngle {
		
		public CRY() {
			super("cry");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CRY(qc, angle, c, t);
		}
	}
	
	public static class CRZ extends ControlAngle {
		
		public CRZ() {
			super("crz");
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.CRZ(qc, angle, c, t);
		}
	}
	
	public static class Swap extends TileQuantumComputerGate {
		
		protected final IntList i, j;
		
		public Swap() {
			super("swap");
			i = new IntArrayList();
			j = new IntArrayList();
			toolMode = "getFirst";
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.Swap(qc, i, j);
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, i);
			highlightQubits(player, j);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.swap_gate_info", getTileBlockDisplayName(), intListToString(i), intListToString(j))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getFirst") && player.isSneaking()) {
					i.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_first_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setFirst") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, i, "qubitIDList");
					highlightQubits(player, i);
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_first_swap_list", getTileBlockDisplayName(), intListToString(i))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getSecond") && player.isSneaking()) {
					j.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_second_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setSecond") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, j, "qubitIDList");
					highlightQubits(player, j);
					player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_second_swap_list", getTileBlockDisplayName(), intListToString(j))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, i, "iQubits");
			NBTHelper.writeIntCollection(nbt, j, "jQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, i, "iQubits");
			NBTHelper.readIntCollection(nbt, j, "jQubits");
		}
	}
	
	public static class ControlSwap extends TileQuantumComputerGate {
		
		protected final IntSet c;
		protected final IntList i, j;
		
		public ControlSwap() {
			super("cswap");
			c = new IntOpenHashSet();
			i = new IntArrayList();
			j = new IntArrayList();
			toolMode = "getControl";
		}
		
		@Override
		protected QuantumGate<?> newGate(QuantumComputer qc) {
			return new QuantumGate.ControlSwap(qc, c, i, j);
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, c);
			highlightQubits(player, i);
			highlightQubits(player, j);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_swap_gate_info", getTileBlockDisplayName(), intListToString(i), intListToString(j), intSetToString(c))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getControl") && player.isSneaking()) {
					c.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, c, "qubitIDSet");
					highlightQubits(player, c);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intSetToString(c))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getFirst") && player.isSneaking()) {
					i.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_first_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setFirst") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, i, "qubitIDList");
					highlightQubits(player, i);
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_first_swap_list", getTileBlockDisplayName(), intListToString(i))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getSecond") && player.isSneaking()) {
					j.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_second_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setSecond") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, j, "qubitIDList");
					highlightQubits(player, j);
					player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_second_swap_list", getTileBlockDisplayName(), intListToString(j))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, c, "cQubits");
			NBTHelper.writeIntCollection(nbt, i, "iQubits");
			NBTHelper.writeIntCollection(nbt, j, "jQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, c, "cQubits");
			NBTHelper.readIntCollection(nbt, i, "iQubits");
			NBTHelper.readIntCollection(nbt, j, "jQubits");
		}
	}
	
	public TileQuantumComputerGate(String gateID) {
		super();
		this.gateID = gateID;
	}
	
	@Override
	public void onMachineAssembled(QuantumComputer multiblock) {
		doStandardNullControllerResponse(multiblock);
	}
	
	@Override
	public void onMachineBroken() {}
	
	@Override
	public int[] weakSidesToCheck(World worldIn, BlockPos posIn) {
		return new int[] {2, 3, 4, 5};
	}
	
	@Override
	public void update() {
		if (!pulsed && getIsRedstonePowered()) {
			if (isMultiblockAssembled()) {
				getMultiblock().getGateQueue().add(newGate(getMultiblock()));
			}
			pulsed = true;
		}
		else if (pulsed && !getIsRedstonePowered()) {
			pulsed = false;
		}
	}
	
	@Override
	public boolean shouldRefresh(World worldIn, BlockPos posIn, IBlockState oldState, IBlockState newState) {
		return oldState != newState;
	}
	
	protected abstract QuantumGate<?> newGate(QuantumComputer qc);
	
	public abstract void sendGateInfo(EntityPlayerMP player);
	
	protected void highlightQubits(EntityPlayerMP player, IntCollection n) {
		QuantumComputer qc = getMultiblock();
		if (qc != null) {
			for (TileQuantumComputerQubit qubit : qc.getQubits()) {
				if (n.contains(qubit.id)) {
					BlockHighlightTracker.sendPacket(player, qubit.getPos(), 5000);
				}
			}
		}
	}
	
	public static void clearMultitoolGateInfo(NBTTagCompound nbt) {
		nbt.setDouble("qGateAngle", 0D);
		NBTHelper.writeIntCollection(nbt, new IntOpenHashSet(), "qubitIDSet");
		NBTHelper.writeIntCollection(nbt, new IntArrayList(), "qubitIDList");
	}
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		nbt.setBoolean("pulsed", pulsed);
		nbt.setString("toolMode", toolMode);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		pulsed = nbt.getBoolean("pulsed");
		toolMode = nbt.getString("toolMode");
	}
}

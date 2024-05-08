package nc.block.quantum;

import nc.enumm.IBlockMetaEnum;
import nc.enumm.ITileEnum;
import nc.tile.quantum.TileQuantumComputerCodeGenerator;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuantumComputerCodeGenerator extends BlockQuantumComputerMetaPart<BlockQuantumComputerCodeGenerator.Type> {
	
	public final static PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);
	
	public BlockQuantumComputerCodeGenerator() {
		super(Type.class, TYPE);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return values[meta].getTile();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand != EnumHand.MAIN_HAND || player.isSneaking()) {
			return false;
		}
		return rightClickOnPart(world, pos, player, hand, facing);
	}
	
	public enum Type implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileQuantumComputerCodeGenerator> {
		
		QASM("qasm", 0, TileQuantumComputerCodeGenerator.Qasm.class),
		QISKIT("qiskit", 1, TileQuantumComputerCodeGenerator.Qiskit.class);
		
		private final String name;
		private final int id;
		private final Class<? extends TileQuantumComputerCodeGenerator> tileClass;
		
		Type(String name, int id, Class<? extends TileQuantumComputerCodeGenerator> tileClass) {
			this.name = name;
			this.id = id;
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
		
		@Override
		public int getHarvestLevel() {
			return 0;
		}
		
		@Override
		public String getHarvestTool() {
			return "pickaxe";
		}
		
		@Override
		public float getHardness() {
			return 2F;
		}
		
		@Override
		public float getResistance() {
			return 15F;
		}

		@Override
		public int getLightValue() {
			return 0;
		}

		@Override
		public Class<? extends TileQuantumComputerCodeGenerator> getTileClass() {
			return tileClass;
		}

		public TileEntity getTile() {
            return switch (this) {
                case QASM -> new TileQuantumComputerCodeGenerator.Qasm();
                case QISKIT -> new TileQuantumComputerCodeGenerator.Qiskit();
            };
		}
	}
}

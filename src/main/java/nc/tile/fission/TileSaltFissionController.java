package nc.tile.fission;

import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.fission.FissionReactor;
import nc.tile.TileContainerInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Iterator;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileSaltFissionController extends TileFissionPart implements IFissionController<TileSaltFissionController> {
	
	protected final TileContainerInfo<TileSaltFissionController> info = TileInfoHandler.getTileContainerInfo("salt_fission_controller");
	
	public TileSaltFissionController() {
		super(CuboidalPartPositionType.WALL);
	}
	
	@Override
	public String getLogicID() {
		return "molten_salt";
	}
	
	@Override
	public TileContainerInfo<TileSaltFissionController> getContainerInfo() {
		return info;
	}
	
	@Override
	public void onMachineAssembled(FissionReactor controller) {
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null) {
			getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}
	}
	
	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
	}
	
	@Override
	public void onBlockNeighborChanged(IBlockState state, World worldIn, BlockPos posIn, BlockPos fromPos) {
		super.onBlockNeighborChanged(state, worldIn, posIn, fromPos);
		if (getMultiblock() != null) {
			getMultiblock().updateActivity();
		}
	}
	
	@Override
	public void doMeltdown(Iterator<IFissionController<?>> controllerIterator) {
		controllerIterator.remove();
		world.removeTileEntity(pos);
		
		IBlockState corium = FluidRegistry.getFluid("corium").getBlock().getDefaultState();
		world.setBlockState(pos, corium);
	}
}

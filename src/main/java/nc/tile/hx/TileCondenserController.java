package nc.tile.hx;

import static nc.block.property.BlockProperties.FACING_ALL;

import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.HeatExchanger;
import nc.tile.info.TileContainerInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileCondenserController extends TileHeatExchangerPart implements IHeatExchangerController<TileCondenserController> {

	protected final TileContainerInfo<TileCondenserController> info = TileInfoHandler.getTileContainerInfo("condenser_controller");

	public TileCondenserController() {
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID() {
		return "condenser";
	}

	@Override
	public TileContainerInfo<TileCondenserController> getContainerInfo() {
		return info;
	}
	
	@Override
	public void onMachineAssembled(HeatExchanger controller) {
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
			getMultiblock().setIsHeatExchangerOn();
		}
	}
	
	@Override
	public int[] weakSidesToCheck(World worldIn, BlockPos posIn) {
		return new int[] {2, 3, 4, 5};
	}
}

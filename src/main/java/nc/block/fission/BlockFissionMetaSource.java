package nc.block.fission;

import nc.block.tile.IActivatable;
import nc.enumm.MetaEnums;
import nc.tile.fission.TileFissionSource;
import nc.util.BlockHelper;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

import javax.annotation.Nullable;

import static nc.block.property.BlockProperties.*;

public class BlockFissionMetaSource extends BlockFissionMetaPart<MetaEnums.NeutronSourceType> implements IActivatable {
	
	public final static PropertyEnum<MetaEnums.NeutronSourceType> TYPE = PropertyEnum.create("type", MetaEnums.NeutronSourceType.class);
	
	public BlockFissionMetaSource() {
		super(MetaEnums.NeutronSourceType.class, TYPE);
		setDefaultState(getDefaultState().withProperty(FACING_ALL, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.FALSE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, FACING_ALL, ACTIVE);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileFissionSource source) {
			EnumFacing facing = source.getPartPosition().getFacing();
			return state.withProperty(FACING_ALL, facing != null ? facing : source.facing).withProperty(ACTIVE, source.isActive);
		}
		return state;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return switch (metadata) {
			case 0 -> new TileFissionSource.RadiumBeryllium();
			case 1 -> new TileFissionSource.PoloniumBeryllium();
			case 2 -> new TileFissionSource.Californium();
			default -> new TileFissionSource.RadiumBeryllium();
		};
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		BlockHelper.setDefaultFacing(world, pos, state, FACING_ALL);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getStateFromMeta(meta).withProperty(FACING_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(ACTIVE, Boolean.FALSE);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileFissionSource source) {
			source.facing = state.getValue(FACING_ALL);
			world.setBlockState(pos, state.withProperty(FACING_ALL, source.facing).withProperty(ACTIVE, source.isActive), 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND || player.isSneaking()) {
			return false;
		}
		return rightClickOnPart(world, pos, player, hand, facing);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null;
	}
}

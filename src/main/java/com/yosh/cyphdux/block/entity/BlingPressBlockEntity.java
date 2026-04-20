package com.yosh.cyphdux.block.entity;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.block.custom.EnrichingFurnaceBlock;
import com.yosh.cyphdux.item.ModItems;
import com.yosh.cyphdux.recipe.EnrichingRecipe;
import com.yosh.cyphdux.sceenhandler.BlingPressScreenHandler;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlingPressBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final  DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);

    private static final int LEFT_SLOT = 0;
    private static final int RIGHT_SLOT = 1;
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 100;
    private int tickingFuel = 0;
    private int maxTickingFuel = 0;
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
    private static final int[] TOP_SLOTS = new int[]{0, 1};
    private static final int[] BOTTOM_SLOTS = new int[]{3};
    private static final int[] SIDE_SLOTS = new int[]{2};



    public BlingPressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.BLING_PRESS_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> BlingPressBlockEntity.this.progress;
                    case 1 -> BlingPressBlockEntity.this.maxProgress;
                    case 2 -> BlingPressBlockEntity.this.tickingFuel;
                    case 3 -> BlingPressBlockEntity.this.maxTickingFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> BlingPressBlockEntity.this.progress = value;
                    case 1 -> BlingPressBlockEntity.this.maxProgress = value;
                    case 2 -> BlingPressBlockEntity.this.tickingFuel = value;
                    case 3 -> BlingPressBlockEntity.this.maxTickingFuel = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }



    public void getRecipesUsedAndDropExperience(ServerWorld world, Vec3d vec3d) {
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + CypherDuxMod.MOD_ID + ".bling_press");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BlingPressScreenHandler(syncId,playerInventory,this, this.propertyDelegate);
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity serverPlayerEntity) {

    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()){
            return;
        }
        boolean lit = hasTickingFuel();
        if (hasTickingFuel()){
            this.decreaseTickingFuel();
        }

        if (isOutputSlotEmptyOrReceivable()&&hasFuel()){
            if(this.hasRecipe()){
                //Optional<RecipeEntry<EnrichingRecipe>> recipeEntry = getCurrentRecipe();
                setMaxProgressForRecipe();
                if (!hasTickingFuel()){
                    this.setTickingFuel();
                }
                this.increaseCraftProgress();
                markDirty();

                if (hasCraftingFinished()){
                    this.craftItem();
                    //this.setLastRecipe(recipeEntry.get());
                    this.resetProgress();
                }
            }else if (hasTickingFuel() && this.progress > 0) {
                this.progress = MathHelper.clamp(this.progress - 2, 0, this.maxProgress);
            }else {
                this.resetProgress();
                this.resetMaxProgress();
            }
        }else {
            this.resetProgress();
            markDirty();
        }
        if (hasTickingFuel() != lit){
            state = state.with(EnrichingFurnaceBlock.LIT, hasTickingFuel());
            world.setBlockState(pos,state, Block.NOTIFY_ALL);
            markDirty();
        }
    }

    private void resetMaxProgress() {
        this.maxProgress = 100;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void setLastRecipe(RecipeEntry<EnrichingRecipe> enrichingRecipeRecipeEntry) {
    }

    private void craftItem() {
        this.getStack(LEFT_SLOT).decrement(1);
        this.getStack(RIGHT_SLOT).decrement(1);
        if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            this.setStack(OUTPUT_SLOT, ModItems.BLING.getDefaultStack());
        } else {
            this.getStack(OUTPUT_SLOT).increment(1);
        }

    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftProgress() {
        this.progress++;
    }

    private void setTickingFuel() {
        this.tickingFuel = FuelRegistry.INSTANCE.get(this.getStack(FUEL_SLOT).getItem());
        this.maxTickingFuel = this.tickingFuel;
        this.getStack(FUEL_SLOT).decrement(1);
    }

    private void setMaxProgressForRecipe() {
        this.maxProgress = 100;
    }

    private Optional<RecipeEntry<EnrichingRecipe>> getCurrentRecipe() {
        return Optional.empty();
    }

    private boolean hasRecipe() {
        return this.getStack(LEFT_SLOT).isOf(Items.GOLD_INGOT)&&this.getStack(RIGHT_SLOT).isOf(Items.DIAMOND);
    }

    private boolean hasFuel() {
        ItemStack fuelSlotItem = this.getStack(FUEL_SLOT);
        return FuelRegistry.INSTANCE.get(fuelSlotItem.getItem()) != null || hasTickingFuel();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void decreaseTickingFuel() {
        this.tickingFuel--;
    }

    private boolean hasTickingFuel() {
        return this.tickingFuel > 0;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir != Direction.DOWN || slot != 2;
    }
    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 3) {
            return false;
        } else if (slot != 2) {
            return true;
        } else {
            return isFuel(stack);
        }
    }

    private boolean isFuel(ItemStack stack) {
        return FuelRegistry.INSTANCE.get(stack.getItem()) != null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);nbt.putInt("bling_press.progress",progress);
        nbt.putInt("bling_press.maxProgress",maxProgress);
        nbt.putInt("bling_press.tickingFuel",tickingFuel);
        nbt.putInt("bling_press.maxTickingFuel",maxTickingFuel);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), count));
        nbt.put("bling_press.recipesUsed", nbtCompound);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        this.getItems().clear();
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("bling_press.progress");
        maxProgress = nbt.getInt("bling_press.maxProgress");
        tickingFuel = nbt.getInt("bling_press.tickingFuel");
        maxTickingFuel = nbt.getInt("bling_press.maxTickingFuel");
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");

        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
        }
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(this.world != null) {
            this.world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

}

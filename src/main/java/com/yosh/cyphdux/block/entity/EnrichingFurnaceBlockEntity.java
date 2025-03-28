package com.yosh.cyphdux.block.entity;

import com.google.common.collect.Lists;
import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.block.custom.EnrichingFurnaceBlock;
import com.yosh.cyphdux.recipe.EnrichingRecipe;
import com.yosh.cyphdux.sceenhandler.EnrichingFurnaceScreenHandler;
import com.yosh.cyphdux.recipe.input.DoubleStackRecipeInput;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.*;
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

import java.util.List;
import java.util.Optional;

public class EnrichingFurnaceBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, SidedInventory, RecipeUnlocker, RecipeInputProvider {
    public static final Text TITLE = Text.translatable("container."+ CypherDuxMod.MOD_ID+".enriching_furnace");
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private final int ADDITION_SLOT =0;
    private final int BASE_SLOT =1;
    private final int FUEL_SLOT = 2;
    private final int OUTPUT_SLOT = 3;
    private int tickingFuel = 0;
    private int maxTickingFuel = 0;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 200;
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
    private static final int[] TOP_SLOTS = new int[]{0, 1};
    private static final int[] BOTTOM_SLOTS = new int[]{3};
    private static final int[] SIDE_SLOTS = new int[]{2};
    public EnrichingFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ENRICHING_FURNACE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> EnrichingFurnaceBlockEntity.this.progress;
                    case 1 -> EnrichingFurnaceBlockEntity.this.maxProgress;
                    case 2 -> EnrichingFurnaceBlockEntity.this.tickingFuel;
                    case 3 -> EnrichingFurnaceBlockEntity.this.maxTickingFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> EnrichingFurnaceBlockEntity.this.progress = value;
                    case 1 -> EnrichingFurnaceBlockEntity.this.maxProgress = value;
                    case 2 -> EnrichingFurnaceBlockEntity.this.tickingFuel = value;
                    case 3 -> EnrichingFurnaceBlockEntity.this.maxTickingFuel = value;
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

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EnrichingFurnaceScreenHandler(syncId,playerInventory,this, this.propertyDelegate);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        var nbt = super.toInitialChunkDataNbt(registryLookup);
        writeNbt(nbt, registryLookup);
        return nbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putInt("enriching_furnace.progress",progress);
        nbt.putInt("enriching_furnace.maxProgress",maxProgress);
        nbt.putInt("enriching_furnace.tickingFuel",tickingFuel);
        nbt.putInt("enriching_furnace.maxTickingFuel",maxTickingFuel);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), count));
        nbt.put("enriching_furnace.recipesUsed", nbtCompound);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        progress = nbt.getInt("enriching_furnace.progress");
        maxProgress = nbt.getInt("enriching_furnace.maxProgress");
        tickingFuel = nbt.getInt("enriching_furnace.tickingFuel");
        maxTickingFuel = nbt.getInt("enriching_furnace.maxTickingFuel");
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");

        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
        }
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
                Optional<RecipeEntry<EnrichingRecipe>> recipeEntry = getCurrentRecipe();
                setMaxProgressForRecipe();
                if (!hasTickingFuel()){
                    this.setTickingFuel();
                }
                this.increaseCraftProgress();
                update();

                if (hasCraftingFinished()){
                    this.craftItem();
                    this.setLastRecipe(recipeEntry.get());
                    this.resetProgress();
                }
            }else {
                this.resetProgress();
                this.resetMaxProgress();
            }
        }else {
            this.resetProgress();
            update();
        }
        if (hasTickingFuel() != lit){
            state = state.with(EnrichingFurnaceBlock.LIT, hasTickingFuel());
            world.setBlockState(pos,state, Block.NOTIFY_ALL);
            update();
        }
    }

    private void resetMaxProgress() {
        this.maxProgress = 200;
    }

    private void setTickingFuel() {
        if(getStack(FUEL_SLOT).isOf(Items.REDSTONE)){
            this.removeStack(FUEL_SLOT, 1);
            this.tickingFuel = 100;
            this.maxTickingFuel = 100;
        }
        if(getStack(FUEL_SLOT).isOf(Items.REDSTONE_BLOCK)){
            this.removeStack(FUEL_SLOT, 1);
            this.tickingFuel = 1000;
            this.maxTickingFuel = 1000;
        }
    }
    private void setMaxProgressForRecipe(){
        Optional<RecipeEntry<EnrichingRecipe>> recipe = getCurrentRecipe();
        this.maxProgress = recipe.get().value().getCookingTime();
    }
    private void decreaseTickingFuel() {
        tickingFuel--;
    }

    private boolean hasTickingFuel() {
        return tickingFuel > 0;
    }

    private boolean hasFuel() {
        return getStack(FUEL_SLOT).isOf(Items.REDSTONE) || getStack(FUEL_SLOT).isOf(Items.REDSTONE_BLOCK) || hasTickingFuel();
    }
    private boolean isFuel(ItemStack itemStack) {
        return itemStack.isOf(Items.REDSTONE) || itemStack.isOf(Items.REDSTONE_BLOCK) || hasTickingFuel();
    }

    private boolean hasRecipe() {
        /* Méthode Brute
        ItemStack result = new ItemStack(ModItems.KAYBER_KRYSTAL);
        boolean hasInput = getStack(BASE_SLOT).getItem() == Items.AMETHYST_SHARD && getStack(ADDITION_SLOT).getItem() == Items.EMERALD;*/
        Optional<RecipeEntry<EnrichingRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))&&canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeEntry<EnrichingRecipe>> getCurrentRecipe() {
        DoubleStackRecipeInput inv = new DoubleStackRecipeInput(this.getStack(ADDITION_SLOT),this.getStack(BASE_SLOT));

        return getWorld().getRecipeManager().getFirstMatch(EnrichingRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem()==item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount()+result.getCount()<=this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void craftItem() {
        Optional<RecipeEntry<EnrichingRecipe>> recipe = getCurrentRecipe();
        this.removeStack(BASE_SLOT,1);
        this.removeStack(ADDITION_SLOT,1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(), getStack(OUTPUT_SLOT).getCount()+recipe.get().value().getResult(null).getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress>=maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty()||this.getStack(OUTPUT_SLOT).getCount()<this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void update(){
        markDirty();
        if(world != null){
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);

        for (RecipeEntry<?> recipeEntry : list) {
            if (recipeEntry != null) {
                player.onRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<RecipeEntry<?>> list = Lists.<RecipeEntry<?>>newArrayList();

        for (Object2IntMap.Entry<Identifier> entry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get((Identifier)entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((EnrichingRecipe)recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            i++;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.id();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Nullable
    @Override
    public RecipeEntry<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }
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
            //ItemStack itemStack = this.inventory.get(1);
            return isFuel(stack);
        }
    }
}

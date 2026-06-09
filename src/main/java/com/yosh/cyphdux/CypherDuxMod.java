package com.yosh.cyphdux;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.yosh.cyphdux.block.ModBlocks;
import com.yosh.cyphdux.block.entity.ModBlockEntityTypes;
import com.yosh.cyphdux.command.RandomTeleportCommand;
import com.yosh.cyphdux.entity.ModEntities;
import com.yosh.cyphdux.item.ModItems;
import com.yosh.cyphdux.network.DisplayItemC2SPayload;
import com.yosh.cyphdux.recipe.ModRecipes;
import com.yosh.cyphdux.sceenhandler.ItemDisplayBoardScreenHandler;
import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import com.yosh.cyphdux.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CypherDuxMod implements ModInitializer {
	public static final String MOD_ID = "cypher-dux-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String path){
		return Identifier.of(CypherDuxMod.MOD_ID,path);
	}

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		if(FabricLoader.getInstance().isModLoaded("configurablefurnaceburntime")){
			CypherDuxMod.LOGGER.info("Configurable Furnace Burn Time is Present!");
			// Fuel time will be divided by 12
			FuelRegistry.INSTANCE.add(ItemTags.LOGS,1200); //100
			FuelRegistry.INSTANCE.add(ItemTags.BAMBOO_BLOCKS,1200); //100
			FuelRegistry.INSTANCE.add(Items.COAL,19200); //1,600
			FuelRegistry.INSTANCE.add(Items.COAL_BLOCK,192000); //16,000
			FuelRegistry.INSTANCE.add(Blocks.DRIED_KELP_BLOCK, 36000); //3,000
			FuelRegistry.INSTANCE.add(Items.BLAZE_ROD, 28800); //2,400
			FuelRegistry.INSTANCE.add(Items.LAVA_BUCKET,28800); //2,400

			FuelRegistry.INSTANCE.add(Items.CHARCOAL,4800); //400
			FuelRegistry.INSTANCE.add(ModBlocks.CHARCOAL_BLOCK,48000); //4,000
			FuelRegistry.INSTANCE.add(ModItems.COPPER_PLATED_COAL,76800); //6,400
			FuelRegistry.INSTANCE.add(ModItems.ENRICHED_COPPER_PLATED_COAL,768000); //64,000
			FuelRegistry.INSTANCE.add(ModBlocks.COPPER_PLATED_COAL_BLOCK,331200); //27,600
			FuelRegistry.INSTANCE.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK,3312000); //276,000
		}


		ModBlockEntityTypes.initialize();
		ScreenHandlerTypes.initialize();
		ModRecipes.initialize();
		ModSounds.initialize();
		ModEntities.initialize();
		PayloadTypeRegistry.playC2S().register(DisplayItemC2SPayload.ID, DisplayItemC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(DisplayItemC2SPayload.ID,(payload, context)->{
			ItemStack displayedItem = payload.itemStack().isOf(Items.BARRIER)? ItemStack.EMPTY:payload.itemStack();
			if (context.player().currentScreenHandler instanceof ItemDisplayBoardScreenHandler screenHandler){
				boolean isSlotTheSame = screenHandler.getSlot(0).getStack() == displayedItem;
				if(displayedItem.isEmpty()){
					screenHandler.getSlot(0).setStack(displayedItem);
					if(!isSlotTheSame){
						context.player().getWorld().playSound(null,context.player().getBlockPos(), SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS);
					}
					screenHandler.getDisplayBoardBlockEntity().markDirty();
				}else{
					screenHandler.getSlot(0).setStack(displayedItem);
					if(!isSlotTheSame){
						context.player().getWorld().playSound(null,context.player().getBlockPos(), SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.PLAYERS);
					}
					screenHandler.getDisplayBoardBlockEntity().markDirty();
				}
				screenHandler.getDisplayBoardBlockEntity().getWorld().updateListeners(screenHandler.getDisplayBoardBlockEntity().getPos(),screenHandler.getDisplayBoardBlockEntity().getCachedState(),screenHandler.getDisplayBoardBlockEntity().getCachedState(), Block.NOTIFY_ALL);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("tp-r")
                .executes(RandomTeleportCommand::randomTeleport)
                .then(argument("width", IntegerArgumentType.integer(1))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
								IntegerArgumentType.getInteger(context, "width")
                        ))
                .then(argument("height", IntegerArgumentType.integer(1,16))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
								IntegerArgumentType.getInteger(context, "width"),
								IntegerArgumentType.getInteger(context, "height")
                        ))
                .then(argument("maxAttempts", IntegerArgumentType.integer(1))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
								IntegerArgumentType.getInteger(context, "width"),
								IntegerArgumentType.getInteger(context, "height"),
                                IntegerArgumentType.getInteger(context, "maxAttempts")
                        )))))));
		/*ResourceManagerHelper.registerBuiltinResourcePack(
				of("cypherduxdark"),
				FabricLoader.getInstance().getModContainer(MOD_ID).get(),
				Text.literal("CypherDux DarkTheme Lite"),
				ResourcePackActivationType.DEFAULT_ENABLED
		);*/
	}
}
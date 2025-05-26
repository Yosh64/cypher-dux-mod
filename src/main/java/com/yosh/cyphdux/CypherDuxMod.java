package com.yosh.cyphdux;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.yosh.cyphdux.armor.ModArmorMaterials;
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
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CypherDuxMod implements ModInitializer {
	public static final String MOD_ID = "cypher-dux-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModArmorMaterials.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
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
                .then(argument("width", FloatArgumentType.floatArg(1))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
                                FloatArgumentType.getFloat(context, "width")
                        ))
                .then(argument("height", FloatArgumentType.floatArg(1,16))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
                                FloatArgumentType.getFloat(context, "width"),
                                FloatArgumentType.getFloat(context, "height")
                        ))
                .then(argument("maxAttempts", IntegerArgumentType.integer(1))
                .executes(context ->
                        RandomTeleportCommand.randomTeleport(
                                context,
                                FloatArgumentType.getFloat(context, "width"),
                                FloatArgumentType.getFloat(context, "height"),
                                IntegerArgumentType.getInteger(context, "maxAttempts")
                        )))))));
	}
}
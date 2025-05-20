package com.yosh.cyphdux.sound;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static void initialize(){
    }

    public static SoundEvent register(String name){
        Identifier id = Identifier.of(CypherDuxMod.MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }

    public static SoundEvent ZINZIN_OF_THE_BELLS = register("zinzin_of_the_bells");
    public static final RegistryKey<JukeboxSong> ZINZIN_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(CypherDuxMod.MOD_ID,"zinzin_of_the_bells"));
}

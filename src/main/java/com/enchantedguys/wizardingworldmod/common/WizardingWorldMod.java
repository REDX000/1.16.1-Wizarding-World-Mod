package com.enchantedguys.wizardingworldmod.common;

import com.enchantedguys.wizardingworldmod.common.init.ModBlocks;
import com.enchantedguys.wizardingworldmod.common.init.ModItems;
import com.enchantedguys.wizardingworldmod.common.init.ModSounds;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("wwm")
public class WizardingWorldMod {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "wwm";

    public WizardingWorldMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        ModSounds.SOUNDS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }
    //examplemod%%C:/Users/tobia/OneDrive/Desktop/Developing/Minecraft/Modding/Projects/1.16.1-Wizarding-World-Mod\build\resources\main;examplemod%%C:/Users/tobia/OneDrive/Desktop/Developing/Minecraft/Modding/Projects//1.16.1-Wizarding-World-Mod\build\classes\java\main

    @SubscribeEvent
    public void onBlockItemRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !ModItems.BLACKLIST.get().contains(block.getRegistryName())).forEach(block -> {
            Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
            BlockItem item = new BlockItem(block, properties);
            item.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
            registry.register(item);
        });
    }

    // Tabs

    public static final ItemGroup BUILDINGBLOCKS = new ItemGroup("building_blocks") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.OBSIDIAN_BRICKS_ITEM.get());
        }
    };

    public static final ItemGroup CHARMS = new ItemGroup("charms") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.WIZARDING_BENCH_ITEM.get());
        }
    };

    public static final ItemGroup WANDS = new ItemGroup("wands") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MR_LIMNER_WAND.get());
        }
    };

    public static final ItemGroup MATERIALS = new ItemGroup("materials") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.PHOENIX_FEATHER.get());
        }
    };

    public static final ItemGroup FOODS = new ItemGroup("foods") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.HAGRIDS_ROCK_CAKE.get());
        }
    };

    public static final ItemGroup OTHER = new ItemGroup("other") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MUSIC_DISC_HARRY_POTTER_THEME.get());
        }
    };

}

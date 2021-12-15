package io.github.kthchew.serverblockade.mixin;

import net.minecraft.client.gui.screen.DirectConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DirectConnectScreen.class)
public interface DirectConnectScreenAccessor {
    @Accessor
    Screen getParent();
}

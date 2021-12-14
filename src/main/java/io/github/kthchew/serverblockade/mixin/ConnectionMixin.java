package io.github.kthchew.serverblockade.mixin;

import io.github.kthchew.serverblockade.ServerBlockade;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class ConnectionMixin {
    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"), cancellable = true)
    public void cancelIfBlocked(ServerInfo entry, CallbackInfo ci) {
        if (!ServerBlockade.CONFIG.isServerAllowed(entry.address)) {
            ci.cancel();
        }
    }
}
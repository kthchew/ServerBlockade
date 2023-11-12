package io.github.kthchew.serverblockade.mixin;

import io.github.kthchew.serverblockade.ServerBlockade;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DirectConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class ConnectionMixin {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"), cancellable = true)
    public void cancelIfBlocked(ServerInfo entry, CallbackInfo ci) {
        if (!ServerBlockade.CONFIG.isServerAllowed(entry.address)) {
            Screen parent;
            // Match vanilla behavior when using direct connect and go straight to the server list
            if (client.currentScreen instanceof DirectConnectScreen) {
                parent = ((DirectConnectScreenAccessor) (client.currentScreen)).getParent();
            } else {
                parent = client.currentScreen;
            }
            Screen blockedScreen = new DisconnectedScreen(parent, Text.translatable("connect.failed"), Text.translatable("disconnect.loginFailedInfo", "ServerBlockade"));
            client.setScreen(blockedScreen);

            ci.cancel();
        }
    }
}
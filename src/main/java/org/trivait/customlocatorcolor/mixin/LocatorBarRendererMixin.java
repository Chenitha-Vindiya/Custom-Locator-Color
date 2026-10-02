package org.trivait.customlocatorcolor.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.waypoints.TrackedWaypoint;
import net.minecraft.world.waypoints.Waypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.trivait.customlocatorcolor.CustomLocatorColor;
import org.trivait.customlocatorcolor.config.Config;

import java.util.List;
import java.util.Optional;

@Mixin(TrackedWaypoint.class)
public class LocatorBarRendererMixin {

    @Inject(method = "icon", at = @At("RETURN"), cancellable = true)
    private void injectCustomColor(CallbackInfoReturnable<Waypoint.Icon> cir) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        TrackedWaypoint self = (TrackedWaypoint) (Object) this;

        String playerName = self.id().<String>map(
                uuid -> {
                    PlayerInfo info = mc.player.connection.getPlayerInfo(uuid);
                    return info != null ? info.getProfile().name() : null;
                },
                name -> name
        );

        if (playerName == null) return;

        List<Config.CustomLocator> locators = CustomLocatorColor.CONFIG.customLocators;
        for (Config.CustomLocator locator : locators) {
            if (locator.name.equalsIgnoreCase(playerName)) {
                Waypoint.Icon original = cir.getReturnValue();
                original.color = Optional.of(0xFF000000 | locator.color);
                return;
            }
        }
    }
}

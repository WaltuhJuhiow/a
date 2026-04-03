package com.autotap.mixin;

import com.autotap.AutoTapMod;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    /**
     * Fire the tap the moment the client sends an attack packet.
     * HEAD inject so the tap starts on the same tick as the hit.
     */
    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void autotap$onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        AutoTapMod.triggerTap();
    }
}

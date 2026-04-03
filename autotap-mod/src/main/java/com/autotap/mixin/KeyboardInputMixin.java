package com.autotap.mixin;

import com.autotap.AutoTapMod;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {

    @Shadow protected Vec2f movementVector;

    @Inject(method = "tick", at = @At("TAIL"))
    private void autotapOnTick(CallbackInfo ci) {
        if (AutoTapMod.wTapMode) {
            this.movementVector = new Vec2f(this.movementVector.x, 0.0f);
        } else {
            this.movementVector = new Vec2f(this.movementVector.x, -1.0f);
        }
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Jamalam360
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.jamalam360.multiblocklib.impl.mixin;

import io.github.jamalam360.multiblocklib.api.Multiblock;
import io.github.jamalam360.multiblocklib.api.MultiblockLib;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {
    private BlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * Checks if the block is a part of a multiblock, and if it is, tries to disassemble the multiblock.
     */
    @Inject(
            method = "onBreak",
            at = @At("HEAD")
    )
    public void multiblocklib$checkForMultiblockOnBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        Optional<Multiblock> multiblock = MultiblockLib.INSTANCE.getMultiblock(world, pos);
        multiblock.ifPresent(value -> MultiblockLib.INSTANCE.tryDisassembleMultiblock(value, true));
    }
}

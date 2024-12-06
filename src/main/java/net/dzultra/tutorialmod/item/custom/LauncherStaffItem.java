package net.dzultra.tutorialmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class LauncherStaffItem extends Item {
    public LauncherStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof IronGolemEntity) {
            user.addVelocity(0, 2.0, 0);
            user.velocityModified = true;
            return  ActionResult.SUCCESS;
        } else  {
            entity.addVelocity(0, 2.0, 0);
            entity.velocityModified = true;
            return  ActionResult.SUCCESS;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.tutorialmod.launcher_staff"));
    }
}

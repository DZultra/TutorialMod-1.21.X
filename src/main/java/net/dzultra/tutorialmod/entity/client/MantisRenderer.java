package net.dzultra.tutorialmod.entity.client;

import net.dzultra.tutorialmod.TutorialMod;
import net.dzultra.tutorialmod.entity.custom.MantisEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MantisRenderer extends MobEntityRenderer<MantisEntity, MantisModel<MantisEntity>> {
    public MantisRenderer(EntityRendererFactory.Context context) {
        super(context, new MantisModel<>(context.getPart(MantisModel.MANTIS)), 0.75f); // 0.75f is the shadow the entity casts
    }

    @Override
    public Identifier getTexture(MantisEntity entity) {
        return Identifier.of(TutorialMod.MOD_ID, "textures/entity/mantis/mantis.png");
    }

    @Override
    public void render(MantisEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f); // Change the sizes of the entity
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

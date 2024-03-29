package com.pouffydev.mw_core.foundation.data;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.advancement.SimpleCreateTrigger;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SimpleMilkywayTrigger extends CriterionTriggerBase<SimpleMilkywayTrigger.Instance> {
    
    public SimpleMilkywayTrigger(String id) {
        super(id);
    }
    
    @Override
    public SimpleMilkywayTrigger.Instance createInstance(JsonObject json, DeserializationContext context) {
        return new SimpleMilkywayTrigger.Instance(getId());
    }
    
    public void trigger(ServerPlayer player) {
        super.trigger(player, null);
    }
    
    public SimpleMilkywayTrigger.Instance instance() {
        return new SimpleMilkywayTrigger.Instance(getId());
    }
    
    public static class Instance extends CriterionTriggerBase.Instance {
        
        public Instance(ResourceLocation idIn) {
            super(idIn, EntityPredicate.Composite.ANY);
        }
        
        @Override
        protected boolean test(@Nullable List<Supplier<Object>> suppliers) {
            return true;
        }
    }
}

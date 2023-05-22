package com.pouffydev.mw_core.foundation.data;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MWAdvancementBehaviour extends TileEntityBehaviour {

    public static final BehaviourType<MWAdvancementBehaviour> TYPE = new BehaviourType<>();

    private UUID playerId;
    private Set<MilkywayAdvancement> advancements;

    public MWAdvancementBehaviour(SmartTileEntity te, MilkywayAdvancement... advancements) {
        super(te);
        this.advancements = new HashSet<>();
        add(advancements);
    }

    public void add(MilkywayAdvancement... advancements) {
        for (MilkywayAdvancement advancement : advancements)
            this.advancements.add(advancement);
    }

    public boolean isOwnerPresent() {
        return playerId != null;
    }

    public void setPlayer(UUID id) {
        Player player = getWorld().getPlayerByUUID(id);
        if (player == null)
            return;
        playerId = id;
        removeAwarded();
        tileEntity.setChanged();
    }

    @Override
    public void initialize() {
        super.initialize();
        removeAwarded();
    }

    private void removeAwarded() {
        Player player = getPlayer();
        if (player == null)
            return;
        advancements.removeIf(c -> c.isAlreadyAwardedTo(player));
        if (advancements.isEmpty()) {
            playerId = null;
            tileEntity.setChanged();
        }
    }

    public void awardPlayerIfNear(MilkywayAdvancement advancement, int maxDistance) {
        Player player = getPlayer();
        if (player == null)
            return;
        if (player.distanceToSqr(Vec3.atCenterOf(getPos())) > maxDistance * maxDistance)
            return;
        award(advancement, player);
    }

    public void awardPlayer(MilkywayAdvancement advancement) {
        Player player = getPlayer();
        if (player == null)
            return;
        award(advancement, player);
    }

    private void award(MilkywayAdvancement advancement, Player player) {
        if (advancements.contains(advancement))
            advancement.awardTo(player);
        removeAwarded();
    }

    private Player getPlayer() {
        if (playerId == null)
            return null;
        return getWorld().getPlayerByUUID(playerId);
    }

    @Override
    public void write(CompoundTag nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        if (playerId != null)
            nbt.putUUID("Owner", playerId);
    }

    @Override
    public void read(CompoundTag nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        if (nbt.contains("Owner"))
            playerId = nbt.getUUID("Owner");
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public static void tryAward(BlockGetter reader, BlockPos pos, MilkywayAdvancement advancement) {
        MWAdvancementBehaviour behaviour = TileEntityBehaviour.get(reader, pos, MWAdvancementBehaviour.TYPE);
        if (behaviour != null)
            behaviour.awardPlayer(advancement);
    }

    public static void setPlacedBy(Level worldIn, BlockPos pos, LivingEntity placer) {
        MWAdvancementBehaviour behaviour = TileEntityBehaviour.get(worldIn, pos, TYPE);
        if (behaviour == null)
            return;
        if (placer instanceof FakePlayer)
            return;
        if (placer instanceof ServerPlayer)
            behaviour.setPlayer(placer.getUUID());
    }

}

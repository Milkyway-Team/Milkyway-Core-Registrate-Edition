package com.pouffydev.mw_core.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TarnishedMotorScene {

    public static void motor(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("tarnished_motor", "Generating Rotational Force using Tarnished Motors");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);

        BlockPos motor = util.grid.at(3, 1, 2);

        for (int i = 0; i < 3; i++) {
            scene.idle(5);
            scene.world.showSection(util.select.position(1 + i, 1, 2), Direction.DOWN);
        }

        scene.idle(10);
        scene.effects.rotationSpeedIndicator(motor);
        scene.overlay.showText(50)
                .text("Tarnished motors are a compact and configurable source of Rotational Force with limited stress capacity")
                .placeNearTarget()
                .pointAt(util.vector.topOf(motor));
        scene.idle(50);

        scene.rotateCameraY(90);
        scene.idle(20);

        Vec3 blockSurface = util.vector.blockSurface(motor, Direction.EAST);
        AABB point = new AABB(blockSurface, blockSurface);
        AABB expanded = point.inflate(1 / 16f, 1 / 5f, 1 / 5f);

        scene.overlay.chaseBoundingBoxOutline(PonderPalette.WHITE, blockSurface, point, 1);
        scene.idle(1);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.WHITE, blockSurface, expanded, 60);
        scene.overlay.showControls(new InputWindowElement(blockSurface, Pointing.DOWN).scroll(), 60);
        scene.idle(20);

        scene.overlay.showText(50)
                .text("Unlike other sources of Rotational Force, Tarnished Motors can be manually configured to generate rotational force in any speed up to 128RPM")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(blockSurface);
        scene.idle(10);
        scene.world.modifyKineticSpeed(util.select.fromTo(1, 1, 2, 3, 1, 2), f -> 4 * f);
        scene.idle(50);

        scene.effects.rotationSpeedIndicator(motor);
        scene.rotateCameraY(-90);
    }
}

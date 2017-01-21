package com.alwex.ggj.factory;

import com.alwex.ggj.components.*;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by samsung on 21/01/2017.
 */
public class EntityFactory {
    public static EntityFactory instance = new EntityFactory();

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<SpriteComponent> spiteMapper;

    private EntityFactory() {

    }


    public Entity createFish(
            World world,
            String name,
            float x, float y, float vx, float vy,
            float width, float height
    ) {
        Entity fish = world.createEntity()
                .edit()
                .add(new FishComponent())
                .add(new PositionComponent(x, y))
                .add(new ShapeComponent(width, height))
//                .add(new SliceableComponent())
                .add(new SpriteComponent(name))
                .add(new RotationComponent(0, MathUtils.random(0.5f, 1f)))
                .add(new PhysicComponent(
                        MathUtils.random(0.5f, 1f),
                        vx, vy
                ))
                .getEntity();

        return fish;
    }

    public void createSlicedFish(World world, Entity fish) {
        ComponentMapper<PositionComponent> positionMapper = world.getMapper(PositionComponent.class);
        ComponentMapper<SpriteComponent> spriteMapper = world.getMapper(SpriteComponent.class);
        ComponentMapper<ShapeComponent> shapeMapper = world.getMapper(ShapeComponent.class);

        PositionComponent position = positionMapper.get(fish);
        SpriteComponent sprite = spriteMapper.get(fish);
        ShapeComponent shape = shapeMapper.get(fish);

        Entity leftPart = this.createFish(
                world, sprite.name + "-a",
                position.x, position.y,
                -2, 0,
                shape.width / 2f,
                shape.height / 2f
        );
        leftPart.edit()
                .add(new BleedingComponent())
                .remove(SliceableComponent.class);

        Entity rightPart = this.createFish(
                world, sprite.name + "-b",
                position.x, position.y,
                2, 0,
                shape.width / 2f,
                shape.height / 2f
        );
        rightPart.edit()
                .add(new BleedingComponent())
                .remove(SliceableComponent.class);

    }

    public Entity createBloodDrop(World world, float x, float y, float vx, float vy) {

        float size = MathUtils.random(0.1f, 0.5f);
        float red = MathUtils.random(0.5f, 1f);
        Color bloodColor = new Color(red, 0, 0, 0.4f);

        return world.createEntity().edit()
                .add(new PositionComponent(x, y))
                .add(new PhysicComponent(MathUtils.random(0.5f, 1f), vx, vy))
                .add(new BloodDropComponent(
                        size,
                        size,
                        bloodColor
                ))
                .getEntity();
    }
}
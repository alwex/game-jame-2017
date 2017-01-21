package com.alwex.ggj.systems;

import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.events.ThrowFishEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class SoundSystem extends BaseSystem {

    AssetManager assetManager;
    Music ambient;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    protected void processSystem() {

    }

    @Override
    protected void initialize() {
        ambient = assetManager.get("sounds/ambient.ogg", Music.class);
        ambient.setLooping(true);
        ambient.play();
    }

    @Subscribe
    public void onSliced(SlicedEvent event) {
        assetManager.get("sounds/splash.mp3", Sound.class).play(1, MathUtils.random(0.8f, 1f), 0);
    }

    @Subscribe
    public void onThrowFish(ThrowFishEvent event) {
        assetManager.get("sounds/water-splash.ogg", Sound.class).play(1, MathUtils.random(0.8f, 1f), 0);
    }
}

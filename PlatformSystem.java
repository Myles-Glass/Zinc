package com.zinc.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

public class PlatformSystem extends IteratingSystem{

	private ComponentMapper<PlatformComponent> platformComponentComponentMapper = ComponentMapper.getFor(PlatformComponent.class);
	
	public PlatformSystem() {
		super(Family.all(PlatformComponent.class).get());

	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		PlatformComponent platformComponent = platformComponentComponentMapper.get(entity);
		TransformComponent transformComponent = ComponentRetriever.get(entity,  TransformComponent.class);
		PhysicsBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
		
		if(platformComponent.originalPosition == null){
			platformComponent.originalPosition = new Vector2(transformComponent.x, transformComponent.y);
			platformComponent.timePassed = MathUtils.random(0,2000);
		}
		
		platformComponent.timePassed += deltaTime;
		
		Vector2 newPosition = new Vector2();
		newPosition.x = physicsBodyComponent.body.getPosition().x; //...ody.getPosition().x + 0.01f
		newPosition.y = (platformComponent.originalPosition.y + MathUtils.cos(platformComponent.timePassed + 1 * MathUtils.degreesToRadians * 40f) * 20f) * PhysicsBodyLoader.getScale();//this line isnt working properly, its not updating
		physicsBodyComponent.body.setTransform(newPosition, physicsBodyComponent.body.getAngle());		
		//transformComponent.y = platformComponent.originalPosition.y + MathUtils.cos(platformComponent.timePassed * MathUtils.degreesToRadians) * 100f;
	}
}

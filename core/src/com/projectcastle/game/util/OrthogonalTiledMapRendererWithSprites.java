package com.projectcastle.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by ernestogonzalezchacon on 24/8/17.
 */

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    SpriteBatch spriteBatch = new SpriteBatch();


    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
    }

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            spriteBatch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
        }
    }
}

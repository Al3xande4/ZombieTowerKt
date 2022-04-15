package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.ScreenUtils

class MainMenuScreen(var game: ZombieTower): Screen {

    lateinit var camera: OrthographicCamera

    var w: Float = Gdx.graphics.width * 1f
    var h: Float = Gdx.graphics.height * 1f

    init{
        camera = OrthographicCamera(w, h)

        camera.update()
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, .2f, 0f)

        game.spriteBatch.begin()
        game.font.draw(game.spriteBatch, "Zombie Tower", w / 2 - 7 * 5, h / 2 + 15)
        game.font.draw(game.spriteBatch, "Tap to play", w / 2 - 5 * 5.5f, h / 2 - 15)
        game.spriteBatch.end()

        if(Gdx.input.isTouched)
            game.screen = GameScreen(game)
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }
}
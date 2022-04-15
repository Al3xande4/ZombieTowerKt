package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeType

class ZombieTower: Game() {
    lateinit var spriteBatch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        spriteBatch = SpriteBatch()
        font = BitmapFont()
        this.setScreen(MainMenuScreen(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        spriteBatch.dispose()
        font.dispose()
    }
}
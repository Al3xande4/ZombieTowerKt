package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.viewport.StretchViewport

class GameScreen(var game: ZombieTower) : Screen {
    val WORLD_WIDTH = 70
    val WORLD_HEIGHT = 70

    var camera: OrthographicCamera? = null
    var touchpad: Touchpad? = null

    var sprite: Sprite? = null

    var stage: Stage? = null
    var touchpadSkin: Skin? = null

    var touchpadStyle: Touchpad.TouchpadStyle? = null
    var cameraMovementSpeed = .3f

    private var touchBackground: Drawable? = null
    private var touchKnob: Drawable? = null

    var blockTexture: Texture? = null
    var blockSpeed = .3f
    var blockSprite: Sprite? = null

    init{
        sprite = Sprite(Texture(Gdx.files.internal("Level1.png")))
        sprite!!.setPosition(0f, 0f)
        sprite!!.setSize(
            WORLD_WIDTH.toFloat(),
            WORLD_HEIGHT.toFloat()
        )

        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera(30f, 30 * (h / w))
        camera!!.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f)
        camera!!.update()

        stage = Stage()
        Gdx.input.inputProcessor = stage

        touchpadSkin = Skin()

        //Set background image
        touchpadSkin!!.add("touchBackground", Texture("trans.png"))

        //Set knob image
        touchpadSkin!!.add("touchKnob", Texture("img_2.png"))

        //Create TouchPad Style
        touchpadStyle = Touchpad.TouchpadStyle()

        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin!!.getDrawable("touchBackground")
        touchKnob = touchpadSkin!!.getDrawable("touchKnob")

        //Apply the Drawables to the TouchPad Style
        touchpadStyle!!.background = touchBackground
        touchpadStyle!!.knob = touchKnob

        //Create new TouchPad with the created style
        touchpad = Touchpad(10f, touchpadStyle)

        //setBounds(x,y,width,height)
        touchpad!!.setBounds(15f, 15f, 200f, 200f)

        //Create a Stage and add TouchPad
        stage = Stage(
            StretchViewport(
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat()
            )
        )
        stage!!.addActor(touchpad)
        Gdx.input.inputProcessor = stage

        //Create block sprite
        blockTexture = Texture(Gdx.files.internal("img_3.png"))
        blockSprite = Sprite(blockTexture)
        blockSprite!!.setSize(2f, 2f)

        //Set position to centre of the screen
        blockSprite!!.setPosition(camera!!.position.x, camera!!.position.y)
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        handleInput()
        camera?.update()

        game.spriteBatch.projectionMatrix = camera!!.combined
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        blockSprite?.setPosition(blockSprite!!.x + touchpad!!.knobPercentX * blockSpeed, blockSprite!!.y + touchpad!!.knobPercentY * blockSpeed)
        game.spriteBatch.begin()
        sprite?.draw(game.spriteBatch)
        blockSprite?.draw(game.spriteBatch)
        game.spriteBatch.end()

        stage?.act(Gdx.graphics.deltaTime)
        stage?.draw()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera!!.zoom += 0.02f
            //If the A Key is pressed, add 0.02 to the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera!!.zoom -= 0.02f
            //If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera!!.translate(-.5f * camera!!.zoom, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera!!.translate(.5f * camera!!.zoom, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera!!.translate(0f, -.5f * camera!!.zoom, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera!!.translate(0f, .5f * camera!!.zoom, 0f)
        }
        camera!!.translate(
            touchpad!!.knobPercentX * cameraMovementSpeed * camera!!.zoom,
            touchpad!!.knobPercentY * camera!!.zoom * cameraMovementSpeed,
            0f
        )
        camera!!.zoom = MathUtils.clamp(camera!!.zoom, .5f, 1f)
        val effectiveViewportWidth = camera!!.viewportWidth * camera!!.zoom
        val effectiveViewportHeight = camera!!.viewportHeight * camera!!.zoom
        camera!!.position.x = MathUtils.clamp(
            camera!!.position.x,
            effectiveViewportWidth / 2f,
            50f - effectiveViewportWidth / 2f
        )
        camera!!.position.y = MathUtils.clamp(camera!!.position.y, 30f, 45f)
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
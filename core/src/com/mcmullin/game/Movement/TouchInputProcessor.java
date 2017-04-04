package com.mcmullin.game.Movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jared on 3/22/2017.
 */

public class TouchInputProcessor implements InputProcessor {

    private Map<Integer, TouchInput> inputs = new HashMap<Integer, TouchInput>();

    //populates map to handle two pointers (2 fingers)
    public TouchInputProcessor () {
        for(int i = 0; i < 2; i++) { //two touches, 0 & 1
            inputs.put(i, new TouchInput());
        }
    }

    @Override public boolean mouseMoved (int screenX, int screenY) {
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(pointer < 2) {
            inputs.get(pointer).setX(screenX);
            inputs.get(pointer).setY(screenY);
            inputs.get(pointer).setTouched(true);
        }
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        return false;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if(pointer <= 1) {
            inputs.get(pointer).setX(screenX);
            inputs.get(pointer).setY(screenY);
            inputs.get(pointer).setTouched(false);
        }
        return true;
    }

    //getter function to check if a requested pointer is currently touched
    public boolean isTouched(int pointer) {
        return inputs.get(pointer).isTouched();
    }

    //getter to check x value of requested pointer
    public float inputX(int pointer) {
        return inputs.get(pointer).getX();
    }

    //getter to check y value of requested pointer
    public float inputY(int pointer) {
        return inputs.get(pointer).getY();
    }

    //returns the index of first input marked as touched
    public int curTouched() {
        for (Map.Entry<Integer, TouchInput> item: inputs.entrySet()) {
            if(item.getValue().isTouched())
                return item.getKey();
        }
        return -1;
    }

    //true if any input is currently pressed
    public boolean touching () {
        for (Map.Entry<Integer, TouchInput> item: inputs.entrySet()) {
            if(item.getValue().isTouched())
                return true;
        }
        return false;
    }

    @Override public boolean keyDown (int keycode) {
        return false;
    }

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

    @Override public boolean scrolled (int amount) {
        return false;
    }
}

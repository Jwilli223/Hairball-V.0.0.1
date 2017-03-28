package com.mcmullin.game.Movement;

/**
 * Created by Produit on 3/28/2017.
 */

public class TouchInput {
    private float touchX, touchY;
    private boolean touched;

    public TouchInput() {
        this(0, 0, false);
    }

    public TouchInput(float touchX, float touchY, boolean touched) {
        this.touchX = touchX;
        this.touchY = touchY;
        this.touched = touched;
    }

    public float getX() {
        return touchX;
    }

    public float getY() {
        return touchY;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setX(float touchX) {
        this.touchX = touchX;
    }

    public void setY(float touchY) {
        this.touchY = touchY;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}

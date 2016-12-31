package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by phcur on 30/12/2016.
 */

public class ButtonPlayer extends Button {
    private int rolePosition;
    public ButtonPlayer(Drawable up) {
        super(up);
    }

    public int getRolePosition() {
        return rolePosition;
    }

    public void setRolePosition(int rolePosition) {
        this.rolePosition = rolePosition;
    }
}

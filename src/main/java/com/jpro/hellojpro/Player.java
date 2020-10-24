package com.jpro.hellojpro;

import javafx.application.Platform;

public class Player {
    private int notes_played;

    public Player() {
        this.notes_played = 0;
        this.notes_played = 0;
    }

    public void addNotes_played() {
        this.notes_played++;
        updateBar();
    }

    public void addNotes_missed() {
        this.notes_missed++;
        updateBar();
    }

    private int notes_missed;

    private void updateBar() {
        Platform.runLater(() -> MainFXML.controller.sg_bar.setProgress((double) notes_played / (notes_played + notes_missed)));
    }
}

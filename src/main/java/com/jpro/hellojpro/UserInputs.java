package com.jpro.hellojpro;

import java.io.Serializable;
import java.util.*;
public class UserInputs implements Serializable {
    //Input Configurations
    private int i_BPM;
    private final ArrayList<String> i_AllowedDurations = new ArrayList<>();
    private final ArrayList<String> i_AllowedNotes = new ArrayList<>();
    private int i_MaxJumpAllowed;
    private String i_LowNoteName;
    private String i_HighNoteName;
    private String sharpPreference;
    private String i_key;
    private String i_midi;

    public String getI_midi() {
        return i_midi;
    }

    public void setI_midi(String i_midi) {
        this.i_midi = i_midi;
    }

    public String getI_clef() {
        return i_clef;
    }

    public void setI_clef(String i_clef) {
        this.i_clef = i_clef;
    }

    private String i_clef;

    public String getI_key() {
        return i_key;
    }

    public void setI_key(String i_key) {
        this.i_key = i_key;
    }

    public String getSharpPreference() {return sharpPreference;}
    public void setSharpPreference(String sharpPreference) {this.sharpPreference = sharpPreference;}

    public int getI_BPM() {
        return i_BPM;
    }

    public ArrayList<String> getI_AllowedDurations() {
        return i_AllowedDurations;
    }

    public ArrayList<String> getI_AllowedNotes() {
        return i_AllowedNotes;
    }

    public int getI_MaxJumpAllowed() {
        return i_MaxJumpAllowed;
    }

    public String getI_LowNoteName() {
        return i_LowNoteName;
    }

    public String getI_HighNoteName() {
        return i_HighNoteName;
    }

    public void setI_BPM(int i_BPM) {
        this.i_BPM = i_BPM;
    }

    public void addI_AllowedDurations(String i_AllowedDuration) {
        this.i_AllowedDurations.add(i_AllowedDuration);
    }
    public void clearI_AllowedDurations() {this.i_AllowedDurations.clear();}

    public void addI_AllowedNotes(String i_AllowedNote) {
        this.i_AllowedNotes.add(i_AllowedNote);
    }
    public void clearI_AllowedNotes() {this.i_AllowedNotes.clear();}

    public void setI_MaxJumpAllowed(int i_MaxJumpAllowed) {
        this.i_MaxJumpAllowed = i_MaxJumpAllowed;
    }

    public void setI_LowNoteName(String i_LowNoteName) {
        this.i_LowNoteName = i_LowNoteName;
    }

    public void setI_HighNoteName(String i_HighNoteName) {
        this.i_HighNoteName = i_HighNoteName;
    }
}

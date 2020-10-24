package com.jpro.hellojpro;

import java.util.ArrayList;
import java.util.HashMap;

public class Keyboard {

    //Keyboard Configuration
    private final int NOTE_RANGE;
    private final int INITIAL_OCTAVE;
    private final int INITIAL_NOTE;
    //Basic Data
    public final String[] SCALE;
    public final static HashMap<String, Double> DurationsMAP = new HashMap<>();

    public final ArrayList<String> NOTE_NAMES = new ArrayList<>();

    public Keyboard(int NOTE_RANGE, int INITIAL_OCTAVE, int INITIAL_NOTE, boolean SHARP_PREFERRED){
        this.NOTE_RANGE = NOTE_RANGE;
        this.INITIAL_OCTAVE = INITIAL_OCTAVE;
        this.INITIAL_NOTE = INITIAL_NOTE;
        if (SHARP_PREFERRED) {
            SCALE = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        }else{
            SCALE = new String[]{"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
        }
        DurationsMAP.put("Whole", 4.); DurationsMAP.put("Half", 2.); 	DurationsMAP.put("HalfP", 3.);
        DurationsMAP.put("Quarter", 1.); DurationsMAP.put("QuarterP", 1.5);	DurationsMAP.put("Eighth", 0.5);
        DurationsMAP.put("EighthP", 0.75); DurationsMAP.put("Sixteenth", 0.25);	DurationsMAP.put("SixteenthP", 0.375);

        //Initialization
        int octave = this.INITIAL_OCTAVE;
        int n_note = this.INITIAL_NOTE;
        for (int i = 0; i < this.NOTE_RANGE ; i++) {
            this.NOTE_NAMES.add(SCALE[n_note] + octave);
            ++n_note;
            if (n_note==12) {
                ++octave;
                n_note = 0;
            }
        }

    }
    public int getNOTE_RANGE() {return NOTE_RANGE;}
    // --Commented out by Inspection (5/6/2020 12:35 PM):public int getINITIAL_OCTAVE() {return INITIAL_OCTAVE;}
    public int getINITIAL_NOTE() {return INITIAL_NOTE;}
    public ArrayList<String> getNOTE_NAMES() {return NOTE_NAMES;}
}

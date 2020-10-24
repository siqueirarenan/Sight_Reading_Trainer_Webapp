package com.jpro.hellojpro;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;

public class PlayNotes implements Runnable {

    public static final ArrayList<Note> notes_groups = new ArrayList<>();
    public static final ArrayList<Note> catchable_Notes = new ArrayList<>();
    public static final ArrayList<String> catchable_noteNames = new ArrayList<>();

    public void run() {
        notes_groups.clear();
        int NotYetCatchable_note = 0;

        //Generate random notes
        Random rand = new Random();
        ArrayList<Integer> AllowedNotes4Jump = new ArrayList<>();
        int rand_value = MainFXML.ALLOWED_NOTE_VALUES.get(rand.nextInt(MainFXML.ALLOWED_NOTE_VALUES.size()));
        int rand_duration;
        double freq;
        double time_i;
        while (MainFXML.controller.playON == 1) {
            AllowedNotes4Jump.clear();
            for (Integer i : MainFXML.ALLOWED_NOTE_VALUES) {
                if (Math.abs(rand_value - i) <= MainFXML.user.getI_MaxJumpAllowed()) {
                    AllowedNotes4Jump.add(i);
                }
            }
            rand_value = AllowedNotes4Jump.get(rand.nextInt(AllowedNotes4Jump.size()));

            rand_duration = rand.nextInt(MainFXML.user.getI_AllowedDurations().size());
            Note a = new Note();
            a.setValue(MainFXML.controller.piano, rand_value);
            // Object[] DURATIONS = DurationsMAP.keySet().toArray();
            a.setDuration(MainFXML.controller.piano, MainFXML.user.getI_AllowedDurations().get(rand_duration));

            //System.out.println(a.getNote_name() + " " + a.getDuration_name());

            try {
                notes_groups.add(a.Generate());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //Moving notes while waiting for the next one
            freq = 1 / (100 * (double) MainFXML.user.getI_BPM() / (60 * 1000));
            time_i = System.nanoTime();
            while ((System.nanoTime() - time_i) / 1e6 < 1000. * a.getDuration_time() * 60. / MainFXML.user.getI_BPM()) {
                for (Note i : notes_groups) {
                    int gXL = (int) i.imageGroup.getLayoutX();
                    if (gXL > 5) {
                        Platform.runLater(() -> i.imageGroup.setLayoutX(gXL - 3));
                    }
                }
                if (notes_groups.get(NotYetCatchable_note).imageGroup.getLayoutX() < 320) {
                    catchable_Notes.add(notes_groups.get(NotYetCatchable_note));
                    catchable_noteNames.add(notes_groups.get(NotYetCatchable_note).getNote_name());
                    NotYetCatchable_note++;
                }
                if (catchable_Notes.size() > 0 && catchable_Notes.get(0).imageGroup.getLayoutX() < 240) {
                    catchable_Notes.remove(0);
                    catchable_noteNames.remove(0);
                    MainFXML.player.addNotes_missed();
                }

                try {
                    Thread.sleep((int) freq * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

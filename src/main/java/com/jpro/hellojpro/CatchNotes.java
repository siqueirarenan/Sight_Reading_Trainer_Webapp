package com.jpro.hellojpro;

import org.jfugue.devices.MusicTransmitterToParserListener;
import org.jfugue.parser.ParserListener;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;


public class CatchNotes implements Runnable {

    MidiDevice device;
    public static MusicTransmitterToParserListener transmitter;
    String note_pressed;
    MidiDevice.Info[] inputDevice;

    @Override
    public void run() {

        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            inputDevice = MainFXML.controller.inputDevice;
            device = MidiSystem.getMidiDevice(inputDevice[
                    MainFXML.controller.midi_List.indexOf(MainFXML.controller.sg_midi.getValue())]);

            transmitter = new MusicTransmitterToParserListener(device);

            //System.out.println(device.getDeviceInfo().getName());
            transmitter.addParserListener(new ParserListener() {
                @Override
                public void beforeParsingStarts() {

                }

                @Override
                public void afterParsingFinished() {

                }

                @Override
                public void onTrackChanged(byte b) {

                }

                @Override
                public void onLayerChanged(byte b) {

                }

                @Override
                public void onInstrumentParsed(byte b) {

                }

                @Override
                public void onTempoChanged(int i) {

                }

                @Override
                public void onKeySignatureParsed(byte b, byte b1) {

                }

                @Override
                public void onTimeSignatureParsed(byte b, byte b1) {

                }

                @Override
                public void onBarLineParsed(long l) {

                }

                @Override
                public void onTrackBeatTimeBookmarked(String s) {

                }

                @Override
                public void onTrackBeatTimeBookmarkRequested(String s) {

                }

                @Override
                public void onTrackBeatTimeRequested(double v) {

                }

                @Override
                public void onPitchWheelParsed(byte b, byte b1) {

                }

                @Override
                public void onChannelPressureParsed(byte b) {
                }

                @Override
                public void onPolyphonicPressureParsed(byte b, byte b1) {

                }

                @Override
                public void onSystemExclusiveParsed(byte... bytes) {

                }

                @Override
                public void onControllerEventParsed(byte b, byte b1) {

                }

                @Override
                public void onLyricParsed(String s) {
                }

                @Override
                public void onMarkerParsed(String s) {

                }

                @Override
                public void onFunctionParsed(String s, Object o) {

                }

                @Override
                public void onNotePressed(Note note) {
                    note_pressed = getGeneralNoteName(note.getToneString()) + (note.getOctave() - 1);
                    //System.out.println(note_pressed);
                    if (PlayNotes.catchable_noteNames.contains(note_pressed)) {
                        PlayNotes.catchable_Notes.get(PlayNotes.catchable_noteNames.indexOf(
                                note_pressed)).imageGroup.setVisible(false);
                        PlayNotes.catchable_Notes.remove(PlayNotes.catchable_noteNames.indexOf(note_pressed));
                        PlayNotes.catchable_noteNames.remove(note_pressed);
                        MainFXML.player.addNotes_played();
                    }
                }

                @Override
                public void onNoteReleased(Note note) {

                }

                @Override
                public void onNoteParsed(Note note) {
                }

                @Override
                public void onChordParsed(Chord chord) {
                }


            });

            transmitter.startListening();


        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }

    public String getGeneralNoteName(String inputNote) {
        if (inputNote.length() > 1) {
            switch (MainFXML.user.getSharpPreference()) {
                case "Sharp":
                    switch (inputNote) {
                        case "Eb":
                            return "D#";
                        case "Bb":
                            return "A#";
                        default:
                            return inputNote;
                    }
                case "Flat":
                    switch (inputNote) {
                        case "C#":
                            return "Db";
                        case "F#":
                            return "Gb";
                        case "G#":
                            return "Ab";
                        default:
                            return inputNote;
                    }
                default:
                    return inputNote;
            }
        } else {
            return inputNote;
        }
    }

}

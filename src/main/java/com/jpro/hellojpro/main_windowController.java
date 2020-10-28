package com.jpro.hellojpro;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//import javax.sound.midi.*;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.io.*;
import java.net.URL;
import java.util.*;

public class main_windowController implements Initializable {

    @FXML
    public Pane main_pane;
    @FXML
    public Button play_button;
    @FXML
    public Pane anchorPane;
    @FXML
    public ProgressBar sg_bar;

    public Keyboard piano;
    public Signature signature;
    public int playON = 0;
    static Group key_group;

    @FXML
    public void play_button_pressed() throws IOException {
        Thread t1 = new Thread(new PlayNotes());
        Thread t2 = new Thread(new CatchNotes());
        if (playON == 0) {
            MainFXML.user.clearI_AllowedDurations();
            if (sg_Whole.isSelected()) {
                MainFXML.user.addI_AllowedDurations("Whole");
            }
            if (sg_Half.isSelected()) {
                MainFXML.user.addI_AllowedDurations("Half");
            }
            if (sg_Quarter.isSelected()) {
                MainFXML.user.addI_AllowedDurations("Quarter");
            }
            if (sg_Eighth.isSelected()) {
                MainFXML.user.addI_AllowedDurations("Eighth");
            }

            MainFXML.user.clearI_AllowedNotes();
            if (sg_C.isSelected()) {
                MainFXML.user.addI_AllowedNotes("C");
            }
            if (sg_Db.isSelected()) {
                MainFXML.user.addI_AllowedNotes("Db");
                MainFXML.user.addI_AllowedNotes("C#");
            }
            if (sg_D.isSelected()) {
                MainFXML.user.addI_AllowedNotes("D");
            }
            if (sg_Eb.isSelected()) {
                MainFXML.user.addI_AllowedNotes("Eb");
                MainFXML.user.addI_AllowedNotes("D#");
            }
            if (sg_E.isSelected()) {
                MainFXML.user.addI_AllowedNotes("E");
            }
            if (sg_F.isSelected()) {
                MainFXML.user.addI_AllowedNotes("F");
            }
            if (sg_Gb.isSelected()) {
                MainFXML.user.addI_AllowedNotes("Gb");
                MainFXML.user.addI_AllowedNotes("F#");
            }
            if (sg_G.isSelected()) {
                MainFXML.user.addI_AllowedNotes("G");
            }
            if (sg_Ab.isSelected()) {
                MainFXML.user.addI_AllowedNotes("Ab");
                MainFXML.user.addI_AllowedNotes("G#");
            }
            if (sg_A.isSelected()) {
                MainFXML.user.addI_AllowedNotes("A");
            }
            if (sg_Bb.isSelected()) {
                MainFXML.user.addI_AllowedNotes("Bb");
                MainFXML.user.addI_AllowedNotes("A#");
            }
            if (sg_B.isSelected()) {
                MainFXML.user.addI_AllowedNotes("B");
            }

            MainFXML.user.setI_BPM(sg_BPM.getValue());
            MainFXML.user.setI_MaxJumpAllowed(mI_List.indexOf(sg_maxInt.getValue()));
            MainFXML.user.setI_LowNoteName(sg_LowNote.getValue());
            MainFXML.user.setI_HighNoteName(sg_HighNote.getValue());
            MainFXML.user.setSharpPreference(sg_sharp.getValue());
            MainFXML.user.setI_key(sg_key.getValue());
            MainFXML.user.setI_clef(sg_clef.getValue());
            MainFXML.user.setI_midi(sg_midi.getValue());

            //No saving users configuration in the web app
/*            String fileName = "UserInputs.srmr";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(MainFXML.user);
            oos.close();*/

            piano = new Keyboard(88, 0,
                    9, MainFXML.user.getSharpPreference().equals("Sharp"));

            signature = new Signature(MainFXML.user.getI_clef(), MainFXML.user.getI_key());
            key_group = signature.addSignature();
            this.main_pane.getChildren().add(key_group);

            //Defining group of note_vales allowed
            int LowNote = Note.NoteToValue(MainFXML.piano, MainFXML.user.getI_LowNoteName());
            int HighNote = Note.NoteToValue(MainFXML.piano, MainFXML.user.getI_HighNoteName());

            MainFXML.ALLOWED_NOTE_VALUES.clear();
            int n_note = piano.getINITIAL_NOTE();
            for (int i = 0; i < piano.getNOTE_RANGE(); i++) {
                if (MainFXML.user.getI_AllowedNotes().contains(piano.SCALE[n_note]) && i >= LowNote && i <= HighNote) {
                    MainFXML.ALLOWED_NOTE_VALUES.add(i);   //Only the ones in the specified range and scale are added
                }
                ++n_note;
                if (n_note == 12) {
                    n_note = 0;
                }
            }

            playON = 1;
            t1.start();
            t2.start();
        } else {
            playON = 0;
            //LibUsb.exit(null);
            t1.interrupt();
            t2.interrupt();
            try {
                CatchNotes.transmitter.stopListening();
            } catch (Exception ignored) {
            }
            for (Note i : PlayNotes.notes_groups) {
                MainFXML.controller.main_pane.getChildren().remove(i.imageGroup);
            }
            MainFXML.controller.main_pane.getChildren().remove(key_group);
        }
    }

    public MainFXML mainApp;

    public void setMainApp(MainFXML mainApp) {
        this.mainApp = mainApp;
    }

    public Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene main_scene;

    public void setScene(Scene main_scene) {
        this.main_scene = main_scene;
    }

    //SETTINGS------------------------------------------------------------------------------
    @FXML
    public Spinner<Integer> sg_BPM;
    @FXML
    public CheckBox sg_Whole;
    @FXML
    public CheckBox sg_Half;
    @FXML
    public CheckBox sg_Quarter;
    @FXML
    public CheckBox sg_Eighth;
    @FXML
    public CheckBox sg_C;
    @FXML
    public CheckBox sg_Db;
    @FXML
    public CheckBox sg_D;
    @FXML
    public CheckBox sg_Eb;
    @FXML
    public CheckBox sg_E;
    @FXML
    public CheckBox sg_F;
    @FXML
    public CheckBox sg_Gb;
    @FXML
    public CheckBox sg_G;
    @FXML
    public CheckBox sg_Ab;
    @FXML
    public CheckBox sg_A;
    @FXML
    public CheckBox sg_Bb;
    @FXML
    public CheckBox sg_B;
    @FXML
    public Spinner<String> sg_maxInt;
    final List<String> mI_List = Arrays.asList("b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7",
            "7", "8", "b9", "9", "b10", "10", "11");
    @FXML
    public Spinner<String> sg_LowNote;
    @FXML
    public Spinner<String> sg_HighNote;
    @FXML
    public Spinner<String> sg_sharp;
    final List<String> sharp_List = Arrays.asList("Sharp", "Flat");
    @FXML
    public Spinner<String> sg_key;
    final List<String> key_List = Arrays.asList(Signature.possible_keys);
    @FXML
    public Spinner<String> sg_clef;
    final List<String> clef_List = Arrays.asList("Tremble", "Bass");
    @FXML
    public ChoiceBox<String> sg_midi;
    public final ArrayList<String> midi_List = new ArrayList<>();
    public MidiDevice.Info[] inputDevice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Synthesizer synthesizer;
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            inputDevice = MidiSystem.getMidiDeviceInfo();
            for (MidiDevice.Info i : inputDevice) {
                midi_List.add(i.getName() + " - " + i.getVendor() + " - " + i.getDescription());
            }
        } catch (MidiUnavailableException e) {
            midi_List.add("Midi Instrument Unavailable");
            e.printStackTrace();
        }


        try {
            String fileName = "UserInputs.srmr";
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            UserInputs user = (UserInputs) ois.readObject();
            ois.close();

            sg_BPM.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 180));
            sg_BPM.getValueFactory().setValue(user.getI_BPM());

            if (user.getI_AllowedDurations().contains(("Whole"))) {
                sg_Whole.setSelected(true);
            }
            if (user.getI_AllowedDurations().contains(("Half"))) {
                sg_Half.setSelected(true);
            }
            if (user.getI_AllowedDurations().contains(("Quarter"))) {
                sg_Quarter.setSelected(true);
            }
            if (user.getI_AllowedDurations().contains(("Eighth"))) {
                sg_Eighth.setSelected(true);
            }

            if (user.getI_AllowedNotes().contains(("C"))) {
                sg_C.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("Db"))) {
                sg_Db.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("D"))) {
                sg_D.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("Eb"))) {
                sg_Eb.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("E"))) {
                sg_E.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("F"))) {
                sg_F.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("Gb"))) {
                sg_Gb.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("G"))) {
                sg_G.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("Ab"))) {
                sg_Ab.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("A"))) {
                sg_A.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("Bb"))) {
                sg_Bb.setSelected(true);
            }
            if (user.getI_AllowedNotes().contains(("B"))) {
                sg_B.setSelected(true);
            }

            sg_maxInt.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(mI_List)));
            sg_maxInt.getValueFactory().setValue(mI_List.get(user.getI_MaxJumpAllowed()));

            sg_LowNote.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(MainFXML.piano.NOTE_NAMES)));
            sg_LowNote.getValueFactory().setValue(user.getI_LowNoteName());

            sg_HighNote.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(MainFXML.piano.NOTE_NAMES)));
            sg_HighNote.getValueFactory().setValue(user.getI_HighNoteName());

            sg_sharp.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(sharp_List)));
            sg_sharp.getValueFactory().setValue(user.getSharpPreference());

            sg_key.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(key_List)));
            sg_key.getValueFactory().setValue(user.getI_key());

            sg_clef.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(clef_List)));
            sg_clef.getValueFactory().setValue(user.getI_clef());

            sg_midi.setItems(FXCollections.observableArrayList(midi_List));
            sg_midi.setValue(user.getI_midi());

        } catch (Exception e) {

            sg_BPM.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 180));
            sg_BPM.getValueFactory().setValue(80);

            sg_Whole.setSelected(true);
            sg_Half.setSelected(true);
            sg_Quarter.setSelected(true);
            sg_Eighth.setSelected(true);
            sg_C.setSelected(true);
            sg_Db.setSelected(true);
            sg_D.setSelected(true);
            sg_Eb.setSelected(true);
            sg_E.setSelected(true);
            sg_F.setSelected(true);
            sg_Gb.setSelected(true);
            sg_G.setSelected(true);
            sg_Ab.setSelected(true);
            sg_A.setSelected(true);
            sg_Bb.setSelected(true);
            sg_B.setSelected(true);


            sg_maxInt.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(mI_List)));
            sg_maxInt.getValueFactory().setValue("5");

            sg_LowNote.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(MainFXML.piano.NOTE_NAMES)));
            sg_LowNote.getValueFactory().setValue("C3");

            sg_HighNote.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(MainFXML.piano.NOTE_NAMES)));
            sg_HighNote.getValueFactory().setValue("C6");

            sg_sharp.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(sharp_List)));
            sg_sharp.getValueFactory().setValue("Sharp");

            sg_key.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(key_List)));
            sg_key.getValueFactory().setValue("C");

            sg_clef.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                    FXCollections.observableArrayList(clef_List)));
            sg_clef.getValueFactory().setValue("Tremble");

            sg_midi.setItems(FXCollections.observableArrayList(midi_List));
        }
    }
}

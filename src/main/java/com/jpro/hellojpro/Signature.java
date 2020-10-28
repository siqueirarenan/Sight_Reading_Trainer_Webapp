package com.jpro.hellojpro;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Signature {

    final static String[] possible_keys = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
    public List<String> accidents;
    public List<String> need_natural;
    public final String clef;
    public int clef_switch;

    public Signature(String clef, String key) {
        this.clef = clef;
        switch (key) {
            case "C":
                accidents = Collections.singletonList("nothing");
                need_natural = Collections.singletonList("nothing");
                break;
            case "Db":
                accidents = Arrays.asList("Bb", "Eb", "Ab", "Db", "Gb");
                need_natural = Arrays.asList("B", "E", "A", "D", "G");
                break;
            case "D":
                accidents = Arrays.asList("F#", "C#");
                need_natural = Arrays.asList("F", "C");
                break;
            case "Eb":
                accidents = Arrays.asList("Bb", "Eb", "Ab");
                need_natural = Arrays.asList("B", "E", "A");
                break;
            case "E":
                accidents = Arrays.asList("F#", "C#", "G#", "D#");
                need_natural = Arrays.asList("F", "C", "G", "D");
                break;
            case "F":
                accidents = Collections.singletonList("Bb");
                need_natural = Collections.singletonList("B");
                break;
            case "Gb":
                accidents = Arrays.asList("Bb", "Eb", "Ab", "Db", "Gb", "Cb");
                need_natural = Arrays.asList("B", "E", "A", "D", "G", "C");
                break;
            case "G":
                accidents = Collections.singletonList("F#");
                need_natural = Collections.singletonList("F");
                break;
            case "Ab":
                accidents = Arrays.asList("Bb", "Eb", "Ab", "Db");
                need_natural = Arrays.asList("B", "E", "A", "D");
                break;
            case "A":
                accidents = Arrays.asList("F#", "C#", "G#");
                need_natural = Arrays.asList("F", "C", "G");
                break;
            case "Bb":
                accidents = Arrays.asList("Bb", "Eb");
                need_natural = Arrays.asList("B", "E");
                break;
            case "B":
                accidents = Arrays.asList("F#", "C#", "G#", "D#", "A#");
                need_natural = Arrays.asList("F", "C", "G", "D", "A");
                break;
        }
    }

    public Group addSignature() throws FileNotFoundException {
        Image F_path = new Image(new FileInputStream("/pics/flat.png"));
        Image S_path = new Image(new FileInputStream("/pics/sharp.png"));
        Image E_path = new Image(new FileInputStream("/pics/empty.png"));
        ImageView FS_imageView;
        Group group = new Group();
        int offset_X = 0;
        int offset_2;
        int offsetY_clef = 0;
        if (this.clef.equals("Bass")){
            offsetY_clef = 20;
        }
        for (String i : this.accidents) {
            if (i.charAt(1) == '#') {
                FS_imageView = new ImageView(S_path);
                offset_2 = 20;
            } else if (i.charAt(1) == 'b') {
                FS_imageView = new ImageView(F_path);
                offset_2 = 15;
            } else {
                FS_imageView = new ImageView(E_path);
                offset_2 = 15;
            }
            FS_imageView.setFitHeight(62);
            FS_imageView.setPreserveRatio(true);
            switch (i.charAt(0)) {
                case 'C':
                    FS_imageView.setY(0);
                    break;
                case 'D':
                    FS_imageView.setY(-10);
                    break;
                case 'E':
                    FS_imageView.setY(-20);
                    break;
                case 'F':
                    FS_imageView.setY(-30);
                    break;
                case 'G':
                    if (i.charAt(1) == '#') {
                        FS_imageView.setY(-40);
                    } else {
                        FS_imageView.setY(30);
                    }
                    break;
                case 'A':
                    FS_imageView.setY(20);
                    break;
                case 'B':
                    FS_imageView.setY(10);
                    break;
            }
            FS_imageView.setX(offset_X);
            offset_X = offset_X + offset_2;
            FS_imageView.setY(FS_imageView.getY() + offsetY_clef);
            group.getChildren().add(FS_imageView);
        }

        if (this.clef.equals("Tremble")) {
            Image C_path = new Image(new FileInputStream("/pics/G-clef.png"));
            ImageView C_imageView = new ImageView(C_path);
            C_imageView.setFitHeight(176);
            C_imageView.setPreserveRatio(true);
            C_imageView.setY(-45);
            C_imageView.setX(-60);
            group.getChildren().add(C_imageView);
            clef_switch = 0;
        } else if (this.clef.equals("Bass")) {
            Image C_path = new Image(new FileInputStream("/pics/F-clef.png"));
            ImageView C_imageView = new ImageView(C_path);
            C_imageView.setPreserveRatio(true);
            C_imageView.setFitHeight(70);
            C_imageView.setY(0);
            C_imageView.setX(-40);
            group.getChildren().add(C_imageView);
            clef_switch = 120;
        }

        group.setLayoutX(50);
        group.setLayoutY(115);
        return group;
    }

}
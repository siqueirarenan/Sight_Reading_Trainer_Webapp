package com.jpro.hellojpro;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Note {
		
	private int value;          //0..NOTE_RANGE
	private String note_name;
	private String duration_name;       //tempo
	private double duration_time;
	public Group imageGroup;
	private int catchable=0;

	public Note(){
	}
	public Note(Keyboard piano, int value, String duration_name){
		this.setValue(piano, value);
		this.setDuration(piano, duration_name);
	}

	// --Commented out by Inspection (5/6/2020 12:3// --Commented out by Inspection (5/6/2020 12:35 PM):5 PM):public void setCatchable(){this.catchable=1;}
	public void setWasCatchable(){this.catchable=2;}
	// --Commented out by Inspection (5/6/2020 12:35 PM):// --Commented out by Inspection (5/6/2020 12:35 PM):public int getCatchable() {return this.catchable;}

	public int getValue() {
		return value;
	}
	public void setValue (Keyboard piano, int value){
		this.note_name = piano.getNOTE_NAMES().get(value);
		this.value = value;
	}

	public static int NoteToValue(Keyboard piano, String note_name) {
		return piano.getNOTE_NAMES().indexOf(note_name);
	}
	
	public String getNote_name() {
		return note_name;
	}

	public String getDuration_name() {return duration_name;}

	public double getDuration_time() {
		return duration_time;
	}
	public void setDuration(Keyboard piano, String duration_name) {
		this.duration_name = duration_name;
		this.duration_time = Keyboard.DurationsMAP.get(duration_name);
	}

	public Note Generate() throws FileNotFoundException {
		FileInputStream pic_input;
		if (!this.duration_name.equals("Eighth")) {
			pic_input = new FileInputStream("/pics/"+ this.duration_name + ".png");
		}else{
			pic_input = new FileInputStream("/pics/"+ this.duration_name + "_up.png");
		}
		Image image = new Image(pic_input);
		ImageView imageView = new ImageView(image);
		//setting the fit height and width of the image view
		imageView.setId("imageView");
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(true);
		switch (duration_name) {
			case "Whole":
				imageView.setFitHeight(19);
				break;
			case "Half":
			case "Quarter":
				imageView.setFitHeight(83);
				imageView.setY(-56);
				break;
			case "Eighth":
				imageView.setFitHeight(85);
				imageView.setY(-65);
				imageView.setX(-7);
				break;
		}

		//Setting Sharp or Flat symbols
		String FS_path = "";
		if(note_name.length()==3 && !MainFXML.controller.signature.accidents.contains(note_name.substring(0,2))) {
			switch (note_name.charAt(1)) {
				case '#':
					FS_path = "/pics/sharp.png";
					break;
				case 'b':
					FS_path = "/pics/flat.png";
					break;
			}
		}else if (note_name.length()==2 && MainFXML.controller.signature.need_natural.contains(note_name.substring(0,1))){
			FS_path = "/pics/natural.png";
		}else{
			FS_path = "/pics/empty.png";}
		Image FS_image = new Image(new FileInputStream(FS_path));
		ImageView FS_imageView = new ImageView(FS_image);
		FS_imageView.setFitHeight(62);
		FS_imageView.setPreserveRatio(true);
		int FS_offsetX; int FS_offsetY;
		switch (duration_name) {
			case "Whole":
				FS_offsetX = -40; FS_offsetY = -20;
				break;
			case "Eighth":
			default:
				FS_offsetX = -30; FS_offsetY = -20;
		}
		FS_imageView.setX(FS_offsetX);
		FS_imageView.setY(FS_offsetY);

		this.imageGroup = new Group(imageView,FS_imageView);

		//Setting the position of the image
		int octave_switch = (Character.getNumericValue(note_name.charAt(note_name.length()-1))-5)*(-70);
		switch (note_name.charAt(0)){
			case 'C':
				this.imageGroup.setLayoutY(137+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'D':
				this.imageGroup.setLayoutY(127+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'E':
				this.imageGroup.setLayoutY(117+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'F':
				this.imageGroup.setLayoutY(107+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'G':
				this.imageGroup.setLayoutY(97+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'A':
				this.imageGroup.setLayoutY(87+octave_switch - MainFXML.controller.signature.clef_switch);break;
			case 'B':
				this.imageGroup.setLayoutY(77+octave_switch - MainFXML.controller.signature.clef_switch);break;
		}
		this.imageGroup.setLayoutX(880);
		this.imageGroup.setAutoSizeChildren(true);

		//Additional lines
		if ((this.imageGroup.getLayoutY()<=87 || this.imageGroup.getLayoutY()>=207)){
			int n = (int)Math.floor(Math.max(87 - this.imageGroup.getLayoutY(),this.imageGroup.getLayoutY()-207)/20)+1;
			int off = 10;
			if ((this.imageGroup.getLayoutY()-7)%20 == 0){off = 0;}
			int s = (int)Math.signum(87-this.imageGroup.getLayoutY());
			for (int j=0;j<n;j++){
				Line line = new Line();
				line.setStrokeWidth(2);
				line.setStartX(FS_offsetX + 35); line.setEndX(FS_offsetX + 75);
				int v = FS_offsetY + 29 + s * (j * 20 + off);
				line.setStartY(v); line.setEndY(v);
				this.imageGroup.getChildren().add(line);
			}
		}

		Platform.runLater(() -> MainFXML.controller.main_pane.getChildren().add(0, imageGroup));

		return this;
	}
}

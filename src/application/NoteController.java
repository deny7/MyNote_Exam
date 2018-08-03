package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.dialog.FontSelectorDialog;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class NoteController implements Initializable {

	@FXML
	public BorderPane border;

	@FXML
	public TextArea textArea;

	@FXML
	public Label label;

	@FXML
	public CheckMenuItem line_check;

	@FXML
	public Label language;

	@FXML
	public ImageView image;

	@FXML
	public ColorPicker font_color_select;

	@FXML
	public ColorPicker background_color_select;

	@FXML
	public ColorPicker tool_color_select;

	@FXML
	public Label current_text_length;

	@FXML
	public HBox status_bar;

	@FXML
	public CheckMenuItem status_show;

	@FXML
	public VBox row_bar;

	@FXML
	public void status() {

		if (status_show.isSelected() == true) {
			status_bar.setVisible(true);
			status_bar.setMinHeight(19);
			status_bar.setMaxHeight(19);
		} else {
			status_bar.setVisible(false);
			status_bar.setMinHeight(0);
			status_bar.setMaxHeight(0);
		}

	}

	public void color_selected() {

		Color font_Color = font_color_select.getValue();
		double red = font_Color.getRed() * 255;
		double green = font_Color.getGreen() * 255;
		double blue = font_Color.getBlue() * 255;
		double alpha = font_Color.getOpacity() * 255;
		String colorString = String.format("-fx-text-fill: rgba(%f,%f,%f,%f) ;", red, green, blue, alpha);

		Color background_Color = background_color_select.getValue();
		int bgreen = (int) (background_Color.getGreen() * 255);
		String greenString = Integer.toHexString(bgreen);

		int bred = (int) (background_Color.getRed() * 255);
		String redString = Integer.toHexString(bred);

		int bblue = (int) (background_Color.getBlue() * 255);
		String blueString = Integer.toHexString(bblue);

		String hexColor = "#" + redString + greenString + blueString;
		String bcolorString = String.format("-fx-control-inner-background:" + hexColor + " ;");

		textArea.setStyle(colorString + bcolorString);
			

	}

	public void tool_color_selected(){
		Color tool_background_Color = tool_color_select.getValue();
		int tool_green = (int) (tool_background_Color.getGreen() * 255);
		String tool_greenString = Integer.toHexString(tool_green);

		int tool_red = (int) (tool_background_Color.getRed() * 255);
		String tool_redString = Integer.toHexString(tool_red);

		int tool_blue = (int) (tool_background_Color.getBlue() * 255);
		String tool_blueString = Integer.toHexString(tool_blue);

		String tool_hexColor = "#" + tool_redString + tool_greenString + tool_blueString;
		String tool_colorString = String.format("-fx-background-color:" + tool_hexColor + " ;");
		
		border.setStyle(tool_colorString);
	}
	
	@FXML
	public void open() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ÅØ½ºÆ® ÆÄÀÏ ¼±ÅÃ");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(Main.primaryStage);

		if (selectedFile != null) {

			try {
				BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getPath()));

				try {

					String line;

					textArea.setText("");

					while ((line = reader.readLine()) != null) {

						textArea.appendText(line + "\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	public void save() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ÅØ½ºÆ® ÆÄÀÏ ÀúÀå");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			SaveFile(textArea.getText(), file);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ÀúÀå ¿Ï·á");
			alert.setHeaderText("ÆÄÀÏ ÀúÀå ¿Ï·á");
			alert.setContentText("ÀúÀåÀ» ¿Ï·áÇÏ¿´½À´Ï´Ù.");
			alert.showAndWait();
		}

	}

	private void SaveFile(String content, File file) {
		try {

			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void close() {
		Platform.exit();
	}

	@FXML
	public void about() throws IOException {

		Stage secondaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/About.fxml"));
		Scene scene = new Scene(root, 600, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		secondaryStage.setScene(scene);
		secondaryStage.setTitle("¸Þ¸ðÀå Á¤º¸");
		secondaryStage.show();

	}

	@FXML
	public void autoline() {
		if (line_check.isSelected() == true) {
			textArea.wrapTextProperty().set(true);
		} else {
			textArea.wrapTextProperty().set(false);
		}
	}

	@FXML
	public void font_click() {

		Dialog<Font> fontDialog = new FontSelectorDialog(null);

		Optional<Font> result = fontDialog.showAndWait();

		if (result.isPresent()) {
			Font newFont = result.get();

			String optEditorFont = newFont.getName();
			double optEditorFontSize = newFont.getSize();
			textArea.setFont(Font.font(optEditorFont, optEditorFontSize));

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textArea.setScrollTop(Double.MAX_VALUE);
		status_show.setSelected(true);

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					Calendar calendar = Calendar.getInstance();
					Date date = new Date(calendar.getTime().getTime());
					String now_date = MessageFormat.format("{0, date, yyyy³â MM¿ù ddÀÏ a hh½Ã mmºÐ ssÃÊ}", date);

					Platform.runLater(() -> label.setText(now_date));

				}
			}
		};
		thread.start();

		Thread check_thread = new Thread() {

			@Override
			public void run() {

				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					String get_String = textArea.getText();

					if (!get_String.equals("")) {
						char lastChar = get_String.charAt(get_String.length() - 1);
						String last = Character.toString(lastChar);

						if (last.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*") == true) {
							Platform.runLater(() -> language.setText("ÇÑ±Û"));
						} else if (last.matches(".*[a-z|A-Z]+.*") == true) {
							Platform.runLater(() -> language.setText("¿µ¹®"));
						}

					}

				}
			}

		};

		check_thread.start();

		Thread text_length_thread = new Thread() {
			@Override
			public void run() {

				while (true) {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					int get_text_length = textArea.getText().length();

					Platform.runLater(() -> current_text_length.setText(Integer.toString(get_text_length)));

				}
			}
		};

		text_length_thread.start();

	}

}

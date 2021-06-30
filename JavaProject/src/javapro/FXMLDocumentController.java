/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapro;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.Button; 
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.Effect;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.imageio.ImageIO;


/**
 *
 * @author Piotr
 */
public class FXMLDocumentController implements Initializable {

    @FXML private StackPane root;
    @FXML private Button buttonLoadImage = new Button();
    @FXML private Button buttonSaveImage = new Button();
    @FXML private Button resetBtn = new Button();
    @FXML private Button infobtn = new Button();
    @FXML private Button seamCarver = new Button();
    @FXML private Button energy = new Button();
    @FXML private Button verticalSeam = new Button();
    @FXML private Button horizontalSeam = new Button();
    @FXML private ImageView myImageView = new ImageView();
    @FXML private TextField numberOfRowToReduce = new TextField();
    @FXML private TextField numberOfColumnToReduce = new TextField();
    private Picture picture;
    
    
    @FXML 
    private void reset() {
        Image newImageView = SwingFXUtils.toFXImage(picture.getBufferedImage(),null);
        myImageView.setImage(newImageView);
    }
    
    @FXML
    private void LoadImage(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();            
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File file = fileChooser.showOpenDialog(null);
            try 
            {
                BufferedImage bufferedImage = ImageIO.read(file);
                this.picture = new Picture(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                myImageView.setImage(image);
                reset();
            } catch (IOException ex) {

            }      
    }

    @FXML
    private void applySeamCarving() {
        System.out.println(numberOfColumnToReduce.getText());
        System.out.println(numberOfRowToReduce.getText());
        int reduceColumn = (this.numberOfColumnToReduce == null) ? 0 : Integer.parseInt(this.numberOfColumnToReduce.getText());
        int reduceRow = (this.numberOfRowToReduce == null) ? 0 : Integer.parseInt(this.numberOfRowToReduce.getText());
        ResizeImage resizeImage = new ResizeImage(this.picture,reduceColumn,reduceRow);
        Picture newPicture = resizeImage.doTheResize();
        Image newImageView = SwingFXUtils.toFXImage(newPicture.getBufferedImage(),null);
        myImageView.setImage(newImageView);
    }

    @FXML
    private void getEnergy() {
        Picture newPicture = ShowEnergy.EnergyPicture(this.picture);
        Image newImageView = SwingFXUtils.toFXImage(newPicture.getBufferedImage(),null);
        myImageView.setImage(newImageView);
    }

    @FXML
    private void showHorizontalSeam() {
        Picture newPicture = ShowSeams.showHorizontalSeam(this.picture);
        Image newImageView = SwingFXUtils.toFXImage(newPicture.getBufferedImage(),null);
        myImageView.setImage(newImageView);
    }

    @FXML
    private void showVerticalSeam() {
        Picture newPicture = ShowSeams.showVerticalSeam(this.picture);
        Image newImageView = SwingFXUtils.toFXImage(newPicture.getBufferedImage(),null);
        myImageView.setImage(newImageView);
    }
    
    @FXML
    private void SaveImage(ActionEvent event) {
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
                 
                File file = fileChooser.showSaveDialog(null);
                if (file != null) {
                    try {
                        BufferedImage bImage = SwingFXUtils.fromFXImage(myImageView.snapshot(null, null), null);
                        ImageIO.write(bImage,"png", file);
                    } catch (IOException ex) {
                    }
                }
            }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        myImageView.fitWidthProperty().bind(root.widthProperty());
        myImageView.fitHeightProperty().bind(root.heightProperty());
        System.out.println("hello");
   }   
    
    
}

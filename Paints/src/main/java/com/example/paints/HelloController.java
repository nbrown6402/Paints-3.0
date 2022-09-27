package com.example.paints;


import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.ScrollPane;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

public class HelloController {
    @FXML
    private TextField brushSize;

    @FXML
    private Canvas canv;
    @FXML
    private RadioMenuItem col;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private RadioMenuItem cork;
    @FXML
    private RadioMenuItem dash;
    @FXML
    private RadioMenuItem eraser;
    @FXML
    private RadioMenuItem lin;
    @FXML
    private RadioMenuItem obo;
    @FXML
    private TabPane pane;
    @FXML
    private StackPane panes;
    @FXML
    private RadioMenuItem pencil;
    @FXML
    private RadioMenuItem rect;
    @FXML
    private MenuItem saves;

    @FXML
    private RadioMenuItem sqar;
    @FXML
    private Tab tab1;
    @FXML
    private VBox vbox;
    @FXML
    private ToggleGroup Type;
    @FXML
    private ScrollPane sp;

    @FXML
    private GraphicsContext graph;
    @FXML
    private RadioMenuItem move;
    @FXML
    private MenuItem qtbtn;

    public String direct;
    private PixelReader read;
    boolean smart;


    //**********************    Open   **************************************\\
    @FXML
    void OpeningImage(ActionEvent event) throws FileNotFoundException {
        smart = false;
        graph = canv.getGraphicsContext2D();
        graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\nicol\\Pictures\\images"));
        File pic = fileChooser.showOpenDialog(null);
        direct = pic.toString();
        if (pic != null)
            try {
                InputStream im = new FileInputStream(pic);
                Image open = new Image(im);
                read = open.getPixelReader();
                double oh = open.getHeight();
                double ow = open.getWidth();
                canv.setHeight(oh);
                canv.setWidth(ow);


                graph.drawImage(open, 0, 0);
            } catch (IOException e) {
                System.out.println("NO IMAGE FOR YOU!");
            }
    }

    //*********************    Save As   ************************************\\
    @FXML
    void SavingNewImage(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        savefile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG ", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp") );

        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }


    //**********************    Save   **************************************\\
    @FXML
    void save(ActionEvent event) {
        smart = true;

        File file = new File(direct);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }

    }

    //***************************    Pencil and Eraser   **************************************\\

    @FXML
    void Draw(ActionEvent event) {
    }

    public void initialize() {
        GraphicsContext graph = canv.getGraphicsContext2D();
        brushSize.setText("10");
        Rectangle rec = new Rectangle();
        Circle circ = new Circle();
        Ellipse elip = new Ellipse();
        Line line = new Line();
        Line lime = new Line();



        canv.setOnMouseClicked(e -> {
            smart = false;
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX();
            double y = e.getY();
            if (col.isSelected()) {
                Color cole = read.getColor((int) x, (int) y);
                colorPicker.setValue(cole);
            }

        });

        canv.setOnMousePressed(e -> {
            smart = false;
            if (rect.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setFill(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                rec.setLocation((int) e.getX(), (int) e.getY());

            }
            else if (sqar.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setFill(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                rec.setLocation((int) e.getX(), (int) e.getY());

            }
            else if (cork.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setFill(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());

            }

            else if (obo.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setFill(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                elip.setCenterX(e.getX());
                elip.setCenterY(e.getY());

            } else if(lin.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }
            else if(dash.isSelected()) {
                smart = false;
                graph.setStroke(colorPicker.getValue());
                graph.setLineWidth(Double.parseDouble(brushSize.getText()));
                graph.setLineDashes(Double.parseDouble(brushSize.getText())*2);
                lime.setStartX(e.getX());
                lime.setStartY(e.getY());
            }

        });


        canv.setOnMouseDragged(e -> {
            smart = false;
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            smart = true;
            if (eraser.isSelected()) {
                graph.clearRect(x, y, size, size);

            } else if (pencil.isSelected()) {
                smart = false;
                graph.setFill(colorPicker.getValue());
                graph.fillOval(x, y, size, size);
                graph.stroke();
            }
        });


        canv.setOnMouseReleased(e -> {
            if (rect.isSelected()) {
                smart = false;
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                graph.setFill(Color.TRANSPARENT);
                graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
                graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
                // undoHistory.push(new Rectangle((int)rec.getX(),(int) rec.getY(),(int) rec.getWidth(), (int)rec.getHeight()));
            }


            else if (sqar.isSelected()) {
                smart = false;
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                graph.setFill(Color.TRANSPARENT);
                graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
                graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
                // undoHistory.push(new Rectangle((int)rec.getX(),(int) rec.getY(),(int) rec.getWidth(), (int)rec.getHeight()));
            }



            else if (cork.isSelected()) {
                smart = false;
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY()))/2);

                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }
                graph.setFill(Color.TRANSPARENT);
                graph.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                graph.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());


            }


            else if (obo.isSelected()) {
                smart = false;
                elip.setRadiusX((Math.abs(e.getX() - elip.getCenterX())));
                elip.setRadiusY((Math.abs(e.getY() - elip.getCenterY())));

                if (elip.getCenterX() > e.getX()) {
                    elip.setCenterX(e.getX());
                }
                if (elip.getCenterY() > e.getY()) {
                    elip.setCenterY(e.getY());
                }
                graph.setFill(Color.TRANSPARENT);
                graph.strokeOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
                graph.fillOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
            }
            else if(lin.isSelected()) {
                smart = false;
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                graph.setLineDashes(0);
                graph.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            }
            else if(dash.isSelected()) {
                smart = false;
                lime.setEndX(e.getX());
                lime.setEndY(e.getY());
                graph.strokeLine(lime.getStartX(), lime.getStartY(), lime.getEndX(), lime.getEndY());

           // Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

            }


        });

    }

    //*******************************    About menu   **************************************\\
    @FXML
    void About(ActionEvent event) {

        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\About.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //*********************************    Help menu   **************************************\\
    @FXML
    void HELP(ActionEvent event) {
        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\HELP.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //********************************    Zoom with mouse   **************************************\\
    @FXML
    void zoom(ScrollEvent event) {
        double fac = 1.05;
        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            fac = 0.95;
        }
        canv.setScaleX(canv.getScaleX() * fac);
        canv.setScaleY(canv.getScaleY() * fac);
        event.consume();
    }

    @FXML
    void tabs(ActionEvent event) {





        ScrollPane s = new ScrollPane();
        //ab tab = new Tab();
        //graph = can.getGraphicsContext2D();
       // tab.setContent(can);
       // pane.getTabs().add(tab);
       // pane.getSelectionModel().selectLast();
        addTab(pane);


    }

    private void addTab(TabPane tabPane) {
        Tab tab = new Tab("Tab: " + 1);
        tab.setContent(createTabContent());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();
    }

    private Node createTabContent() {
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, 100, 100);

        return canvas;
    }

    @FXML
    void bye(ActionEvent event) throws IOException {
        if (smart == false) {
            //Create an Alert with predefined warning image
            Alert alert = new Alert(Alert.AlertType.WARNING);
//Set text in conveinently pre-defined layout
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to close the application?");
//Set custom buttons
            ButtonType okButton = new ButtonType("Yes, exit", OK_DONE);
            ButtonType cancelButton = new ButtonType("No, go back to Paint", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType sButton = new ButtonType("Save", OK_DONE);
            ButtonType SAButton = new ButtonType("Save As", OK_DONE);

            alert.getButtonTypes().setAll(okButton, cancelButton, sButton, SAButton);
//Prevent all interaction with application until resolved.
            alert.initModality(Modality.APPLICATION_MODAL);
//Launch
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    Platform.exit();
                } else if (response == sButton) {
                    save(event);
                    Platform.exit();
                } else if (response == SAButton) {
                    SavingNewImage(event);
                    Platform.exit();
                }

            });
        }
        else {Platform.exit();}
    }


}






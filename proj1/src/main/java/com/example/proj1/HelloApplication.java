package com.example.proj1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws IOException {

        Pane root = new Pane();
        root.setPrefSize(800, 800);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.prefWidthProperty().bind(stage.widthProperty());
        scrollPane.prefHeightProperty().bind(stage.heightProperty());
        Label coordinatesLabel = new Label();
        coordinatesLabel.relocate(6.4, 567.2);
        root.getChildren().add(coordinatesLabel);


        Button gn = new Button("Generate");

        VBox FileEntry = new VBox();
        FileEntry.relocate(0, 57);
        FileEntry.setPrefHeight(77);
        FileEntry.setPrefWidth(187);


        TextField path = new TextField();
        path.setDisable(true);
        path.setText("");
        Button upload = new Button("Browse...");
        upload.setOnAction(e -> {
            FileChooser filename = new FileChooser();
            File file = filename.showOpenDialog(stage);
            String filepath = String.valueOf(file);
            path.setText(filepath);

        });

        gn.setOnAction(e -> {
            String filepath = path.getText();
            File myfile = new File(filepath);
            Scanner myReader = null;
            String[] l;
            try {
                myReader = new Scanner(myfile);
                String powersupply = myReader.nextLine();
                String Noofbulbs = myReader.nextLine();


                if (powersupply.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("error");
                    errorAlert.setHeaderText("the powersupply section is empty");
                    errorAlert.showAndWait();
                    return;
                }

                if (Noofbulbs.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("error");
                    errorAlert.setHeaderText("the bulbs section is empty");
                    errorAlert.showAndWait();
                    return;
                }
                if (!isanumber(powersupply)) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("error");
                    errorAlert.setHeaderText("please enter a number");
                    errorAlert.showAndWait();
                    return;
                } else {
                    double newHeight = calculateNewHeight(Integer.parseInt(powersupply));
                    root.setPrefHeight(newHeight);
                }
                int initialx = 735;
                int initialy = 25;
                int initialxpower = 500;

                l = Noofbulbs.split(",");
                Label[] pl = new Label[Integer.parseInt(powersupply)];
                Label[] ll = new Label[pl.length];


                int[] arrl = new int[l.length];

                if (Integer.parseInt(powersupply) != arrl.length) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("error");
                    errorAlert.setHeaderText("the number of the Leds does not equal the number of the power supplies");
                    errorAlert.setContentText("Be aware that the number of the leds and the power supplies should be equal to each other");
                    errorAlert.showAndWait();
                    return;
                }
                for (int i = 0; i < l.length; i++) {
                    l[i] = l[i].trim();
                    ll[i] = new Label();
                    ll[i].setText(l[i]);

                    if (!isanumber(l[i])) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("error");
                        errorAlert.setHeaderText("please enter a number");
                        errorAlert.showAndWait();
                        return;
                    }
                    arrl[i] = Integer.parseInt(l[i]);
                }


                for (int i = 0; i < arrl.length - 1; i++) {
                    for (int j = i + 1; j < arrl.length; j++) {
                        if (arrl[i] == arrl[j]) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("error");
                            errorAlert.setHeaderText("there is duplicated number in the entry");
                            errorAlert.setContentText("the number " + arrl[i] + " duplicated in the index " + i + " and" + j);
                            errorAlert.showAndWait();
                            return;
                        }
                    }
                }


                int[] indecies = LIS(arrl);
                Label pathlabel= new Label("Result");
                Label indexLabel2 = new Label("index");
                HBox pathlabelbox = new HBox();
                HBox indexbox = new HBox();
                pathlabelbox.getChildren().add(pathlabel);
                indexbox.getChildren().add(indexLabel2);

                pathlabelbox.setLayoutX(20);
                pathlabelbox.setLayoutY(290);
                indexbox.setLayoutX(20);
                indexbox.setLayoutY(275);
                root.getChildren().add(pathlabelbox);
                root.getChildren().add(indexbox);


                int[] path1 = path(arrl);
                Label[] pathlab = new Label[path1.length];
                Label[] labelindex = new Label[path1.length];
                HBox hboxpath = new HBox();
                hboxpath.setBorder(Border.stroke(Color.BLACK));
                HBox hboxpathind = new HBox();

                for (int i=0;i<path1.length;i++){
                    pathlab[i] = new Label();
                    labelindex[i] = new Label();
                    pathlab[i].setText(String.valueOf(path1[i]) + "\t");
                    labelindex[i].setText(String.valueOf(i) + "\t");
                    hboxpath.getChildren().add(pathlab[i]);
                    hboxpathind.getChildren().add(labelindex[i]);
                }
                hboxpath.setLayoutX(56);
                hboxpath.setLayoutY(290);
                hboxpathind.setLayoutX(56);
                hboxpathind.setLayoutY(275);
                root.getChildren().add(hboxpathind);
                root.getChildren().add(hboxpath);



                int[] tables = table(arrl);
                Label lb = new Label("index");
                Label resb = new Label("Path");
                Label[] ind = new Label[tables.length];
                Label[] result = new Label[tables.length];
                HBox hbox = new HBox();
                HBox hboxind  = new HBox();
                HBox hboxlabel = new HBox();
                HBox hboxlabel2 = new HBox();

                hboxlabel2.getChildren().add(resb);

                hboxlabel2.setLayoutX(20);
                hboxlabel2.setLayoutY(230);

                hboxind.setLayoutX(56);
                hboxind.setLayoutY(215);
                hbox.setLayoutX(56);
                hbox.setLayoutY(230);
                hbox.setBorder(Border.stroke(Color.BLACK));

                hboxlabel.setLayoutY(215);
                hboxlabel.setLayoutX(20);
                hboxlabel.getChildren().add(lb);
                root.getChildren().add(hboxlabel);
                root.getChildren().add(hboxlabel2);

                for (int i=0;i<result.length;i++){
                    result[i] = new Label();
                    ind[i] = new Label();
                    ind[i].setText(String.valueOf(i) + "\t");
                    result[i].setText(String.valueOf(tables[i])+  "\t");
                    hbox.getChildren().add(result[i]);
                    hboxind.getChildren().add(ind[i]);
                }
                root.getChildren().add(hboxind);
                root.getChildren().add(hbox);



                Image powerImage;
                Image OFfImage;
                ImageView[] Offview = new ImageView[ll.length];
                ImageView[] powerview = new ImageView[pl.length];
                int[] arrp = new int[pl.length];

                for (int i = 0; i < pl.length; i++) {
                    pl[i] = new Label();
                    pl[i].setText(String.valueOf(i + 1));
                    arrp[i] = Integer.parseInt(pl[i].getText());
                    pl[i].setLayoutX(initialxpower);
                    pl[i].setLayoutY(initialy + 20 + i * 80);
                    ll[i].setLayoutX(initialx);
                    ll[i].setLayoutY(initialy + 20 + i * 80);

                    OFfImage = new Image("file:////C:/Users/ahmad/IdeaProjects/proj1/src/off.png");
                    Offview[i] = new ImageView(OFfImage);
                    Offview[i].setFitHeight(50);
                    Offview[i].setFitWidth(30);
                    Offview[i].setLayoutX(initialx + 50);
                    Offview[i].setLayoutY(initialy + i * 80);

                    powerImage = new Image("file:////C:/Users/ahmad/IdeaProjects/proj1/src/powersupply.png");
                    powerview[i] = new ImageView(powerImage);
                    powerview[i].setFitHeight(50);
                    powerview[i].setFitWidth(30);
                    powerview[i].setLayoutX(initialxpower - 50);
                    powerview[i].setLayoutY(initialy + i * 80);
                    root.getChildren().add(powerview[i]);
                    root.getChildren().add(Offview[i]);
                    root.getChildren().add(pl[i]);
                    root.getChildren().add(ll[i]);
                }



                Image OnImage;
                ImageView[] onview = new ImageView[indecies.length];
                for (int j = 0; j < indecies.length; j++) {
                    for (int i = 0; i < pl.length; i++) {
                        if (Integer.parseInt(ll[indecies[j]].getText()) == Integer.parseInt(pl[i].getText())) {

                            OnImage = new Image("file:////C:/Users/ahmad/IdeaProjects/proj1/src/on.png");
                            Offview[indecies[j]] = new ImageView(OnImage);
                            Offview[indecies[j]].setFitHeight(50);
                            Offview[indecies[j]].setFitWidth(30);
                            Offview[indecies[j]].setLayoutX(initialx + 46);
                            Offview[indecies[j]].setLayoutY(initialy + indecies[j] * 80);

                            Line line = new Line();

                            line.setStyle("-fx-stroke-width: 2px;-fx-stroke: #e0e01f;");

                            line.setStartX(ll[indecies[j]].getLayoutX() - 3);
                            line.setStartY(ll[indecies[j]].getLayoutY() + 10);

                            line.setEndX(pl[i].getLayoutX() + 10);
                            line.setEndY(pl[i].getLayoutY() + 10);
                            root.getChildren().add(Offview[indecies[j]]);
                            root.getChildren().add(line);
                            break;
                        }
                    }
                }
//                int tablelayx = 16;
//                int tablelayy = 230;
//                Label s = new Label("index");
//                s.setLayoutX(50);
//                s.setLayoutY(242);
//                root.getChildren().add(s);
//                Label[] lp = new Label[pl.length];
//                for (int i=0; i<lp.length;i++){
//                    lp[i] = new Label();
//                    lp[i].setText(pl[i].getText());
//                    lp[i].setLayoutX(tablelayx);
//                    lp[i].setLayoutY(tablelayy +i *80);
//                    root.getChildren().add(lp[i]);
//                }


                for (int i = 0; i < l.length; i++) {
                    if (Integer.parseInt(l[i]) < 0) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("error");
                        errorAlert.setHeaderText("There is a number that is less than 0");
                        errorAlert.setContentText("the number" + l[i] + " is less than 0");
                        errorAlert.showAndWait();
                        Platform.exit();

                    }
                }


            } catch (FileNotFoundException ex) {
                System.out.println("file not found");
            }
        });
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            path.setText("");

            root.getChildren().removeIf(node ->
                    node instanceof Label || node instanceof ImageView || node instanceof Line || node instanceof HBox);

            root.setPrefHeight(800);
        });

        FileEntry.getChildren().add(path);
        FileEntry.getChildren().add(upload);
        FileEntry.getChildren().add(gn);
        FileEntry.getChildren().add(resetButton);
        FileEntry.setSpacing(5);
        root.getChildren().add(FileEntry);
        Image img = new Image("file:////C:/Users/ahmad/IdeaProjects/proj1/src/img.png");
        Scene scene = new Scene(scrollPane, 1000, 600);

//        scene.setOnMouseMoved(event -> {
//            double x = event.getX();
//            double y = event.getY();
//            coordinatesLabel.setText("Mouse coordinates: (" + x + ", " + y + ")");
//        });
        stage.getIcons().add(img);
        stage.setTitle("LIS Project");
        stage.setScene(scene);
        stage.show();
    }

    public static boolean isanumber(String str) {
        try {

            Integer.parseInt(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double calculateNewHeight(int numberOfLEDs) {

        double minHeight = 200;

        double calculatedHeight = minHeight + (numberOfLEDs * 80);
        return Math.max(calculatedHeight, minHeight);
    }

    //-----------------------------------------------
    //||    max(Lis[j] + 1, Lis[i]) , arr[j]<arr[i]
    //||
    //||    1                      , if(arr[j]<arr[i]) is false
    //||
    //------------------------------------------------

    public int[] LIS(int[] power) {

        int[] temp = new int[power.length];
        int[] prevIndices = new int[power.length];
        Arrays.fill(temp, 1);
        Arrays.fill(prevIndices, -1);

        int maxLength = 1;
        int endIndex = 0;

        for (int i = 1; i < power.length; i++) {
            for (int j = 0; j < i; j++) {
                if (power[j] < power[i]) {
                    if (temp[j] + 1 > temp[i]) {
                        temp[i] = temp[j] + 1;
                        prevIndices[i] = j;
                        if (temp[i] > maxLength) {
                            maxLength = temp[i];
                            endIndex = i;
                        }
                    }
                }
            }
        }

        int[] lis = new int[maxLength];
        int currentIndex = endIndex;
        int[] index = new int[maxLength];
        for (int i = maxLength - 1; i >= 0; i--) {
            index[i] = currentIndex;
            lis[i] = power[currentIndex];
            currentIndex = prevIndices[currentIndex];
        }
        return index;
    }

    public int[] path(int[] power) {

        int[] temp = new int[power.length];
        int[] prevIndices = new int[power.length];
        Arrays.fill(temp, 1);
        Arrays.fill(prevIndices, -1);

        int maxLength = 1;
        int endIndex = 0;

        for (int i = 1; i < power.length; i++) {
            for (int j = 0; j < i; j++) {
                if (power[j] < power[i]) {
                    if (temp[j] + 1 > temp[i]) {
                        temp[i] = temp[j] + 1;
                        prevIndices[i] = j;
                        if (temp[i] > maxLength) {
                            maxLength = temp[i];
                            endIndex = i;
                        }
                    }
                }
            }
        }

        int[] lis = new int[maxLength];
        int currentIndex = endIndex;
        int[] index = new int[maxLength];
        for (int i = maxLength - 1; i >= 0; i--) {
            index[i] = currentIndex;
            lis[i] = power[currentIndex];
            currentIndex = prevIndices[currentIndex];
        }
        return temp;
    }

    public int[] table(int[] power) {

        int[] temp = new int[power.length];
        int[] prevIndices = new int[power.length];
        Arrays.fill(temp, 1);
        Arrays.fill(prevIndices, -1);

        int maxLength = 1;
        int endIndex = 0;

        for (int i = 1; i < power.length; i++) {
            for (int j = 0; j < i; j++) {
                if (power[j] < power[i]) {
                    if (temp[j] + 1 > temp[i]) {
                        temp[i] = temp[j] + 1;
                        prevIndices[i] = j;
                        if (temp[i] > maxLength) {
                            maxLength = temp[i];
                            endIndex = i;
                        }
                    }
                }
            }
        }

        int[] lis = new int[maxLength];
        int currentIndex = endIndex;
        int[] index = new int[maxLength];
        for (int i = maxLength - 1; i >= 0; i--) {
            index[i] = currentIndex;
            lis[i] = power[currentIndex];
            currentIndex = prevIndices[currentIndex];
        }
        return prevIndices;
    }


    private int matchingindex(int[] i, int x) {
        for (int j = 0; j < i.length; j++) {
            if (i[j] == x) {
                return i[j];
            }
        }
        return -1;
    }

    public static boolean containsDuplicates(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return true;
                }
            }
        }

        return false;
    }


    public static void main(String[] args) {
        launch();
    }
}
package org.myjavafx.demo.laboratorjavafx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloApplication extends Application {
    private final List<ImageView> images = new ArrayList<>();
    private final Random random = new Random();
    private final int randomDelay = random.nextInt(10 - 1 + 1) + 1;

    public void loadImages(String directoryPath) {
        File directoryFile = new File(directoryPath);
        File[] files = directoryFile.listFiles();

        if (files != null) {
            for (File file : files) {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                images.add(imageView);
            }
        }
    }

    public void displayRandomImage(StackPane root) {
        if (!images.isEmpty()) {
            int randomIndex = random.nextInt(images.size());
            ImageView imageView = images.get(randomIndex);
            root.getChildren().clear();
            root.getChildren().add(imageView);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Timeline timeline = new Timeline();

        loadImages("src/main/resources/image");

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        TranslateTransition transition = new TranslateTransition(Duration.seconds(randomDelay), root);
        RotateTransition rotate = new RotateTransition(Duration.seconds(randomDelay), root);

        transition.setFromX(randomDelay);
        transition.setToX(random.nextInt(200 - 50 + 1) + 50);
        transition.setFromY(randomDelay);
        transition.setToY(random.nextInt(200 - 50 + 1) + 50);
        rotate.setByAngle(random.nextInt(200 - 50 + 1) + 50);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
        rotate.play();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(randomDelay), event -> displayRandomImage(root)));
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

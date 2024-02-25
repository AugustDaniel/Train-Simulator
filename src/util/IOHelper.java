package util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

public class IOHelper {

    public static <T> void saveObject(T object, File file) throws Exception {
        new ObjectOutputStream(Files.newOutputStream(file.toPath()));
    }

    public static Object readObject(File file) throws Exception {
        ObjectInputStream input = new ObjectInputStream(Files.newInputStream(file.toPath()));
        return input.readObject();
    }

    public static File getFileFromChooser(String message) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(message);
        return fileChooser.showOpenDialog(new Stage());
    }
}

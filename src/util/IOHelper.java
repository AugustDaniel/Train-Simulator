package util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

public class IOHelper {

    public static <T> void saveObject(T object, File file) {
        try(
                ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(file.toPath()))
                ) {
            output.writeObject(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object readObject(File file) {
        try(
                ObjectInputStream input = new ObjectInputStream(Files.newInputStream(file.toPath()))
        ) {
            return input.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static File getFileFromChooser(String message) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(message);
        return fileChooser.showOpenDialog(new Stage());
    }
}

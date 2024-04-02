package guiapplication.schedulePlanner.Simulator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sounds {
    private static Clip trainClip;
    private static Clip disasterClip;
    private static String trainWhisleFilePath = "res/train-whistle-102834.wav ";
    private static String disasterSoundFilePath = "res/nederlands-luchtalarm.wav ";
    private static File trainWhistleMusicPath = new File(trainWhisleFilePath);
    private static File disasterSoundMusicPath = new File(disasterSoundFilePath);
    private static AudioInputStream trainAudioInput;
    private static AudioInputStream disasterAudioInput;

    static {
        try {
            if (disasterSoundMusicPath.exists() && trainWhistleMusicPath.exists()) {
                disasterClip = AudioSystem.getClip();
                trainClip = AudioSystem.getClip();
                disasterAudioInput = AudioSystem.getAudioInputStream(disasterSoundMusicPath);
                trainAudioInput = AudioSystem.getAudioInputStream(trainWhistleMusicPath);
            } else {
                System.out.println("cant find file");
            }
            disasterClip.open(disasterAudioInput);
            trainClip.open(trainAudioInput);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void whistleWhenTrainLeaving() {
      trainClip.start();
    }

    public static void disasterSound(Boolean disaster) {
            if (disasterClip.isActive() && !disaster) {
            disasterClip.stop();
            disasterClip.close();
        } else if (disaster && !disasterClip.isActive()) {
            disasterClip.start();
        }
    }
}

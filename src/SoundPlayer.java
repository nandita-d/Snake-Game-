import javax.sound.sampled.*;
import java.io.File;

class SoundPlayer {
    public static void play(String fileName) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception ignored) {}
    }
}
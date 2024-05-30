package org.LanceOfDestiny.domain.managers;

import javax.sound.sampled.*;
import java.io.File;
import java.nio.file.InvalidPathException;

public class AudioManager {
    private static AudioManager instance;
    private Thread musicThread;
    private Clip musicClip;
    private String backgroundAudioPath = "Audio/BackgroundMusic.wav";
    private String soundEffectPath = "Audio/SoundEffect.wav";
    private String ymirActivatedPath = "Audio/YmirActivated.wav";
    private String spellActivatedPath = "Audio/SpellActivated.wav";
    private String winEffectPath = "Audio/WinEffect.wav";
    private String loseEffectPath = "Audio/LoseEffect.wav";



    private AudioManager() {
    }


    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }


    public void startBackgroundMusic() {
        if (musicThread != null) {
            musicThread.interrupt();
        }

        musicThread = new Thread(() -> {
            try {
                musicClip = AudioSystem.getClip();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(getFilePath(backgroundAudioPath));
                musicClip.open(audioStream);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        musicThread.start();
    }


    public void stopBackgroundMusic() {
        if (musicClip != null) {
            musicClip.stop();
        }
        musicThread = null;
    }


    public void playSoundEffect() {
//        if (.isMuteModeActivated()) {
//            return;
//        }

        Thread soundEffectThread = new Thread(() -> {
            try {
                Clip soundClip = AudioSystem.getClip();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(getFilePath(soundEffectPath));
                soundClip.open(audioStream);
                soundClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }

    private File getFilePath(String soundPath) {
        String path;
        File audioFile = null;
        try {
            var userDirectory = System.getProperty("user.dir");
            if (userDirectory.endsWith("/LanceOfDestiny-FourthDimension")) {
                path = new File(soundPath).getAbsolutePath();
            } else {
                path = userDirectory + "/LanceOfDestiny-FourthDimension/" + soundPath;
            }
            audioFile = new File(path);

        } catch (InvalidPathException e) {
            System.err.println("Invalid path: " + soundPath);
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Unable to access system property 'user.dir'.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting the file path.");
            e.printStackTrace();
        }
        return audioFile;
    }
}

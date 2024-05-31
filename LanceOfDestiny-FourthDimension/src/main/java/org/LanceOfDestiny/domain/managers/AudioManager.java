package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.spells.SpellType;

import javax.sound.sampled.*;
import java.io.File;
import java.nio.file.InvalidPathException;

public class AudioManager {
    private static AudioManager instance;
    private Thread musicThread;
    private Clip musicClip;
    private boolean isMuted = false;

    private final String backgroundAudioPath = "Audio/BackgroundMusic.wav";
    private final String barrierHitPath = "Audio/BarrierHit.wav";

    private final String ymirActivatedPath = "Audio/EvilLaugh.wav";
    private final String spellGainedPath = "Audio/SpellGained.wav";
    private final String spellActivatedPath = "Audio/SpellActivated.wav";

    private final String winGamePath = "Audio/WinGame.wav";
    private final String loseGamePath = "Audio/SadTrombone.wav";

    private final String countdownPath = "Audio/Count3sec.wav";

    private final String loseChancePath = "Audio/LoseChance.wav";
    private final String winChancePath = "Audio/WinChance.wav";

    private final String shootHexPath = "Audio/ShootHex.wav";


    // overwhelmingde müzik değişebilir

    // oyun müziği kısmak gerekiyor

    private AudioManager() {
        Event.EndGame.addListener(e -> {
            if(((String) e).equals("You Lost")) playLoseGameEffect();
            else playWinGameEffect();
        });

        Event.GainSpell.addListener(e-> {
            if (!((SpellType) e).equals(SpellType.CHANCE) && SessionManager.getInstance().getLoopExecutor().getLoop().isActive()) playSpellGainedEffect();
        });
        Event.ActivateExpansion.addRunnableListener(this::playSpellActivatedEffect);
        Event.ActivateOverwhelming.addRunnableListener(this::playSpellActivatedEffect);
        Event.ActivateCanons.addRunnableListener(this::playSpellActivatedEffect);
        Event.StartCounting.addRunnableListener(this::playCountdownEffect);
        Event.UpdateChance.addListener(this::playChanceEffect);
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void mute () {
        isMuted = true;
        stopBackgroundMusic();
    }

    public void unmute () {
        isMuted = false;
        startBackgroundMusic();
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

    public void playSoundEffect(String path) {
        if (isMuted) return;
        Thread soundEffectThread = new Thread(() -> {
            try {
                Clip soundClip = AudioSystem.getClip();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(getFilePath(path));
                soundClip.open(audioStream);
                soundClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundEffectThread.start();
    }

    public void playShootHex() {
        playSoundEffect(shootHexPath);
    }

    private void playChanceEffect(Object change) {
        if(((int) change) == -1) playSoundEffect(loseChancePath);
        else playSoundEffect(winChancePath);
    }

    public void playBarrierHitEffect() {
        playSoundEffect(barrierHitPath);
    }

    private void playWinGameEffect() {
        playSoundEffect(winGamePath);
    }

    public void playLoseGameEffect() {
        playSoundEffect(loseGamePath);
    }

    public void playYmirEffect() {
        playSoundEffect(ymirActivatedPath);
    }

    public void playSpellActivatedEffect() {
        playSoundEffect(spellActivatedPath);
    }

    public void playSpellGainedEffect() {
        playSoundEffect(spellGainedPath);
    }

    public void playCountdownEffect() {
        playSoundEffect(countdownPath);
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

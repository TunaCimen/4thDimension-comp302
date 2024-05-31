package org.LanceOfDestiny.ui.UIElements;

import org.LanceOfDestiny.domain.managers.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuteButton extends JButton {

    private ImageIcon speakerIcon;
    private ImageIcon mutedSpeakerIcon;
    private boolean isMuted;

    public MuteButton(ImageIcon speakerIcon, ImageIcon mutedSpeakerIcon, Dimension size) {
        this.speakerIcon = new ImageIcon(speakerIcon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
        this.mutedSpeakerIcon = new ImageIcon(mutedSpeakerIcon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
        this.isMuted = false;

        setIcon(this.speakerIcon);
        setPreferredSize(size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMute();
            }
        });
    }

    private void toggleMute() {
        if (isMuted) {
            setIcon(speakerIcon);
            AudioManager.getInstance().unmute();
        } else {
            setIcon(mutedSpeakerIcon);
            AudioManager.getInstance().mute();
        }
        isMuted = !isMuted;
    }
}

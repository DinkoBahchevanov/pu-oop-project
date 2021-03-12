package com.company.winFrame;

import com.company.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinFrame extends JFrame {

    public WinFrame() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(640, 660));
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(new File("src/com/company/resources/Win.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setContentPane(new BackImage(bf));

        JButton button = new JButton("Restart");
        button.setBounds(260,560,100,47);
        button.addActionListener(new RestartGame(this));

        button.setFocusPainted(false);
        button.setBackground(new Color(2, 131, 239));

        this.add(button);
    }

    private class RestartGame implements ActionListener {
        public RestartGame(WinFrame winFrame) {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new Board();
        }
    }
    private static class BackImage extends JComponent {

        Image i;
        public BackImage(Image i) {
            this.i = i;
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(i, 0, 0, null);  // Drawing image using drawImage method
        }
    }
}

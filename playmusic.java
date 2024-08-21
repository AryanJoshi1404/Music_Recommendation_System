import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FilenameFilter;

public class playmusic implements ActionListener{
    JFrame frame = new JFrame("Music Player");
    JButton mybutton = new JButton("Back");
    JLabel songNameLabel, songProgressLabel;
    JButton playPauseButton, previousButton, nextButton;
    private boolean isPlaying = false;
    private Player player;
    private File[] songFiles;
    private int currentSongIndex = 0;



    playmusic(){

        File musicFolder = new File("D:\\Playlist");
        songFiles = musicFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mp3");
            }
        });


        mybutton.setBounds(358, 0, 125, 40);
        mybutton.setFocusable(false);
        mybutton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        mybutton.setForeground(new Color(0,0,0));
        mybutton.setBackground(new Color(0xFFB6AF));
        mybutton.addActionListener(this);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(0x123456));
        songNameLabel = new JLabel("Song Name");
        songNameLabel.setFont(new Font("Mona Sans",Font.BOLD, 15));
        songNameLabel.setForeground(new Color(0xFFB6AF));
        songProgressLabel = new JLabel("00:00 / 00:00"); 
        songProgressLabel.setForeground(new Color(0xFFB6AF));
        songProgressLabel.setFont(new Font("Mona Sans",Font.BOLD, 15));// Update with actual song duration
        
        infoPanel.add(songNameLabel);
        infoPanel.add(songProgressLabel);
        infoPanel.setBounds(10, 150, 450, 50); // Set bounds for info panel
        frame.add(infoPanel);

        JPanel controlPanel = new JPanel();
        playPauseButton = new JButton("Play");
        playPauseButton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        playPauseButton.setBackground(new Color(0xFFB6AF));
        playPauseButton.addActionListener(this);
        previousButton = new JButton("Previous");
        previousButton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        previousButton.setBackground(new Color(0xFFB6AF));
        previousButton.addActionListener(this);
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        nextButton.setBackground(new Color(0xFFB6AF));
        nextButton.addActionListener(this);
        controlPanel.setBackground(new Color(0x123456));
        controlPanel.add(previousButton, BorderLayout.WEST);
        controlPanel.add(playPauseButton,BorderLayout.CENTER);
        controlPanel.add(nextButton, BorderLayout.EAST);
        controlPanel.setBounds(25, 325, 400, 100); // Set bounds for control panel
        frame.add(controlPanel);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0x123456));
        frame.add(mybutton);
    }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == mybutton){
                frame.dispose();
            }else if (e.getSource() == playPauseButton) {
                if (isPlaying) {
                    stopSong();
                } else {
                    playSong(songFiles[currentSongIndex]); // Play the current song
                }
            } else if (e.getSource() == nextButton) {
                if (currentSongIndex < songFiles.length - 1) {
                    stopSong();
                    currentSongIndex++;
                    playSong(songFiles[currentSongIndex]);
                }
            } else if (e.getSource() == previousButton) {
                if (currentSongIndex > 0) {
                    stopSong();
                    currentSongIndex--;
                    playSong(songFiles[currentSongIndex]);
                }
            }
        }
        private void playSong(File songFile) {
            try {
                FileInputStream fis = new FileInputStream(songFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                player = new Player(bis);
                
                // Update UI
                isPlaying = true;
                playPauseButton.setText("Pause");
                songNameLabel.setText("Now Playing: " + songFile.getName());

                // Start a new thread to play the song
                new Thread(() -> {
                    try {
                        player.play();
                        if (player.isComplete()) {
                            nextSong();
                        }
                    } catch (Exception e) {
                        // JOptionPane.showMessageDialog(this, "Error playing file: " + songFile.getName(), "Playback Error", JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            } catch (Exception e) {
                // JOptionPane.showMessageDialog(this, "Error playing file: " + songFile.getName(), "Playback Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void stopSong() {
            if (player != null) {
                player.close(); // Stop the current song
                isPlaying = false;
                playPauseButton.setText("Play");
            }
        }
    
        private void nextSong() {
            if (currentSongIndex < songFiles.length - 1) {
                currentSongIndex++;
                playSong(songFiles[currentSongIndex]);
            }
        }
    }
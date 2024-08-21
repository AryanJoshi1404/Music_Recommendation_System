import java.awt.event.ActionListener;
// import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

public class RecommendMusicWindow  implements ActionListener {

    JFrame frame = new JFrame("Music Recommender");
    JButton backbutton = new JButton("Back");
    JButton submitbutton = new JButton("Submit");
    JTextField search = new JTextField("Enter the name of the Song.");
    private JPanel songPanel;


    RecommendMusicWindow(){

        backbutton.setFocusable(false);
        backbutton.setForeground(new Color(0,0,0));
        backbutton.setBackground(new Color(0xFFB6AF));
        backbutton.setBounds(358, 0, 125, 40);
        backbutton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        backbutton.addActionListener(this);

        search.setPreferredSize(new Dimension(300, 100));
        search.setBackground(new Color(0xFFB6AF));
        search.setBounds(40, 70, 290, 40);
        search.setFont(new Font("Mona Sans",Font.BOLD, 14));
        search.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                search.setText("");
            }
        });
        
        submitbutton.setBounds(335, 75, 100, 30);
        submitbutton.setFocusable(false);
        submitbutton.setForeground(new Color(0,0,0));
        submitbutton.setBackground(new Color(0xFFB6AF));
        submitbutton.setFont(new Font("Mona Sans",Font.BOLD, 15));
        submitbutton.addActionListener(this);

        songPanel = new JPanel();
        songPanel.setLayout(new GridLayout(0, 1)); // Dynamic rows, 1 column
        songPanel.setBounds(60, 150, 350, 200);
        songPanel.setBackground((new Color(0x123456))); 
        songPanel.setFont(new Font("Mona Sans",Font.BOLD, 13));// Set bounds for song panel
        frame.add(songPanel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0x123456));
        frame.add(backbutton);
        frame.add(search);
        frame.add(submitbutton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backbutton){
            frame.dispose();
        }else if (e.getSource() == submitbutton) {
            String searchTerm = search.getText();

            try {
                // Execute the Python script
                ProcessBuilder pb = new ProcessBuilder("python", "musicrecommend.py", searchTerm).redirectErrorStream(true);
                Process process = pb.start();
                
                // Read and display output from the Python script
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder outputBuilder = new StringBuilder(); // Store complete output

                String line;
                while ((line = reader.readLine()) != null) {
                    // Check if line contains "downloading" or other unwanted messages
                    if ( !line.contains("[nltk_data]") && !line.contains("Downloading") && !line.contains("Package") && !line.contains("C:") && !line.contains("Traceback") && !line.contains("line") && !line.contains("File") && !line.contains("INdexError")) {
                        outputBuilder.append(line).append("\n"); // Append valid lines
                    }
                }
                String filteredOutput = outputBuilder.toString().trim(); // Remove leading/trailing whitespaces
                if (!filteredOutput.isEmpty()) {
                    // Process the filtered output (split into song names)
                    String[] recommendedSongs = filteredOutput.split(",");
                    updateSongRecommendations(recommendedSongs);
                } else {
                    // Handle the case where no valid recommendations were found
                    System.out.println("No song recommendations found.");
                }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

private void updateSongRecommendations(String[] recommendedSongs) {
    // Clear the song panel
    songPanel.removeAll();

    if (recommendedSongs.length==0) {
        // Handle the case where no recommendations were found
        System.out.println("No song recommendations found.");
        // Optionally, display a message to the user
        return;  // Exit the method if no recommendations
    }

    // For each song, create a button and add it to the panel
    for (int i = 0; i < 5; i++) {
        String songName = recommendedSongs[i];
        String songLink = recommendedSongs[i + 5];

        // String googleSearchURL = "https://www.google.com/search?q=" + encodedLink;

        JButton songButton = new JButton(songName);
        songButton.setFont(new Font("Mona Sans", Font.BOLD, 14));
        songButton.setBackground(new Color(0xFFB6AF));
        songButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Replace with the actual domain name
                    String fullURL = "https://www.youtube.com/results?search_query=" + songLink;
                    Desktop.getDesktop().browse(new URI(fullURL));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        songPanel.add(songButton);
    }
    // Refresh the song panel
    songPanel.revalidate();
    songPanel.repaint();
}
}
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


public class MusicRecSys 
{
    public static void main(String[] args){
        
        JFrame myframe = new JFrame();
        myframe.setTitle("Music Recommendation System");
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.setResizable(false);
        myframe.setSize(650,650);
        myframe.setVisible(true);
        myframe.setLayout(null);
        myframe.getContentPane().setBackground(new Color(0x123456));
        
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(800,100));
        panel1.setBackground(new Color(0x123456));
        panel1.setVisible(true);
        panel1.setBounds(0, 10, 800, 100);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(800,200));
        panel2.setBackground(new Color(0x123456));
        panel2.setVisible(true);
        panel2.setBounds(0, 150, 800, 100);

        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(800,400));
        panel3.setBackground(new Color(0x123456));
        panel3.setVisible(true);
        panel3.setBounds(0, 240, 800, 100);

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(800,400));
        panel4.setBackground(new Color(0x123456));
        panel4.setVisible(true);
        panel4.setBounds(0, 340, 800, 100);
        
        JLabel label = new JLabel();
        label.setText("RE-DEFINE");
        label.setForeground(new Color(0xFF899B));
        label.setFont(new Font("Sylfaen",Font.BOLD, 48));
        label.setVerticalAlignment(JLabel. TOP);
        label.setHorizontalAlignment(JLabel. LEFT);
        label.setBounds(175, 60, 800, 230);

        JLabel label2 = new JLabel();
        label2.setText("What Can I do for You?");
        label2.setForeground(new Color(237,247,246));
        label2.setFont(new Font("Segoe UI Semibold", Font.BOLD , 35));
        label2.setVerticalAlignment(JLabel. CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(25, 20, 575, 100);
        

        JButton button1 = new JButton();
        button1.setText("Recommend Music");
        button1.setFocusable(false);
        button1.setFont(new Font("Sans Display typeface", Font.BOLD , 16));
        button1.setForeground(new Color(0,0,0));
        button1.setBackground(new Color(0xFFB6AF));
        button1.setVerticalAlignment(JButton. CENTER);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setBounds(200, 45, 200, 30);

        button1.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new frame or window when the Recommend Music button is clicked
                @SuppressWarnings("unused")
                RecommendMusicWindow newwindow = new RecommendMusicWindow();
            }
        });
        
        JButton button2 = new JButton();
        button2.setText("Play Music");
        button2.setFocusable(false);
        button2.setFont(new Font("Sans Display typeface", Font.BOLD , 16));
        button2.setForeground(new Color(0,0,0));
        button2.setBackground(new Color(0xFFB6AF));
        button2.setVerticalAlignment(JButton. CENTER);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setBounds(200, 0, 200, 30);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new frame or window when the Play Music button is clicked
                @SuppressWarnings("unused")
                playmusic playmusic1=  new playmusic();
            }
        });
        
        myframe.add(panel1);
        myframe.add(panel2);
        myframe.add(panel3);
        myframe.add(panel4);
        panel1.add(label);
        panel2.add(label2);
        panel3.add(button1);
        panel4.add(button2);
    
    }
}
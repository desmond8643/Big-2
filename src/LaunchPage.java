import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage implements ActionListener {
    JFrame frame = new JFrame();
    JButton startGameButton = new JButton("Start Game");
    JButton quitGameButton = new JButton("Quit Game");

    LaunchPage() {
        startGameButton.setBounds(300, 200, 200, 40);
        startGameButton.setFocusable(false);
        startGameButton.addActionListener(this);
        startGameButton.setFont(new Font("Agency FB", Font.PLAIN, 20));
        startGameButton.setBackground(Color.BLACK);
        startGameButton.setForeground(Color.WHITE);

        quitGameButton.setBounds(300, 250, 200, 40);
        quitGameButton.setFocusable(false);
        quitGameButton.addActionListener(this);
        quitGameButton.setFont(new Font("Agency FB", Font.PLAIN, 20));
        quitGameButton.setBackground(Color.BLACK);
        quitGameButton.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("Big 2");
        titleLabel.setBounds(300, 100, 200, 70);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Agency FB", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/background/poker-background.jpg"));
        backgroundLabel.setBounds(0, 0, 800, 450);

        frame.setTitle("Big 2");
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(titleLabel);
        frame.getContentPane().add(startGameButton);
        frame.getContentPane().add(quitGameButton);
        frame.getContentPane().add(backgroundLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            frame.dispose();
            new GamePanel();
        }
        if (e.getSource() == quitGameButton) {
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaunchPage());
    }
}
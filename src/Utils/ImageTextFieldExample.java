package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageTextFieldExample extends JFrame {

    private JTextField imagePathField;
    private JButton chooseImageButton;
    private JLabel imageLabel;

    public ImageTextFieldExample() {
        chooseImageButton = new JButton("Choose Image");
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    imagePathField.setText(imagePath);

                    // Hiển thị hình ảnh tải lên
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage(); // chuyển ImageIcon thành Image
                    Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale hình ảnh
                    imageIcon = new ImageIcon(newImage);  // Chuyển lại thành ImageIcon có kích thước mới
                    imageLabel.setIcon(imageIcon); // Đặt hình ảnh mới cho Label
                }
            }
        });

        imagePathField = new JTextField(20);
        imageLabel = new JLabel();

        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.add(new JLabel("Image Path:"));
        imagePanel.add(imagePathField);
        imagePanel.add(chooseImageButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.NORTH);
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        add(mainPanel);

        setTitle("Image in JTextField Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

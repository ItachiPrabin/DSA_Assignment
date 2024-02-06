package Question_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Executors;

public class ImageDownloaderApp extends JFrame {
    private static final String DOWNLOAD_DIRECTORY = "downloaded_images/";
    private JTextField urlField;
    private JButton downloadButton, pauseButton, resumeButton, cancelButton;
    private JProgressBar progressBar;
    private ExecutorService executorService;
    private List<Future<?>> downloadTasks;

    public ImageDownloaderApp() {
        setTitle("Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        executorService = Executors.newFixedThreadPool(5); // Using a fixed thread pool with 5 threads
        downloadTasks = new CopyOnWriteArrayList<>(); // Using CopyOnWriteArrayList for thread safety
    }

    private void initComponents() {
        urlField = new JTextField(30);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlField.getText();
                if (!url.isEmpty()) {
                    downloadImage(url);
                } else {
                    JOptionPane.showMessageDialog(ImageDownloaderApp.this, "Please enter a valid URL.");
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownloads();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownloads();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownloads();
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(urlField, gbc);

        gbc.gridx = 1;
        panel.add(downloadButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(progressBar, gbc);

        gbc.gridx = 1;
        panel.add(pauseButton, gbc);

        gbc.gridy = 2;
        panel.add(resumeButton, gbc);

        gbc.gridy = 3;
        panel.add(cancelButton, gbc);

        getContentPane().add(panel);
    }

    private void downloadImage(String urlString) {
        Runnable downloadTask = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Some servers require a user agent

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        int contentLength = connection.getContentLength();
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        int totalBytesRead = 0;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            int progress = (int) ((totalBytesRead / (double) contentLength) * 100);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setValue(progress);
                                }
                            });
                        }

                        // Save image to disk
                        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                        saveImage(outputStream.toByteArray(), fileName);

                        JOptionPane.showMessageDialog(ImageDownloaderApp.this,
                                "Image downloaded successfully!");

                        inputStream.close();
                        outputStream.close();
                    } else {
                        throw new IOException("Failed to download image. Response code: " + responseCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ImageDownloaderApp.this,
                            "Error downloading image: " + e.getMessage());
                }
            }
        };

        Future<?> task = executorService.submit(downloadTask);
        downloadTasks.add(task);
    }

    private void saveImage(byte[] imageData, String fileName) {
        // Create directory if it does not exist
        File directory = new File(DOWNLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Construct the full path including the directory and file name
        String fullPath = DOWNLOAD_DIRECTORY + fileName;

        // Write image data to a file in the download directory
        try {
            FileOutputStream outputStream = new FileOutputStream(fullPath);
            outputStream.write(imageData);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseDownloads() {
        for (Future<?> task : downloadTasks) {
            task.cancel(true);
        }
    }

    private void resumeDownloads() {
        for (Future<?> task : downloadTasks) {
            if (task.isCancelled()) {
                executorService.submit((Runnable) task);
            }
        }
    }

    private void cancelDownloads() {
        for (Future<?> task : downloadTasks) {
            task.cancel(true);
        }
        progressBar.setValue(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageDownloaderApp();
            }
        });
    }
}

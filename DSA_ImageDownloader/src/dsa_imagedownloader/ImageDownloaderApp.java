/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dsa_imagedownloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;




 /* Class representing the main application frame for image downloading*/
 
public class ImageDownloaderApp extends javax.swing.JFrame {
    
    private ExecutorService executorService; // Executor service for managing threads
    private static final String DOWNLOAD_DIRECTORY = "downloaded_file/"; // Directory for downloaded files
    private List<Future<?>> downloadTasks; // List to store download tasks
    private Map<Future<?>, DownloadInfo> downloadInfoMap; // Map to store download information
    
//  Constructor for initializing the application frame
    public ImageDownloaderApp() {
         initComponents(); // Initialize components
        executorService = Executors.newFixedThreadPool(5); // Initialize executor service with fixed thread pool
        downloadTasks = new CopyOnWriteArrayList<>(); // Initialize list for download tasks
        downloadInfoMap = new ConcurrentHashMap<>(); // Initialize map for download information
        
    }

    
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        downloadImage = new javax.swing.JButton();
        PauseDownload = new javax.swing.JButton();
        ResumeDownload = new javax.swing.JButton();
        CancelDownload = new javax.swing.JButton();
        progressindicator = new javax.swing.JProgressBar();
        textarea = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1260, 670));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setLayout(null);

        downloadImage.setBackground(new java.awt.Color(238, 245, 255));
        downloadImage.setText("Download");
        downloadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadImageActionPerformed(evt);
            }
        });
        jPanel1.add(downloadImage);
        downloadImage.setBounds(890, 110, 122, 44);

        PauseDownload.setText("Pause");
        PauseDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseDownloadActionPerformed(evt);
            }
        });
        jPanel1.add(PauseDownload);
        PauseDownload.setBounds(480, 380, 117, 48);

        ResumeDownload.setText("Resume");
        ResumeDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResumeDownloadActionPerformed(evt);
            }
        });
        jPanel1.add(ResumeDownload);
        ResumeDownload.setBounds(480, 311, 120, 50);

        CancelDownload.setText("Cancel");
        CancelDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelDownloadActionPerformed(evt);
            }
        });
        jPanel1.add(CancelDownload);
        CancelDownload.setBounds(480, 450, 120, 47);

        progressindicator.setBackground(new java.awt.Color(255, 255, 255));
        progressindicator.setForeground(new java.awt.Color(255, 204, 204));
        jPanel1.add(progressindicator);
        progressindicator.setBounds(310, 240, 480, 30);

        textarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textareaActionPerformed(evt);
            }
        });
        jPanel1.add(textarea);
        textarea.setBounds(300, 90, 530, 80);

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Progress Bar");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(210, 240, 110, 20);

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Image Address");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(180, 120, 110, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1260, 670);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ResumeDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResumeDownloadActionPerformed
       resumeDownloads(); // Resume paused downloads
    }//GEN-LAST:event_ResumeDownloadActionPerformed

    private void PauseDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauseDownloadActionPerformed
       pauseDownloads(); // Pause ongoing downloads
    }//GEN-LAST:event_PauseDownloadActionPerformed

    private void CancelDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelDownloadActionPerformed
          cancelDownloads(); // Cancel ongoing downloads
    }//GEN-LAST:event_CancelDownloadActionPerformed

    private void downloadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadImageActionPerformed
        String urlsText = textarea.getText();
                String[] urls = urlsText.split("[,\\s]+"); // Split the text by commas or whitespace
                for (String url : urls) {
                    if (!url.isEmpty()) {
                        downloadImage(url); // Initiate download for each URL
                    }
                }
    
    }//GEN-LAST:event_downloadImageActionPerformed

    private void textareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textareaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textareaActionPerformed

    /**
     * @param args the command line arguments
     */
        // Method for downloading image from a given URL
       private void downloadImage(String urlString) {
        Runnable downloadTask = new Runnable() {
            @Override
            public void run() {
                DownloadInfo downloadInfo = downloadInfoMap.get(Thread.currentThread());
                int progress = downloadInfo != null ? downloadInfo.getProgress() : 0;
                try {
                    // Establish connection to URL
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    if (progress > 0) {
                        connection.setRequestProperty("Range", "bytes=" + progress + "-");
                    }
                    // Get response code
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                        int contentLength = connection.getContentLength();
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        // Read from input stream and write to output stream
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            progress += bytesRead;
                            int currentProgress = (int) ((progress / (double) contentLength) * 100);
                            // Update progress bar
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    progressindicator.setValue(currentProgress);
                                }
                            });
                            // Check if download interrupted
                            if (Thread.currentThread().isInterrupted()) {
                                throw new InterruptedException("Download interrupted");
                            }

                            Thread.sleep(50);
                        }

                        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                        saveImage(outputStream.toByteArray(), fileName);

//                        JOptionPane.showMessageDialog(ImageDownloaderApp.this,
//                                "Image downloaded successfully!");

                        inputStream.close();
                        outputStream.close();
                    } else {
                        throw new IOException("Failed to download image. Response code: " + responseCode);
                    }
                } catch (IOException | InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    if (!(e instanceof InterruptedException)) {
                        e.printStackTrace();
                       // JOptionPane.showMessageDialog(ImageDownloaderApp.this,
                              //  "Error downloading image: " + e.getMessage());
                    }
                }
            }
        };
        // Submit download task to executor service
        Future<?> task = executorService.submit(downloadTask);
        downloadTasks.add(task);
        downloadInfoMap.put(task, new DownloadInfo(urlString, 0));
    }
       // Method for saving image data to file
    private void saveImage(byte[] imageData, String fileName) {
        File directory = new File(DOWNLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fullPath = DOWNLOAD_DIRECTORY + fileName;

        try {
            FileOutputStream outputStream = new FileOutputStream(fullPath);
            outputStream.write(imageData);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method for resuming paused downloads
    private void resumeDownloads() {
        for (Future<?> task : downloadTasks) {
            if (task.isCancelled()) {
                DownloadInfo downloadInfo = downloadInfoMap.get(task);
                if (downloadInfo != null) {
                    downloadImage(downloadInfo.getUrl());
                }
            }
        }
    }
    // Method for pausing ongoing downloads
    private void pauseDownloads() {
        for (Future<?> task : downloadTasks) {
            if (!task.isDone() && !task.isCancelled()) {
                task.cancel(true);
            }
        }
    }
     // Method for canceling ongoing downloads
     private void cancelDownloads() {
        for (Future<?> task : downloadTasks) {
            task.cancel(true);
        }
        progressindicator.setValue(0); // Reset progress bar
    }
     // Inner class to hold download information
    private class DownloadInfo {
        private String url;
        private int progress;
        // Constructor
        public DownloadInfo(String url, int progress) {
            this.url = url;
            this.progress = progress;
        }
        // Getter for URL
        public String getUrl() {
            return url;
        }
        // Getter for progress
        public int getProgress() {
            return progress;
        }
    }
    // Main method to launch the application
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImageDownloaderApp().setVisible(true); // Set the application frame visible
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelDownload;
    private javax.swing.JButton PauseDownload;
    private javax.swing.JButton ResumeDownload;
    private javax.swing.JButton downloadImage;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar progressindicator;
    private javax.swing.JTextField textarea;
    // End of variables declaration//GEN-END:variables
}

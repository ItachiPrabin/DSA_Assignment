/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package socialmediaapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Acer
 */
public class viewpost extends javax.swing.JFrame {
    private int clickPost;
    private int userID;
        DefaultTableModel model;

  
    public viewpost(int userId ) {
        this.userID=userId;
        initComponents(); // Initialize GUI component
        setRecordsToTable(tablepost); // Display posts in the table
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panePost = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablepost = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panePost.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablepost.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablepost.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Content", "Likes"
            }
        ));
        tablepost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablepostMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablepost);

        panePost.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 910, 600));

        jButton2.setText("Dashboard");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panePost.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 690, 130, 50));

        jButton3.setText("like");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panePost.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 690, 130, 50));

        getContentPane().add(panePost, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 780));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tablepostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablepostMouseClicked
        // TODO add your handling code here:
        int rowNo = tablepost.getSelectedRow();
        TableModel model = tablepost.getModel();
        clickPost = Integer.parseInt(model.getValueAt(rowNo, 0).toString());
    }//GEN-LAST:event_tablepostMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new dashboard(userID).setVisible(true);
        
        dispose(); // Close the current interface
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        LikePost(clickPost); 
    }//GEN-LAST:event_jButton3ActionPerformed

    
    // Method to retrieve the username of a user by their ID
    public String getUsernameByUserId(int userId) {
        String username = null;
        Connection conn = dbConnection.dbconnect(); // Establish database connection

        try {
            // Prepare SQL query
            String query = "SELECT UserName FROM Users WHERE UserId = ?";

            // Execute query
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            if (rs.next()) {
                username = rs.getString("username");
            }
            conn.close(); // Close connection
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return username;
    }

    // Method to handle the action of liking a post
    private void LikePost(int postId){
        Connection conn = dbConnection.dbconnect(); // Establish database connection

        try{
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Likes (userID, postID) VALUES (?, ?)");
            pstmt.setInt(1, userID); // Set user ID
            pstmt.setInt(2, postId); // Set post ID
            pstmt.executeUpdate(); // Execute the insert query
            JOptionPane.showMessageDialog(null, "You have liked " + postId); // Display success message
            PreparedStatement pstmt2 = conn.prepareStatement("UPDATE Posts SET like_count = like_count + 1 WHERE post_id = ?");
            pstmt2.setInt(1, postId);
            pstmt2.executeUpdate(); // Update the like count of the post
            conn.close(); // Close connection
            setRecordsToTable(tablepost); // Refresh the post table
        } catch (Exception ex){
            ex.printStackTrace(); // Print error stack trace
        }
    }

    // Method to populate the post table with records from the database
    public void setRecordsToTable(JTable tablepost) {
        DefaultTableModel model = (DefaultTableModel) tablepost.getModel();
        model.setRowCount(0); // Clear existing table data
        try {
            Connection con = dbConnection.dbconnect(); // Establish database connection

            // Prepare SQL query to retrieve posts based on user's selected categories
            PreparedStatement pst = con.prepareStatement("SELECT p.post_id, p.userID, p.content, p.like_count " +
                                                           "FROM Posts p " +
                                                           "INNER JOIN UserCategories uc ON p.category_id = uc.CategoryId " +
                                                           "WHERE uc.UserId = ?");
            pst.setInt(1, userID); // Set user ID
            ResultSet rs = pst.executeQuery(); // Execute query

            // Iterate through the result set and populate the table
            while (rs.next()) {
                int postId = rs.getInt("post_id");
                int userId = rs.getInt("userID");
                String content = rs.getString("content");
                int likes = rs.getInt("like_count");

                Object[] obj = {postId, getUsernameByUserId(userId), content, likes};
                model.addRow(obj); // Add row to the table model
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print error stack trace
        }
    }

    // Method to find user ID by username (not currently used)
    public static int findUserIdByUsername(String username) {
        int userId = -1; // Default value if user is not found or if an error occurs
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection (you need to implement this)
            connection = dbConnection.dbconnect(); // Example method for establishing connection

            // Prepare SQL query
            String query = "SELECT userid FROM users WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute query
            resultSet = statement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                userId = resultSet.getInt("userid");
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle or log the exception as needed
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle or log the exception as needed
            }
        }

        return userId;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(viewpost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewpost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewpost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewpost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewpost(0).setVisible(true); // Open the view post interface
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panePost;
    private javax.swing.JTable tablepost;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package socialmediaapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class signup extends javax.swing.JFrame {
    
    private Connection conn;
    
    private Statement stmt;
    /**
     * Creates new form 
     */
    public signup() {
        initComponents(); // Initialize GUI components
        
        conn = dbConnection.dbconnect(); // Establish database connection

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        btnsignup = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        gaming = new javax.swing.JRadioButton();
        music = new javax.swing.JRadioButton();
        event = new javax.swing.JRadioButton();
        hobbies = new javax.swing.JRadioButton();
        art = new javax.swing.JRadioButton();
        anime = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 231, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Already an user?");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 520, 130, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Password:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, 83, -1));

        password.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 231, 28));

        btnsignup.setBackground(new java.awt.Color(0, 0, 255));
        btnsignup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsignup.setForeground(new java.awt.Color(255, 255, 255));
        btnsignup.setText("Sign Up");
        btnsignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsignupActionPerformed(evt);
            }
        });
        jPanel1.add(btnsignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, 130, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Username:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 83, 16));

        jButton3.setText("Login");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 520, 110, 30));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Choose your poison here");
        jPanel2.add(jLabel4);

        gaming.setText("Gaming");
        gaming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gamingActionPerformed(evt);
            }
        });
        jPanel2.add(gaming);

        music.setText("Music");
        music.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                musicActionPerformed(evt);
            }
        });
        jPanel2.add(music);

        event.setText("Events");
        event.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionPerformed(evt);
            }
        });
        jPanel2.add(event);

        hobbies.setText("Hobbies");
        jPanel2.add(hobbies);

        art.setText("Art");
        jPanel2.add(art);

        anime.setText("Anime");
        jPanel2.add(anime);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 240, 90));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Create your account here!!");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 210, 16));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnsignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsignupActionPerformed

       String Username = username.getText().trim(); // Get username from text field
        String pass = password.getText().trim();  // Get password from text field
        List<String> selectedCategories = getSelectedCategoriesFromUI(); // Get selected categories


        signup(Username,pass,selectedCategories );

    }//GEN-LAST:event_btnsignupActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new signin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void eventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eventActionPerformed

    private void musicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_musicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_musicActionPerformed

    private void gamingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gamingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gamingActionPerformed
    
   // Method to retrieve the selected categories from the UI
    private List<String> getSelectedCategoriesFromUI() {
        List<String> selectedCategories = new ArrayList<>();
        
        // Add selected categories to the list
        if (gaming.isSelected()) {
            selectedCategories.add("Gaming");
        }
        if (event.isSelected()) {
            selectedCategories.add("Event");
        }
        if (hobbies.isSelected()) {
            selectedCategories.add("Hobbies");
        }
        if (art.isSelected()) {
            selectedCategories.add("Art");
        }
        if (anime.isSelected()) {
            selectedCategories.add("Anime");
        }
        if (music.isSelected()) {
            selectedCategories.add("Music");
        }
        
        return selectedCategories;
    }
    
    // Method to perform the sign-up process
    private void signup(String Username, String Password, List<String> selectedCategories) {
        if (Username.equals("") || Password.equals("")) { // Check if username or password is empty
            JOptionPane.showMessageDialog(null, "Please fill in all the fields");
            return;
        }
        
        try {
            String query = "SELECT UserName FROM Users WHERE UserName = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, Username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) { // Check if the username already exists
                JOptionPane.showMessageDialog(null, "Username Already Taken. Please choose a different one.");
                return;
            }
            
            // Close the previous statement before creating a new one
            statement.close();

            // Insert the user into the Users table
            String insertUserSQL = "INSERT INTO Users (UserName, pass) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, Username);
                pstmt.setString(2, Password);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) { // If user insertion is successful
                    System.out.println("User inserted successfully");

                    // Retrieve the generated UserId
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        System.out.println("UserId of the inserted user: " + userId);

                        // Store the selected categories for the user
                        String insertCategorySQL = "INSERT INTO UserCategories (UserId, CategoryId) VALUES (?, ?)";
                        try (PreparedStatement pstmtCategories = conn.prepareStatement(insertCategorySQL)) {
                            for (String category : selectedCategories) {
                                // Retrieve the CategoryId for each selected category
                                String categoryIdQuery = "SELECT CategoryId FROM Categories WHERE CategoryName = ?";
                                PreparedStatement pstmtCategoryId = conn.prepareStatement(categoryIdQuery);
                                pstmtCategoryId.setString(1, category);
                                ResultSet rsCategoryId = pstmtCategoryId.executeQuery();
                                if (rsCategoryId.next()) {
                                    int categoryId = rsCategoryId.getInt("CategoryId");
                                    // Insert the UserId and CategoryId into UserCategories table
                                    pstmtCategories.setInt(1, userId);
                                    pstmtCategories.setInt(2, categoryId);
                                    pstmtCategories.addBatch();
                                }
                            }
                            // Execute batch insert
                            pstmtCategories.executeBatch();
                        }
                    }

                    // Show success message and open the sign-in interface
                    JOptionPane.showMessageDialog(null, "User successfully registered");
                    new signin().setVisible(true);
                    dispose(); // Close the current sign-up interface
                } else {
                    System.out.println("Data insertion failed");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the database or inserting data");
            e.printStackTrace();
        }
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
            java.util.logging.Logger.getLogger(signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(signup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new signup().setVisible(true); // Open the sign-up interface
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton anime;
    private javax.swing.JRadioButton art;
    private javax.swing.JButton btnsignup;
    private javax.swing.JRadioButton event;
    private javax.swing.JRadioButton gaming;
    private javax.swing.JRadioButton hobbies;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton music;
    private javax.swing.JTextField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

   
}

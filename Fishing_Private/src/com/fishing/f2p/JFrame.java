/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fishing.f2p;

/**
 *
 * @author t7emon
 */
public class JFrame extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    public JFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ShrimpjCheckBox = new javax.swing.JCheckBox();
        SalmonjCheckBox = new javax.swing.JCheckBox();
        StartjButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("                                T7emon's Fishing Bot");

        ShrimpjCheckBox.setText("Shrimps Fishing lvl 1 - 30");
        ShrimpjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShrimpjCheckBoxActionPerformed(evt);
            }
        });

        SalmonjCheckBox.setText("Salmon Fishing lvl 30 - 99");
        SalmonjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalmonjCheckBoxActionPerformed(evt);
            }
        });

        StartjButton.setText("Start");
        StartjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ShrimpjCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SalmonjCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(StartjButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ShrimpjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalmonjCheckBox)
                .addGap(103, 103, 103)
                .addComponent(StartjButton)
                .addGap(0, 107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ShrimpjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShrimpjCheckBoxActionPerformed
        Main.Shrimps_Fishing = true;
        SalmonjCheckBox.setSelected(false);
    }//GEN-LAST:event_ShrimpjCheckBoxActionPerformed

    private void SalmonjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalmonjCheckBoxActionPerformed
       Main.Salmon_Fishing = true;
       ShrimpjCheckBox.setSelected(false);
    }//GEN-LAST:event_SalmonjCheckBoxActionPerformed

    private void StartjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartjButtonActionPerformed
        Main.start = true;
        this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_StartjButtonActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox SalmonjCheckBox;
    private javax.swing.JCheckBox ShrimpjCheckBox;
    private javax.swing.JButton StartjButton;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}

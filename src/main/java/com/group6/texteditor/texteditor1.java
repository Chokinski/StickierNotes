
package com.group6.texteditor;

import com.group6.texteditor.customstuff.*;
import javax.swing.JFrame;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author Luiz Macedo, Aidan, Zach, Ohm
 */
public class texteditor1 extends JFrame implements UndoableEditListener, DocumentListener, ActionListener{
    public int x;
    public int y;
    public boolean onTop = true;
    public JFrame form = this;
    private JFileChooser fileChooser;
      private DefaultStyledDocument document;
    private StyledEditorKit kit;
    /**
     * Creates new form texteditor1
     */
     public texteditor1() {
        initComponents();
        
        jScroll.setVerticalScrollBar(new SBCustom());
        SBCustom sbH = new SBCustom();
        sbH.setOrientation(JScrollBar.HORIZONTAL);
        jScroll.setHorizontalScrollBar(sbH);
        form.setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20));
        
        // Initialize fileChooser
        fileChooser = new JFileChooser();
        
        // Configure fileChooser (optional)
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
        
         document = (DefaultStyledDocument) jTextPane1.getStyledDocument();
        kit = new StyledEditorKit();
       
    miFOpen1.addActionListener(this);
    miFSave1.addActionListener(this);
   miFDelete1.addActionListener(this);
    
      
    }
    
    
    private void clearTextPane() {
        jTextPane1.setText("");
    }

 
    // Method to perform delete action
    private void deleteAction() {
        clearTextPane();
      
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionCommand = evt.getActionCommand();

        switch (actionCommand) {
            case "miFDelete1":
                deleteAction();
                break;
            
       
            case "miFOpen1":
                openFile();
                break;
            case "miFSave1":
                saveFile();
                break;
            default:
                // Handle other actions...
        }
    }

  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScroll = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        JBItalic = new javax.swing.JButton();
        jBBold = new javax.swing.JButton();
        jBUl = new javax.swing.JButton();
        mBCustom1 = new com.group6.texteditor.customstuff.MBCustom();
        jMenu2 = new javax.swing.JMenu();
        miFSave1 = new javax.swing.JMenuItem();
        miFOpen1 = new javax.swing.JMenuItem();
        miFDelete1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StickierNotes");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(51, 51, 51));
        setForeground(java.awt.Color.black);
        setLocation(new java.awt.Point(50, 50));
        setUndecorated(true);
        setSize(new java.awt.Dimension(206, 211));

        jScroll.setBackground(new java.awt.Color(0, 0, 0));
        jScroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScroll.setForeground(new java.awt.Color(255, 255, 255));
        jScroll.setViewportBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jScroll.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextPane1.setBackground(new java.awt.Color(102, 102, 102));
        jTextPane1.setForeground(java.awt.Color.white);
        jTextPane1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jTextPane1.setName("StickyNotes"); // NOI18N
        jScroll.setViewportView(jTextPane1);

        jToolBar1.setBackground(new java.awt.Color(130, 142, 175));
        jToolBar1.setFloatable(false);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar1.setMaximumSize(new java.awt.Dimension(100000, 10));
        jToolBar1.setMinimumSize(new java.awt.Dimension(0, 0));
        jToolBar1.setName(""); // NOI18N
        jToolBar1.setPreferredSize(new java.awt.Dimension(234, 18));
        jToolBar1.setRequestFocusEnabled(false);

        JBItalic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/italic.png"))); // NOI18N
        JBItalic.setToolTipText("Italicize...");
        JBItalic.setBorderPainted(false);
        JBItalic.setContentAreaFilled(false);
        JBItalic.setFocusable(false);
        JBItalic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JBItalic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        JBItalic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBItalicActionPerformed(evt);
            }
        });
        jToolBar1.add(JBItalic);

        jBBold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bold.png"))); // NOI18N
        jBBold.setToolTipText("Bold...");
        jBBold.setBorderPainted(false);
        jBBold.setContentAreaFilled(false);
        jBBold.setFocusable(false);
        jBBold.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBBold.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBoldActionPerformed(evt);
            }
        });
        jToolBar1.add(jBBold);

        jBUl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/underline.png"))); // NOI18N
        jBUl.setToolTipText("Underline...");
        jBUl.setContentAreaFilled(false);
        jBUl.setFocusable(false);
        jBUl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBUl.setMargin(new java.awt.Insets(-10, 0, -10, 0));
        jBUl.setMaximumSize(new java.awt.Dimension(30, 30));
        jBUl.setMinimumSize(new java.awt.Dimension(5, 5));
        jBUl.setPreferredSize(new java.awt.Dimension(20, 20));
        jBUl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBUl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUlActionPerformed(evt);
            }
        });
        jToolBar1.add(jBUl);

        mBCustom1.setColor(new java.awt.Color(135, 141, 148));
        mBCustom1.setDoubleBuffered(true);
        mBCustom1.setOpaque(true);
        mBCustom1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mBCustom1MouseDragged(evt);
            }
        });
        mBCustom1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mBCustom1MousePressed(evt);
            }
        });

        jMenu2.setMnemonic('F');
        jMenu2.setText("File");

        miFSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jwebedit/icons/Save16.gif"))); // NOI18N
        miFSave1.setMnemonic('S');
        miFSave1.setText("Save");
        miFSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFSave1ActionPerformed(evt);
            }
        });
        jMenu2.add(miFSave1);

        miFOpen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jwebedit/icons/Open16.gif"))); // NOI18N
        miFOpen1.setMnemonic('O');
        miFOpen1.setText("Open");
        miFOpen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFOpen1ActionPerformed(evt);
            }
        });
        jMenu2.add(miFOpen1);

        miFDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jwebedit/icons/Delete16.gif"))); // NOI18N
        miFDelete1.setMnemonic('D');
        miFDelete1.setText("Delete");
        miFDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFDelete1ActionPerformed(evt);
            }
        });
        jMenu2.add(miFDelete1);

        mBCustom1.add(jMenu2);

        jMenu4.setBackground(new java.awt.Color(153, 153, 153));
        jMenu4.setText("+");
        jMenu4.setToolTipText("");
        jMenu4.setBorderPainted(false);
        jMenu4.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        mBCustom1.add(jMenu4);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pinlight.png"))); // NOI18N
        jMenu3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pinlight.png"))); // NOI18N
        jMenu3.setRequestFocusEnabled(false);
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        mBCustom1.add(jMenu3);

        setJMenuBar(mBCustom1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScroll)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void miFSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFSave1ActionPerformed
         saveFile();
    }//GEN-LAST:event_miFSave1ActionPerformed

    private void miFOpen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFOpen1ActionPerformed
         openFile();
    }//GEN-LAST:event_miFOpen1ActionPerformed

    private void miFDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFDelete1ActionPerformed
      deleteAction();
    }//GEN-LAST:event_miFDelete1ActionPerformed

    private void mBCustom1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mBCustom1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_mBCustom1MousePressed

    private void mBCustom1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mBCustom1MouseDragged
        setLocation(evt.getXOnScreen() - x, evt.getYOnScreen() - y);    
    }//GEN-LAST:event_mBCustom1MouseDragged

    private void JBItalicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBItalicActionPerformed
        StyledEditorKit.ItalicAction italicAction = new StyledEditorKit.ItalicAction();
    italicAction.actionPerformed(evt);
    }//GEN-LAST:event_JBItalicActionPerformed

    private void jBBoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBoldActionPerformed
        StyledEditorKit.BoldAction boldAction = new StyledEditorKit.BoldAction();
    boldAction.actionPerformed(evt);
    }//GEN-LAST:event_jBBoldActionPerformed

    private void jBUlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUlActionPerformed
                StyledEditorKit.UnderlineAction underlineAction = new StyledEditorKit.UnderlineAction();
    underlineAction.actionPerformed(evt);
    }//GEN-LAST:event_jBUlActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
    new texteditor1().setVisible(true);
    }//GEN-LAST:event_jMenu4MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        if (onTop) {
            form.setAlwaysOnTop(false);
            onTop = false;
        }
        else {
            form.setAlwaysOnTop(true);
            onTop = true;
        }
    }//GEN-LAST:event_jMenu3MouseClicked
   
    private void openFile() {
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            String content = Files.readString(selectedFile.toPath());
            jTextPane1.setText(content);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }
    }
}

private void deleteFile() {
            clearTextPane();

    JOptionPane.showMessageDialog(this, "Content cleared successfully");
}

private void saveFile() {
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            FileWriter writer = new FileWriter(selectedFile);
            writer.write(jTextPane1.getText());
            writer.close();
            JOptionPane.showMessageDialog(this, "File saved successfully");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }
}



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
            java.util.logging.Logger.getLogger(texteditor1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(texteditor1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(texteditor1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(texteditor1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new texteditor1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBItalic;
    private javax.swing.JButton jBBold;
    private javax.swing.JButton jBUl;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JScrollPane jScroll;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private com.group6.texteditor.customstuff.MBCustom mBCustom1;
    private javax.swing.JMenuItem miFDelete1;
    private javax.swing.JMenuItem miFOpen1;
    private javax.swing.JMenuItem miFSave1;
    // End of variables declaration//GEN-END:variables
 
 


    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}

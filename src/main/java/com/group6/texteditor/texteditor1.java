package com.group6.texteditor;

import javax.swing.JFrame;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.UndoableEditEvent;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *
 * @author Luiz Macedo, Aidan, Zach, Ohm
 */
public class texteditor1 extends JFrame implements UndoableEditListener, DocumentListener, ActionListener {

    public int x;
    public int y;
    public boolean onTop = true; // by default always stay pinned

    public JFrame form = this;
    private final JFileChooser fileChooser;
    private final DefaultStyledDocument document;
    private final StyledEditorKit kit;
    private final ImageIcon darkPin = new ImageIcon(getClass().getResource("/images/pindark.png"));
    private final ImageIcon lightPin = new ImageIcon(getClass().getResource("/images/pinlight.png"));
    private final UIManager manager = new UIManager();
    UndoManager undoManager;
    private Timer editTimer;
    private static final int EDIT_TIMEOUT = 500;
    private String lastCommittedText = "";
    private List<Integer> typedCharacterCounts = new ArrayList<>();
    private int currentcount = 0;
    private boolean isBold = false;
    private boolean isItal = false;
    private boolean isUl = false;

    /**
     * Creates new form texteditor1
     */
    public texteditor1() {

        try {
            manager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        // Set FlatLaf Dark look and feel

        jBBold.setBackground(null);
        jBBold.setForeground(null);
        JBItalic.setBackground(null);
        JBItalic.setForeground(null);
        jBUl.setBackground(null);
        jBUl.setForeground(null);
        jMenu3.setFocusCycleRoot(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setMaximizedBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));

        // Initialize fileChooser
        fileChooser = new JFileChooser();

        // Configure fileChooser (optional)
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));

        document = (DefaultStyledDocument) jTextPane1.getStyledDocument();
        kit = new StyledEditorKit();
        jTextPane1.setEditorKit(kit);
        // Initialize UndoManager
        undoManager = new UndoManager(); // Ensure UndoManager is initialized
        jTextPane1.getDocument().addUndoableEditListener(this);

        
        miFOpen1.addActionListener(this);
        miFSave1.addActionListener(this);
        miFDelete1.addActionListener(this);
        jTextPane1.addCaretListener(e -> updateButtonStates());
        if (onTop == true) {

            jMenu3.setIcon(darkPin);
        } else {

            jMenu3.setIcon(lightPin);
        }

        editTimer = new Timer(EDIT_TIMEOUT, e -> commitLastEdit());
        editTimer.setRepeats(false); // Ensure it only fires once

        jTextPane1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                currentcount++;
                resetEditTimer(); // Restart the timer on key typed
            }
        });

    }

    private void clearTextPane() {
        jTextPane1.setText("");
    }

    // Method to perform delete action
    private void deleteAction() {
        clearTextPane();

    }

    private void commitLastEdit() {
        if (currentcount > 0) { // Check if any characters were typed
            typedCharacterCounts.add(currentcount); // Add current count to the list
            System.out.println("Characters typed since last pause: " + currentcount);
            currentcount = 0; // Reset current count
        }
        lastCommittedText = jTextPane1.getText(); // Update the last committed text
    }

    private void resetEditTimer() {
        editTimer.restart();
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        // editTimer.restart(); // Reset the timer on each edit
        undoManager.addEdit(e.getEdit());
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



    // Method to create a simple fade-out and fade-in transition between two icons
    private void fadeIcon(ImageIcon currentIcon, ImageIcon newIcon) {
        final float[] alpha = { 1.0f }; // Use an array to hold the alpha value, since it's mutable

        // Timer for fading out the current icon
        Timer fadeOutTimer = new Timer(50, e -> {
            alpha[0] -= 0.1f;
            if (alpha[0] <= 0.0f) {
                ((Timer) e.getSource()).stop(); // Stop the fade-out timer
                jMenu3.setIcon(newIcon); // Switch to the new icon

                // After fading out, start fading in the new icon
                Timer fadeInTimer = new Timer(50, evt -> {
                    alpha[0] += 0.1f;
                    jMenu3.setIcon(createTransparentIcon(newIcon, alpha[0]));
                    if (alpha[0] >= 1.0f) {
                        ((Timer) evt.getSource()).stop(); // Stop the fade-in timer
                    }
                });
                fadeInTimer.start();
            } else {
                jMenu3.setIcon(createTransparentIcon(currentIcon, alpha[0])); // Continue fading out
            }
        });

        fadeOutTimer.start();
    }

    // Create a transparent version of the icon with a given alpha value
    private ImageIcon createTransparentIcon(ImageIcon icon, float alpha) {
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(icon.getImage(), 0, 0, null);
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Update button states based on selected text attributes
    private void updateButtonStates() {
        int start = jTextPane1.getSelectionStart();
        int end = jTextPane1.getSelectionEnd();

        // Get the selected text's attributes
        AttributeSet attributes = jTextPane1.getCharacterAttributes();

        // Check if bold, italic, and underline are applied
        boolean isBold = StyleConstants.isBold(attributes);
        boolean isItalic = StyleConstants.isItalic(attributes);
        boolean isUnderlined = StyleConstants.isUnderline(attributes);

        // Update button states visually
        jBBold.setSelected(isBold);
        JBItalic.setSelected(isItalic);
        jBUl.setSelected(isUnderlined);

        // Change background based on state
        jBBold.setSelected(isBold);
        JBItalic.setSelected(isItalic);
        jBUl.setSelected(isUnderlined);

        // Change background based on state
        jBBold.setBackground(isBold ? Color.LIGHT_GRAY : null);
        JBItalic.setBackground(isItalic ? Color.LIGHT_GRAY : null);
        jBUl.setBackground(isUnderlined ? Color.LIGHT_GRAY : null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScroll = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        JBItalic = new javax.swing.JButton();
        jBBold = new javax.swing.JButton();
        jBUl = new javax.swing.JButton();
        mBCustom1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        miFSave1 = new javax.swing.JMenuItem();
        miFOpen1 = new javax.swing.JMenuItem();
        miFDelete1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jUndo = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StickierNotes");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(51, 51, 51));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setForeground(java.awt.Color.black);
        setLocation(new java.awt.Point(50, 50));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 2, 2));
        setSize(new java.awt.Dimension(206, 211));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        jToolBar1.setBackground(new java.awt.Color(255, 255, 204));
        jToolBar1.setFloatable(false);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar1.setMaximumSize(new java.awt.Dimension(100000, 10));
        jToolBar1.setMinimumSize(new java.awt.Dimension(0, 0));
        jToolBar1.setName(""); // NOI18N
        jToolBar1.setPreferredSize(new java.awt.Dimension(234, 18));
        jToolBar1.setRequestFocusEnabled(false);

        JBItalic.setBackground(new Color(0, 0, 0, 0));
        JBItalic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/italic.png"))); // NOI18N
        JBItalic.setToolTipText("Italicize...");
        JBItalic.setBorderPainted(false);
        JBItalic.setDoubleBuffered(true);
        JBItalic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JBItalic.setOpaque(true);
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
        jBBold.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBBold.setOpaque(true);
        jBBold.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBoldActionPerformed(evt);
            }
        });
        jToolBar1.add(jBBold);

        jBUl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/underline.png"))); // NOI18N
        jBUl.setToolTipText("Underline...");
        jBUl.setBorderPainted(false);
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

        mBCustom1.setBackground(new java.awt.Color(60, 63, 65));
        mBCustom1.setMaximumSize(new java.awt.Dimension(32768, 21));
        mBCustom1.setMinimumSize(new java.awt.Dimension(80, 21));
        mBCustom1.setOpaque(true);
        mBCustom1.setPreferredSize(new java.awt.Dimension(195, 21));

        jMenu2.setBorder(null);
        jMenu2.setMnemonic('F');
        jMenu2.setText("File");
        jMenu2.setBorderPainted(false);
        jMenu2.setContentAreaFilled(false);
        jMenu2.setDoubleBuffered(true);
        jMenu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu2.setPreferredSize(new java.awt.Dimension(30, 16));

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

        jMenu3.setBorder(null);
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pinlight.png"))); // NOI18N
        jMenu3.setToolTipText("Pin");
        jMenu3.setBorderPainted(false);
        jMenu3.setContentAreaFilled(false);
        jMenu3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pinlight.png"))); // NOI18N
        jMenu3.setDoubleBuffered(true);
        jMenu3.setFocusable(false);
        jMenu3.setRequestFocusEnabled(false);
        jMenu3.setRolloverEnabled(false);
        jMenu3.setVerifyInputWhenFocusTarget(false);
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        mBCustom1.add(jMenu3);

        jUndo.setBorder(null);
        jUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jwebedit/icons/Undo16.gif"))); // NOI18N
        jUndo.setToolTipText("Pin");
        jUndo.setBorderPainted(false);
        jUndo.setContentAreaFilled(false);
        jUndo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pinlight.png"))); // NOI18N
        jUndo.setDoubleBuffered(true);
        jUndo.setFocusable(false);
        jUndo.setRequestFocusEnabled(false);
        jUndo.setRolloverEnabled(false);
        jUndo.setVerifyInputWhenFocusTarget(false);
        jUndo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUndoMouseClicked(evt);
            }
        });
        mBCustom1.add(jUndo);

        setJMenuBar(mBCustom1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScroll)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JBItalicActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_JBItalicActionPerformed
        StyledEditorKit.ItalicAction italicAction = new StyledEditorKit.ItalicAction();
        italicAction.actionPerformed(evt);
        updateButtonStates();
    }// GEN-LAST:event_JBItalicActionPerformed

    private void jBBoldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBBoldActionPerformed
        StyledEditorKit.BoldAction boldAction = new StyledEditorKit.BoldAction();
        boldAction.actionPerformed(evt);
        updateButtonStates();

    }// GEN-LAST:event_jBBoldActionPerformed

    private void jBUlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBUlActionPerformed
        StyledEditorKit.UnderlineAction underlineAction = new StyledEditorKit.UnderlineAction();
        underlineAction.actionPerformed(evt);
        updateButtonStates();
    }// GEN-LAST:event_jBUlActionPerformed

    private void miFSave1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miFSave1ActionPerformed
        saveFile();
    }// GEN-LAST:event_miFSave1ActionPerformed

    private void miFOpen1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miFOpen1ActionPerformed
        openFile();
    }// GEN-LAST:event_miFOpen1ActionPerformed

    private void miFDelete1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_miFDelete1ActionPerformed
        deleteFile();
    }// GEN-LAST:event_miFDelete1ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jMenu3MouseClicked
        if (onTop) {
            form.setAlwaysOnTop(false);
            onTop = false;
            fadeIcon((ImageIcon) jMenu3.getIcon(), lightPin);
        } else {
            form.setAlwaysOnTop(true);
            onTop = true;

            fadeIcon((ImageIcon) jMenu3.getIcon(), darkPin);
        }
        jMenu3.setFocusable(false); // Make the JMenu non-focusable
        jMenu3.setSelected(false); // Ensure it remains unselected
    }// GEN-LAST:event_jMenu3MouseClicked

    private void jUndoMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jUndoMouseClicked
        undo();

    }// GEN-LAST:event_jUndoMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        form.setAlwaysOnTop(false);
        onTop = false;

        int result = JOptionPane.showConfirmDialog(null, "Would you like to save before exiting?", "Exit Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {

            new texteditor1().setVisible(true);
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
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
    }// GEN-LAST:event_formWindowClosing

    public void undo() {
        /*
         * all this did was undoManager.undo();
         * 
         * 
         */
        if (typedCharacterCounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to undo.");
            return;
        }

        int lastTypedCount = typedCharacterCounts.remove(typedCharacterCounts.size() - 1); // Get and remove last count

        try {
            for (int i = 0; i < lastTypedCount; i++) {
            undoManager.undo();
            }
            //JOptionPane.showMessageDialog(this, "Undo action performed: " + lastTypedCount + " characters.");
        } catch (CannotUndoException e) {
            //JOptionPane.showMessageDialog(this, "Unable to undo. Please try again.", "Error",
            //        JOptionPane.ERROR_MESSAGE);
        }
    }

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
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    private javax.swing.JScrollPane jScroll;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu jUndo;
    private javax.swing.JMenuBar mBCustom1;
    private javax.swing.JMenuItem miFDelete1;
    private javax.swing.JMenuItem miFOpen1;
    private javax.swing.JMenuItem miFSave1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

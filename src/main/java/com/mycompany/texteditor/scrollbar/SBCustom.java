/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.texteditor.scrollbar;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;
/**
 *
 * @author Aidan
 */
public class SBCustom extends JScrollBar{

    public SBCustom() {
    setUI(new ModernScroller());
    setPreferredSize(new Dimension(8,8));
    setForeground(new Color(48,144,216));
    setBackground(Color.WHITE);
    }
}

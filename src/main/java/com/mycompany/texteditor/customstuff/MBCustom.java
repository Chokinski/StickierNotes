/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.texteditor.customstuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JMenuBar;

/**
 *
 * @author Aidan
 */
public class MBCustom extends JMenuBar{
    public Color bgCo = new Color(153,153,153);
    
    public void setColor(Color co) {
    bgCo = co;}
    
    @Override
    protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(bgCo);
    g2.fillRect(0,0, getWidth() - 1, getHeight() -1);
    }
}

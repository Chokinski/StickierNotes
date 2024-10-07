/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group6.texteditor.customstuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Aidan
 */
public class ModernScroller extends BasicScrollBarUI {
    private final int THUMB_SIZE = 60;
    @Override
    protected Dimension getMaximumThumbSize(){
        if(scrollbar.getOrientation()==JScrollBar.VERTICAL){
        return new Dimension(0,THUMB_SIZE);
        }
        else{
        return new Dimension(THUMB_SIZE, 0);    
        }
    }
    
    @Override
    protected Dimension getMinimumThumbSize() {
        return super.getMinimumThumbSize();
    
    }
    
    @Override
    protected JButton createDecreaseButton(int i) {
        return new ScrollBarButton();}
    
    
        @Override
    protected JButton createIncreaseButton(int i) {
        return new ScrollBarButton();}
    
    @Override
    protected void paintTrack(Graphics gp, JComponent jc, Rectangle rec) {
        Graphics2D g2 = (Graphics2D) gp;
        g2.setRenderingHint
        (RenderingHints.KEY_ANTIALIASING,
         RenderingHints.VALUE_ANTIALIAS_ON);
        int orien= scrollbar.getOrientation();
        int size, x, y, w, h;
        if (orien == JScrollBar.VERTICAL) {
        size = rec.width /2;
        x=rec.x + ((rec.width-size)/2);
        y=rec.y;
        w=size;
        h=rec.height;}
        else {
        size = rec.height/2;
        y=rec.y+((rec.height-size)/2);
        x = 0;
        w = rec.width;
        h = size;
        }
        g2.setColor(new Color(240,240,240));
        g2.fillRect(x, y, w, h);
    }
    
    @Override
    protected void paintThumb(Graphics gp, JComponent jc, Rectangle rec){
        Graphics2D g2 = (Graphics2D) gp;
        g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        int x=rec.x;
        int y=rec.y;
        int w = rec.width;
        int h = rec.height;
        if(scrollbar.getOrientation() == JScrollBar.VERTICAL){
            y+=8;
            h -=16;
            
        } else{
            x +=8;
            w -=16;
        }
        g2.setColor(scrollbar.getForeground());
        g2.fillRoundRect(x,y,w,h,10,10);
    }
    private class ScrollBarButton extends JButton {
    
    public ScrollBarButton(){
        setBorder(BorderFactory.createEmptyBorder());
        
    }
    
    @Override
    public void paint(Graphics gp) {
    
    }
    
    
    }
    
}

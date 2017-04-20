/*
 * LeafJFrame.java
 * 
 * Original project proposal:
 * 
 * The program would be a generator for life-like tree leaf images. The leaves would have variance within 
 * species, and would have the feature of a branching vein generation system. The leaves would be 
 * generated in similar structures as real-life leaves. The structures would for example include veins 
 * extending from one point on the leaf, veins branching from one midrib along the length of the leaf, 
 * and more. While veins are key features of leaves, this is put in place mainly to allow for the easier 
 * creation of the actual leaf. The veins would determine the path of the margin of the leaf, enclosing 
 * the middle of the leaf. The margins would also be customizable, with the margins able to be specified 
 * as a given pattern (smooth, finely-toothed, saw-toothed, etc.). 
 * 
 * This program would have applications mainly limited to research. The main inspiration for this program 
 * is my science fair project, where it was eventually shown that I needed a high quantity of leaf images 
 * to improve the accuracy of the leaf-identification program. This program would allow me to program in 
 * species of leaves and then batch-create hundreds, perhaps even thousands of leaf images. Other research 
 * projects that involve computer vision could also perhaps benefit from this project. Additionally, the 
 * program's methods could perhaps be useful to graphic designers who need random leaves for their project.
 * For example, the leaves could be used in a game to make trees look life-like.
 */

// import statements
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;

import GUI.LeafGenerator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pthom
 */
public class Main extends GUI.LeafGenerator 
{
    public Main() {
        super();
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
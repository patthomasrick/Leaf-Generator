/**
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
 * program’s methods could perhaps be useful to graphic designers who need random leaves for their project.
 * For example, the leaves could be used in a game to make trees look life-like.
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LeafJFrame extends JFrame implements ItemListener
{
	// static strings
	final static String WINDOW_TITLE = "Leaf Generator";
	final static String COMBO_OPT1 = "Option 1";
	final static String COMBO_OPT2 = "Option 2";
	
	// panel
	JPanel cards;

	/** To supress warning... */
	private static final long serialVersionUID = 1L;

	public LeafJFrame()
	{
		super(WINDOW_TITLE);
	} // end constructor
	
	public void createMenuBar()
	{
		// create the bar itself
		JMenuBar menuBar = new JMenuBar();
		
		// create the "File" menu
		JMenu menuFile = new JMenu("File");
		
		// create Exit option
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		// add menu items to the menu
		menuFile.add(menuItemExit);
		
		// add menubar
		menuBar.add(menuFile);
		 
		// adds menu bar to the frame
		this.setJMenuBar(menuBar);
	} // end createMenuBar
	
	public void createComboPane(Container pane)
	{
		JPanel comboBoxPane = new JPanel();
		String[] comboBoxItems = {COMBO_OPT1, COMBO_OPT2};
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb =  new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		
		comboBoxPane.add(cb);
		
		pane.add(comboBoxPane, BorderLayout.PAGE_START);
	} // end createComboPane
	
	public void createCards(Container pane)
	{
		JPanel card1 = new JPanel();
		card1.add(new JButton("Button 1"));
		
		JPanel card2 = new JPanel();
		card2.add(new JTextField("TextField", 20));
		
		this.cards = new JPanel(new CardLayout());
		
		this.cards.add(card1, "Card 1");
		this.cards.add(card2, "Card 2");
		
		pane.add(this.cards, BorderLayout.CENTER);
	} // end createCards
	
    public void itemStateChanged(ItemEvent e)
    {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) e.getItem());
    }
	
	public static void main(String[] args)
	{
		// create frame
		LeafJFrame frame = new LeafJFrame();
		frame.setLayout(new CardLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// add menu
		frame.createMenuBar();
		
		// add cards
		frame.createComboPane(frame.getContentPane());
		frame.createCards(frame.getContentPane());
		
		// make the window visible
		frame.pack();
		frame.setVisible(true);
	} // end main
} // end LeafJFrame

//// import statements 
//import java.awt.Container;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
// 
///* FrameDemo.java requires no other files. */
//public class LeafJFrame
//{
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI()
//    {
//        //Create and set up the window.
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        JFrame frame = new JFrame("Leaf Generator");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        Container menuControls = frame.getContentPane();
//        JButton helloButton = new JButton("Hello");
//        menuControls.add(helloButton);
// 
////        JLabel emptyLabel = new JLabel("Hello");
////        emptyLabel.setPreferredSize(new Dimension(800, 600));
////        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
//        
//        //Display the window.
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//
//        frame.setSize(800, 600);
//        frame.setVisible(true);
//    } // end createAndShowGUI
// 
//    public static void main(String[] args)
//    {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                createAndShowGUI();
//            } // end run()
//        });
//    } // end main
//} // end LeafJFrame
package pobj.graphic;

import pobj.structure.Analyzer;
import pobj.structure.DataContainer;
import pobj.structure.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author Haron
 *	G�re une fen�tre
 */
public class Window extends JFrame
{
	private String file = null;
	private JTree jTree = null;
	private JMenuBar menu = null;
	
	private JButton bouton = null;
	private JPanel panneau = null;

	private Analyzer analyzer;

	public Window(String name,int w,int h) 
	{
		
		super(name);
		
		try {
		      File myObj = new File("log.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		      System.setOut(new PrintStream(myObj));
		      System.out.println(new java.util.Date());
		      System.out.println("\n\n");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		ImageIcon img = new ImageIcon("./wireshark_logo.png");
		setIconImage(img.getImage());
		
		WindowListener l = new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{
				closing();
			}
		};

		addWindowListener(l); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(w, h));
		setJMenuBar(menu = new Menu(this)); //TODO
		setVisible(true);
	
		
		if(file == null)
		{
			//cr�ation d'un bouton
			this.bouton = new JButton("Open a Frame");
			
			//cr�ation d'un panneau h�bergeant le bouton
			this.panneau = new JPanel();
			panneau.add(bouton);
			setContentPane(panneau);
			setVisible(false);
			setVisible(true);
			
			bouton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Graphic :Button pressed");
					((Menu) menu).openDialog();
					if(file != null)
					{
						setVisible(false);
						panneau.remove(bouton);
						remove(panneau);
						setVisible(true);
					}
				}
			});
		}
	 }
	
	public String getFile()
	{
		return file;
	}
	
	public void setFile(String file)
	{
		this.file= file;
		String[] args = {file,"./res.txt"};
		this.analyzer = new Analyzer(args);
		showTree(this.analyzer.getFrameList());
		hidePannel(panneau, bouton);
	}

	public void showTree(ArrayList<Frame> s)
	{
		setVisible(false);
		if(jTree == null)
			System.out.println("Graphic : Tree = null");
		else
		{
			remove(((Tree) jTree).getTreeContainer());
		}

		jTree = new Tree(this);
		DataContainer dc;
		int i= 0;
		for (Frame frame : s) 
		{
			dc = new DataContainer(i, frame);
			((Tree) jTree).setData(dc);
			System.out.println(i);
			i++;
		}

		((Tree)jTree).createTrameTree(this);
		setVisible(true);
	}
	
	public void hidePannel(JPanel arg0,JButton arg1)
	{
		setVisible(false);
		arg0.remove(arg1);
		remove(arg0);
		setVisible(true);
	}
	public void closing()
	{
		System.out.println("Graphic : exit");
		file = null;
		System.exit(0);
	}
	public Tree getTree()
	{
		return (Tree)jTree;
	}
	
	public void initDecryptorAnalyzor(File file)
	{
		
	}

	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

}

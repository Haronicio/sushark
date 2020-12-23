package pobj.graphic;

import pobj.*;
import pobj.structure.Analyzer;
import pobj.structure.DataContainer;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import javax.swing.*;


/**
 * @author Haron
 *	g�re la bar de menu
 */
public class Menu extends JMenuBar
{
	Tree tree;
	Window parent;

	public Menu(Window parent)
	{
		this.parent = parent;
		//Listener
		ActionListener afficherMenuListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				switch (event.getActionCommand()) {
				case "About": 
				{
					System.out.println("Graphic : about");
					JDialog d = new JDialog(parent);
					JLabel l1,l2;
					d.setSize(230, 230);
					d.setLocationRelativeTo(null);
					d.add(l1 = new JLabel("Projet dans le cadre de l'UE r�seaux"));
					l1.setBounds(0, 20, 230, 10);
					d.add(new JLabel("par Haron DAUVET et Aur�lien DUVAL"));
					d.setVisible(true);
					break;
				}
				case "Exit":
				{
					parent.closing();
				}
				case "Open":
				{
					openDialog();
					break;
				}
				case "Debug":
				{
					debugDialog();
					break;
				}
				case "Export":
				{
					exportDialog();
					break;
				}
				default:
					throw new IllegalArgumentException("Graphic : Unexpected value: " + event.getActionCommand());
				}
		    }
		};
		
	    JMenuItem item;
	    
		//Cr�ation du menu Fichier
		JMenu fichierMenu = new JMenu("File");
	    
	    item = new JMenuItem("Open", 'O');
	    item.addActionListener(afficherMenuListener);
	    fichierMenu.add(item);
	    item = new JMenuItem("Export", 'E');
	    item.addActionListener(afficherMenuListener);
	    fichierMenu.insertSeparator(1);
	    fichierMenu.add(item);
	    item = new JMenuItem("Exit");
	    item.addActionListener(afficherMenuListener);
	    fichierMenu.add(item);
	    
	    //Cr�ation du menu Help
	    JMenu helpMenu = new JMenu("Help");
	    
	    item = new JMenuItem("Debug");
	    item.addActionListener(afficherMenuListener);
	    helpMenu.add(item);
	    item = new JMenuItem("About");
	    item.addActionListener(afficherMenuListener);
	    helpMenu.add(item);
	    
	    add(fichierMenu);
	    add(helpMenu);
	    
	    
		
	}
	public void openDialog()
	{
		System.out.println("Graphic : open");
		//cr�ation d'une boite de dialogue pour h�berger le s�lectionneur
		JDialog d = new JDialog(parent);
		d.setLocationRelativeTo(null);
		d.setAlwaysOnTop(true);
		//cr�� un s�lectionneur de fichier
		JFileChooser fileChooser = new JFileChooser();
		//r�pertoir actuelle comme r�pertoir par d�faut
		fileChooser.setCurrentDirectory(new File("./data"));
		//le fichier s�lectionn� sous forme binaire est dans r�sult
		int result = fileChooser.showOpenDialog(d);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Graphic : Selected file: " + selectedFile.getAbsolutePath());
			parent.setFile(selectedFile.getAbsolutePath());

		}
//		
		
	}
	
	public void debugDialog()
	{
		System.out.println("Graphic : debug");
		//cr�ation d'une boite de dialogue pour h�berger le debug
		JDialog d = new JDialog(parent);
		
		d.setSize(500, 500);
		
		d.setLocationRelativeTo(null);
		JTextArea text = new TextArea("log.txt");
		JScrollPane scrollP = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollP.setBorder(BorderFactory.createEmptyBorder());
		((TextArea) text).printLog();
		
		
		d.add(scrollP);
//		d.add(text);
		
		
		d.setVisible(true);
		
	}
	public void exportDialog()
	{
		tree = parent.getTree();
		if(tree == null)
		{
			System.out.println("Graphic : Open a tree before export");
			openDialog();
		} 
		else
		{
			JDialog d = new JDialog(parent);
			d.setLocationRelativeTo(null);
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to save");
			
			int userSelection = fileChooser.showSaveDialog(d);
			String baseName = "";
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
			    baseName = fileToSave.getAbsolutePath();
			    System.out.println("Graphic :Save as file: " + fileToSave.getAbsolutePath());
			}
			else
			{
				System.out.println("Graphic : No file selected");
				return;
			}

			try {
				Analyzer analyzer = this.parent.getAnalyzer();
				analyzer.saveAsFile(baseName);
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			/*List<DataContainer> datas = tree.getDatas();
			File export;
			int i =0;
			BufferedWriter bw;
			for (DataContainer data : datas) 
			{
				i++;
				try
				{
					export = new File(baseName+i+".txt");
					export.createNewFile();
					bw = new BufferedWriter(new FileWriter(export));
					
					bw.write("\n");
					bw.write("---Ethernet II");
					for (String string : data.data_Ethernet) 
					{
						bw.write("\n");
						bw.write(string);
					}
					bw.write("\n");
					bw.write("---IP");
					for (String string : data.data_IP) 
					{
						bw.write("\n");
						bw.write(string);
					}
					bw.write("\n");
					bw.write("---TCP");
					for (String string : data.data_TCP)
					{
						bw.write("\n");
						bw.write(string);
					}
					bw.write("\n");
					bw.write("---HTTP");
					for (String string : data.data_HTTP) 
					{
						bw.write("\n");
						bw.write(string);
					}
					bw.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}*/
		}
	}
}

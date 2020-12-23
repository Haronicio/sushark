package pobj.graphic;

import pobj.structure.DataContainer;
import pobj.structure.DataContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
/**
 * @author Haron
 * Cr�e un Arbre contenant les informations par couche de la Trame
 */
public class Tree extends JTree
{
	List<DataContainer> datas = new ArrayList<>();
	DefaultTreeModel dTree;
	private Window parent;
	private JScrollPane container = null;
	
	public Tree(Window parent)
	{
		super();
		this.parent = parent;
	}
	
	public void setData(DataContainer data)
	{
		datas.add(data);
	}
	
	public void createTrameTree(Window jf)
	{
		
		JPanel label1 = new JPanel();
		//Integer.toString(this.hashCode())
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		
		
		for (DataContainer data : datas) 
		{
			
			DefaultMutableTreeNode trameNode = new DefaultMutableTreeNode("Trame "+data.trameNumber);

			//Creating 3 children of the root node, Trame
			DefaultMutableTreeNode eth = new DefaultMutableTreeNode("Ethernet II");
			DefaultMutableTreeNode ip = new DefaultMutableTreeNode("Internet Protocol");
			DefaultMutableTreeNode tcp = new DefaultMutableTreeNode("Transmission Control Protocol");
			DefaultMutableTreeNode http = new DefaultMutableTreeNode("Hypertext Transfer Protocol");

			//Adding 4 children to Trame
			trameNode.add(eth);
			trameNode.add(ip);
			trameNode.add(tcp);
			trameNode.add(http);

			DefaultMutableTreeNode element = null;

			//Creating children of Ethernet
			if(data.data_Ethernet != null)
			{
			for (String string : data.data_Ethernet) 
			{
				if(string == null)
					break;
				element = new DefaultMutableTreeNode(string);
				eth.add(element);
			}
			}
			//Creating children of Ethernet
			if(data.data_IP != null)
			{
			for (String string : data.data_IP) 
			{ 
				if(string == null)
				{
					break;
				}

				element = new DefaultMutableTreeNode(string);
				ip.add(element);
				
				
				if(string.contains("Flags: "))
				{
					DefaultMutableTreeNode flag;
					for (String flag_str : data.data_IP_Flags) 
					{
						flag = new DefaultMutableTreeNode(flag_str);
						element.add(flag);
					}
				}
			}
			}
			//Creating children of Ethernet
			if(data.data_TCP != null)
			{
			for (String string : data.data_TCP) 
			{
				if(string == null)
					break;
				element = new DefaultMutableTreeNode(string);
				tcp.add(element);
				if(string.contains("Flags: "))
				{
					DefaultMutableTreeNode flag;
					for (String flag_str : data.data_TCP_Flags) 
					{
						flag = new DefaultMutableTreeNode(flag_str);
						element.add(flag);
					}
					
				}
				
				if(string.contains("Options: "))
				{
					DefaultMutableTreeNode option;
					if(data.data_TCP_Options == null)
						break;
					for (String option_str : data.data_TCP_Options) 
					{
						//System.out.println("option :"+option_str);
						if(option_str.matches("[\\n\\t ]*"))
							continue;
						String[] option_line = option_str.split("\\n\\t\\t\\t");

						option = new DefaultMutableTreeNode(option_line[0]);
						element.add(option);

						for (int x = 1; x < option_line.length; x++) {
							DefaultMutableTreeNode option_line_tree = new DefaultMutableTreeNode(option_line[x]);
							option.add(option_line_tree);
						}

					}
				}
			}
			}
			//Creating children of Ethernet
			if(data.data_HTTP != null)
			{
			for (String string : data.data_HTTP) 
			{
				if(string == null)
					break;

				String[] http_line = string.split("\\n\\t");
				for (String http_field : http_line) {
					if(http_field.matches("[\\n\\t ]*"))
						continue;
					element = new DefaultMutableTreeNode(http_field);
					http.add(element);
				}

			}
			}
			rootNode.add(trameNode);
		}

		dTree= new DefaultTreeModel(rootNode);

		//Creating a JTree from DefaultTreeModel, implementer of TreeModel Interface.
		JTree tree = new JTree(dTree);

		//Adding JTree to JScrollPane
		JScrollPane scrollP = new JScrollPane(tree);

		scrollP.setBorder(BorderFactory.createEmptyBorder()); //How to remove the border of JScrollPane.
		scrollP.setPreferredSize(new Dimension(parent.getWidth(),(int) (parent.getHeight()-parent.getHeight()*0.1f)));

//		jf.add(label1);
		jf.add(scrollP);
		
		container = scrollP;
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) this.getCellRenderer();
	    Icon closedIcon = new ImageIcon("leaf.png");
	    Icon openIcon = new ImageIcon("leaf.png");
	    Icon leafIcon = new ImageIcon("leaf.png");
	    renderer.setClosedIcon(closedIcon);
	    renderer.setOpenIcon(openIcon);
	    renderer.setLeafIcon(leafIcon); 
	    tree.setCellRenderer(renderer);
	}
	
	public List<DataContainer> getDatas()
	{
		return datas;
	}
	
	public JScrollPane getTreeContainer()
	{
		return container;
	}
}

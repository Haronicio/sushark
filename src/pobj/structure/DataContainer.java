package pobj.structure;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Haron
 *Un adapatateur pour stock� la trame converti
 */
public class DataContainer 
{
	 public int trameNumber = 0;
	
	 public String[] data_Ethernet = new String[3];
	 public String[] data_IP = new String[12];
	 public String[] data_IP_Flags = new String [3];
	 public String[] data_TCP = new String[10];
	 public String[] data_TCP_Flags = new String [10];
	 public String[] data_TCP_Options;
	 public String[] data_HTTP = new String [1];
	 
	 private boolean eth = true;
	 private boolean ip = true;
	 private boolean tcp = true;
	 private boolean http = true;
	
	public DataContainer(int trameNumber, String[] data_Ethernet, String[] data_IP, String[] data_TCP,String[] data_HTTP) 
	
	{
		this.trameNumber = trameNumber;
		this.data_Ethernet = data_Ethernet;
		this.data_IP = data_IP;
		this.data_TCP = data_TCP;
		this.data_HTTP = data_HTTP;
		
		System.out.println(data_IP);
	}
	
	public DataContainer(int fnumber,Frame f)
	{

		this.trameNumber = fnumber+1;
		this.data_Ethernet = genEthData(f);
		if(f == null)
		{
			this.data_IP = null;
			this.data_TCP = null;
			this.data_HTTP = null;
			
		}
		else
		{
			this.data_IP = genIPData(f.getDatagram());
			if(f.getDatagram() == null)
			{
				this.data_TCP = null;
				this.data_HTTP = null;
			}
			else
			{
				this.data_TCP = genTCPData(f.getDatagram().getSegment());
				if(f.getDatagram().getSegment() == null)
				{
					this.data_HTTP = null;
				}
				else
				{
					this.data_HTTP = genHTTPData(f.getDatagram().getSegment().getMessage());
				}
			}
		}
		
		
		
		
		
	}
	
	
	
	public String[] genEthData(Frame frame)
	{
		if(frame == null)
		{
			String[] res = {"no frame"};
			eth = false;
			return res;
		}
		String[] res = {"Destination: " +frame.getDestination(),
				"Source: " +frame.getDestination(),
				"Type: " +frame.getType()};
		return res;
	}
	
	public String[] genIPData(Datagram datag)
	{
		if(datag == null || eth == false)
		{
			String[] res = {"no datagram"};
			ip = false;
			return res;
		}
		String[] flags = Arrays.copyOf(datag.getFlags().split("\n"),4, String[].class);
		setIPFLagsData(flags);
		
		String[] res = {"Version: "+datag.getVersion(),
				"Header Length: "+datag.getIhl(),
				"Differentiated Services Field: " +datag.getTos(),
				"Total Length: " +datag.getTotalLength(),
				"Identification: " + datag.getIdentification() ,
				"Flags: "+ flags[0],
				"Fragment offset: "+ datag.getFragmentOffset(),
				"Time to live: "+datag.getTtl(),
				"Protocol: "+ datag.getProtocol(),
				"Header Checksum: " + datag.getHeaderChecksum(),
				"Source: "+ datag.getSource(),
				"Destination: "+datag.getDestination()
				};
		return res;
	}
	
	public  String[] genTCPData(Segment seg)
	{
		if(seg == null || eth == false || ip == false)
		{
			String[] res = {"no segment"};
			tcp = false;
			return res;
		}
		String[] flags = Arrays.copyOf(seg.getFlags().split("\n"),11, String[].class);
		setTCPFLagsData(flags);

		String[] options = null;
		
		if(seg.getOptions() != null)
			 options = seg.getOptions().split(">");
	
		setTCPOptions(options);
		String[] res = {"Source Port: "+seg.getSource(),
				"Destination Port: " +seg.getDestination(),
				"Sequence number: " +seg.getSeqNumber(),
				"Acknowledgment number: " + seg.getAckNumber() ,
				"Header Length: "+ seg.getHeaderLength(),
				"Flags: "+ flags[0],
				"Window size value: "+seg.getWindowSize(),
				"Checksum: "+ seg.getChecksum(),
				"Urgent Pointer: " + seg.getUrgentPointer(),
				"Options: "};
		return res;
	}
	
	public String[] genHTTPData(Message mess)
	{
		if (mess == null || eth == false || ip == false || tcp == false)
		{
			http = false;
			String[] res = {"no message"};
			return res;
		}
		String[] res = {mess.getPayload()};
		
		return res;
	}
	
	public void setIPFLagsData(String[] flags)
	{
		if(flags == null)
		{
			String[] res = {"no Flags"};
			this.data_IP_Flags = res;
		}
		String[] res = new String[3];
		for (int i = 1; i < flags.length; i++) 
		{
			if(flags[i] == null)
			{
				res[i] = "";
			}
			else
				res[i-1] = flags[i];
		}
		this.data_IP_Flags = res;
	}
	public void setTCPFLagsData(String[] flags)
	{
		if(flags == null)
		{
			String[] res = {"no Flags"};
			this.data_TCP_Flags = res;
		}
		String[] res = new String[10];
		for (int i = 1; i < flags.length; i++) 
		{
			if(flags[i] == null)
			{
				res[i] = "";
			}
			else
				res[i-1] = flags[i];
		}
		this.data_TCP_Flags = res;
	}
	
	public void setTCPOptions(String[] options)
	{
		if(options == null)
		{
			String[] res = {"no Options"};
			this.data_TCP_Options = res;
		}
		this.data_TCP_Options = options;
	}
}

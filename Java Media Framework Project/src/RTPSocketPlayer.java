import java.io.*;
import java.net.*;
import java.util.*;
import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.rtp.*;
import javax.media.rtp.event.*;
import javax.media.rtp.rtcp.*;


public class RTPSocketPlayer implements ControllerListener{

	String address = "";
	
	int port = 0;
	
	String media = "video";
	
	RTPConnector rtpsocket = null;
	
	RTPPushDataSource rtcpsource = null;

	@Override
	public void controllerUpdate(ControllerEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	AudioFormat audio = new AudioFormat(AudioFormat.MPEGLAYER3,48000,16,1);
	
	
}

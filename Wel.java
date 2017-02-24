package evolution;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.*;

public class Wel extends Canvas {
	Display d;
	Displayable dis;
	int i;
	String[] key={"请按中键"};
	RecordStore rec;
    byte[] keyvalue=new byte[key.length];
	
    public Wel(Display d,Displayable dis){
    	this.d=d;
    	this.dis=dis; 
    	try {
    	if(RecordStore.listRecordStores().length==0){
    		i=0;

				rec=RecordStore.openRecordStore("key", true, RecordStore.AUTHMODE_ANY, true);

    	}else{
    		i=-1;
    		keyvalue=RecordStore.openRecordStore("key",false).getRecord(0);
    	}
		} catch (Exception e) {
			
		}
    }
    
	protected void paint(Graphics g) {
		try {
			g.drawImage(Image.createImage("/wel.jpg"), 0, 0, Graphics.LEFT|Graphics.TOP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	// TODO Auto-generated method stub
         if(i>=0){
        	 g.setColor(255, 0, 0);
        	 g.drawString(key[i],100,100,Graphics.LEFT|Graphics.TOP);
         }
	}
	public void keyPressed(int k){
		if(i>=0){
			if(i<key.length){
				keyvalue[i]=(byte)k;
				i++;
			}else{
				try {
					d.setCurrent(dis);
					rec.addRecord(keyvalue,0, keyvalue.length);

				} catch (Exception e) {
					System.out.print(e.toString());
				}
			}
		}else{
		d.setCurrent(dis);
		}
	}
}

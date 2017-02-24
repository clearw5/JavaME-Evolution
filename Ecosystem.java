package evolution;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
   
public class Ecosystem extends Canvas {
   Vector foods,biologys;
   int weight,length,num,plifeLimit,delay,initEnergy,imageNum;
   String msg,BasicGene;
   double geneMP;
   Runrec runrec;
   evol ev;
   Image background,food;
   Biology ab;
   evol midlet;
   boolean key_help;
   
   public Ecosystem(int bn,int fn,String bg,int ie,int pl,double gp,String sg,String ag,int rd,String p,evol m){
	   this.setFullScreenMode(true);
	   midlet =m;
	   runrec = new Runrec(p);
	   weight=(getWidth())/20;
	   length=(getHeight()-20)/20;
	   num=0;
	   imageNum=0;
	   foods=new Vector();
	   biologys=new Vector();
	   initEnergy=ie;
	   msg="";
	   plifeLimit=pl;
	   delay=rd;
	   geneMP=gp;
	   BasicGene=Biology.StringToGene(bg);
	   key_help=false;
		try {
			 background = Image.createImage("/bg.jpg");
			 food= Image.createImage("/food.png");
		} catch (IOException e1) {
		}
	   for(int i=0;i<fn;i++){
			foods.addElement(new Food(weight,length));
	   }
		Image im=Images();
	   for(int i=0;i<bn;i++){
		   try{
	    		  Thread.sleep(50);
	    	  }catch(Exception e){}
		   addBiology(new Biology(ie,Biology.RanInt(0,weight-1),Biology.RanInt(0,length-1),BasicGene,this,im,false));
	   }
	   im=Images();
	   addBiology(new Biology(ie,Biology.RanInt(0,weight-1),Biology.RanInt(0,length-1),Biology.StringToGene(sg),this,im,false));
	   im=Images();
	   ab =new Biology(ie,weight-1,-1,Biology.StringToGene(ag),this,im,false);
       addBiology(ab);
   }
	protected void paint(Graphics g) {
        if(background!=null){
		g.drawImage(background, 0, 0, Graphics.LEFT|Graphics.TOP);
          }else{
          g.setColor(255,255,255);
		g.fillRect(0,0,getWidth(),getHeight());
}
		g.setColor(255,0,255);
		msg="现存生物:"+String.valueOf(biologys.size())+" 历史生物:"+String.valueOf(num);
		g.drawString(msg,0,0,Graphics.LEFT|Graphics.TOP);
		g.drawString("位置:("+String.valueOf(ab.x)+","+String.valueOf(ab.y)+")  能量:"+String.valueOf(ab.energy),0,getHeight(),Graphics.LEFT|Graphics.BOTTOM);
		if(key_help){
			for(int i=0;i<midlet.key_help.length;i++){
			g.drawString(midlet.key_help[i],0,20*i+20,Graphics.LEFT|Graphics.TOP);
			}
		}
		g.setColor(255,0,0);
			for(int i=0;i<foods.size();i++){
				Food f=(Food)foods.elementAt(i);
				if(food==null){
				g.fillRect(20*f.x, 20*f.y+20,20,20);
			}else{
				g.drawImage(food,20*f.x, 20*f.y+20, Graphics.LEFT|Graphics.TOP);
			}
			}
			for(int i=0;i<biologys.size();i++){
				Biology bg=(Biology)biologys.elementAt(i);
				int x=20*bg.x;
				int y=20*bg.y+20;
     if(bg.img!=null){
		g.drawImage(bg.img, x, y, Graphics.LEFT|Graphics.TOP);
          }else{
				g.setColor(0,0,255);
				g.fillRect(x,y,20,20);
				g.setColor(0,255,0);
				g.drawString(String.valueOf(bg.num), x, y, Graphics.LEFT|Graphics.TOP);
          }
			}

	}
	public void keyPressed(int key){
		switch(key){
		case 49:
		ab.lifeAct(11);
		break;
		case 50:
		ab.lifeAct(12);
		break;
		case 51:
		ab.lifeAct(14);
		break;
		case 52:
		ab.lifeAct(3);
		break;
		case 53:
		ab.lifeAct(-1);
		break;
		case 54:
		if(ab.lifeState==1){
		ab.lifeState=0;
		}else{
		if(ab.lifeState==0){
		ab.lifeState=1;
		}
		}
		case 57:
			key_help=!key_help;
		break;
		case 48:
		ab.die();
		break;
		case -1:
		ab.y--;
		break;
		case -2:
		ab.y++;
		break;
		case -3:
		ab.x--;
		break;
		case -4:
		ab.x++;
		break;
		case -5:
		ab.lifeAct(1);
		break;
		case -6:
			while(biologys.size()>0){
				Biology bg=(Biology)biologys.elementAt(biologys.size()-1);
				bg.lifeState=-1;
				biologys.removeElementAt(biologys.size()-1);
			}
		Display.getDisplay(midlet).setCurrent(midlet.f);
		break;
		case 42:
			runrec.writeRec();break;
		case -7:
			ev.bye();
			break;
		}
	}

	public int numlize(){
		return ++num;
	}
	public boolean isFoodAt(int x,int y){
		if(x<0||y<0||x>=weight||y>=length){
			return false;
		}
		for(int i=0;i<foods.size();i++){
			Food f=(Food)foods.elementAt(i);
			if(x==f.x&&y==f.y){
				return true;
			}
		}
		return false;
	}
	public boolean isBiologyAt(int x,int y){
		if(x<0||y<0||x>=weight||y>=length){
			return false;
		}
		for(int i=0;i<biologys.size();i++){
			Biology bg=(Biology)biologys.elementAt(i);
			if(x==bg.x&&y==bg.y){
				return true;
			}
		}
		return false;
	}
	public Biology getBiologyAt(int x,int y){
		if(x<0||y<0||x>=weight||y>=length){
			throw new RuntimeException("God,there is no biology at "+String.valueOf(x)+","+String.valueOf(y)+".");
		}
		for(int i=0;i<biologys.size();i++){
			Biology bg=(Biology)biologys.elementAt(i);
			if(x==bg.x&&y==bg.y){
				return bg;
			}
		}
		throw new RuntimeException("God,there is no biology at "+String.valueOf(x)+","+String.valueOf(y)+".");
	}
	public void eatFoodAt(int x,int y){
		if(x<0||y<0||x>=weight||y>=length){
			throw new RuntimeException("God,there is no food at "+String.valueOf(x)+","+String.valueOf(y)+".");
		}
		for(int i=0;i<foods.size();i++){
			Food f=(Food)foods.elementAt(i);
			if(x==f.x&&y==f.y){
			foods.removeElementAt(i);
			foods.addElement(new Food(weight,length));
			}
		}
		throw new RuntimeException("God,there is no food at "+String.valueOf(x)+","+String.valueOf(y)+".");
	}
	public void killBiologyAt(int x,int y){
		if(x<0||y<0||x>=weight||y>=length){
			throw new RuntimeException("God,there is no biology at "+String.valueOf(x)+","+String.valueOf(y)+".");
		}
		for(int i=0;i<biologys.size();i++){
			Biology bg=(Biology)biologys.elementAt(i);
			if(x==bg.x&&y==bg.y){
				biologys.removeElementAt(i);
			}
		}
		throw new RuntimeException("God,there is no biology at "+String.valueOf(x)+","+String.valueOf(y)+".");
	}
	public void killBiologyAt(int num){
		for(int i=0;i<biologys.size();i++){
			Biology bg=(Biology)biologys.elementAt(i);
			if(num==bg.num){
				biologys.removeElementAt(i);
			}
		}
		throw new RuntimeException("God,the num "+String.valueOf(num)+" belongs to no biology");
	}
	public void addBiology(Biology bg){
		biologys.addElement(bg);
		Thread t=new Thread(bg);
		t.start();
	}
	public Image Images() {
		Image i;
		//System.out.println(imageNum);
		try {
			i=Image.createImage("/biologyImages/"+String.valueOf(imageNum++)+".png");
			return i;
		} catch (IOException e) {// TODO Auto-generated catch block
		return null;
		}

	}
}

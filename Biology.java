package evolution;
import java.util.Random;

import javax.microedition.lcdui.Image;

public class Biology implements Runnable {
public int energy,x,y,lifeState,num;
public String gene;
public byte[] prot;
public static Random rand=new Random();
public Ecosystem es;
boolean gene9,gene14,gene15;
Image img;

public Biology(int e,int x,int y,String g,Ecosystem es,Image i,boolean roomRejection){
	//Random ran=new Random();
	if(roomRejection&&es.isBiologyAt(x, y)){
		die();
	}
	gene15=false;
	lifeState=1;
	energy=e;
	gene=g;
	this.x=x;
	this.y=y;
	this.es=es;
	num=es.numlize();
	img=i;
	translate();
}

public void translate(){
	try{
		prot=gene.getBytes();
	}catch(Exception exc){
		throw new RuntimeException("God,the gene cannot be understood.");
	}
	for(int j=0;j<prot.length;j++){
		switch(prot[j]){
		case 9:
			gene9=true;
		break;
		case 14:
			gene14=true;
		break;
		case 15:
			gene15=true;
		break;
		}
	}
}

	public void run() {
		// TODO Auto-generated method stub
      while(true){
    	  try{
    		  Thread.sleep(es.delay);
    	  }catch(Exception e){}
    	  if(lifeState==1){
    		  for(int i=0;i<prot.length;i++){
    	    	  if(lifeState==1){
    	    		  lifeAct(prot[i]);
    	    		  es.repaint();
    	    	  }else{
    	    		  break;
    	    	  }
    		  }
    		  energy--;
    	  }else{
    		  if(lifeState==0){
    			  if(es.isFoodAt(x,y)){
    				  es.eatFoodAt(x,y);
    				  energy+=10;
    				  lifeState=1;
    			  }
    		  }else{
    			  break;
    		  }
    	  }
      }
	}

	public void lifeAct(int i){
		try{
			switch(i){
			case -1:
				if(!(x<0||y<0||x>=es.weight||y>=es.length)){
					for(int j=0;j<es.biologys.size();j++){
					Biology bg=(Biology)es.biologys.elementAt(j);
					if(x==bg.x&&y==bg.y&&bg!=this){
						bg.die();;
					}
				}
				}
			break;
			case 1: //eat
				if(es.isFoodAt(x, y)){
					energy+=20;
					es.eatFoodAt(x, y);
				}
				break;
			case 2: //move
				int move=RanInt(-1,1);
			if(rand.nextDouble()>=0.5){
			if(es.isBiologyAt(x+move, y)&&gene9){
				Biology bg=es.getBiologyAt(x+move, y);
				if(bg.energy<energy){
					bg.die();
					energy+=30;
				}
			}
			if(!(x+move<0||x+move>=es.weight||es.isBiologyAt(x+move, y))){x+=move;}
			}else{
				if(es.isBiologyAt(x, y+move)&&gene9){
					Biology bg=es.getBiologyAt(x, y+move);
					if(bg.energy<energy){
						bg.die();
						energy+=30;
					}
				}
			if(!(y+move<0||y+move>=es.length||es.isBiologyAt(x, y+move))){y+=move;}
			}
			break;
			case 3: 
				if(energy>=es.plifeLimit){//proliferation
				proliferation(!gene15);
				}
				break;
			case 4: //die
				if(energy<0){
					die();
				}
				break;
			case 5:    //disease
				if(RanInt(0,1)==0){
					energy--;
				}
				break;
			case 6: //move to food
				if(es.isFoodAt(x-1,y)){
					energy+=20;
					es.eatFoodAt(x-1, y);
				}else{
					if(es.isFoodAt(x+1,y)){
						energy+=20;
						es.eatFoodAt(x+1, y);
					}else{
						if(es.isFoodAt(x,y-1)){
							energy+=20;
							es.eatFoodAt(x, y-1);
						}else{
							if(es.isFoodAt(x,y+1)){
								energy+=20;
								es.eatFoodAt(x, y+1);
							}
						}
					}
				}
				break;
			case 7: //move to or avoid enemy
				boolean tmp1=false;
				for(int j=-1;j<=1;j++){
					for(int k=-1;k<=1;k++){
						if(!(j==0&&k==0)){
						if(es.isBiologyAt(x+j, y+k)){
							Biology bg=es.getBiologyAt(x+j, y+k);
							if(bg.energy<=energy){
								if(k==0){
									y+=j;
								}else{
									x+=k;
								}
							}else{
								if(k==0){
									y-=j;
								}else{
									x-=k;
								}
							}
						}
							tmp1=true;
							break;
						}
					}
					if(tmp1){break;}
				}
				break;
			case 8:
				lifeAct((byte)RanInt(0,15));
				break;
			case 10:    //gene double
				if(RanInt(0,9)==0){
					gene+=gene;
				}
				break;
			case 11: //Photosynthesis
				if(RanInt(0,1)==0){
					energy+=1;
				}
			break;
			case 12:   //release toxins
				if(es.isBiologyAt(x, y)){
				Biology bg=es.getBiologyAt(x, y);
				bg.gene+="\u0005";
				}
			case 13:   //sleep
				if(energy<=2){
					lifeState=0;
				}
				break;
			case 14:  //Fusion
				if(es.isBiologyAt(x, y)){
					Biology bg=es.getBiologyAt(x, y);
					if(bg.gene14){
							gene+=bg.gene;
							energy=(energy+bg.energy)/2;
							translate();
							bg.die();
							break;
						}
					}
				break;
			}
	}catch(Exception exc){
		
	}
}
	public void die (){
	lifeState=-1;
	es.killBiologyAt(num);
	}
	public void proliferation(boolean randGene){
		energy=energy/2;
		double geneMP=es.geneMP;
		if(!randGene){geneMP=0.01;}
		try{
			byte[] b=gene.getBytes();
			for(int i=0;i<b.length;i++){
				if(rand.nextDouble()<geneMP){
					b[i]=(byte)RanInt(0,15);
				}
			}
			String gene_new=new String(b);
			Image i=img;
			if(!gene_new.equals(gene)){
				i=es.Images();
			}
			Biology bg=new Biology(energy,RanInt(0,es.weight-1),RanInt(0,es.length -1),gene_new,es,i,true);
			es.addBiology(bg);
			es.runrec.rec(String.valueOf(bg.num)+": "+GeneToString(bg.gene));
		}catch(Exception exc){}
	}
	public static String GeneToString(String s) {
		try{
	    	   byte[] b=new byte[s.length()];
	    	   for(int i=0;i<b.length;i++){
	    		   int code=(int)s.charAt(i);
	    		   if(code>=0&&code<10){
	    			   b[i]=(byte)(code+48);
	    		   }else{
	    			   b[i]=(byte)(code+55);
	    		   }
	    	   }
	    	   return new String(b);
	       }catch(Exception exc){
	    	   throw new RuntimeException("gene error");
	       }
	}

	public static int RanInt(int a,int b){
		if(a>b){
			 int tmp=a;
			 a=b;
			 b=tmp;
		 }
		return a+(int)Math.floor(rand.nextDouble()*(b-a+1)/0.9999999999999999);
	 }

	public static String StringToGene(String s) {
		s=s.toUpperCase();
       try{
    	   byte[] b=new byte[s.length()];
    	   for(int i=0;i<b.length;i++){
    		   int code=(int)s.charAt(i);
    		   if(code>47&&code<58){
    			   b[i]=(byte)(code-48);
    		   }else{
    			   b[i]=(byte)(code-55);
    		   }
    	   }
    	   return new String(b);
       }catch(Exception exc){
    	   throw new RuntimeException("gene error");
       }
	}
	
}

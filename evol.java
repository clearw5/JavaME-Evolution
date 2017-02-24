package evolution;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class evol extends MIDlet implements CommandListener{
	Form f=new Form("evolution");
	TextField bn=new TextField("初始生物数量","5", 100, TextField.ANY);
	TextField fn=new TextField("食物数量","20", 100, TextField.ANY);
	TextField bg=new TextField("初始基因","12344", 100, TextField.ANY);
	TextField ie=new TextField("初始能量","40", 100, TextField.ANY);
	TextField pl=new TextField("增值所需能量","150", 100, TextField.ANY);
	TextField gp=new TextField("基因变异概率","0.1", 100, TextField.ANY);
	TextField sg=new TextField("超级生物基因","123469bd", 100, TextField.ANY);TextField ag=new TextField("人造生物基因","0", 100, TextField.ANY);
	TextField rd=new TextField("循环时间间隔","1000", 100, TextField.ANY);
	TextField p=new TextField("日志保存路径","file:///sdcard/runrec.txt", 100, TextField.ANY);
	Command ok=new Command("开始", Command.OK, 0);
	Command help=new Command("帮助&关于", Command.HELP, 0);
	Command back=new Command("返回", Command.BACK, 0);
	Command exit=new Command("退出", Command.EXIT,0);
	Form h=new Form("帮助&关于");
	Ecosystem es;
	String keyhelp="按键说明：\n方向键:移动  中键:摄食  左软键:返回\n右软键:退出  1:光合作用  2:释放毒素\n3:细胞融合 4:增殖  5:捕杀\n6:休眠/恢复  9:帮助 *:日志保存\n0:死亡";
	String[] key_help={"按键说明：","方向键:移动  中键:摄食  左软键:返回","右软键:退出  1:光合作用  2:释放毒素","3:细胞融合 4:增殖  5:捕杀","6:休眠/恢复  9:帮助 *:日志保存","0:死亡"};
	public evol() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		f.append(bn);
		f.append(fn);
		f.append(bg);
		f.append(ie);
		f.append(pl);
		f.append(gp);
		f.append(sg);
		f.append(ag);
		f.append(rd);
		f.append(p);
		f.addCommand(ok);
		f.addCommand(exit);
		f.addCommand(help);
		f.setCommandListener(this);
		Wel w=new Wel( Display.getDisplay(this),f);
		Display.getDisplay(this).setCurrent(w);
		h.addCommand(back);
		h.append("软件作者：星尘幻影\nQQ: 946994919\n 基因说明：\n1:摄食 2:移动 3:增殖 4:死亡 5:生病 6:找食 7:避开或趋向敌人 8:蛋白质变异 9:捕食 10:基因加倍 11:光合作用 12:释放毒素 13:休眠 14:细胞融合 15:基因稳定\n"+keyhelp);// TODO Auto-generated method stub
        h.setCommandListener(this);
	}
	public void commandAction(Command c,Displayable dis){
		if(c==ok){
		    es=new Ecosystem(Integer.parseInt(bn.getString()),Integer.parseInt(fn.getString()),bg.getString(),Integer.parseInt(ie.getString()),Integer.parseInt(pl.getString()),Double.parseDouble(gp.getString()),sg.getString(),ag.getString(),Integer.parseInt(rd.getString()),p.getString(),this);
			es.ev=this;
		    Display.getDisplay(this).setCurrent(es);
		}else{
			if(c==help){
				 Display.getDisplay(this).setCurrent(h);
			}else{
				if(c==back){
					 Display.getDisplay(this).setCurrent(f);
				}else{
					bye();	
				}
			}
		}
	}
	public void bye(){
		try{
			destroyApp(true);
		}catch(Exception exc){}
		notifyDestroyed();
	}

}

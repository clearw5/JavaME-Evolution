package evolution;

import java.io.DataOutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
public class Runrec{
	
	public boolean isPC=false;
	
	public String path;
 StringBuffer rec=new StringBuffer();
public Runrec(String p){path=p;};
public void rec(String str){
rec.append(str+"\n");
if(isPC){
	System.out.println(str);
}
}
public  void rec(int i){
rec(String.valueOf(i));
}
public void writeRec(){
	if(isPC){
		System.out.println(rec.toString());
	}else{
try{
FileConnection fc =(FileConnection)Connector.open(path,Connector.READ_WRITE);
if(fc.exists()){
fc.delete();}
fc.create();
DataOutputStream dos=fc.openDataOutputStream();
dos.write(rec.toString().getBytes());
dos.close();
fc.close();
}catch(Exception e){
throw new RuntimeException(e.toString());
}
}
}
public String rec(){
return rec.toString();
}
}
package evolution;

public class Food {
	int x,y;
     public Food(int x,int y){
    	 this.x=Biology.RanInt(0, x-1);
    	this.y=Biology.RanInt(0, y-1);
     }
}

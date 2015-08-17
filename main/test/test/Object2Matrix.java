package test;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTItems2MatrixConverter;
import com.sample.calltree.model.CTRoot;

public class Object2Matrix {
	public static void main(String...args) {
		CTRoot ctroot = new CTRoot("1");
		CTItem item1 = new CTItem("1");
		item1.addChild(new CTItem("11"));
		CTItem item12 = new CTItem("12");
		item1.addChild(item12);
		item12.addChild(new CTItem("121"));
		item12.addChild(new CTItem("122"));
		
		CTItem item2 = new CTItem("2");
		item2.addChild(new CTItem("21"));
		CTItem item22 = new CTItem("22");
		item2.addChild(item12);
		item22.addChild(new CTItem("221"));
		item22.addChild(new CTItem("222"));
		ctroot.addChild(item1);
		ctroot.addChild(item2);
		
		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(ctroot.getChildItems());
		
		for ( int row=0;row<ctItems2Matrix[0].length; row++ ) {
			for ( int col=0;col<ctItems2Matrix.length; col++ ) {
				if ( ctItems2Matrix[col][row] == null ) {
					System.out.print("0.");
				} else {
					System.out.print("1.");
				}
			}
			System.out.println();
		}
		
	}
}

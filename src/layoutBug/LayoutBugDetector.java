package layoutBug;

import java.util.ArrayList;

import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListErrorConsumer;
import org.im4java.process.ProcessStarter;

public class LayoutBugDetector {

	public LayoutBugDetector(final String pathToIM){
		
		ProcessStarter.setGlobalSearchPath(pathToIM);
	}
	
	public void getImageInfo(final String filename){
		
		try {
			Info imageInfo = new Info(filename,true);
			System.out.println("Format: " + imageInfo.getImageFormat());
			System.out.println("Width: " + imageInfo.getImageWidth());
			System.out.println("Height: " + imageInfo.getImageHeight());
			System.out.println("Geometry: " + imageInfo.getImageGeometry());
			System.out.println("Depth: " + imageInfo.getImageDepth());
			System.out.println("Class: " + imageInfo.getImageClass());
		} catch (InfoException e) {
			e.printStackTrace();
		}
		
	}
	
	public void resize(final String exp, final String cur){
		
		try{
			ConvertCmd cmd = new ConvertCmd();

			IMOperation op = new IMOperation();
			op.addImage(cur);
			op.resize(800,200, "!");
			op.addImage(exp);

			cmd.run(op);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean compare(final String exp, final String cur, final String diff) {

		CompareCmd compare = new CompareCmd();
		
		ArrayListErrorConsumer errorConsumer = new ArrayListErrorConsumer();
		compare.setErrorConsumer(errorConsumer);
		
		IMOperation cmpOp = new IMOperation();
		cmpOp.addImage();
		cmpOp.addImage();
		cmpOp.metric("ae"); // root mean squared (normalized root mean squared)
		cmpOp.addImage();

		try {
			compare.run(cmpOp, cur, exp, diff);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			ArrayList<String> stringArrayList = errorConsumer.getOutput();
			System.out.println(stringArrayList);
			return false;
		}
	}
	
	public static void main(final String[] args){
	
		final String pathToIM = "C:/Software/ImageMagic/ImageMagick-windows/ImageMagick-7.0.1-Q16";
		LayoutBugDetector instance = new LayoutBugDetector(pathToIM);
		
		final String path = System.getProperty("user.dir") + "/src/LayoutBugs/";
//		instance.resize(path + "singsaver.png", path + "singsaver_resize.png");
		
//		instance.compare(path + "rose.jpg", path + "reconstruct.jpg", path + "diff.jpg");
		
		instance.getImageInfo(path + "rose.jpg");
	}
}

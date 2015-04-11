import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


public class GenerateDataCompressed {

	public static void main(String[] args) throws Exception {
		
	    GZIPOutputStream gos = null;
        

		try {

	        File myGzipFile = new File("myFile.gzip");

	        gos = new GZIPOutputStream(new FileOutputStream(myGzipFile));
	        for( int i=0; i<100*1000*1000; i++ ) {
	        	String line = "ABC,DEF,XYZ\n";
	        	
	        	gos.write( line.getBytes() );
	        }
			
		} finally {
		    if (gos != null) {
		        gos.close();
		    }
		}
		
		
	}
}

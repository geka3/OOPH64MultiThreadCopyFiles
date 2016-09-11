import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileThread implements Runnable {

	File source = null;// file that will be copied
	File DestinationFolder = null;// destination directory
	long totalByteCopied = 0;
	boolean fileCopied = false;

	public CopyFileThread(File source, File destinationFolder) {
		super();
		this.source = source;
		this.DestinationFolder = destinationFolder;
		
	}

	@Override
	public void run(){
		
		File destination = new File(DestinationFolder.getPath() + "/" + source.getName());
		
		try(FileOutputStream fos = new FileOutputStream(destination);
				FileInputStream fis = new FileInputStream(source)){
			
			byte[] buffer = new byte[1024*1024*128];
			int byteCopy = 0;
			
			for(;(byteCopy = fis.read(buffer)) > 0;){
//				System.out.println("writing");
				fos.write(buffer, 0, byteCopy);
//				System.out.println("reading");
				totalByteCopied +=byteCopy;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @return the totalByteCopied
	 */
	public long getTotalByteCopied() {
		return totalByteCopied;
	}

	/**
	 * @return the fileCopied
	 */
	public boolean isFileCopied() {
		return fileCopied;
	}
	
	
}

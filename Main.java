import java.io.File;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		copyDirectory("D:/temp2", "D:/temp3");

	}

	public static void copyDirectory(String strSource, String strDestination) {
		
		
		File fileSource = new File(strSource);
		File fileDestionation = new File(strDestination);
		long totalSize = 0;

		boolean areAliveThread = true;

		if (!fileSource.isDirectory()) {
			System.out.println("source is not directory, select the correct folder");
			return;
		}

		if (fileDestionation.exists() && !fileDestionation.isDirectory()) {
			System.out.println("destination exist but it is not folder");
			return;
		}

		if (!fileDestionation.exists()) {
			fileDestionation.mkdirs();
		}
		
		File[] fileList = fileSource.listFiles();
		System.out.println("we have next files and file size:" + System.lineSeparator());
		CopyFileThread[] cft = new CopyFileThread[fileList.length];
		Thread[] th = new Thread[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			if(fileList[i].isDirectory()){
				continue;
			}
			System.out.println(fileList[i].getName() + " " + fileList[i].length() + " bytes");
			cft[i] = new CopyFileThread(fileList[i], fileDestionation);
			th[i] = new Thread(cft[i]);
			th[i].start();
			areAliveThread = true;
			totalSize +=  fileList[i].length();

		}
		//show progress of coping
		for (; areAliveThread;) {
			long totalSum = 0;
			for (int i = 0; i < th.length; i++) {
				totalSum += cft[i].getTotalByteCopied();
				if (th[i].isAlive()) {
					areAliveThread = true;
					
//					System.out.println("totalSum " + totalSum);
//					System.out.println("cft[i].getTotalByteCopied();" + cft[i].getTotalByteCopied());
					break;
				} else {
					areAliveThread = false;
				}

			}
			
			System.out.println(((totalSum * 100) / totalSize ) + "%");
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}

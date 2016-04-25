package drfresh.resources;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryListing implements Serializable{
	

	private static final long serialVersionUID = 1L;
	//Root directory for motion capture recordings.
	private final static String ROOT_DIR= "/Users/derohde/mocaps";
	
	public List<String> fileNames = new ArrayList<String>();
	public String currentDir = new String();
	
	/**
	 * By default will display the directory contents in the mocaps dir(specified by variable)
	 */
	public DirectoryListing(){
		getAndSetDirectoryContents(ROOT_DIR);
	}
	
	/**
	 * Overloaded constructor, used when the user does a get on a particular directory
	 */
	public DirectoryListing(String dirName){
		String dirLocation = ROOT_DIR+File.separator+dirName;
		getAndSetDirectoryContents(dirLocation);
	}

	/**
	 * Sets directory contents to this object which is then serialized by jackson
	 * @param dirLoc
	 */
	private void getAndSetDirectoryContents(String dirLoc){
		File rDir = new File(dirLoc);
		currentDir = rDir.getAbsolutePath();
		if(!rDir.exists() || !rDir.isDirectory()){
			System.out.println("directory doesn't exist!");
			return;
		}
		String[] availableDirectories = rDir.list();
		if(availableDirectories != null && availableDirectories.length  >0){
			//for each found directory
			fileNames.addAll(Arrays.asList(availableDirectories));
		}else{
			return;
		}
	}
}

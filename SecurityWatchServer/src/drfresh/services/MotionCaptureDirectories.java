package drfresh.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import drfresh.resources.DirectoryListing;
/**
 * Simple servlet allows a user to get the directories (in this case timestamped dirs of when motion was captured)
 * on a system, and further drill down into those directories to find the location of the motion capture images.
 * These images can then be displayed by supplying the full path to the ImageGetterService
 * @author derohde
 *
 */
public class MotionCaptureDirectories extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String DIR_NAME_PARAM = "subDir";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		String dirInp = request.getParameter(DIR_NAME_PARAM);
		String jsonDirString;
		DirectoryListing dirList;
		if(dirInp == null || dirInp.length() == 0){
			dirList = new DirectoryListing();
		}else{
			dirList = new DirectoryListing(dirInp);
		}
		jsonDirString = mapper.writeValueAsString(dirList);
		
 		System.out.println("Sending available Directories: "+jsonDirString);
		PrintWriter responseWriter = response.getWriter();
		responseWriter.write(jsonDirString);
	}
	
	
	
}

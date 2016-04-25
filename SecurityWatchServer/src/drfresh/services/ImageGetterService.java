package drfresh.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Basic servlet for getting images using the full path to the picture on the url
 * Full path specified using the ?imageLoc=query parameter
 * The full path for the pictures can be obtained by using the ImageGetterService
 * @author derohde
 *
 */
public class ImageGetterService extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_LOC_PARAM = "imageLoc";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("image/jpeg");
		String imageLoc = request.getParameter(IMAGE_LOC_PARAM);
		if(imageLoc == null || imageLoc.length() == 0){
			 response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		

		File image = new File(imageLoc);
		if(!image.exists() || image.isDirectory()){
			 response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		
		BufferedImage imgBuffer = ImageIO.read(image);
		OutputStream outStream = response.getOutputStream();
		ImageIO.write(imgBuffer, "jpg", outStream);
		outStream.close();
		
	}
}

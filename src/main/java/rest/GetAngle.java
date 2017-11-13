package rest;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetAngle", urlPatterns = { "/clock/*" })
public class GetAngle extends HttpServlet {

	/**
	 * @author clenio.si
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		Integer hour = 0;
		Integer minute = 0;
		//Validate URL
		try {
			String timeTotal = request.getPathInfo();
			String[] timeSplit = timeTotal.split("/");
			String[] timeFinal = timeSplit[1].split(":");
			
			if (timeFinal.length > 1) {
				hour = Integer.parseInt(timeFinal[0]);
				minute = Integer.parseInt(timeFinal[1]);
			}else {
				hour = Integer.parseInt(timeFinal[0]);
			}
			//Validate hour and minute
			if (hour < 0 || minute < 0 || hour > 12 || minute > 60) {
				PrintWriter out2 = response.getWriter();
				out2.print(new Gson().toJson("Incorrect Number"));
				return;
			}
			if (hour == 12) {
				hour = 0;
			}
			if (minute == 60) {
				minute = 0;
			}
			
			//Call method that return angle
			PrintWriter out2 = response.getWriter();
			out2.print(validate_clock(hour, minute));
		} catch (Exception e) {
			// TODO: handle exception
			PrintWriter out2 = response.getWriter();
			out2.print(new Gson().toJson("Invalid Number"));
			return;
		}

	}
	//This method calculates the angle through the time indicated in the url
	private String validate_clock(Integer hour, Integer minute) {

		int hour_angle = (60 * hour + minute) / 2;
		int minute_angle = (6 * minute);

		int angleFinal = Math.abs(hour_angle - minute_angle);
		angleFinal = Math.min(360-angleFinal, angleFinal);
		HashMap<String,Integer> objMap = new HashMap<String,Integer>();
		objMap.put("angle", angleFinal);

		Gson gson = new Gson();
		String value = gson.toJson(objMap);
		
		return value;
	}
}
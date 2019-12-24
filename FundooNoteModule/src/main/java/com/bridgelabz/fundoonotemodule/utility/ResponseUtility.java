/******************************************************************************
 *  Compilation:  javac -d bin Responseutility.java
 *  Execution:    
 *               
 *  
 *  Purpose:       create utility for  Rabbitmq
 *
 *  @author  pandit walde
 *  @version 1.0
 *  @since  19-11-2019
 *
 ******************************************************************************/

package com.bridgelabz.fundoonotemodule.utility;

import java.util.List;

import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.response.Response;

public class ResponseUtility {
	
	
	/**Method: To Return Success Response message of String type 
	 * @param message
	 * @return Success Response Message
	 */
	public static Response customSuccessResponse(String message) {
		
		return new Response(200, message, true);
	}
	
	
    /**Method: To Return Unsucess Response message of String type
     * @param message
     * @return Unsucess Response Message
     */
    public static Response customUnsuccessResponse(String message) {
		
		return new Response(200, message, true);
	}
    
    
    /**Method: To Return Sucess Response Message of String and List Type
     * @param <T>
     * @param message
     * @param list
     * @return Sucess Response Message
     */
    public static <T> Response customSucessResponse(String message,List<T> list) {
    	
    	return new Response(200, message, list);
    }
    
    
	/**Method: To Return Sucess Response Message of String and Object Type
	 * @param message
	 * @param note
	 * @return Sucess Response Message
	 */
	public static Response CustomSucessResponse(String message,Note note) {
		
		return new Response(200,message, note);
	}

}

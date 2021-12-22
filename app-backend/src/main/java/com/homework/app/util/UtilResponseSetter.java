package com.homework.app.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UtilResponseSetter {

    public static void setErrorResponse(HttpServletResponse response, int status,  String message){
        response.setStatus(status);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

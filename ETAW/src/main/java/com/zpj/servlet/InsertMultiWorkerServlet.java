package com.zpj.servlet;

import analysis.Analyser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "InsertMultiWorkerServlet")
public class InsertMultiWorkerServlet extends HttpServlet {

    private String account = "123";



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UploadServlet uploadServlet = new UploadServlet();
        String url = uploadServlet.getUploadUrl(request,response);
        System.out.println(url);
        Analyser analyser = new Analyser(account);
        try {
            ArrayList<String> result = analyser.getProbabilityFromCSV(url,account);
            System.out.println("批量读取文件成功");
            System.out.println(result);

        } catch (Exception e) {
            System.out.println("批量读取文件失败");
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        try{
            out.print("<script>alert('上传成功');window.location.href = 'http://localhost:8080/analyseMultiWorker.jsp'</script>");
        } catch (Exception e){
            out.print("<script>alert('上传失败');window.location.href = 'http://localhost:8080/insertWorker.jsp'</script>");
        }

    }
}

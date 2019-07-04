package com.zpj.servlet;

import analysis.Analyser;
import com.zpj.pojo.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InsertMultiWorkerServlet")
public class InsertMultiWorkerServlet extends HttpServlet {

    private User user = new User();
    private String account;

    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    public static final String UPLOAD_DIRECTORY = "upload";
    public static String uploadPath;
    public static String filePath;

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    public String getUploadUrl(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        List<String> plist = new ArrayList<>();

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        uploadPath = getServletContext().getRealPath("/")  + UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        String fileUrl="";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(req);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (item.isFormField()) {
                        String value = item.getString("UTF-8");
                        plist.add(value);
                        continue;
                    }else{
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        fileUrl=filePath;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        try {
                            item.write(storeFile);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
        account = plist.get(0);

        return fileUrl;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = getUploadUrl(request,response);
        System.out.println(url);
        Analyser analyser = new Analyser(account);
        String aid = account + "分析方案";
        try {
            ArrayList<String> result = analyser.getProbabilityFromCSV(url,aid);
            ArrayList<Float> leftResult2 = analyser.getResult(result,0);
            ArrayList<Float> scoreResult2 = analyser.getResult(result,1);
            System.out.println(leftResult2);
            System.out.println(scoreResult2);

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
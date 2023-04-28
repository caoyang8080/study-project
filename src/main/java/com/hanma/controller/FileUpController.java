package com.hanma.controller;

import com.hanma.pojo.UploadUtil;
import com.hanma.service.IMyService;
import com.hanma.service.impl.CmsArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fileup")
public class FileUpController {

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private IMyService iMyService;

    @RequestMapping(value = "/upload")
    @ApiOperation(value = "本地文件上传",notes ="本地文件上传" )
    public Map uploadfunction(HttpServletRequest request, HttpServletResponse response){
        //创建文件对象并获取请求中的文件对象
        MultipartFile file = null;
        Map resultData = new HashMap();

        try{
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            file = mRequest.getFile("fileNames");

            //判断上传非空
            if(null == file) {
                resultData.put("code",0);
                resultData.put("msg","上传文件失败");
                resultData.put("filename",file.getOriginalFilename());
                return resultData;
            }
            //上传需要导入数据的文件
            //用来检测程序运行时间
            long  startTime=System.currentTimeMillis();
            System.out.println("上传的文件名为："+file.getOriginalFilename());
            String fileName = file.getOriginalFilename();

            InputStream inputStream = file.getInputStream();
            UploadUtil uploadUtil = new UploadUtil();
            String hostName = uploadUtil.getHostname();
            String username = uploadUtil.getUsername();
            String password = uploadUtil.getPassword();
            String targetPath = uploadUtil.getTargetPath();
            String suffix = "";
            fileName = cmsArticleService.upload(hostName,username,password,targetPath,suffix,inputStream);
            //计算上传时间
            long  endTime=System.currentTimeMillis();
            String uploadTime = String.valueOf(endTime-startTime);
            System.out.println("上传所用时间："+uploadTime+"ms");

            resultData.put("code",200);
            resultData.put("msg","上传文件成功");
            resultData.put("filename",fileName);
            return resultData;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单个文件上传
     * @param multipartFile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/upload1")
    @ResponseBody
    //将上传的文件放在tomcat目录下面的file文件夹中
    public String upload(@RequestParam(value = "imageFile") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取到原文件全名
        String originalFilename = multipartFile.getOriginalFilename();
        // request.getServletContext()。getRealPath("")这里不能使用这个，这个是获取servlet的对象，并获取到的一个临时文件的路径，所以这里不能使用这个
        //这里获取到我们项目的根目录，classpath下面
        /*String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();*/
        String realPath="D:/tmp/";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        //文件夹路径,这里以时间作为目录
        String path = realPath + "static/" + format;
        //判断文件夹是否存在，存在就不需要重新创建，不存在就创建
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //转换成对应的文件存储，new File第一个参数是目录的路径，第二个参数是文件的完整名字
        multipartFile.transferTo(new File(file, originalFilename));

        //上传文件的全路径
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + format + "/" + originalFilename;
        System.out.println("url:"+url);
        System.out.println("path:"+path);
        return url;

    }
    /**
     * 图片导入
     * @param
     * @param
     * @return
     */
    @PostMapping(value = "/imageUpload")
    @ApiOperation(value = "接线图片上传", notes = "接线图片上传")
    public String imageUpload(@RequestParam(value = "imageFile", required = true) MultipartFile imageFile) {
        return iMyService.imageUpload(imageFile);
    }
    /**
     * 图片导出
     * @param imagePath
     * @param response
     */
    @GetMapping(value = "/imageDownload")
    @ApiOperation(value = "接线图片下载", notes = "接线图片下载")
    public void imageDownload(@RequestParam(value = "imagePath", required = true) String imagePath,HttpServletResponse response) {
        iMyService.imageDownload(imagePath,response);
    }
}

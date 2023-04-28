/*
package com.hanma.utils;
 
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang3.StringUtils;


 
*/
/**
 * 文件上传工具类
 * 描述1：基于commons-fileupload，测试时使用的版本为1.3.1
 * 描述2：如果在springmvc项目中使用，需要关闭其自身的上传功能，springboot项目也需要在配置中spring.servlet.multipart.enabled=false
 * 注意：文件存放根目录（basePath）必须存在
 *
 * zkh
 * 2019年11月7日 12:26
 *//*

public class UploadUtils {
 
    // （必须）文件保存目录根路径（默认为项目根路径）
    private String basePath = System.getProperty("user.dir");
    // （可选）文件的目录名（根路径下继续往下分的目录）
    private String dirName = "";
    // （可选）文件访问根地址（比如项目为 http://localhost:8080/xxx/fileupload，则baseUrl可以为fileupload）
    private String baseUrl = "";
    // （可选）单个文件最大大小
    private Long fileSizeMax = 1024 * 1024 * 10L;
    // （可选）所有文件最大大小之和
    private Long sizeMax = 1024 * 1024 * 10L;
    // （可选）允许上传的文件扩展名（逗号","隔开）
    private String extNames;
    // （可选）是否允许重命名文件(默认允许，多个文件时自动设置为不允许)
    private boolean allowRename = true;
    // （可选）自定义的文件名（不一定是最终的文件名，还要看是否允许重命名）
    private String fileRename;
    // （可选）上传临时路径（默认为系统的临时目录）
    private String tempPath = System.getProperty("java.io.tmpdir");
 
    */
/**上边的是可自定义的属性，下边的为只读get（不能赋值set）属性==================================================================================*//*

 
    // 文件的最终文件名
    private String newFileName;
 
    // 所有文件的实际大小
    private Long filesSize = 0L;
 
    // 文件保存目录路径
    private String savePath;
    // （不带域名及项目路径）文件访问路径（不包括文件名），中间变量，用于组装最终的访问URL
    private String saveUrl;
    // （不带域名及项目路径）文件最终访问的URL（包括文件名）
    private String fileUrl;
 
    // 请求中的普通表单字段
    private Map<String, String> fields = new HashMap<String, String>();
    // 请求中的文件类型字段
    private List<FileItem> fileList = new ArrayList<FileItem>();
 
    // 上传错误提心信息
    private String errorMsg;
    // 每个文件的上传结果
    private List<Result> uploadResults = new ArrayList<Result>();
 
 
    private HttpServletRequest request = null;
    public UploadUtils(HttpServletRequest request) {
        this.request = request;
    }
 
 
    */
/**
     * 文件上传
     *//*

    @SuppressWarnings("unchecked")
    public ResultData upload() throws Exception {
        // 验证初始化表单字段
        this.initCheckDir();
        if(StringUtils.isNotBlank(this.errorMsg)) {
            return ResultData.fail(this.errorMsg);
        }
        // 表单字段初始化及验证
        this.initFieldAndCheckFile();
        if(StringUtils.isNotBlank(this.errorMsg)) {
            return ResultData.fail(this.errorMsg);
        }
 
        // 上传文件
        if (!this.fileList.isEmpty()) {
            if(this.fileList.size() > 1) {
                // 多个文件时不允许重命名
                this.allowRename = false;
            }
            // 保存上传的文件
            for (FileItem item : this.fileList) {
                Result result = new Result();
                result.setOldFileName(item.getName());
                result.setFieldName(item.getFieldName());
                this.saveFile(item); // （重要）保存文件
                result.setSavePath(this.savePath + this.newFileName);
                result.setFileUrl(this.saveUrl + this.newFileName);
                result.setNewFileName(this.newFileName);
                uploadResults.add(result);
            }
        } else {
            return ResultData.fail("没有发现文件");
        }
 
        if(StringUtils.isBlank(this.errorMsg)) {
            return ResultData.success("上传成功", uploadResults);
        } else {
            return ResultData.fail("上传失败", uploadResults);
        }
    }
 
    */
/**
     * 上传前验证及初始化文件目录
     * 描述：验证-1
     *//*

    private void initCheckDir() throws Exception {
        // 文件保存目录路径
        this.savePath = this.basePath + "/";
        // 文件保存目录URL
        this.saveUrl = this.baseUrl + "/";
        // 文件存放文件夹目录
        File uploadDir = new File(this.savePath);
        // 获取内容类型
        String contentType = this.request.getContentType();
        if (contentType == null || !contentType.startsWith("multipart")) {
            this.errorMsg = "请求不包含multipart/form-data流";
        } else if (!ServletFileUpload.isMultipartContent(this.request)) {
            this.errorMsg = "请选择文件";
        } else if (!uploadDir.isDirectory()) {
            this.errorMsg = "上传目录[" + this.savePath + "]不存在";
        } else if (!uploadDir.canWrite()) {
            this.errorMsg = "上传目录[" + this.savePath + "]没有写权限";
        } else {
            // 创建文件夹 basePath/dirName/
            this.savePath += this.dirName + File.separator;
            this.saveUrl += this.dirName + "/";
            File saveDirFile = new File(this.savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            // 获取上传临时路径
            this.tempPath += File.separator;
            File file = new File(this.tempPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 统一路径分隔符
            this.savePath = this.savePath.replace("/", File.separator)
                               .replace("//", File.separator)
                               .replace("\\", File.separator)
                               .replace("\\\\", File.separator);
            this.saveUrl = this.saveUrl.replace("\\", "/").replace("//", "/");
        }
    }
 
    */
/**
     * 初始化请求中的表单属性字段，验证文件字段
     * 描述：验证-2
     * 描述：返回表单中的一般属性字段和文件属性字段的Map
     *//*
    private void initFieldAndCheckFile() throws Exception {
        // 判断request是否是文件上传请求
        if (ServletFileUpload.isMultipartContent(this.request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 阀值,超过这个值才会写到临时目录,否则在内存中
            factory.setSizeThreshold(1024 * 1024 * 10);
            factory.setRepository(new File(this.tempPath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            // 单个文件最大上传限制
            if(this.fileSizeMax > 0L) {
                upload.setFileSizeMax(this.fileSizeMax);
            }
            // 所有文件最大上传限制
            if(this.sizeMax > 0L) {
                upload.setSizeMax(this.sizeMax);
            }
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(new ServletRequestContext(this.request));
            } catch (FileUploadBase.FileSizeLimitExceededException fileSizeLimit){
                errorMsg = "上传文件中有文件的大小超出了单个文件允许的最大值(最大允许：" + FileUtils.formatLength(fileSizeMax) + ")";
                return;
            } catch (FileUploadBase.SizeLimitExceededException sizeLimit) {
                errorMsg = "上传文件大小之和超出允许的最大值(最大允许：" + FileUtils.formatLength(sizeMax) + ")";
                return;
            }
            // 处理请求中的FileItem
            if (items != null && !items.isEmpty()) {
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (item.isFormField()) {
                        // 普通表单字段
                        this.fields.put(item.getFieldName(), item.getString());
                    } else {
                        String fileName = item.getName();
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        // 文件类型字段
                        this.filesSize += item.getSize();
                        if (this.extNames != null && !Arrays.<String> asList(this.extNames.split(",")).contains(fileExt)) {// 检查扩展名
                            this.errorMsg += "上传文件扩展名是不允许的扩展名。\n只允许 " + this.extNames + " 格式 ";
                        } else {
                            this.fileList.add(item);
                        }
                    }
                }
            }
        }
    }
 
    */
/**
     * 保存文件
     *//*

    private void saveFile(FileItem item) throws Exception {
        String oldFileName = item.getName();
        String fileExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toLowerCase();
        // 允许重命名
        if (this.allowRename) {
            // 重命名字段为null或者重命名后的文件已存在则自动生成新的文件名
            if (StringUtils.isBlank(this.fileRename) || new File(this.savePath, this.fileRename + fileExt).exists()) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                this.newFileName = df.format(new Date()) + "_" + (new Random().nextInt(899) + 100) + "." + fileExt;
            } else {
                this.newFileName = this.fileRename + "." + fileExt;
            }
        } else {
            // 如果原文件名已存在则自动生成新文件名
            if(new File(this.savePath, oldFileName).exists()) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                this.newFileName = df.format(new Date()) + "_" + (new Random().nextInt(899) + 100) + "." + fileExt;
            } else {
                this.newFileName = oldFileName;
            }
        }
        File uploadedFile = new File(this.savePath, this.newFileName);
        // 写入文件
        item.write(uploadedFile);
    }
 
    public Long getFileSizeMax() { return fileSizeMax; }
    public void setFileSizeMax(Long fileSizeMax) { this.fileSizeMax = fileSizeMax; }
    public Long getSizeMax() { return sizeMax; }
    public void setSizeMax(Long sizeMax) { this.sizeMax = sizeMax; }
    public String getExtNames() { return extNames; }
    public void setExtNames(String extNames) { this.extNames = extNames; }
    public String getBasePath() { return basePath; }
    public void setBasePath(String basePath) { this.basePath = basePath; }
    public String getDirName() { return dirName; }
    public void setDirName(String dirName) { this.dirName = dirName; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getTempPath() { return tempPath; }
    public void setTempPath(String tempPath) { this.tempPath = tempPath; }
    public boolean isAllowRename() { return allowRename; }
    public void setAllowRename(boolean allowRename) { this.allowRename = allowRename; }
    public String getFileRename() { return fileRename; }
    public void setFileRename(String fileRename) { this.fileRename = fileRename; }
    public String getNewFileName() { return newFileName; }
    public Long getFilesSize() { return filesSize; }
    public String getSavePath() { return savePath; }
    public String getSaveUrl() { return saveUrl; }
    public String getFileUrl() { return fileUrl; }
    public Map<String, String> getFields() { return fields; }
    public List<FileItem> getFileList() { return fileList; }
    public String getErrorMsg() { return errorMsg; }
    public List<Result> getUploadResults() { return uploadResults; }
    public void setUploadResults(List<Result> uploadResults) { this.uploadResults = uploadResults; }
 
    */
/**
     * 每个文件的上传结果
     *//*

    public static class Result {
        // 文件保存绝对路径（包含文件名）
        private String savePath;
        // 文件访问路径（包含文件名）
        private String fileUrl;
        // 文件上传时的原始文件名
        private String oldFileName;
        // 文件最终保存时的文件名
        private String newFileName;
        // 表单文本框(input[type=file])的name属性
        private String fieldName;
 
        public String getSavePath() { return savePath; }
        public void setSavePath(String savePath) { this.savePath = savePath; }
        public String getFileUrl() { return fileUrl; }
        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
        public String getNewFileName() { return newFileName; }
        public void setNewFileName(String newFileName) { this.newFileName = newFileName; }
        public String getOldFileName() { return oldFileName; }
        public void setOldFileName(String oldFileName) { this.oldFileName = oldFileName; }
        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    }
 
}*/

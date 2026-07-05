package com.seek.food.util.FileUtil;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.TimeUtil.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class FileSave {
    //直接全流程保存文件
    public static String checkAndSaveFile(MultipartFile file, String dest, long size, HashSet<String> types) {
        //检查文件格式,并且生成一个随机的文件名,如果有必要，可以使用Redis进行强流量环境必定唯一文件名绑定
        String newFileName=UUID.randomUUID()+checkFile(file,size,types);
        //将目录路径与新的文件名尝试拼接后
        saveFile(file,createDestDir(dest).resolve(newFileName));
        return newFileName;
    }
    //直接保存文件，不检查目录
    public static String quickCheckAndSaveFile(MultipartFile file, String dest, long size, HashSet<String> types) {
        //检查文件格式,并且生成一个随机的文件名
        String newFileName=UUID.randomUUID()+checkFile(file,size,types);
        //将目录路径与新的文件名尝试拼接后
        saveFile(file,Paths.get(dest).resolve(newFileName));
        return newFileName;
    }

    //检查文件格式合法性
    public static String checkFile(MultipartFile file, long size, HashSet<String> types) {
        //检查文件是否为空
        if (file==null||file.isEmpty()) throw new BizException(ErrorCodeEnum.FILE_IS_EMPTY);
        //检查文件大小
        if (file.getSize() > size) throw new BizException(ErrorCodeEnum.TOO_BIG_FILE);
        //检查文件名
        String oldFileName = file.getOriginalFilename();
        if (oldFileName == null || oldFileName.isBlank()) throw new BizException(ErrorCodeEnum.ERROR_FILE_NAME);
        //获取后缀名索引
        int dotIndex = oldFileName.lastIndexOf(".");
        if (dotIndex == -1) throw  new BizException(ErrorCodeEnum.ERROR_FILE_TYPE);
        // 统一转小写，避免大小写问题,并且获取类型
        String type = oldFileName.substring(dotIndex).toLowerCase();
        //检测头像的后缀名
        if (!types.contains(type)) throw new BizException(ErrorCodeEnum.ERROR_FILE_TYPE);
        return type;
    }

    //创建文件目录
    public static Path createDestDir(String dest) {
        //获取目录
        Path destDir = Paths.get(dest);
        //如果目录不存在
        if (!Files.exists(destDir)) {
            //尝试创建目录
            try {Files.createDirectories(destDir);}
            catch (IOException e) {throw new RuntimeException("文件保存目录创建失败",e);}
        }
        return destDir;
    }

    //保存文件
    public static void saveFile(MultipartFile file,Path destPath) {
        //保存文件
        try {Files.copy(file.getInputStream(), destPath);}
        catch (IOException e) {throw new RuntimeException("文件保存失败",e);}
    }
}

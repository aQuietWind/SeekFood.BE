package com.seek.food.util.FileUtil;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

@Slf4j
public class FileSave {
    //直接全流程保存文件
    public static String checkAndSaveFile(MultipartFile file, String dest, long size, Set<String> types) {
        //检查文件格式,并且生成一个随机的文件名,如果有必要，可以使用Redis进行强流量环境必定唯一文件名绑定
        String newFileName=checkFile(file,size,types);
        //将目录路径与新的文件名尝试拼接后
        saveFile(file,createDestDir(dest).resolve(newFileName));
        return newFileName;
    }
    //直接保存文件，不检查目录
    public static String quickCheckAndSaveFile(MultipartFile file, String dest, long size, Set<String> types) {
        //检查文件格式,并且生成一个随机的文件名
        String newFileName=checkFile(file,size,types);
        //将目录路径与新的文件名尝试拼接后,直接保存
        saveFile(file,Paths.get(dest).resolve(newFileName));
        return newFileName;
    }


    //直接保存多文件，不检查目录
    public static void quickJudgeAndSaveFiles(List<MultipartFile> files, String dest, long size, Set<String> types
    , Function<List<String>,Boolean> beforeSaveCheck) {
        //检查文件是否为空
        if (files==null || files.isEmpty()) return;
        //检查文件格式,并且生成一个随机的文件名
        List<String> newFileNames=new ArrayList<>();
        for(MultipartFile file:files) newFileNames.add(checkFile(file,size,types));
        //进行中间操作的执行，适配mysql写入的判断
        if (!beforeSaveCheck.apply(newFileNames)) throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        //将目录路径与新的文件名尝试拼接后,保存文件
        saveFiles(files,dest,newFileNames);
    }

    //检查文件格式合法性
    private static String checkFile(MultipartFile file, long size, Set<String> types) {
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
        return UUID.randomUUID()+type;
    }

    //检查多文件格式合法性
    private static List<String> checkFiles(List<MultipartFile> files, long size, Set<String> types) {
        //检查文件是否为空
        if (files==null||files.isEmpty()) throw new BizException(ErrorCodeEnum.FILE_IS_EMPTY);
        List<String> newFileNames=new ArrayList<>();
        for (int i = 0; i < files.size(); i++) newFileNames.add(checkFile(files.get(i),size,types));
        return newFileNames;
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
    private static void saveFile(MultipartFile file,Path destPath) {
        //尝试保存文件
        try {Files.copy(file.getInputStream(), destPath);}
        catch (IOException e) {throw new RuntimeException("文件保存失败",e);}
    }

    //保存多文件于统一目录
    private static void saveFiles(List<MultipartFile> files,String dest,List<String> fileNames) {
        //尝试保存文件
        int i = 0;
        try {
            for (; i < files.size(); i++) Files.copy(files.get(i).getInputStream(),Paths.get(dest).resolve(fileNames.get(i)));
        }
        catch (IOException e) {
            int j = 0;
            //善后删除已经保存文件
            for (; j < i; j++) try {
                FileRemove.removeFile(dest, fileNames.get(j));
            } catch (Exception removeError){log.error("dest:{},addr:{},在进行多文件保存失败时重新删除的时候发生异常",dest,fileNames.get(j),e);}
            throw new RuntimeException("多文件保存失败",e);
        }
    }












}

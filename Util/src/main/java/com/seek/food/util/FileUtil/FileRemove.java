package com.seek.food.util.FileUtil;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRemove {
    //传输拼接路径下来
    public static void removeFile(String dest,String addr) {
        quickRemoveFile(Paths.get(dest).resolve(addr));
    }
    //直接完整路径
    public static void removeFileByPath(String path) {
        quickRemoveFile(Paths.get(path));
    }

    //拼接路径
    public static String resolvePath(String dest,String addr) {
        return Paths.get(dest).resolve(addr).toString();
    }

    public static  void quickRemoveFile(Path path) {
        //检查路径是否存在
        if (!Files.exists(path)){
            throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        }
        //删除文件
        if(!path.toFile().delete()){
            throw new BizException(ErrorCodeEnum.FILE_DELETE_ERROR);
        }
    }
}

CREATE DATABASE if not exists  seek_food_user;
USE seek_food_user;
drop table if exists  `user`;
CREATE TABLE `user` (
                        `user_id` bigint NOT NULL COMMENT '用户id',
                        `username` varchar(15) NOT NULL COMMENT '用户名',
                        `phone_number` varchar(11) NOT NULL UNIQUE COMMENT '手机号',
                        `password` varchar(20) NOT NULL COMMENT '密码',
                        `sex` tinyint COMMENT '性别',
                        `header_image_addr` varchar(50) COMMENT '头像地址',
                        `birthday` date COMMENT '生日',
                        `create_time` datetime NOT NULL default now() COMMENT '创建时间',
                        `is_delete` boolean NOT NULL DEFAULT false COMMENT '是否被删除',
                        `order_amount` int NOT NULL DEFAULT 0 COMMENT '交易次数',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uk_phone` (`phone_number`),
                        UNIQUE KEY `uk_header_image_addr`(`header_image_addr`),
                        INDEX phone_index(`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
#旧文件存储
drop table if exists `old_file`;
CREATE TABLE `old_file` (
                            `file_addr` varchar(50) NOT NULL COMMENT '文件地址',
                            `user_id` bigint NOT NULL COMMENT '原持有者id',
                            `create_time` datetime NOT NULL default now() COMMENT '创建时间',
                            `is_delete` boolean NOT NULL DEFAULT false COMMENT '是否被删除',
                            PRIMARY KEY (`file_addr`),
                            index id_index(`user_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='待删除文件表';
create trigger file_update before update on seek_food_user.user for each row
#触发器，用于删除旧文件
BEGIN
    -- 判断：只有头像路径发生变化才执行内部逻辑
    IF OLD.header_image_addr != NEW.header_image_addr THEN
        -- 这里写你要执行的逻辑，示例：把旧头像存入文件待删表
        INSERT INTO old_file(file_addr,user_id)
        VALUES (OLD.header_image_addr,OLD.user_id);
    END IF;
END
































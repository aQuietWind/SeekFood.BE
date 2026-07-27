
CREATE DATABASE if not exists seek_food_interaction;
USE seek_food_interaction;

drop table if exists  `merchant_like_connection`;
CREATE TABLE `merchant_like_connection` (
                                            `connection_id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '该关联关系的id',
                                            `merchant_id` bigint COMMENT '商家id',
                                            `account_id` bigint COMMENT '关注方的id',
                                            `update_time` datetime NOT NULL default now() COMMENT '更新时间',
                                            `is_like` boolean NOT NULL DEFAULT false COMMENT '是否点赞',
                                            unique key uk_like_key(merchant_id,account_id),
                                            INDEX merchant_index(merchant_id),
                                            INDEX account_index(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家点赞表';


drop table if exists  `first_comment_like_connection`;
CREATE TABLE `first_comment_like_connection` (
                                                 `connection_id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '该关联关系的id',
                                                 `first_comment_id` bigint COMMENT '一级评论id',
                                                 `account_id` bigint COMMENT '关注方的id',
                                                 `update_time` datetime NOT NULL default now() COMMENT '更新时间',
                                                 `is_like` boolean NOT NULL DEFAULT false COMMENT '是否点赞',
                                                 unique key uk_like_key(first_comment_id,account_id),
                                                 INDEX first_comment_index(first_comment_id),
                                                 INDEX account_index(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一级评论点赞表';


drop table if exists  `second_comment_like_connection`;
CREATE TABLE `second_comment_like_connection` (
                                                  `connection_id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '该关联关系的id',
                                                  `second_comment_id` bigint COMMENT '二级评论id',
                                                  `account_id` bigint COMMENT '关注方的id',
                                                  `update_time` datetime NOT NULL default now() COMMENT '更新时间',
                                                  `is_like` boolean NOT NULL DEFAULT false COMMENT '是否点赞',
                                                  unique key uk_like_key(second_comment_id,account_id),
                                                  INDEX second_comment_index(second_comment_id),
                                                  INDEX account_index(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='二级评论点赞表';


drop table if exists  `merchant_collect_connection`;
CREATE TABLE `merchant_collect_connection` (
                                               `connection_id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT '该关联关系的id',
                                               `merchant_id` bigint COMMENT '商家id',
                                               `account_id` bigint COMMENT '关注方的id',
                                               `update_time` datetime NOT NULL default now() COMMENT '更新时间',
                                               `is_collect` boolean NOT NULL DEFAULT false COMMENT '是否收藏',
                                               unique key uk_like_key(merchant_id,account_id),
                                               INDEX merchant_index(merchant_id),
                                               INDEX account_index(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家收藏表';


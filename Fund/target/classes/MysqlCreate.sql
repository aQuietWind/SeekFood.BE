CREATE DATABASE if not exists seek_food_fund;
USE seek_food_fund;
drop table if exists `fund`;
CREATE TABLE `fund` (
                        `fund_id` bigint NOT NULL COMMENT ' 余额id',
                        `account_id` bigint NOT NULL COMMENT ' 账户id',
                        `create_time` datetime NOT NULL default now() COMMENT '创建时间',
                        `fund_amount` bigint NOT NULL DEFAULT 0 COMMENT '金额量',
                        `is_delete` boolean NOT NULL DEFAULT false COMMENT '是否被删除',
                        PRIMARY KEY (`fund_id`),
                        UNIQUE KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额表';

































create table if not exists t_user (
    `id` varchar(32) not null comment 'id',
    `name` varchar(50) default null comment '名称',
    `username` varchar(50) default null comment '登录名',
    `password` varchar(50) default null comment '密码',
    `phone` varchar(20) default null comment '手机',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '左后修改时间',
    PRIMARY KEY (id)
) default charset utf8mb4 collate utf8mb4_general_ci;
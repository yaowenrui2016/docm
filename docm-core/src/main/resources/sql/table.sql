create table if not exists t_user (
    `id` varchar(32) not null comment 'id',
    `name` varchar(50) default null comment '名称',
    `username` varchar(50) default null comment '登录名',
    `password` varchar(50) default null comment '密码',
    `phone` varchar(20) default null comment '手机',
    `email` varchar(20) default null comment '邮箱',
    `activate` bit default b'0' comment '激活状态：1-激活 0-待激活',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '左后修改时间',
    PRIMARY KEY (id)
) default charset utf8mb4 collate utf8mb4_general_ci;
delete from t_user where id = '190714112153000';
insert into t_user(id, username, password, activate) values('190714112153000', 'admin', md5('1234'), b'1');

create table if not exists t_docm (
    `id` varchar(32) not null comment 'id',
    `name` varchar(50) default null comment '名称',
    `project_name` varchar(50) default null comment '项目名称',
    `project_type` varchar(50) default null comment '项目类型',
    `company` varchar(50) default null comment '公司名称',
    `contract_num` varchar(50) default null comment '合同号',
    `contract_time` varchar(50) default null comment '合同签订时间',
    `credential_num` varchar(50) default null comment '凭证号',
    `credential_time` varchar(50) default null comment '凭证时间',
    `money` bigint default null comment '金额',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '左后修改时间',
    PRIMARY KEY (id)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_attachment (
    `id` varchar(32) not null comment 'id',
    `docm_id` varchar(32) not null comment 'docm_id',
    `name` varchar(50) default null comment '名称',
    `doc_name` varchar(256) default null comment '文档名称',
    `doc_path` varchar(256) default null comment '文档路径',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '左后修改时间',
    PRIMARY KEY (id)
) default charset utf8mb4 collate utf8mb4_general_ci;
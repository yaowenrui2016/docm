create table if not exists t_account (
    `id` varchar(32) not null comment 'id',
    `name` varchar(200) default null comment '名称',
    `username` varchar(50) default null comment '登录名',
    `password` varchar(50) default null comment '密码',
    `phone` varchar(20) default null comment '手机',
    `email` varchar(20) default null comment '邮箱',
    `dept_id` varchar(20) default null comment '所属科室',
    `activate` bit default b'0' comment '激活状态：1-激活 0-待激活',
    `frozen` bit default b'0' comment '冻结状态：1-冻结 0-正常',
    `isBuiltIn` bit default b'0' comment '是否内置账号：1-内置 0-非内置',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id),
    unique key (username)
) default charset utf8mb4 collate utf8mb4_general_ci;
delete from t_account where id = '190714112153000';
insert into t_account(id, username, password, isBuiltIn, activate) values('190714112153000', 'admin', md5('1234'), b'1', b'1');

create table if not exists t_dept (
    `id` varchar(32) not null comment 'id',
    `name` varchar(200) default null comment '科室名称',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id),
    unique key (name)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_docm (
    `id` varchar(32) not null comment 'id',
    `name` varchar(200) default null comment '名称',
    `project_name` varchar(200) default null comment '项目名称',
    `project_type` varchar(100) default null comment '项目类型',
    `company` varchar(200) default null comment '公司名称',
    `contract_num` varchar(50) default null comment '合同号',
    `contract_time` varchar(50) default null comment '合同签订时间',
    `money` bigint default null comment '金额',
    `dept_id` varchar(100) default null comment '所属科室',
    `desc` varchar(2000) default null comment '备注',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_pay_item (
    `id` varchar(32) not null comment 'id',
    `order` int(4) default null comment '序号',
    `credential_num` varchar(50) default null comment '凭证号',
    `credential_time` varchar(50) default null comment '凭证时间',
    `pay_time` varchar(50) default null comment '付款时间',
    `money` bigint default null comment '金额',
    `contract_id` varchar(20) default null comment '所属合同',
    `desc` varchar(2000) default null comment '备注',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci comment '付款项';

create table if not exists t_attachment (
    `id` varchar(32) not null comment 'id',
    `name` varchar(50) default null comment '名称',
    `docm_id` varchar(32) not null comment '所属文档管理对象的id',
    `doc_path` varchar(256) default null comment '附件路径',
    `doc_name` varchar(256) default null comment '文件名',
    `type` varchar(200) default null comment '文件类型',
    `size` bigint(20) default null comment '文件大小',
    `md5` varchar(100) default null comment '文件MD5',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_permission (
    `id` varchar(32) not null comment 'id',
    `name` varchar(100) default null comment '名称',
    `module` varchar(50) default null comment '模块',
    `desc` varchar(255) default null comment '描述',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_account_correlate_permission (
    `id` int(20) auto_increment comment 'id',
    `account_id` varchar(32) default null comment '账号id',
    `permission_id` varchar(32) default null comment '权限id',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci;

create table if not exists t_oper_log (
    `id` varchar(32) not null comment 'id',
    `name` varchar(50) default null comment '操作名称',
    `module` varchar(50) default null comment '模块',
    `result` varchar(20) default null comment '结果',
    `operator` varchar(32) default null comment '操作者',
    `user_agent` varchar(255) default null comment '浏览器信息',
    `ip` varchar(20) default null comment 'ip地址',
    `method` varchar(20) default null comment 'HTTP方法',
    `url` varchar(255) default null comment 'URL',
    `status` bit default b'0' comment '日志状态：0-未查看 1-已查看',
    `content` varchar(4000) default null comment '内容',
    `state` bit default b'1' comment '状态：1-正常 0-逻辑删除',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `last_modify_time` timestamp null on update current_timestamp comment '最后修改时间',
    primary key (id)
) default charset utf8mb4 collate utf8mb4_general_ci;
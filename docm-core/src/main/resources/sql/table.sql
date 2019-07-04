CREATE TABLE IF NOT EXISTS t_user (
    account_id    VARCHAR(32) NOT NULL COMMENT '账号ID',
    username      VARCHAR(32) DEFAULT NULL COMMENT '用户名',
    nickname      VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    email         VARCHAR(32) DEFAULT NULL COMMENT '邮箱',
    cellphone     VARCHAR(15) DEFAULT NULL COMMENT '手机',
    password      VARCHAR(32) NOT NULL COMMENT '密码',
    state         TINYINT(4)  DEFAULT 1 COMMENT '状态：1-正常 8-冻结',
    create_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '注册账号时间',
    PRIMARY KEY (account_id)
) DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS t_sync_method (
    method_id     INT(10) AUTO_INCREMENT COMMENT '方法ID',
    method_name   VARCHAR(32) DEFAULT NULL COMMENT '方法名',
    PRIMARY KEY (method_id),
    UNIQUE KEY `UNIQUE_METHOD_NAME` (method_name)
) DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
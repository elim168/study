INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('解忧杂货店','东野圭吾',27.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('边城','沈从文',23.00,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('中国哲学史','冯友兰',44.5,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('忽然七日',' 劳伦',19.33,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('苏东坡传','林语堂',19.30,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('百年孤独','马尔克斯',29.50,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('扶桑','严歌苓',19.8,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('给孩子的诗','北岛',22.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('为奴十二年','所罗门',16.5,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('平凡的世界','路遥',55.00,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('悟空传','今何在',14.00,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('硬派健身','斌卡',31.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('从晚清到民国','唐德刚',39.90,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('三体','刘慈欣',56.5,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('看见','柴静',19.50,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('活着','余华',11.00,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('小王子','安托万',19.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('我们仨','杨绛',11.30,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('生命不息,折腾不止','罗永浩',25.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('皮囊','蔡崇达',23.90,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('恰到好处的幸福','毕淑敏',16.40,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('大数据预测','埃里克',37.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('人月神话','布鲁克斯',55.90,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('C语言入门经典','霍尔顿',45.00,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('数学之美','吴军',29.90,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('Java编程思想','埃史尔',70.50,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('设计模式之禅','秦小波',20.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('图解机器学习','杉山将',33.80,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('艾伦图灵传','安德鲁',47.20,100,100,'static/img/default.jpg');
INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES('教父','马里奥普佐',29.00,100,100,'static/img/default.jpg');

create table t_cart(id int primary key auto_increment,user_id int not null);
create table t_cart_item(id int primary key auto_increment, cart_id int not null, book_id int not null, nums int);

-- 创建订单表
CREATE TABLE t_order(
id VARCHAR(100) PRIMARY KEY,
create_time DATETIME NOT NULL,
total_num INT NOT NULL,
total_amount DOUBLE(11,2) NOT NULL,
state INT NOT NULL,
user_id INT
);

-- 创建订单项表
CREATE TABLE t_order_item(
id INT PRIMARY KEY AUTO_INCREMENT,
nums INT NOT NULL,
amount DOUBLE(11,2) NOT NULL,
title VARCHAR(100) NOT NULL,
author VARCHAR(100) NOT NULL,
price DOUBLE(11,2) NOT NULL,
img_path VARCHAR(100) NOT NULL,
order_id VARCHAR(100) NOT NULL
);
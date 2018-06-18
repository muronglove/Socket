# 功能
本项目代码为[收纳APP](https://github.com/muronglove/TheFirst)服务端代码，功能主要有两个：
1. 客户端登录注册
2. 数据库同步

# 配置
1. 安装mysql
2. 因为代码中访问的是我自己的数据库，所以用户名和密码是root和jiqing，如果不想麻烦的话就修改成一样的，或者把代码中用到的用户名和密码全部改成自己的
3. 创建数据库androidstorage，并在该数据库中建立三个表（user/data/imei）,建表语句如下： 
 
```CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(80) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8```

```CREATE TABLE `data` (
  `userid` int(11) DEFAULT NULL,
  `uuid` varchar(80) NOT NULL,
  `caption` varchar(80) DEFAULT NULL,
  `label` varchar(80) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `color` varchar(80) DEFAULT NULL,
  `position` varchar(80) DEFAULT NULL,
  `date` varchar(80) DEFAULT NULL,
  `image` longtext,
  PRIMARY KEY (`uuid`),
  KEY `userid` (`userid`),
  CONSTRAINT `data_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8```

```CREATE TABLE `imei` (
  `uuid` varchar(80) NOT NULL,
  `imeinumber` varchar(80) NOT NULL,
  `a` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`,`imeinumber`),
  CONSTRAINT `imei_ibfk_1` FOREIGN KEY (`uuid`) REFERENCES `data` (`uuid`),
  CONSTRAINT `imei_ibfk_2` FOREIGN KEY (`uuid`) REFERENCES `data` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8```

# 注意事项
如果是第一次使用mysql，经常会出现代码第一次好用，接着就无法使用的情况，其实多数是因为没有打开mysql所致，在命令行中输入`mysql.server start`即可；另外，mysql是目前最简单的轻量级数据库，语法并不难，建议大家多查多用
一、请在src目录下新建一个名为easy.properties的文件，
然后在properties中给出以下项的配置：
1.driverClassName----数据库驱动类名
2.url----------------数据库地址
3.username-----------数据库用户名
4.password-----------数据库密码
5.entityPackage------实体类包名

示例如下（可直接复制以下内容并修改）：
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/db_blog
username=root
password=mysqladmin
entityPackage=com.hegongshan.easy.generator.bean

二、任意新建一个类，在主方法下调用如下方法：
com.hegongshan.easy.generator.Generator.generateEntity();
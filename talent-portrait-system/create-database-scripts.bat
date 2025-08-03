@echo off
echo 正在创建数据库脚本目录...

mkdir database-scripts
cd database-scripts

echo 数据库脚本目录创建完成！
echo.
echo 请将以下SQL脚本文件保存到 database-scripts 目录中：
echo 1. 01-create-database.sql - 创建数据库
echo 2. 02-create-tables.sql - 创建表结构
echo 3. 03-create-indexes.sql - 创建索引
echo 4. 04-init-dict-data.sql - 初始化字典数据
echo 5. 05-init-demo-data.sql - 初始化演示数据
echo.
pause
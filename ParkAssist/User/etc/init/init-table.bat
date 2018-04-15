@echo off

set user=%1
set host=%2
set port=%3


mysql -u%user%  -h%host% -P%port% <init-table.sql
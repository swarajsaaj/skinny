@echo off

REM
REM Grab the directory where this script resides, for use later
REM


REM
REM Read all parameters into a single variable using an ugly loop
REM
set CMD_LINE_ARGS=%1
if ""%1""=="""" goto doneStart
shift
:getArgs
if ""%1""=="""" goto doneStart
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto getArgs
:doneStart

java -jar C:/skinny/skn.jar %CMD_LINE_ARGS%

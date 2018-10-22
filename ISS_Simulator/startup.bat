@echo off

title IssSimulator

SET ROOT_PATH=.
SET BIN_HOME=%ROOT_PATH%/bin
SET LIB_HOME=%ROOT_PATH%/lib
SET CLASSPATH=%BIN_HOME%;%LIB_HOME%/commons-pool2-2.0.jar;%LIB_HOME%/jedis-2.9.0.jar;%LIB_HOME%/poi-3.13.jar;%LIB_HOME%/poi-ooxml-3.13.jar;%LIB_HOME%/poi-ooxml-schemas-3.13.jar;%LIB_HOME%/xmlbeans-2.6.0.jar;

java com.iss.simulator.IssSimulator
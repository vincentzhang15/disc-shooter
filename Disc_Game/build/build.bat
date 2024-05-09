dir /s /B ..\src\*.java  > sources.txt

javac -cp ../lib/jogl-all.jar;../lib/gluegen-rt.jar;../lib/vecmath-1.5.1.jar  -d ../class  @sources.txt

xcopy /s /y ..\res ..\class

jar -cfe ds.jar frisbee.DiscShooter  -C ..\class .
set CHARSET=UTF-8 
set MIN_MEM=512m
set MAX_MEM=512m

java -Xms%MIN_MEM% -Xmx%MAX_MEM% -Dfile.encoding=%CHARSET% -Dclient.encoding.override=%CHARSET% -Ddefault.client.encoding=%CHARSET% -jar ExsNettyClientEx-1.0.0-SNAPSHOT.jar 9711

set CHARSET=UTF-8 
set MIN_MEM=256m
set MAX_MEM=512m

java -Xms%MIN_MEM% -Xmx%MAX_MEM% -Dfile.encoding=%CHARSET% -Dclient.encoding.override=%CHARSET% -Ddefault.client.encoding=%CHARSET%  -jar Exs4j-1.1.0-RELEASE.jar -server ./exs4j.conf

export CHARSET=UTF-8 
export MIN_MEM=256m
export MAX_MEM=512m

java -Xms$MIN_MEM -Xmx$MAX_MEM -Dfile.encoding=$CHARSET -Dclient.encoding.override=$CHARSET -Ddefault.client.encoding=$CHARSET -jar ExsNettyClientEx-1.0.0-SNAPSHOT.jar 9711
 

import sys;

f = open( sys.argv[1], "r" );
out = open( sys.argv[2], "w" );

i = 0;

for line in f:
  if ( i % 4 == 1 ):
    out.writelines( line );
  i += 1;
  
f.close();
out.close();
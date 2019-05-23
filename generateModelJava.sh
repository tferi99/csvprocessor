# it generates input JAVA Model class for CSV processor package
SRC_BASE=./src/main/java/org/ftoth/cvsproc

if [ $# -ne 3 ]
then
	app=`basename $0`
	echo "Usage: $app <mode> <target JAVA package> <input model descriptor file>" >&2
	echo "    where:"
	echo "       - mode: in/out"
	exit 1
fi
MODE=$1
PKG=$2
DESC=$3

if [ $MODE != "in" -a $MODE != "out" ]
then
	echo "$MODE: bad mode" >&2
	exit 2
fi
if [ $MODE == "in" ]
then
	CLASS=InputModel
fi
if [ $MODE == "out" ]
then
	CLASS=OutputModel
fi
MODEL_OUT=${CLASS}.java

# generating
./_genModelJava.sh $PKG $CLASS $DESC > $MODEL_OUT

# deployment
DIR=$SRC_BASE/$PKG
if [ ! -d $DIR ]
then
	echo "$DIR : target package directory not found" >&2
	exit 3
fi

TARGET_FILE=$DIR/$MODEL_OUT
if [ -f $TARGET_FILE ]
then
	echo "$TARGET_FILE : target file already exists."
	echo "Do you want to overwrite it (y/n)? " >&2
	read c
	if [ $c != 'y' ]
	then
		exit
	fi
fi

mv $MODEL_OUT $DIR

echo "Done." >&2
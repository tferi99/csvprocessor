# it generates input JAVA Model class for CSV processor package

if [ $# -ne 2 ]
then
	app=`basename $0`
	echo "Usage: $app <target JAVA package> <model descriptor file with header>" >&2
	exit 1
fi
PKG=$1
IN=$2
IN_TMP=$IN.tmp


if [ ! -f $IN ]
then
	echo "$IN : descriptor file not found" >&2
	exit 1
fi

cp $IN $IN_TMP
dos2unix $IN_TMP

# model
awk -v pkg=$PKG '
	BEGIN {
		FS="[;]"
		rowIdx=0
		colNum=0
	}
	{
		if (rowIdx == 0) {
			collectPropNames()
		}
		if (rowIdx == 1) {
			collectPropTypes()
		}
		if (rowIdx == 2) {
			collectAdditionalParams()
		}
		
		rowIdx++
	}
	END {
		generateModel()
	}

	function collectPropNames()
	{
		colNum = NF
		for (n=0; n<=colNum; n++) {
			props[n]=$n
		}
	}
	
	function collectPropTypes()
	{
		for (n=0; n<=colNum; n++) {
			colTypes[n]=$n
		}
	}
	
	function collectAdditionalParams()
	{
		for (n=0; n<=colNum; n++) {
			p=trim($n)
			params[n]=$n
		}		
	}
	
	function generateModel() {
		printf "package org.ftoth.cvsproc.%s;\n\n", pkg
		printf "import com.opencsv.bean.CsvBindByName;\n"
		printf "import com.opencsv.bean.CsvBindByPosition;\n"
		printf "import com.opencsv.bean.CsvDate;\n\n"
		
		printf "public class InputModel\n"
		printf "{\n"
		for(n=1; n<=colNum; n++) {
			f=trim(props[n])
			printf "\t@CsvBindByName(column = \"%s\")\n", f
			printf "\t@CsvBindByPosition(position = %d)\n", n-1
			if (params[n] != "") {
				if (colTypes[n] == "Date") {
					printf "\t@CsvDate(\"%s\")\n", params[n]
				}
			}
			var=camelCase(f)
			printf "\tprivate %s %s;\n\n", colTypes[n], var
		}
		printf "}\n"
	}
	
	function trim(s)
	{
		gsub("^[ ]*", "", s)
		gsub("[ ]*$", "", s)
		return s
	} 	 
	
	function camelCase(s)
	{
		res=""
		split(s, words, /[^a-zA-Z]+/);
		for (i=1; i<=length(words); i++) {
			if (i == 1) {
				res = res tolower(substr(words[i],1));
			}
			else {
				res = res toupper(substr(words[i],1,1)) tolower(substr(words[i],2));
			}
		}
		return res
	}
	
' $IN_TMP

rm $IN_TMP

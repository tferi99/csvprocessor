# it generates JAVa Model calss for CSV processor package.
if [ $# -ne 2 ]
then
	app=`basename $0`
	echo "Usage: $app <package> <input CSV file with header>"
	exit 1
fi
PKG=$1
IN_CSV=$2
MODEL_OUT=Model.java
MAPPING_OUT=CustomMappingStrategyAll.java

awk -v pkg=$PKG '
	BEGIN {
		FS="[,]"
		printf "package org.ftoth.cvsproc.%s;\n\n", pkg
		printf "import com.opencsv.bean.CsvBindByName;\n"
		printf "import com.opencsv.bean.CsvBindByPosition;\n\n"		
		printf "public class Model\n"
		printf "{\n"
	}
	{
		for(n=1; n<NF; n++) {
			f=trim($n)
			printf "\t@CsvBindByName(column = \"%s\")\n", f
			printf "\t@CsvBindByPosition(position = %d)\n", n-1
			var=camelCase(f)
			printf "\tprivate String %s;\n\n", var
		}
		exit
	}
	END {
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
	
' $IN_CSV > $MODEL_OUT


awk -v pkg=$PKG '
	BEGIN {
		FS="[,]"
		printf "package org.ftoth.cvsproc.%s;\n\n", pkg
		printf "import com.opencsv.bean.ColumnPositionMappingStrategy;\n\n"
		printf "public class CustomMappingStrategyAll<Model> extends ColumnPositionMappingStrategy<Model>\n"
		printf "{\n"
		printf "\tprivate static final String[] HEADER = new String[] {\n"
	}
	{
		printf "\t\t"
		for(n=1; n<NF; n++) {
			f=trim($n)
			if (n > 1) {
				printf ", "
			}
			printf "\"%s\"", f
		}
		exit
	}
	END {
		printf "\n\t};\n\n"
		printf "\t@Override\n"
		printf "\tpublic String[] generateHeader(Model bean) {\n"
        printf "\t\treturn HEADER;\n"
		printf "\t}\n"		
		printf "}\n"
	}
	
	function trim(s)
	{
		gsub("^[ ]*", "", s)
		gsub("[ ]*$", "", s)
		return s
	}	
' $IN_CSV > $MAPPING_OUT


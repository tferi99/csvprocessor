# ___UNDER CONSTRUCTION___

# cvsprocessor
CVS file processor based on OpenCVS library.

## Architecture
Project contains a general layer contains CSV reader, writer and group-processor components.

#### CsvReader
It reads a CSV file (or multiple) into a list of object (or a a list of object with associated list of other objects)

Usage:
1. Inherit a class from generics CsvReader
2. create: CSV loaded into reader
3. get list of beans

```Java
    CsvReader<Model> processor = new CsvReaderImpl<Model>(inputFile);
    List<Model> beans = processor.getBeans()
```
CSV field mapping during processing based on annotated model objects. CSV fields mapped into object properties.
A reader can read one CSV (one class) but OpenCVS supports reading associated object from multiple CSV file (theoratically).
So processing is not limited to a single CSV file.
  
Reading based on @CsvBindByPosition property annotations of Model object.

#### CsvWriter
It renders object data into a Writer in CSV format.


#### CsvGroupProcessor
It identifies object groups from CSV records by record ID. That means it collects CSV records into groups by the same record ID.
Current version supports only files ordered by ID. First it reads CSV into an object list with a CsvProcessor. Then it identifies groups by ID and calls processGroup(...) abstract method. ID is specified by getGroupIdFromBean(...) which returns an object. Object can contain composite ID (values from multiple fields). In this case create an ID class and implement hashCode() and equals() for this class.

Generally groups of records are processed. A group is built from records which have the same ID (Object.equals()). ID of a record is created/retrieved by implementation of CsvGroupProcessor.getGroupIdFromBean().

A group is processed if validation criterias are passed. Otherwise items of group will be redirected into REST file.
To add processing create a new class inherinting CsvGroupProcessor and implement validateGroup(...) and processGroup(...).
Base implementation povides you 2 writers:
1. __processingResultWriter__ for result
2. __restWriter__ for the rest (not-processed) records.

Non-processed records are redirected and written automatically into rest-file. Writing result records should be implemented by developer.

Usage:
1. Inherit a class from generics CsvGroupProcessor
2. implement getGroupIdFromBean(...) method to specify record ID
3. implement validateGroup(...)
3. implement processGroup(...)
4. create: CSV loaded into internal reader
5. Call process(). processGroup() will be called at the end of all groups.

```Java
    CsvGroupProcessor gp = new CsvGroupProcessorImpl(inputFile);
    gp.process();
```


# Custom processors
## How to add new custom processors
You can generate some metadata classes with a shellscript which generates Java classes from CSV column headers.

```bash
$ ./generateModelJava.sh
Usage: generateModelJava.sh <mode> <target JAVA package> <input model descriptor file>
    where:
       - mode: in/out
```
Where:
- target JAVA package: package of custom processor
- mode:
    - in: generating input data model (InputModel.java)
    - out: generating (primary) output data model (OutputModel.java)
- input model descriptor file: describes colums (see next section)    

JAVA classes generated into:

    src/main/java/org/ftoth/cvsproc/<target JAVA package>

For example to generate input model for 'custom2' processor:

```bash
$ ./generateModelJava.sh in custom2 c2_in_cfg.csv
```
## Model descriptor
- It describes an input/output model class
- CSV file, where separator is comma (,)
- in CSV a column contains information about a corresponding column in processed/generated CSV 
  (order of descriptor columns are of course in the same order as columns in data CSVs)
- It has 3 lines
    1. header titles of CSV columns
    2. JAVA types of properties where values are mapped from data CSVs
    3. additional parameter for types (e.g date or number format)
- Supported types are almost all JAVA types, but there are special types which require additional parameters. This parameter describes format. Syntax of format follows JAVA formatter rules (by SimpleDateFormat or DecimalFormat)
- Special types which require additional parameters:
    - Date: format 
    


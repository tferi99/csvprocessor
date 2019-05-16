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
  
#### CsvGroupProcessor
It identifies object groups from CSV records by record ID. That means it collects CSV records into groups by the same record ID.
Current version supports only files ordered by ID. First it reads CSV into an object list with a CsvProcessor. Then it identifies groups by ID and calls processGroup(...) abstract method. ID is specified by getGroupIdFromBean(...) which returns an object. Object can contain composite ID (values from multiple fields). In this case create an ID class and implement hashCode() and equals() for this class.

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

## Processors
CSV field mapping during processing based on annotated model objects. CSV fields mapped into object properties.
A processor can read one CSV (one class) but OpenCVS supports reading associated object from multiple CSV file (theoratically).

So processing is not limited to a single CSV file

Reading and writing based on independent strategies.


# Custom processors
## How to add new custom processors
You can generate some metadata classes with a shellscript which generates Java classes from CSV column headers.
1. Generate model and mapping startegy JAVA by input CSV.
2. Move Java classes into custom processor package (org.ftoth.cvsproc.&lt;CustProc&gt;)
3. 

## General behavior
Generally groups of records are processed. A group is built from records which have the same ID (Object.equals()). ID of a record is created/retrieved by implementation of CsvGroupProcessor.getGroupIdFromBean().

A group is processed if validation criterias are passed ()

## grp2
Requirement:
- records are groupped by 'Open Iem Key' (column 1)
- find groups which contain 2 records and where
    - 'USD Amount' (column 12) is negative in one record but positive in other record
- create a single record from processed groups with these fields:
       - 'Open Item Key' (col1)
       - USD amount difference (col12)
- other records 

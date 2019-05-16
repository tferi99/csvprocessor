# cvsprocessor
CVS file processor based on OpenCVS library.

## Architecture
Project contains a general layer contains processor group-processor and writer components.

#### CsvProcessor
It reads a CSV file (or multiple) into a list of object (or a a list of object with associated list of other objects)

Usage:
1. create: CSV loaded into processor
2. get list of beans

``` Java
    CsvProcessor<Model> processor = new CsvProcessor<Model>(inputFile);
    List<Model> beans = processor.getBeans()
```
  
#### CsvGroupProcessor

# Processors
CSV field mapping during processing based on annotated model objects. CSV fields mapped into object properties.
A processor can read one CSV (one class) but OpenCVS supports reading associated object from multiple CSV file (theoratically).

So processing is not limited to a single CSV file

Reading and writing based on independent strategies.


## Adding new custom processors


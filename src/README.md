# DataLakeSemanticAware
OpenData project
Class project
By apolitogaga and miguelmossa

********************
We have trhee packages.
********************

********************
dataAmazonType
********************
This is a package we use exclusively to do the logic of interpreting the amazon database, immediatly after poarsing the xml.
Every user has reviews. The review class has a user, and the movie it's reviewing. The movie class has an average of all the ratings given so far.

********************
utils
********************

Static classes used to do algorithms or text processing for the most part.

********************
dataLake
********************
Main class, where we do the querying, the tdb manipulation etc.

Load XML, loads the DOM file, and then it passes it to the Amazon packages
TDBManager, handles all the conection with the TDB, which is our databbase.
SPARQLManager. handles all the queries we are using to COnstruct from the web, select from our model, and insert new data from the XML.
QueryBUilder help us simplify the query generation. This is the reason we do not have queries, per se, we created this class with the aim of automatizing, the selection of the metadata.

Recommendator has a simple recomendator.

Integrator, handles all the integration stuff.

***************

PLease do check out the data folder because that's were we have the metadata file.

In most of our classes we have a bunch of constants, that's because we were thinking that most of it could be automatically generated jsut by reading from a metadata config gule.

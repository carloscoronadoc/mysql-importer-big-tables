# mysql-importer-big-tables
It allows you to import large tables into a mysql database using plain text files.

To import the data from the text file, you must use TAB to separate each column; you must include the header in the first line of the file; You must also create the table in the database with the same structure, it is not necessary that the column names of the file are the same as the fields in the table.

Syntax:

java -jar importerbig \"Host[:Port]\" \"DataBaseName\" \"User\" \"Password|*\" \"TableName\" \"FilePath\"

Example:

java -jar importerbig "localhost" "world_db" "root" "1234567890" "person" "c:/files/person20180602.txt"




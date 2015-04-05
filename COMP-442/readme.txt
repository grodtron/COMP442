The folder COMP-442-1-lexical-analyzer (despite the name) contains the complete code for
the lexical analyzer, the syntactic analyzer, and the semantic analyzer. It is an eclipse project which can be imported directly

To run the parse, run the class src/comp442/syntactical/parser/Driver.java as a Java application
- The file to be parsed is specified in settings.txt
- output files are:
    <code file name>.output
        - Contains a "pretty-printed" version of the parsed code based on the generated parse tree
    <code file name>.derivation
        - Contains a representation of the parse tree (the derivation of the code)
    <code file name>.error
        - Contains a list of the errors encountered during parsing
    <code file name>.symbols
        - Contains a printout of the symbol tables

        
There are 3 important test suites (which can be run as JUnit Tests):

/COMP-442-1-lexical-analyzer/src/comp442/test/semantic/SymbolCreationTestSuite.java
    - Tests over 5000 permutations of variables and functions to ensure that all possible scenarios work properly
/COMP-442-1-lexical-analyzer/src/comp442/test/syntactical/parser/CorrectCode.java
    - Tests that various sample codes (which are syntactically and semantically correct) are parsed without error
/COMP-442-1-lexical-analyzer/src/comp442/test/syntactical/parser/IncorrectCode.java
    - Tests parsing on various incorrect (semantically or syntactically) sample codes. These tests will fail, but are used to generate the error reports which are analyzed by hand

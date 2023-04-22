fo76-swf-patcher
================

This tool was created to automate the patching process for fallout76 mods after a new game patch is released.
The ASAsm reading capabilities are not exhaustive and the input is to be expected to come from RABCDAsm, 
other formats will likely not be processable.

Requirements
------------

* JDK/Java 17
* Maven
* BSA Browser
* RABCDAsm
* Fallout 76

Build
-----

`mvn package`

Usage
-----

`java -jar target/swf-patcher-1.0-SNAPSHOT.jar "config/my-config.yaml"`

Configuration
-------------

See examples in the `config` folder.
Paths to the install directories of BSA Browser, RABCDAsm, and Fallout 76 need to be set.
Patch section: Each entry defines an SWF file to be extracted from the given input BA2,
which can then be manipulated. To see how individual edits work, refer to ASASMEdits.java
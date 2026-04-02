# 'biosilico-crypto' 

## Use this module 

### Use the code

See Licence in BioSilico. 

See opart cocnerning code in some parts below. 

### Use the Jar 

Compile with Maven : "mvn clean package" on root of project (as usual). 

 ?? or soeficially with "mvn clean package -pl :biosilico-crypto -am" ?? 

```bash
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar ...
```

General arguments : 
 - (encode | decode)
 - 'content' | 'file' | 'directory'
 - 'simple' | 'more' | 'random'
 - 'fasta' | 'embl' | 'genbank' (later)
 - 'outputFILE' (optional !) (later)
 
```
Usage: biosilico-crypto [-hV] -D=<dataTotranscript> (-x | -e) (-d | -c | -f)
                        (-r | -s | -m) [-t | -a | -l | -k] [--info | --debug |
                        --warn | --none | --error]
Application CLI with picocli.
  -D, --DATA=<dataTotranscript>
                    Data to transcript (content, path to file or path to
                      directory).
  -h, --help        Show this help message and exit.
  -V, --version     Print version information and exit.
Code Method Options
  -e, --encode      Encode Command.
  -x, --decode      Decode Command.
Code Transcription Options
  -c, --content     Content (if only direct content)
  -d, --directory   Directory Path (if only direct content), all files contents
                      and pathes
  -f, --file        File Path (if only direct content), file content and path
Code Method Options
  -m, --more        More code Method.
  -r, --random      Random code Method.
  -s, --simple      Simple code Method.
Output Type Options
  -a, --fasta       FASTA Output Type.
  -k, --genbank     GENBANK Output Type.
  -l, --embl        EMBL Output Type.
  -t, --direct      Direct Output Type.
Log Level Options
      --debug       Sets log level to DEBUG.
      --error       Sets log level to ERROR.
      --info        Sets log level to INFO.
      --none        Sets log level to NONE.
      --warn        Sets log level to WARN.
```

Some examples : 

```bash
## ENCODE !!
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -c -s --DATA=toto 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/notesPicocli.txt
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -d -s --DATA=biosilico-crypto/src/test/resources/sample/
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -c -s --DATA=toto --genbank
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/notesPicocli.txt --genbank
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -d -s --DATA=biosilico-crypto/src/test/resources/sample/ --genbank
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -c -s --DATA=toto --embl
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/notesPicocli.txt --embl
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -d -s --DATA=biosilico-crypto/src/test/resources/sample/ --embl
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -c -s --DATA=toto --fasta
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/notesPicocli.txt --fasta
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -d -s --DATA=biosilico-crypto/src/test/resources/sample/ --fasta

## DECODE !!
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -c -s --DATA=":@@@@@ctcacgttctcacgtt:" 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -c -s --DATA=":@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:"

## ... 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar ... 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar ... 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar ... 
```

### Debug the Jar

```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar
```

Puis connecter avec debugger (typiquement via IDE Eclipse) : new Debug Configuration, Remote, ... et inclure les "Lookup Path..." !

## Java Code Part

### Data Part

TODO ... 

### Crypto Part

TODO ... 


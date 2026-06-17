# 'biosilico-creatures' 

## Use this module 

### Use the code

See Licence in BioSilico. 

See part concerning code in some parts below. 

### Use the Jar 

Compile with Maven : "mvn clean package" on root of project (as usual). 

 ?? or soeficially with "mvn clean package -pl :biosilico-crypto -am" ?? 

```bash
java -jar biosilico-creatures/target/biosilico-creatures-0.4.0-SNAPSHOT-jar-with-dependencies.jar...
```

General arguments : 
 - geneticfile ... 
 
```
Missing required option: '--geneticfile=<creaturesGENfile>'
Usage: biosilico-creatures [-hV] -g=<creaturesGENfile> [--warn | --trace |
                           --none | --info | --debug | --error]
Application CLI with picocli.
  -g, --geneticfile=<creaturesGENfile>
                  Creatures Genetic (.gen) file to analyse.
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Log Level Options
      --debug     Sets log level to DEBUG.
      --error     Sets log level to ERROR.
      --info      Sets log level to INFO.
      --none      Sets log level to NONE.
      --trace     Sets log level to NONE.
      --warn      Sets log level to WARN.

```

Some examples : 

```bash
java -jar biosilico-creatures/target/biosilico-creatures-0.4.0-SNAPSHOT-jar-with-dependencies.jar -g=biosilico-creatures/src/main/resources/creatures/creaturesOriginals/dad1.gen
java -jar biosilico-creatures/target/biosilico-creatures-0.4.0-SNAPSHOT-jar-with-dependencies.jar -g=biosilico-creatures/src/main/resources/creatures/creaturesOriginals/mum1.gen
java -jar biosilico-creatures/target/biosilico-creatures-0.4.0-SNAPSHOT-jar-with-dependencies.jar -g=biosilico-creatures/src/main/resources/creatures/creaturesOriginals/Gren.gen

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


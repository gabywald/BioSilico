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

Three (3) commands : 
  - encode
  - decode

General arguments : 
  - 'content'
  - 'file'
  - 'directory'
  - 'output'

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


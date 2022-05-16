---
page: https://idle.run/swagger-yaml
title: "Swagger with Split YAML Files"
tags: swagger yaml nodejs
date: 2016-02-11
---


Swagger is a great way of describing an API in YAML or JSON. From that description one can generate client or server bindings for a huge number of different languages.

Described here is an alternate structure for defining a Swagger API which splits the definition into separate files which are combined by a NodeJS script prior to processing by the Swagger Generator.

## The Problem

There are two problems I ran into when defining an API in Swagger:

1. The API must be defined in a single monolithic file
2. All paths must be defined seperately from the data-type definitions

## Solution: Split YAML Files

Split the YAML definition to a directory of files which can be combined.

### Original Structure

``` yaml
definitions:
  foo:
    type: object
    ...
  bar:
    type: object
    ...
```

### Split

#### [foo.yaml](https://github.com/idlerun/swagger-yaml/blob/master/foo.yaml)
```yaml
definitions:
  foo:
    type: object
    properties:
      fooValue:
        type: string
```

#### [bar.yaml](https://github.com/idlerun/swagger-yaml/blob/master/bar.yaml)
```yaml
definitions:
  bar:
    type: object
    properties:
      barValue:
        type: string
```

YAML is a simple key-value tree structure like JSON, so multiple trees can be combined quite easily with existing libraries.


## Combine YAML Files

NodeJS package.json. Install the dependencies with `npm install`

#### [package.json](https://github.com/idlerun/swagger-yaml/blob/master/package.json)
```json
{
  "main": "generate.js",
  "scripts": {
    "start": "node generate.js"
  },
  "dependencies": {
    "extendify": "^1.0.0",
    "glob": "^6.0.4",
    "yaml-js": "^0.1.3"
  }
}
```

Script to combine all yaml files in an src directory

#### [generate.js](https://github.com/idlerun/swagger-yaml/blob/master/generate.js)
```javascript
const fs = require('fs');
const glob = require('glob');
const YAML = require('yaml-js');
const extendify = require('extendify');

glob("src/**/*.yaml", function (er, files) {
  const contents = files.map(f => {
    return YAML.load(fs.readFileSync(f).toString());
  });
  const extend = extendify({
    inPlace: false,
    isDeep: true
  });
  const merged = contents.reduce(extend);
  console.log("Generating swagger.yaml, swagger.json");
  fs.existsSync("target") || fs.mkdirSync("target");
  fs.writeFile("target/swagger.yaml", YAML.dump(merged));
  fs.writeFile("target/swagger.json", JSON.stringify(merged, null, 2));
});
```

Create an `src` directory containing the yaml sources

#### src/info.yaml
```yaml
swagger: '2.0'
info:
  version: '1.0.0'
  title: Swagger Petstore (Simple)
...
```

Run the script
``` bash
node .
```

A combined Swagger definition will be written to `target/swagger.yaml`

#### [Full example src directory](https://github.com/idlerun/swagger-yaml/tree/master/src) (From petstore_simple)

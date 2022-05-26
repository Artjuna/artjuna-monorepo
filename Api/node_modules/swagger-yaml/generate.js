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
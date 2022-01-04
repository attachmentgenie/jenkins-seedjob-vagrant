@Grab('org.yaml:snakeyaml:1.30')
import org.yaml.snakeyaml.Yaml

folder("test")
job('test/whoami') {
  label('nomad')
  steps {
      shell('whoami')
  }
}

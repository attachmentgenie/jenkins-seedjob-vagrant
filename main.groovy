@Grab('org.yaml:snakeyaml:1.25')
@Library('attachmentgenie-lib')

import org.yaml.snakeyaml.Yaml

folder("test")

buildPlugin name: 'git'
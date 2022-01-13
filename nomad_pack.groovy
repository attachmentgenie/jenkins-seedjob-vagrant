folder("nomad-pack")
job('nomad-pack/example') {
  label('nomad')
  
  steps {
      shell('whoami')
  }
}

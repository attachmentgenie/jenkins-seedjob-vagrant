folder("test")
job('test/whoami') {
  label('nomad')
  steps {
      shell('whoami')
  }
}

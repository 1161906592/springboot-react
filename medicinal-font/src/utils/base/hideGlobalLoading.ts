export default (): void => {
  const node = document.getElementById('global-loading')
  if (node) {
    document.body.removeChild(node)
  }
}

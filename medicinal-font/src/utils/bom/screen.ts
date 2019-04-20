const requestFullscreen = (el: Element) => {
  // @ts-ignore
  (el.requestFullscreen && el.requestFullscreen()) ||
  // @ts-ignore
  (el.mozRequestFullScreen && el.mozRequestFullScreen()) ||
  // @ts-ignore
  (el.webkitRequestFullscreen && el.webkitRequestFullscreen()) ||
  // @ts-ignore
  (el.msRequestFullscreen && el.msRequestFullscreen())
}

const exitFullscreen = () => {
  (document.exitFullscreen && document.exitFullscreen()) ||
  // @ts-ignore
  (document.mozCancelFullScreen && document.mozCancelFullScreen()) ||
  // @ts-ignore
  (document.webkitExitFullscreen && document.webkitExitFullscreen()) ||
  // @ts-ignore
  (document.mozCancelFullScreen && document.mozCancelFullScreen())
}

const checkIsFullscreen = () => {
  // @ts-ignore
  return document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen
}

export {
  requestFullscreen,
  exitFullscreen,
  checkIsFullscreen
}

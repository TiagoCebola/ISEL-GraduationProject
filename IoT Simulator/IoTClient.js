const cameras = [
  {
    id: '1',
    resolution: '230x120',
    zoom: '1x',
    sensibility: 'Medium',
    angle: '0',
    status: true
  },
  {
    id: '2',
    resolution: '540x540',
    zoom: '5x',
    sensibility: 'Low',
    angle: '90',
    status: false
  },
  {
    id: '3',
    resolution: '720x720',
    zoom: '10x',
    sensibility: 'High',
    angle: '180',
    status: true
  },
  {
    id: '4',
    resolution: '1080x1080',
    zoom: '20x',
    sensibility: 'Medium',
    angle: '360',
    status: false
  }
]

module.exports = {
  getCamInfoById: getCamInfoById,
  updateCamInfoById: updateCamInfoById,
  createCamInfoEntry: createCamInfoEntry
}

function getCamInfoById(camId) {
  const camera = cameras.find(c => c.id == camId)
  if (!camera) return Promise.reject(`${camId} Not found`)

  return Promise.resolve(camera)
}

function updateCamInfoById(camId, resolution, zoom, sensibility, angle) {
  const camera = cameras.find(c => c.id == camId)

  if (!camera) return Promise.reject(`${camId} Not found`)

  if (resolution) camera.resolution = resolution
  if (zoom) camera.zoom = zoom
  if (sensibility) camera.sensibility = sensibility
  if (angle) camera.angle = angle

  console.log('UPDATED -> ', camera)
  return Promise.resolve(camera)
}

function createCamInfoEntry(camId) {
  newCamera = {
    id: camId,
    resolution: '480x480',
    zoom: '1x',
    sensibility: 'Medium',
    angle: '0',
    status: true
  }

  cameras.push(newCamera)

  return Promise.resolve(newCamera)
}

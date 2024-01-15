module.exports = function (cameraData) {
  return {
    getCamInfoById: getCamInfoById,
    updateCamInfoById: updateCamInfoById,
    createCamInfoEntry: createCamInfoEntry
  }

  //DONE
  function getCamInfoById(camId) {
    if (!camId) return Promise.reject('Invalid CamId')

    return cameraData.getCamInfoById(camId).catch(error => error)
  }

  //DONE
  function updateCamInfoById(camId, resolution, zoom, sensibility, angle) {
    if (!camId) return Promise.reject('Invalid CamId')

    return cameraData
      .updateCamInfoById(camId, resolution, zoom, sensibility, angle)
      .catch(error => error)
  }

  function createCamInfoEntry(camId) {
    return cameraData
    .createCamInfoEntry(camId)
    .catch(error => error)
  }
}

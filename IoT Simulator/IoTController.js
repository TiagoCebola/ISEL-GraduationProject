const cors = require('cors')

function promiseToResponse(resp, promise, statusCode) {
  return promise
    .then(user => {
      return resp.status(statusCode).json(user)
    })
    .catch(error => {
      const httpError = errors.convertToHttpError(error)
      resp.status(httpError.status).json(httpError.body)
    })
}

module.exports = function (router, cameraServices) {
  router.get('/caminfo/:camId', cors(), getCamInfoById)

  router.put('/caminfo/:camId', cors(), updateCamInfoById)

  router.post('/caminfo/', cors(), createCamInfoEntry)

  return router

  function getCamInfoById(req, resp) {
    const promise = cameraServices.getCamInfoById(req.params.camId)
    promiseToResponse(resp, promise, 200)
  }

  function updateCamInfoById(req, resp) {
    const promise = cameraServices.updateCamInfoById(
      req.params.camId,
      req.body.Resolution,
      req.body.Zoom,
      req.body.Sensibility,
      req.body.Angle
    )

    promiseToResponse(resp, promise, 200)
  }

  function createCamInfoEntry(req, resp) {
    const promise = cameraServices.createCamInfoEntry(req.params.camId)

    promiseToResponse(resp, promise, 201)
  }
}

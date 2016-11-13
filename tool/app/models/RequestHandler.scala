package models

import javax.inject.Inject

import play.api.Logger
import play.api.http.{DefaultHttpRequestHandler, HttpConfiguration, HttpErrorHandler, HttpFilters}
import play.api.mvc.RequestHeader
import play.api.routing.Router

class RequestHandler @Inject() (errorHandler: HttpErrorHandler,
                                configuration: HttpConfiguration,
                                filters: HttpFilters,
                                router: Router)
  extends DefaultHttpRequestHandler(router, errorHandler, configuration, filters) {

  override def routeRequest(request: RequestHeader) = {
    Logger.info(request.toString())
    super.routeRequest(request)
  }
}

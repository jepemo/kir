package com.github.jepemo.kir.web

sealed class HttpResponse (val statusCode: Int, val contentType: String? = null, val out: String? = null) {
    class Ok(out: String) : HttpResponse(200, "text/plain", out)
    class Redirect : HttpResponse(302)
    class NotModified : HttpResponse(304)
    class BadRequest : HttpResponse(400)
    class Unauthorized : HttpResponse(401)
    class Forbidden : HttpResponse(403)
    class NotFound : HttpResponse(404)
    class Error : HttpResponse(500)
    //    class RedirectToStatic : HttpResponse()
//    class RenderBinary : HttpResponse()
    class Html(out: String) : HttpResponse(200, "text/plain", out)

    class Json(out: String) : HttpResponse(200, "application/json", out)
    //    class RenderStatic: HttpResponse()
//    class RenderTemplate : HttpResponse()
    class Text(out: String) : HttpResponse(200, "text/plain", out)

    class Xml(out: String) : HttpResponse(200, "text/xml", out)
    class Status(statusCode: Int) : HttpResponse(statusCode)
//    class WebSocketResult : HttpResponse()
}
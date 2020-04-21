import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.features.callId
import io.ktor.html.respondHtml
import io.ktor.http.formUrlEncode
import io.ktor.http.formUrlEncodeTo
import io.ktor.http.push
import io.ktor.response.respondRedirect

fun hello(): String {
    return "Hello, world!"
}
fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText(hello())
            }
            get("/real time cases") {
                call.respondRedirect("https://gisanddata.maps.arcgis.com/apps/opsdashboard/index.html#/bda7594740fd40299423467b48e9ecf6")
            }

            get("/uiuc") {
                call.respondRedirect("https://covid19.illinois.edu/")
            }



            get("/add/{first}/{second}") {
                    try {
                        val first = call.parameters["first"]!!.toInt()
                        val second = call.parameters["second"]!!.toInt()
                        call.respondText((first + second).toString())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }.start(wait = true)
    }


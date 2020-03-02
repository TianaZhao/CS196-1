import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun hello(): String {
    return "Hello, world!"
}

data class CalculatorRequest(
    val operation: String, val first: Int, val second: Int
)
data class Result(val first: Int, val second: Int, val result: Int)


fun Application.adder() {
    val counts: MutableMap<String, Int> = mutableMapOf()
    install(ContentNegotiation) {
        gson { }
    }
    routing {
        get("/count/{first}") {
            val firstCount = counts.getOrDefault(call.parameters["first"],0) + 1
            counts[call.parameters["first"].toString()] = firstCount
            call.respondText(firstCount.toString())
        }

        get("/") {
            call.respondText(hello())
        }
        get("/{operation}/{first}/{second}") {
            try {
                val operation = call.parameters["operation"]!!
                val first = call.parameters["first"]!!.toInt()
                val second = call.parameters["second"]!!.toInt()
                val result = when (operation) {
                    "add" -> request.first + request.second
                    "substract" -> first - second
                    "multiply" -> first * second
                    else -> Exception("${request.operation} is not supported")
                }

                val addResult = Result(first, second, result as Int)
                call.respond(addResult)
            } catch (e: Exception) {
                println(e)
                call.respond(HttpStatusCode.BadRequest)
            }

        }

    }
    post("/calculate") {
        try {
            val request = call.receive<CalculatorRequest>()
            val result = when (request.operation) {
                "add" -> request.first + request.second
                else -> throw java.lang.Exception("${request.operation} is not supported")
            }

            val response = Result(request.operation, request.first, request.second, result)
            call.respond(response)


        } catch (e:Exception) {
            call.respond(HttpStatusCode.BadRequest)
        }

    }
}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::adder).start(wait = true)

}



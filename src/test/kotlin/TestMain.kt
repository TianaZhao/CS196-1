import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.netty.handler.codec.http.HttpHeaders.addHeader

class TestMain : StringSpec({
    "should retrieve root path properly" {
    withTestApplication(Application::adder) {
        handleRequest(HttpMethod.Get, "/").apply {

            response.status() shouldBe HttpStatusCode.OK
            response.content shouldBe "Hello,world"
        }
    }
    }


    "should count routes path properly" {
        withTestApplication(Application::adder) {
            handleRequest(HttpMethod.Get, "/count/first").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "1"
            }
            handleRequest(HttpMethod.Get, "/count/first").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "2"
            }
        }
    }
    "should calculate routes path properly" {
        withTestApplication(Application::adder) {
            handleRequest(HttpMethod.Get, "/calculate") {
                addHeader("content-type","application/json")
                setBody(""""
{
    "operation": "add"
    "first": 4
    "second": 3
}
                """".trim())
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "1"
            }.apply
                println(response.content)
                response.status shouldBe HttpStatusCode
        }
    }




})
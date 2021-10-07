import com.mifmif.common.regex.Generex
import io.javalin.Javalin

class Application {

    init {
        val genVals = ArrayList<String>()
        val app = Javalin.create().start(8081)
        app.get("/generate") { ctx ->
            run {
                genVals.add(generate())
                genVals.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it })
                genVals.sortBy { it.length }
                ctx.result(genVals.joinToString(separator = "\n"))
            }
        }
        app.get("/search") { ctx ->
            run {
                val param = ctx.queryParam("text")
                if (param !== null && isValid(param)) {
                    var possibleSolution: String? = null
                    for (value in genVals) {
                        if (possibleSolution === null) {
                            if (param.contains(value)) {
                                possibleSolution = value
                            }
                        } else {
                            if (value.contains(possibleSolution) && param.contains(value)) {
                                possibleSolution = value
                            }
                        }
                    }

                    if (possibleSolution !== null) {
                        ctx.result("Tato veta obsahuje $possibleSolution")
                    } else {
                        ctx.result("Tato veta neobsahuje zadne shody")
                    }
                } else {
                    ctx.result("Vstup neni validni!")
                }
            }
        }
    }

    fun generate(): String {
        val regex = "([abcdef]{3}\\d){1,2}"
        val generex = Generex(regex)
        return generex.random()
    }

    fun isValid(text: String): Boolean {
        val regex = "(?:[abcdef]{3}\\d){5,10}"
        return Regex(regex).containsMatchIn(text)
    }
}